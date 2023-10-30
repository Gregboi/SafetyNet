package Controller;

import Domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PersonsService;

import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonsService personsService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public PersonController(PersonsService personsService){
        this.personsService = personsService;
    }

    @GetMapping(path = "/phoneAlert")
    public ResponseEntity<Object> getPhones(@RequestParam int fireStation) {
        LinkedHashSet<String> phones = personsService.getAllPhoneNumbersByStation(fireStation);

        if (phones != null) {
            logger.info("Fire Station {} is queried to get phone.", fireStation);
            return ResponseEntity.ok(phones);
        } else {
            logger.error("Failed to get phones by fire station: {}", fireStation);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EmptyJsonResponse());
        }
    }

    @RequestMapping(path = "/publicEmail")
    public ResponseEntity<Object> getAllEmailsInCity(@RequestParam String city) {
        LinkedHashSet<String> emails = personsService.getAllEmailsByCity(city);

        if (emails != null){
            logger.info("City: {} gets all emails", city);
            return ResponseEntity.ok(emails);
        } else {
            logger.error("Failed to obtain email by city: {}", city);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EmptyJsonResponse());
        }
    }

    @GetMapping(path = "/persons")
    public List<Person> getPeople(){return personsService.getAllPeople(); }

    @PostMapping("/persons")
    public ResponseEntity<Object> createPerson (@RequestParam Person person){
        Boolean determineIfPersonExist = personsService.createNewPerson(person);
        if(determineIfPersonExist) {
            logger.info("Person {} is created", person);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }
        else {
            logger.error("Failed to create {} person", person);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/persons")
    public ResponseEntity<Object> updatePerson(@RequestParam Person person) {
        Boolean updatePerson = personsService.updatePerson(person);
        if (updatePerson){
            logger.info("Person {} is updated!", person);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }
        else {
            logger.error("Failed to update Person {}", person);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/persons")
    public ResponseEntity<Object> deletePerson(@RequestParam Person person){
        Boolean deletePerson = personsService.DeleteAPersonFromSource(person);
        if (deletePerson){
            logger.info("Person is {} deleted", person);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            logger.error("Failed to delete Person {}", person);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

}
