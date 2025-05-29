package SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.controller;

import SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.model.Usuario;
import SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // AJUSTADO: Removido .html para Thymeleaf
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha,
                        HttpSession session, RedirectAttributes redirectAttributes) {
        // System.out.println("DEBUG LOGIN: Tentativa de login. Email digitado: '" + email + "', Senha digitada: '" + senha + "'"); // Removido DEBUG
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            // System.out.println("DEBUG LOGIN: Usuario encontrado no DB. Email DB: '" + usuario.getEmail() + "', Senha DB: '" + usuario.getSenha() + "'"); // Removido DEBUG
            // System.out.println("DEBUG LOGIN: Senhas correspondem. Login bem-sucedido."); // Removido DEBUG
            session.setAttribute("usuarioLogadoId", usuario.getId());
            session.setAttribute("usuarioLogadoEmail", usuario.getEmail());
            return "redirect:/dashboard";
        } else {
            // System.out.println("DEBUG LOGIN: Usuario NÃO encontrado no DB ou senhas não correspondem."); // Removido DEBUG
            redirectAttributes.addFlashAttribute("erroLogin", "Email ou senha inválidos.");
            return "redirect:/login";
        }
    }

    @GetMapping("/cadastro")
    public String showCadastroForm() {
        return "cadastro"; // AJUSTADO: Removido .html para Thymeleaf
    }

    @PostMapping("/cadastro")
    public String cadastro(@RequestParam String email, @RequestParam String senha,
                           @RequestParam String confirmarSenha, RedirectAttributes redirectAttributes) {
        if (!senha.equals(confirmarSenha)) {
            redirectAttributes.addFlashAttribute("erroCadastro", "As senhas não coincidem.");
            return "redirect:/cadastro";
        }
        if (usuarioRepository.findByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("erroCadastro", "Este email já está cadastrado.");
            return "redirect:/cadastro";
        }

        // System.out.println("DEBUG CADASTRO: Tentando cadastrar. Email: '" + email + "', Senha: '" + senha + "'"); // Removido DEBUG
        Usuario novoUsuario = new Usuario(email, senha);
        try {
            usuarioRepository.save(novoUsuario);
            // System.out.println("DEBUG CADASTRO: Usuario salvo com ID: " + novoUsuario.getId()); // Removido DEBUG
            redirectAttributes.addFlashAttribute("sucessoCadastro", "Cadastro realizado com sucesso! Faça login.");
            return "redirect:/login";
        } catch (Exception e) {
            // System.err.println("DEBUG CADASTRO: ERRO ao salvar usuario! " + e.getMessage()); // Removido DEBUG
            // e.printStackTrace(); // Removido DEBUG
            redirectAttributes.addFlashAttribute("erroCadastro", "Erro interno ao realizar cadastro.");
            return "redirect:/cadastro";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}