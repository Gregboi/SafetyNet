package repository;

import Domain.Person;
import Domain.Index;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class PersonsRepository {

    private final Index index;

    @Autowired
    public PersonsRepository(Index index) {
       this.index = index;
    }

    public List<Person> getAllPeople(){
        return index.getPeople();
    }

    public Person findPersonByName(String firstName, String lastName) {
        return this.getAllPeople().stream()
                .filter(personInformation -> personInformation.getFirstName().equals(firstName) && personInformation.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);

    }

    //Check if a person exist and create one if they don't
    public Boolean createNewPerson(Person person){
        if (person.getFirstName() == null || person.getLastName() == null){
            return false;
        }

        List<Person> allPeople = this.getAllPeople();
        if(allPeople.stream().noneMatch(p-> p.getLastName().equals(person.getFirstName())
                        && p.getLastName().equals(person.getLastName()))) {
            allPeople.add(person);
            return true;
        }
        return false;
    }

    public Boolean deletePerson(Person person){
        if(person.getFirstName() == null || person.getLastName() == null) {
            return false; // Reject people with no first and last name
        }

        List<Person> allPeople = this.getAllPeople();
        for(Iterator<Person> iterator = allPeople.iterator(); iterator.hasNext(); ) {
            Person personInformation = iterator.next();
            if (person.getFirstName().equals(personInformation.getFirstName()) && person.getFirstName().equals(personInformation.getLastName())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }


    public Boolean updatePerson(Person person){
        if (person.getFirstName() == null || person.getLastName() == null) {
            return false; // Invalid input
        }

        List<Person> allPeople = this.getAllPeople();
        Optional<Person> matchingPerson = allPeople.stream()
                .filter(p -> person.getFirstName().equals(person.getFirstName()) && person.getLastName().equals(person.getLastName()))
                .findFirst();

        if (matchingPerson.isPresent()) {
            Person foundPerson = matchingPerson.get();
            foundPerson.setAddress(person.getAddress());
            foundPerson.setCity(person.getCity());
            foundPerson.setEmail(person.getEmail());
            foundPerson.setZip(person.getZip());
            foundPerson.setPhone(person.getPhone());
            return true;
        }
        return  false;
    }

    public List<Person> findPersonsByAddress(String address) {
        List<Person> persons = this.getAllPeople();
        List<Person> list = new ArrayList<>();
        for (Person people: persons) {
            if (people.getAddress().equals(address)){
                list.add(people);
            }
        }
        return list;
    }

}
