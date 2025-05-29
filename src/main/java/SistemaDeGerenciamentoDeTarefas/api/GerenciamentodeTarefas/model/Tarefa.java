package SistemaDeGerenciamentoDeTarefas.api.GerenciamentodeTarefas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDate; 

@Entity
@Table(name = "tarefas") 
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    @Column(name = "data_vencimento") // Nome da coluna no banco
    private LocalDate dataVencimento;

    private String status; // Ex: Pendente, Em Andamento, Concluída

    @ManyToOne // Muitas tarefas para um usuário
    @JoinColumn(name = "usuario_id", nullable = false) // Coluna da chave estrangeira
    private Usuario usuario; // Relacionamento com Usuario

    // Construtor vazio
    public Tarefa() {
    }

    public Tarefa(String titulo, String descricao, LocalDate dataVencimento, String status, Usuario usuario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.status = status;
        this.usuario = usuario;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) { // Correção de método
        this.descricao = descricao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
