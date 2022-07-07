package br.com.zup.edu.livraria.livros;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import base.SpringBootIntegrationTest;
import br.com.zup.edu.livraria.autores.Autor;
import br.com.zup.edu.livraria.autores.AutorRepository;

class DetalhaLivroControllerTest extends SpringBootIntegrationTest {

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
    public void deveDetalharLivroExistente() throws Exception {

        // cenário
        Livro existente = new Livro(
            "Spring Security", "Sobre Spring Security", "978-0-4703-2225-3", AUTOR, LocalDate.now()
        );
        repository.save(existente);

        // ação e validação
        mockMvc.perform(
            GET("/api/livros/{id}", existente.getId()).with(
                jwt().authorities(new SimpleGrantedAuthority("SCOPE_livros:read"))
            )
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(existente.getId()))
               .andExpect(jsonPath("$.nome").value(existente.getNome()))
               .andExpect(jsonPath("$.isbn").value(existente.getIsbn()))
               .andExpect(jsonPath("$.descricao").value(existente.getDescricao()))
               .andExpect(jsonPath("$.autorId").value(AUTOR.getId()))
               .andExpect(jsonPath("$.criadoEm").exists())
               .andExpect(jsonPath("$.publicadoEm").exists());
    }

    @Test
    public void naoDeveDetalharLivro_quandoNaoEncontrado() throws Exception {
        // cenário
        Livro existente = new Livro(
            "Spring Security", "Sobre Spring Security", "978-0-4703-2225-3", AUTOR, LocalDate.now()
        );
        repository.save(existente);

        // ação e validação
        mockMvc.perform(
            GET("/api/livros/{id}", -99999).with(
                jwt().authorities(new SimpleGrantedAuthority("SCOPE_livros:read"))
            )
        ).andExpect(status().isNotFound()).andExpect(status().reason("livro não encontrado"));
    }

    @Test
    public void naoDeveDetalharLivroExistente_quandoTokenNaoEnviado() throws Exception {

        // cenário
        Livro existente = new Livro(
            "Spring Security", "Sobre Spring Security", "978-0-4703-2225-3", AUTOR, LocalDate.now()
        );
        repository.save(existente);

        // ação e validação
        mockMvc.perform(GET("/api/livros/{id}", existente.getId()))
               .andExpect(status().isUnauthorized());
    }

    @Test
    public void naoDeveDetalharLivroExistente_quandoTokenNaoPossuiEscopoApropriado() throws Exception {

        // cenário
        Livro existente = new Livro(
            "Spring Security", "Sobre Spring Security", "978-0-4703-2225-3", AUTOR, LocalDate.now()
        );
        repository.save(existente);

        // ação e validação
        mockMvc.perform(GET("/api/livros/{id}", existente.getId()).with(jwt()))
               .andExpect(status().isForbidden());
    }

}
