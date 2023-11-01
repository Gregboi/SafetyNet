package com.openclassrooms.SafetyNet;
import Controller.MedicalRecordController;
import Domain.MedicalRecords;
import Domain.dto.medical.PersonMedicalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.MedicalRecordService;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordsTest {

    @Mock
    private MedicalRecordService medicalRecordService;

    private MedicalRecordController medicalRecordController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        medicalRecordController = new MedicalRecordController(medicalRecordService);
    }

    @Test
    public void testGetPersonByName() {
        String firstName = "John";
        String lastName = "Doe";
        PersonMedicalDto personMedicalDto = new PersonMedicalDto();

        Mockito.when(medicalRecordService.getAPersonMedicalRecord(firstName, lastName)).thenReturn(personMedicalDto);

        ResponseEntity<Object> response = medicalRecordController.getPersonByName(firstName, lastName);

        assert (response.getStatusCodeValue() == HttpStatus.OK.value());
        assert (response.getBody() instanceof PersonMedicalDto);
        assert (response.getBody() == personMedicalDto);
    }

    @Test
    public void testGetPersonByNameWhenNotFound() {
        String firstName = "John";
        String lastName = "Doe";

        Mockito.when(medicalRecordService.getAPersonMedicalRecord(firstName, lastName)).thenReturn(null);

        ResponseEntity<Object> response = medicalRecordController.getPersonByName(firstName, lastName);

        assert (response.getStatusCodeValue() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testGetRecords() {
        List<MedicalRecords> records = new ArrayList<>();
        // Initialize the records list with some data

        Mockito.when(medicalRecordService.getAllRecords()).thenReturn(records);

        List<MedicalRecords> response = medicalRecordController.getRecords();

        assert (response == records);
    }

}
