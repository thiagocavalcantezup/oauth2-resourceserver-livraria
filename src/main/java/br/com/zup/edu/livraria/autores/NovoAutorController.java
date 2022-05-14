package br.com.zup.edu.livraria.autores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
public class NovoAutorController {

    @Autowired
    private AutorRepository repository;

    @Transactional
    @PostMapping("/api/autores")
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovoAutorRequest request, UriComponentsBuilder uriBuilder) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "autor com este email já existente");
        }

        Autor autor = request.toModel();
        repository.save(autor);

        URI location = uriBuilder.path("/api/autores/{id}")
                                .buildAndExpand(autor.getId())
                                .toUri();

        return ResponseEntity
                .created(location).build();
    }
}
