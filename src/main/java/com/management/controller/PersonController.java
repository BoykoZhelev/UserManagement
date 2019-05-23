package com.management.controller;

import com.management.assemblers.PersonResourceAssembler;
import com.management.model.Person;
import com.management.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/*
 * @author Boyko Zhelev
 */
@RestController
@RequestMapping("/")
 public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonResourceAssembler personResourceAssembler;




    @GetMapping("/")
    public Resources<Resource<Person>> findAll(){

        List<Resource<Person>> people = personService.findAll().stream().map(person -> personResourceAssembler.toResource(person)).collect(Collectors.toList());

        return new Resources<>(people,linkTo(methodOn(PersonController.class).findAll()).withSelfRel());
    }

    @PostMapping("/")
    public ResponseEntity<Resource<Person>> create(@Valid @RequestBody Person person) throws URISyntaxException {
        Person saved = personService.save(person);
        Resource<Person> resource = personResourceAssembler.toResource(saved);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource<Person>> update(@PathVariable Long id ,@Valid @RequestBody Person person) throws URISyntaxException {

        personService.update(id,person);
        Person updated = personService.findById(id);
            Resource<Person> resource = personResourceAssembler.toResource(updated);
            return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> delete(@PathVariable Long id){
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public Resource<Person> getPerson(@PathVariable Long id){
        Person person = personService.findById(id);
        if(person !=null) {
             return personResourceAssembler.toResource(person);
        }
        else{
            throw new NotFoundException("Not found");
        }
    }
}
