package service;

import Domain.FireStation;
import Domain.Person;
import repository.FireStationRepository;
import repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PersonsService {

    PersonsRepository personsRepository;
    FireStationRepository fireStationRepository;

    @Autowired
    public PersonsService (PersonsRepository personsRepository, FireStationRepository fireStationRepository) {
        this.personsRepository = personsRepository;
        this.fireStationRepository =  fireStationRepository;
    }

    //Call the get fireStation repo to get the complete list of stations
    public List<Person> getAllPeople() {return personsRepository.getAllPeople();}
    public List<FireStation> getAllFireStations() {return fireStationRepository.getAllStations();}

    public LinkedHashSet<String> getAllEmailsByCity(String city){
        return this.getAllPeople().stream()
                .filter(personInformation -> personInformation.getCity().equals(city))
                .map(Person:: getEmail)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LinkedHashSet<String> getAllPhoneNumbersByStation(int fireStation){
        LinkedHashSet<String> phones = new LinkedHashSet<>();
        List<String> addresses = new ArrayList<>();
        List<FireStation> allStations = this.getAllFireStations();
        List<Person> allPeople = this.getAllPeople();

        for (FireStation aFireStation: allStations) {
            if (aFireStation.getFireStation() == fireStation){
                addresses.add(aFireStation.getAddress());
            }
        }
        if (addresses.isEmpty()) return null;

        for (String address : addresses){
            for (Person person : allPeople) {
                if (person.getAddress().equals(address)){
                    phones.add(person.getPhone());
                }
            }
        }

        return phones;
    }


    public Boolean createNewPerson(Person person) {
        return personsRepository.createNewPerson(person);
    }

    public Boolean updatePerson(Person person) {
        return personsRepository.updatePerson(person);
    }

    public Boolean DeleteAPersonFromSource(Person person) {
        return personsRepository.deletePerson(person);
    }
}