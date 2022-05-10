package br.com.zup.edu.livraria.livros;

import br.com.zup.edu.livraria.autores.Autor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Livro {

    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String descricao;

    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToOne
    private Autor autor;

    private LocalDate publicadoEm;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Deprecated
    public Livro(){}

    public Livro(String nome, String descricao, String isbn, Autor autor, LocalDate publicadoEm) {
        this.nome = nome;
        this.descricao = descricao;
        this.isbn = isbn;
        this.autor = autor;
        this.publicadoEm = publicadoEm;
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

    public Autor getAutor() {
        return autor;
    }

    public LocalDate getPublicadoEm() {
        return publicadoEm;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
