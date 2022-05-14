package br.com.zup.edu.livraria.livros;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DetalhesDoLivroResponse {

    private Long id;
    private String nome;
    private String descricao;
    private String isbn;
    private Long autorId;
    private LocalDate publicadoEm;
    private LocalDateTime criadoEm;

    public DetalhesDoLivroResponse(Livro livro) {
        this.id = livro.getId();
        this.nome = livro.getNome();
        this.descricao = livro.getDescricao();
        this.isbn = livro.getIsbn();
        this.autorId = livro.getAutor().getId();
        this.publicadoEm = livro.getPublicadoEm();
        this.criadoEm = livro.getCriadoEm();
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
