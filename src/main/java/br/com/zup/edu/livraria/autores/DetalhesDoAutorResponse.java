package br.com.zup.edu.livraria.autores;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DetalhesDoAutorResponse {

    private final Long id;
    private final String nome;
    private final String email;
    private final String descricao;
    private final LocalDateTime criadoEm;
    private final List<LivroResponse> livros;

    public DetalhesDoAutorResponse(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
        this.email = autor.getEmail();
        this.descricao = autor.getDescricao();
        this.criadoEm = autor.getCriadoEm();
        this.livros = autor.getLivros().stream().map((livro) -> {
           return new LivroResponse(livro);
        }).collect(toList());
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

    public List<LivroResponse> getLivros() {
        return livros;
    }
}
