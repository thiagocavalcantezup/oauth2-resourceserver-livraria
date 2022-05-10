package br.com.zup.edu.livraria.autores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DetalhaAutorController {

    @Autowired
    private AutorRepository repository;

    @Transactional
    @GetMapping("/api/autores/{id}")
    public ResponseEntity<?> detalha(@PathVariable Long id) {

        Autor autor = repository.findById(id).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "autor não encontrado");
        });

        return ResponseEntity
                .ok(new DetalhesDoAutorResponse(autor));
    }
}
