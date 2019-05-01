package com.management.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.entitiy.PersonEntity;
import com.management.model.Person;
import com.management.repository.PersonRepository;
import com.management.service.PersonService;

/*
 * @author Boyko Zhelev
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public List<Person> findAll(){
        List<PersonEntity> personEntities = personRepository.findAll();
        return personEntities.stream().map(PersonEntity::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Person save(Person person) {
        PersonEntity entity =  personRepository.saveAndFlush( new PersonEntity().convertToEntity(person));
        return entity.convertToDto();
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<PersonEntity> entityOpt = personRepository.findById(id);
        if(entityOpt.isPresent()) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Long id, Person person) {
        Optional<PersonEntity> entityOpt = personRepository.findById(id);
        if(entityOpt.isPresent()){
            PersonEntity entity = entityOpt.get();

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(person,entity);

            return personRepository.saveAndFlush(entity) != null;
        }
        return false;
    }

    @Override public Person findById(final Long id) {
        Optional<PersonEntity> entityOpt = personRepository.findById(id);
        if(entityOpt.isPresent()) {
            personRepository.findById(id);
            return entityOpt.get().convertToDto();
        }
        return null;
     }
}
