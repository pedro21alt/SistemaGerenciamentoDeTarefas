package SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.controller;

import SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.model.Tarefa;
import SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.model.Usuario;
import SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.repository.TarefaRepository;
import SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Long usuarioId = (Long) session.getAttribute("usuarioLogadoId");
        if (usuarioId == null) {
            redirectAttributes.addFlashAttribute("erro", "Você precisa estar logado para acessar o dashboard.");
            return "redirect:/login";
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        List<Tarefa> tarefas = tarefaRepository.findByUsuario(usuario);
        model.addAttribute("tarefas", tarefas);
        model.addAttribute("usuarioEmail", usuario.getEmail());
        return "dashboard"; // AJUSTADO: Removido .html para Thymeleaf
    }

    @GetMapping("/nova-tarefa")
    public String showTarefaForm(@RequestParam(required = false) Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Long usuarioId = (Long) session.getAttribute("usuarioLogadoId");
        if (usuarioId == null) {
            redirectAttributes.addFlashAttribute("erro", "Você precisa estar logado para adicionar tarefas.");
            return "redirect:/login";
        }

        Tarefa tarefa = new Tarefa();
        if (id != null) {
            Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
            if (tarefaOptional.isPresent()) {
                tarefa = tarefaOptional.get();
                if (!tarefa.getUsuario().getId().equals(usuarioId)) {
                    redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para editar esta tarefa.");
                    return "redirect:/dashboard";
                }
            } else {
                redirectAttributes.addFlashAttribute("erro", "Tarefa não encontrada.");
                return "redirect:/dashboard";
            }
        }
        model.addAttribute("tarefa", tarefa);
        return "nova-tarefa"; // AJUSTADO: Removido .html para Thymeleaf
    }

    @PostMapping("/salvar-tarefa")
    public String salvarTarefa(@RequestParam(required = false) Long id,
                             @RequestParam String titulo,
                             @RequestParam String descricao,
                             @RequestParam LocalDate dataVencimento,
                             @RequestParam String status,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        Long usuarioId = (Long) session.getAttribute("usuarioLogadoId");
        if (usuarioId == null) {
            redirectAttributes.addFlashAttribute("erro", "Você precisa estar logado para salvar tarefas.");
            return "redirect:/login";
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Tarefa tarefa;
        if (id == null) {
            tarefa = new Tarefa();
            tarefa.setUsuario(usuario);
        } else {
            tarefa = tarefaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));
            if (!tarefa.getUsuario().getId().equals(usuarioId)) {
                redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para editar esta tarefa.");
                return "redirect:/dashboard";
            }
        }
        tarefa.setTitulo(titulo);
        tarefa.setDescricao(descricao);
        tarefa.setDataVencimento(dataVencimento);
        tarefa.setStatus(status != null && !status.isEmpty() ? status : "Pendente");

        tarefaRepository.save(tarefa);
        redirectAttributes.addFlashAttribute("sucesso", "Tarefa salva com sucesso!");
        return "redirect:/dashboard";
    }

    @GetMapping("/deletar-tarefa/{id}")
    public String deletarTarefa(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Long usuarioId = (Long) session.getAttribute("usuarioLogadoId");
        if (usuarioId == null) {
            redirectAttributes.addFlashAttribute("erro", "Você precisa estar logado para deletar tarefas.");
            return "redirect:/login";
        }

        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        if (tarefaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();
            if (!tarefa.getUsuario().getId().equals(usuarioId)) {
                redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para deletar esta tarefa.");
                return "redirect:/dashboard";
            }
            tarefaRepository.delete(tarefa);
            redirectAttributes.addFlashAttribute("sucesso", "Tarefa deletada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Tarefa não encontrada.");
        }
        return "redirect:/dashboard";
    }
}