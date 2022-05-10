package br.com.zup.edu.livraria.autores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class NovoAutorController {

    @Autowired
    private AutorRepository repository;

    @Transactional
    @PostMapping("/api/autores")
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovoAutorRequest request, UriComponentsBuilder uriBuilder) {

        Autor autor = request.toModel();
        repository.save(autor);

        URI location = uriBuilder.path("/api/autores/{id}")
                                .buildAndExpand(autor.getId())
                                .toUri();

        return ResponseEntity
                .created(location).build();
    }
}
