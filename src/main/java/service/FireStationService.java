package service;

import Domain.FireStation;
import Domain.Index;
import Domain.MedicalRecords;
import Domain.Person;
import Domain.dto.FireStationDto;
import Domain.dto.PersonMedicalDto;
import org.springframework.beans.factory.annotation.Autowired;
import repository.FireStationRepository;
import repository.MedicalRecordRepository;
import repository.PersonsRepository;

import java.util.*;
import java.util.stream.Collectors;


public class FireStationService {

    FireStationRepository fireStationRepository;
    MedicalRecordRepository medicalRecordRepository;
    PersonsRepository personsRepository;

    @Autowired
    public FireStationService(PersonsRepository personsRepository, MedicalRecordRepository medicalRecordRepository, FireStationRepository fireStationRepository) {
        this.personsRepository = personsRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.fireStationRepository = fireStationRepository;
    }

    /**Return a list of phone numbers of each person within the fire station jurisdiction.
     *We use this to send emergency text messages
     */



    /** Function to return a list of all households in each fire station jurisdiction.
     * The list needs to group people by household address, name, phone number, age of each person, medication, allergeries.
     */


}
