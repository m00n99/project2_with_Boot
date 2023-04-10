package ru.zakirov.springcourse.Project2Boot.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.springcourse.Project2Boot.models.Person;
import ru.zakirov.springcourse.Project2Boot.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    @Transactional
    public void save(Person newPerson){
        peopleRepository.save(newPerson);
    }

    public Person findOne(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public Optional<Person> findByFio(String fio){
        return peopleRepository.findByFio(fio);
    }
}
