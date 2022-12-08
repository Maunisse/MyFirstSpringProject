package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Role;
import com.example.springsecurityapplication.repositories.PersonRepository;
import com.example.springsecurityapplication.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    public Person findByLogin(Person person){
        Optional<Person> person_db = personRepository.findByLogin(person.getLogin());
        return person_db.orElse(null);
    }

    @Transactional
    public void register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
//        person.setRole("ROLE_USER");
        person.setRole_role(roleRepository.getReferenceById(2));
        personRepository.save(person);
    }
    public Person getPersonId(int id){
        Optional<Person> optionalPerson=personRepository.findById(id);
        return optionalPerson.orElse(null);
    }
    @Transactional
    public void updateRole(int id, Person person){
        person.setId(id);
        personRepository.save(person);
    }
//    @Transactional
//    public void updateRole(Role role_role){
//        roleRepository.save(role_role);
//    }



}
