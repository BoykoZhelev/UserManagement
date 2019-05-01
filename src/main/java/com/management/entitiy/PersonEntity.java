package com.management.entitiy;

/*
 * @author Boyko Zhelev
 */

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.management.model.Person;
import org.modelmapper.ModelMapper;

@Entity
@Table(name = "PERSON")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Person convertToDto(){
        Person dto = new Person();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(this,dto);
//        dto.setId(getId());
//        dto.setFirstName(getFirstName());
//        dto.setLastName(getLastName());
//        dto.setEmail(getEmail());
//        dto.setBirthDate(getBirthDate());
        return dto;
    }

    public PersonEntity convertToEntity(Person dto){
//        setId(dto.getId());
//        setFirstName(dto.getFirstName());
//        setLastName(dto.getLastName());
//        setEmail(dto.getEmail());
//        setBirthDate(dto.getBirthDate());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(dto,this);
        return this;
    }
}

