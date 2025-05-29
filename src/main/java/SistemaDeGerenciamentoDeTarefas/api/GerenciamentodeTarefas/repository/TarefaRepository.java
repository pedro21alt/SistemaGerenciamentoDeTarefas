package SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.repository; 

import SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.model.Tarefa; 
import SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.model.Usuario; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByUsuario(Usuario usuario);
    List<Tarefa> findByUsuarioAndStatus(Usuario usuario, String status);
}
