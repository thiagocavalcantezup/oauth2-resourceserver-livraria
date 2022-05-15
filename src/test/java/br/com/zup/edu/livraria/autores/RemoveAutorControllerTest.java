package br.com.zup.edu.livraria.autores;

import base.SpringBootIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RemoveAutorControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private AutorRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void deveRemoverAutorExistente() throws Exception {
        // cenário
        Autor autor = new Autor("Rafael","rafael.ponte@zup.com.br", "dev cansado");
        repository.save(autor);

        // ação
        mockMvc.perform(DELETE("/api/autores/{id}", autor.getId())
                        .with(jwt()
                            .authorities(new SimpleGrantedAuthority("SCOPE_autores:write"))
                        ))
                .andExpect(status().isNoContent())
        ;

        // validação
        assertEquals(0, repository.count(), "total de autores");
    }

    @Test
    public void naoDeveRemoverAutor_quandoNaoEncontrado() throws Exception {
        // cenário
        Autor autor = new Autor("Rafael","rafael.ponte@zup.com.br", "dev cansado");
        repository.save(autor);

        // ação
        mockMvc.perform(DELETE("/api/autores/{id}", -99999)
                        .with(jwt()
                            .authorities(new SimpleGrantedAuthority("SCOPE_autores:write"))
                        ))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("autor não encontrado"))
        ;

        // validação
        assertEquals(1, repository.count(), "total de autores");
    }

    @Test
    public void deveRemoverAutorExistente_quandoAccessTokenNaoEnviado() throws Exception {
        // cenário
        Autor autor = new Autor("Rafael","rafael.ponte@zup.com.br", "dev cansado");
        repository.save(autor);

        // ação
        mockMvc.perform(DELETE("/api/autores/{id}", autor.getId()))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void deveRemoverAutorExistente_quandoAccessTokenNaoPossuiScopeApropriado() throws Exception {
        // cenário
        Autor autor = new Autor("Rafael","rafael.ponte@zup.com.br", "dev cansado");
        repository.save(autor);

        // ação
        mockMvc.perform(DELETE("/api/autores/{id}", autor.getId())
                        .with(jwt()))
                .andExpect(status().isForbidden())
        ;
    }

}