package service;

import Domain.FireStation;
import Domain.dto.*;
import Domain.MedicalRecords;
import Domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import repository.FireStationRepository;
import repository.MedicalRecordRepository;
import repository.PersonsRepository;

import java.util.*;
import java.util.stream.Collectors;


public class MedicalRecordService {

    PersonsRepository personsRepository;
    MedicalRecordRepository medicalRecordRepository;
    FireStationRepository fireStationRepository;

    @Autowired
    public MedicalRecordService(PersonsRepository personsRepository, MedicalRecordRepository medicalRecordRepository){
        this.personsRepository = personsRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<MedicalRecords> getAllRecords(){return medicalRecordRepository.getAllRecords();}

    /**Find A person with his medical records by Name
       first Name and last Name
       @Return firstName, lastName, address, age, email and medical records
     **/

    public PersonMedicalDto getAPersonMedicalRecord(String firstName, String lastName) {
        Person personByName = personsRepository.findPersonByName(firstName, lastName);
        MedicalRecords recordByName = medicalRecordRepository.findRecordsByPerson(firstName, lastName);

        if (personByName == null || recordByName == null) {
            return null;
        }

        PersonMedicalDto personMedicalDto = createPersonMedicalDto(personByName, recordByName);
        return personMedicalDto;
    }

    private PersonMedicalDto createPersonMedicalDto(Person person, MedicalRecords records) {
        PersonMedicalDto personMedicalDto = new PersonMedicalDto();

        personMedicalDto.setFirstName(person.getFirstName());
        personMedicalDto.setLastName(person.getLastName());
        personMedicalDto.setAddress(person.getAddress());

        int age = medicalRecordRepository.getAge(records.getBirthday());
        personMedicalDto.setAge(age);

        personMedicalDto.setEmail(person.getEmail());
        personMedicalDto.setMedications(records.getMedications());
        personMedicalDto.setAllergies(records.getAllergies());

        return personMedicalDto;
    }
    

    /**
     Find all children living in the address and their family members.
     @return a list of children or an empty list
     */

    public ChildAlertDto findChildrenByAddress(String address) {
        List<Person> persons = personsRepository.findPersonsByAddress(address);

        List<AgeDto> childrenList = persons.stream()
                .filter(person -> isChild(person))
                .map(person -> new AgeDto(person.getFirstName(), person.getLastName(), getAge(person)))
                .collect(Collectors.toList());

        List<Person> familyMembers = persons.stream()
                .filter(person -> isFamilyMember(person))
                .collect(Collectors.toList());

        if (childrenList.isEmpty()) {
            return null;
        }

        ChildAlertDto childAlertDto = new ChildAlertDto();
        childAlertDto.setChildren(childrenList);
        childAlertDto.setFamilyMembers(familyMembers);
        return childAlertDto;
    }

    private boolean isChild(Person person) {
        int age = getAge(person);
        return age <= 18;
    }

    private boolean isFamilyMember(Person person) {
        int age = getAge(person);
        return age > 18 && person.getLastName().equals(person.getLastName());
    }

    private int getAge(Person person) {
        return medicalRecordRepository.getAgeByName(person.getFirstName(), person.getLastName());
    }

    public Boolean addMedicalRecords(MedicalRecords medicalRecords){return medicalRecordRepository.createNewRecord(medicalRecords);}
    public Boolean updateMedicalRecord(MedicalRecords medicalRecords){return medicalRecordRepository.updateMedicalRecords(medicalRecords);}
    public Boolean deleteMedicalRecords(MedicalRecords medicalRecords){return medicalRecordRepository.deleteMedicalRecords(medicalRecords);}

}
