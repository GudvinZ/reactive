package com.example.reactive.repository;

import com.example.reactive.dto.PersonCreateDto;
import com.example.reactive.dto.PersonDto;
import com.example.reactive.dto.PersonUpdateDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.reactive.db.Tables.PERSON;

@RequiredArgsConstructor
@Repository
public class PersonRepository {
    private final DSLContext dslContext;

    public Mono<PersonDto> createPerson(PersonCreateDto personCreateDto) {
        return Mono.from(
                        dslContext.insertInto(PERSON)
                                .columns(PERSON.FIRSTNAME)
                                .values(personCreateDto.getFirstname())
                                .returningResult(PERSON)
                )
                .map(record -> record.into(PersonDto.class));
    }

    public Flux<PersonDto> findPersons() {
        return Flux.from(
                        dslContext.select(PERSON.fields())
                                .from(PERSON)
                                .orderBy(PERSON.ID)
                )
                .map(record -> record.into(PersonDto.class));
    }

    public Mono<PersonDto> findPersonById(Long personId) {
        return Mono.from(
                        dslContext.select(PERSON.fields())
                                .from(PERSON)
                                .where(PERSON.ID.eq(personId))
                )
                .map(record -> record.into(PersonDto.class));
    }

    public Mono<PersonDto> updatePerson(PersonUpdateDto personUpdateDto) {
        return Mono.from(
                        dslContext.update(PERSON)
                                .set(PERSON.FIRSTNAME, personUpdateDto.getFirstname())
                                .where(PERSON.ID.eq(personUpdateDto.getId()))
                                .returningResult(PERSON)
                )
                .map(record -> record.into(PersonDto.class));
    }

    public Mono<Void> deletePerson(Long personId) {
        return Mono.from(
                        dslContext.delete(PERSON)
                                .where(PERSON.ID.eq(personId))
                                .returningResult()
                )
                .then();
    }
}
