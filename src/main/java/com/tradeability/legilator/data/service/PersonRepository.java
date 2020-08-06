package com.tradeability.legilator.data.service;

import com.tradeability.legilator.data.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}