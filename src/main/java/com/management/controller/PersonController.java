package com.management.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.management.assemblers.PersonResourceAssembler;
import com.management.model.Person;
import com.management.service.PersonService;

/*
 * @author Boyko Zhelev
 */
@RestController
@Path("/")
 public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonResourceAssembler personResourceAssembler;


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Resources<Resource<Person>> findAll(@Context UriInfo uriInfo) {
        List<Resource<Person>> people = personService.findAll()
                .stream()
                .map(this::initLinks)
                .collect(Collectors.toList());
        Resources<Resource<Person>> resources = new Resources<>(people);

        resources.add(JaxRsLinkBuilder.linkTo(PersonController.class)
                .withSelfRel());
        return resources;
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

    private Resource<Person> initLinks(Person person) {
        Resource<Person> resource = new Resource<>(person);
        resource.add(linkTo(methodOn(PersonController.class)
                .getPerson(person.getId()))
                .withSelfRel(), linkTo(PersonController.class)
                .withRel("all"));
        return resource;
    }
}
