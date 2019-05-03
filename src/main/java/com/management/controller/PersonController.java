package com.management.controller;

import com.management.model.Person;
import com.management.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/*
 * @author Boyko Zhelev
 */
@CrossOrigin
@RestController
@RequestMapping("/")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/all")
    public ResponseEntity<List<Person>> findAll(){

        List<Person> people = personService.findAll();
        if(people==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(people,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Person> create(@Valid @RequestBody Person person){

        Person created = personService.save(person);
        return new ResponseEntity<>(created,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id ,@Valid @RequestBody Person person){

        boolean ok =personService.update(id,person);
        return new ResponseEntity<>(ok ?HttpStatus.OK: HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Person> delete(@PathVariable Long id){
        boolean ok =personService.deleteById(id);
        return new ResponseEntity<>(ok ?HttpStatus.OK: HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id){
        if(personService.findById(id)!=null) {
             return new ResponseEntity<>(personService.findById(id),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
