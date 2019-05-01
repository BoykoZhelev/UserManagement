package com.management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.management.model.Person;
import com.management.service.PersonService;

/*
 * @author Boyko Zhelev
 */
@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    PersonService personService;

    @GetMapping("/all")
    public ResponseEntity<List<Person>> findAll(){
        HttpHeaders headers = getHttpHeaders();

        List<Person> people = personService.findAll();
        if(people==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(people,headers,HttpStatus.OK);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        return headers;
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create")
    public ResponseEntity<Person> create(@RequestBody Person person){
        Person created = personService.save(person);
        return new ResponseEntity<>(created,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/update/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id ,@RequestBody Person person){

        boolean ok =personService.update(id,person);
        return new ResponseEntity<>(ok ?HttpStatus.OK: HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Person> delete(@PathVariable Long id){
        boolean ok =personService.deleteById(id);
        return new ResponseEntity<>(ok ?HttpStatus.OK: HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id){
        if(personService.findById(id)!=null) {
            HttpHeaders headers = getHttpHeaders();
            return new ResponseEntity<>(personService.findById(id),headers,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
