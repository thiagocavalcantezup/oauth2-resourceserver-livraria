package br.com.zup.edu.livraria.livros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DetalhaLivroController {

    @Autowired
    private LivroRepository repository;

    @Transactional
    @GetMapping("/api/livros/{id}")
    public ResponseEntity<?> detalha(@PathVariable Long id) {

        Livro livro = repository.findById(id).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "livro não encontrado");
        });

        return ResponseEntity
                .ok(new DetalhesDoLivroResponse(livro));
    }
}
