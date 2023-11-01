package com.openclassrooms.SafetyNet;

import Controller.MedicalRecordController;
import Controller.PersonController;
import Domain.Person;
import Domain.dto.medical.PersonMedicalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.MedicalRecordService;
import service.PersonsService;

import java.util.LinkedHashSet;


public class PersonControllerTest {
    @Mock

    private PersonsService personsService;

    private PersonController personController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        personController = new PersonController(personsService);
    }

    @Test
    public void testGetPhones() {
        int fireStation = 1;
        LinkedHashSet<String> phones = new LinkedHashSet<>();
        phones.add("123-456-7890");

        Mockito.when(personsService.getAllPhoneNumbersByStation(fireStation)).thenReturn(phones);

        ResponseEntity<Object> response = personController.getPhones(fireStation);

        // Assert that the response status is OK and contains the expected phone numbers
        assert (response.getStatusCodeValue() == HttpStatus.OK.value());
        assert (response.getBody() instanceof LinkedHashSet);
        LinkedHashSet<String> resultPhones = (LinkedHashSet<String>) response.getBody();
        assert (resultPhones.equals(phones));
    }

    @Test
    public void testGetPhonesWhenNoPhonesFound() {
        int fireStation = 1;

        Mockito.when(personsService.getAllPhoneNumbersByStation(fireStation)).thenReturn(null);

        ResponseEntity<Object> response = personController.getPhones(fireStation);

        // Assert that the response status is NOT_FOUND
        assert (response.getStatusCodeValue() == HttpStatus.NOT_FOUND.value());
    }
}
