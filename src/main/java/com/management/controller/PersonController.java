package com.management.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
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
    public Response findAll(@Context UriInfo uriInfo) {
        List<Resource<Person>> people = personService.findAll()
                .stream()
                .map(this::initLinks)
                .collect(Collectors.toList());
        Resources<Resource<Person>> resources = new Resources<>(people);

        resources.add(JaxRsLinkBuilder.linkTo(PersonController.class)
                .withSelfRel());
        return Response.ok(resources)
                .build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid @RequestBody Person person) {
        Person saved = personService.save(person);

        return Response.ok(initLinks(saved))
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, @Valid @RequestBody Person person) {

        personService.update(id,person);
        Person updated = personService.findById(id);
        Resource<Person> resource = initLinks(updated);
        return Response.ok(resource)
                .build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        personService.deleteById(id);
        return Response.ok()
                .build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Long id) {
        Person person = personService.findById(id);
        if(person !=null) {
            Resource<Person> resource = initLinks(person);
            return Response.ok(resource)
                    .build();
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
