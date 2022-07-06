package br.com.zup.edu.livraria.autores;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.zup.edu.livraria.livros.Livro;

@Entity
public class Autor {

    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String descricao;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @OneToMany(mappedBy = "autor", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Livro> livros = new ArrayList<>();

    @Deprecated
    public Autor() {}

    public Autor(String nome, String email, String descricao) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public List<Livro> getLivros() {
        return livros;
    }

}
