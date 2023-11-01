package service;

import Domain.FireStation;
import Domain.MedicalRecords;
import Domain.Person;
import Domain.dto.firestation.AddressMappingDto;
import Domain.dto.firestation.FireStationDto;
import Domain.dto.firestation.FireStationHeadCount;
import Domain.dto.medical.PersonMedicalDto;
import Domain.dto.person.PersonBasicContactInfo;
import org.springframework.beans.factory.annotation.Autowired;
import repository.FireStationRepository;
import repository.MedicalRecordRepository;
import repository.PersonsRepository;

import java.util.*;


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

    public Map<String, Set<PersonMedicalDto>> findAllPersonByStation(List<Integer> stations) {
        Map<String, Set<PersonMedicalDto>> map;
        List<FireStationDto> list = new ArrayList<>();
        Set<FireStation> stationsByNumber;
        Set<String> allAddresses;
        Set<String> groupAddress = new HashSet<>();

        for (Integer station : stations) {

            stationsByNumber = fireStationRepository.getStationsByNumber(station);

            allAddresses = fireStationRepository.findAllAddresses(stationsByNumber);
            groupAddress.addAll(allAddresses);
        }

        List<Person> allPersons = personsRepository.getAllPeople();
        for (String address : groupAddress) {
            for (Person person : allPersons) {
                if (person.getAddress().equals(address)) {
                    String firstName = person.getFirstName();
                    String lastName = person.getLastName();
                    String phone = person.getPhone();
                    MedicalRecords medicalRecordByName = medicalRecordRepository.findRecordsByPerson(firstName, lastName);
                    int age = fireStationRepository.getAge(medicalRecordByName.getBirthday());
                    List<String> medications = medicalRecordByName.getMedications();
                    List<String> allergies = medicalRecordByName.getAllergies();
                    list.add(new FireStationDto(address, new PersonMedicalDto(firstName, lastName, phone, age, medications, allergies)));
                }
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        map = getMapOfAddress (list, groupAddress);
        return map;
    }

    /**
     * Map to set all people who live in a household
     * @param list
     * @param addresses
     * @return
     */

    private static Map<String, Set<PersonMedicalDto>> getMapOfAddress(List<FireStationDto> list, Set<String> addresses) {
        Map<String, Set<PersonMedicalDto>> dtoMap = new HashMap<>();

        //1, Use each address as key:
        for (String address : addresses) {
            Set<PersonMedicalDto> persons = new HashSet<>();

            for (FireStationDto floodStationDto : list) {
                if (floodStationDto.getAddress().equals(address)) {
                    persons.add(floodStationDto.getPersonWithRecord());
                }
            }
            dtoMap.put(address, persons);
        }

        return dtoMap;
    }



    /** Function to return a list of all households in each fire station jurisdiction.
     * The list needs to group people by household address, name, phone number, age of each person, medication, allergeries.
     */

    public AddressMappingDto findStationsByAddress(String address) {
        AddressMappingDto fireStationAddressDto = new AddressMappingDto();
        List<PersonMedicalDto> list = new ArrayList<>();
        List<FireStation> stations = fireStationRepository.getFireStationsByAddress(address);
        //1, Find all station numbers for this address:
        List<Integer> numbers = new ArrayList<>();
        for (FireStation fireStation : stations) {
            numbers.add(fireStation.getFireStation());
        }
        //2, Find all person nearby:
        List<Person> persons = personsRepository.findPersonsByAddress(address);
        //3, Find the records:
        for (Person person : persons) {
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            String phone = person.getPhone();
            MedicalRecords recordByName = medicalRecordRepository.findRecordsByPerson(firstName, lastName);
            int age = medicalRecordRepository.getAge(recordByName.getBirthday());
            List<String> medications = recordByName.getMedications();
            List<String> allergies = recordByName.getAllergies();
            list.add(new PersonMedicalDto(firstName, lastName, phone, age, medications, allergies));
        }
        if (list.isEmpty()) return null;
        fireStationAddressDto.setPersonWithRecordList(list);
        fireStationAddressDto.setStationNumbers(numbers);
        return fireStationAddressDto;
    }

    public FireStationHeadCount findPersonsByStationNumber(int station) {
        FireStationHeadCount fireStationHeadCount = new FireStationHeadCount();
        Set<PersonBasicContactInfo> personList = new HashSet<>();
        int total_adult = 0;
        int total_child = 0;
        Set<FireStation> stationsByNumber = fireStationRepository.getStationsByNumber(station);
        Set<String> addresses = fireStationRepository.findAllAddresses(stationsByNumber);
        for (String address : addresses) {
            List<Person> persons = personsRepository.findPersonsByAddress(address);
            for (Person person : persons) {
                String firstName = person.getFirstName();
                String lastName = person.getLastName();
                int age = medicalRecordRepository.getAgeByName(firstName, lastName);
                if (age <= 18) {
                    total_child += 1;
                } else {
                    total_adult += 1;
                }
                personList.add(new PersonBasicContactInfo(firstName, lastName, address, person.getPhone()));
            }
        }
        if (personList.isEmpty()) {
            return null;
        }
        fireStationHeadCount.setPersonSet(personList);
        fireStationHeadCount.setTotal_adult(total_adult);
        fireStationHeadCount.setTotal_child(total_child);

        return fireStationHeadCount;
    }

    public List<FireStation> getAllStations() {
        return fireStationRepository.getAllStations();
    }

    public Boolean addStation(FireStation fireStation) {
        return fireStationRepository.createNewStation(fireStation);
    }

    public Boolean updateStation(FireStation fireStation) {
        return fireStationRepository.updateFireStation(fireStation);
    }

    public Boolean deleteStation(FireStation fireStation) {
        return fireStationRepository.deleteFireStation(fireStation);
    }
}
