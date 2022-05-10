package br.com.zup.edu.livraria.autores;

import br.com.zup.edu.livraria.livros.Livro;

public class LivroResponse {

    private Long id;
    private String nome;

    public LivroResponse(Livro livro) {
        this.id = livro.getId();
        this.nome = livro.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
