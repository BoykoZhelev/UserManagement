package com.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.entitiy.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,Long> {
}
