package br.com.zup.edu.livraria.livros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import base.SpringBootIntegrationTest;
import br.com.zup.edu.livraria.autores.Autor;
import br.com.zup.edu.livraria.autores.AutorRepository;

class NovoLivroControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    private Autor AUTOR;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        autorRepository.deleteAll();
        this.AUTOR = autorRepository.save(
            new Autor("Rafael", "rafael.ponte@zup.com.br", "dev cansado")
        );
    }

    @Test
    public void deveCadastrarNovoLivro() throws Exception {
        // cenário
        NovoLivroRequest novoLivro = new NovoLivroRequest(
            "Arquitetura Java", "Sobre arquitetura java", "978-0-4703-2225-3", AUTOR.getId(),
            LocalDate.now()
        );

        // ação
        mockMvc.perform(
            POST("/api/livros", novoLivro).with(
                jwt().authorities(new SimpleGrantedAuthority("SCOPE_livros:write"))
            )
        ).andExpect(status().isCreated()).andExpect(redirectedUrlPattern("**/api/livros/*"));

        // validação
        assertEquals(1, repository.count(), "total de livros");
    }

    @Test
    public void deveCadastrarNovoLivro_quandoParametrosInvalidos() throws Exception {
        // cenário
        NovoLivroRequest livroInvalido = new NovoLivroRequest("", "", "", null, null);

        // ação
        mockMvc.perform(
            POST("/api/livros", livroInvalido).with(
                jwt().authorities(new SimpleGrantedAuthority("SCOPE_livros:write"))
            )
        ).andExpect(status().isBadRequest());

        // validação
        assertEquals(0, repository.count(), "total de livros");
    }

    @Test
    public void deveCadastrarNovoLivro_quandoLivroJaExistente() throws Exception {
        // cenário
        Livro existente = new Livro(
            "Spring Security", "Sobre Spring Security", "978-0-4703-2225-3", AUTOR, LocalDate.now()
        );
        repository.save(existente);

        NovoLivroRequest livroInvalido = new NovoLivroRequest(
            "outro nome", "outra descricao", existente.getIsbn(), AUTOR.getId(), LocalDate.now()
        );

        // ação
        mockMvc.perform(
            POST("/api/livros", livroInvalido).with(
                jwt().authorities(new SimpleGrantedAuthority("SCOPE_livros:write"))
            )
        )
               .andExpect(status().isUnprocessableEntity())
               .andExpect(status().reason("livro com este ISBN já existente"));

        // validação
        assertEquals(1, repository.count(), "total de livros");
    }

    @Test
    public void naoDeveCadastrarNovoLivro_quandoTokenNaoEnviado() throws Exception {
        // cenário
        NovoLivroRequest novoLivro = new NovoLivroRequest(
            "Arquitetura Java", "Sobre arquitetura java", "978-0-4703-2225-3", AUTOR.getId(),
            LocalDate.now()
        );

        // ação
        mockMvc.perform(POST("/api/livros", novoLivro)).andExpect(status().isUnauthorized());
    }

    @Test
    public void naoDeveCadastrarNovoLivro_quandoTokenNaoPossuiEscopoApropriado() throws Exception {
        // cenário
        NovoLivroRequest novoLivro = new NovoLivroRequest(
            "Arquitetura Java", "Sobre arquitetura java", "978-0-4703-2225-3", AUTOR.getId(),
            LocalDate.now()
        );

        // ação
        mockMvc.perform(POST("/api/livros", novoLivro).with(jwt()))
               .andExpect(status().isForbidden());
    }

}
