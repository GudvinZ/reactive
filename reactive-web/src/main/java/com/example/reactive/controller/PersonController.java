package com.example.reactive.controller;

import com.example.reactive.dto.PersonCreateDto;
import com.example.reactive.dto.PersonDto;
import com.example.reactive.dto.PersonUpdateDto;
import com.example.reactive.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {
    private final PersonRepository personRepository;

    @PostMapping
    public Mono<PersonDto> create(@RequestBody PersonCreateDto personCreateDto) {
        return personRepository.createPerson(personCreateDto);
    }

    @GetMapping
    public Flux<PersonDto> getAll() {
        return personRepository.findPersons();
    }

    @GetMapping("/{id}")
    public Mono<PersonDto> getById(@PathVariable Long id) {
        return personRepository.findPersonById(id);
    }

    @PutMapping
    public Mono<PersonDto> update(@RequestBody PersonUpdateDto personUpdateDto) {
        return personRepository.updatePerson(personUpdateDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Long id) {
        return personRepository.deletePerson(id);
    }
}
