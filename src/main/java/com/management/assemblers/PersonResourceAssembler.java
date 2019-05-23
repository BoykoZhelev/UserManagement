package com.management.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.management.controller.PersonController;
import com.management.model.Person;

@Component
public class PersonResourceAssembler  implements ResourceAssembler<Person, Resource<Person>> {
    @Override
    public Resource<Person> toResource(Person person) {
        return new Resource<>(person,
                linkTo(methodOn(PersonController.class).getPerson(person.getId())).withSelfRel());
//                linkTo(methodOn(PersonController.class).findAll()).withRel("/"));
    }
}
