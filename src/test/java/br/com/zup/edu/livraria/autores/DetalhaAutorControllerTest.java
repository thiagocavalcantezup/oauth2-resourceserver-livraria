package br.com.zup.edu.livraria.autores;

import base.SpringBootIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DetalhaAutorControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private AutorRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void deveDetalharAutorExistente() throws Exception {
        // cenário
        Autor autor = new Autor("Rafael","rafael.ponte@zup.com.br", "dev cansado");
        repository.save(autor);

        // ação e validação
        mockMvc.perform(GET("/api/autores/{id}", autor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(autor.getId()))
                .andExpect(jsonPath("$.nome").value(autor.getNome()))
                .andExpect(jsonPath("$.email").value(autor.getEmail()))
                .andExpect(jsonPath("$.descricao").value(autor.getDescricao()))
                .andExpect(jsonPath("$.criadoEm").exists())
                .andExpect(jsonPath("$.livros").exists())
                .andExpect(jsonPath("$.livros").isEmpty())
        ;
    }

    @Test
    public void naoDeveDetalharAutor_quandoNaoEncontrado() throws Exception {
        // cenário
        Autor autor = new Autor("Rafael","rafael.ponte@zup.com.br", "dev cansado");
        repository.save(autor);

        // ação e validação
        mockMvc.perform(GET("/api/autores/{id}", -99999))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("autor não encontrado"))
        ;
    }
}