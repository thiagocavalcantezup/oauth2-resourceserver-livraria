package br.com.zup.edu.livraria.livros;

import br.com.zup.edu.livraria.autores.Autor;
import br.com.zup.edu.livraria.autores.AutorRepository;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class NovoLivroRequest {

    @NotBlank
    private String nome;

    @NotBlank
    @Size(max = 4000)
    private String descricao;

    @ISBN
    @NotBlank
    private String isbn;

    @NotNull
    private Long autorId;

    @NotNull
    private LocalDate publicadoEm;

    public NovoLivroRequest(String nome, String descricao, String isbn, Long autorId, LocalDate publicadoEm) {
        this.nome = nome;
        this.descricao = descricao;
        this.isbn = isbn;
        this.autorId = autorId;
        this.publicadoEm = publicadoEm;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getIsbn() {
        return isbn;
    }

    public Long getAutorId() {
        return autorId;
    }

    public LocalDate getPublicadoEm() {
        return publicadoEm;
    }

    public Livro toModel(AutorRepository todosOsAutores) {

        Autor autor = todosOsAutores.findById(autorId).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "autor não encontrado");
        });

        return new Livro(nome, descricao, isbn, autor, publicadoEm);
    }
}
