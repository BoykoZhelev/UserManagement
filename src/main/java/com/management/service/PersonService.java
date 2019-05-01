package com.management.service;

/*
 * @author Boyko Zhelev
 */

import java.util.List;
import com.management.model.Person;

public interface PersonService {

    List<Person> findAll();

    Person save(Person person);

    boolean deleteById(Long id);

    boolean update(Long id , Person person);

    Person findById(Long id);
}
