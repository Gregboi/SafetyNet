package com.openclassrooms.SafetyNet;

import Controller.PersonController;
import Domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.PersonsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonControllerTest {
    private PersonController personController;
    private PersonsService personsService;

    @BeforeEach
    void setUp() {
        // Initialize your controller and mock the service
        personsService = mock(PersonsService.class);
        personController = new PersonController(personsService);
    }

    @Test
    void testCreatePersonSuccess() {
        // Arrange
        Person person = new Person(); // Create a Person instance for the test
        when(personsService.createNewPerson(person)).thenReturn(true);

        // Act
        ResponseEntity<Object> response = personController.createPerson(person);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testCreatePersonFailure() {
        // Arrange
        Person person = new Person(); // Create a Person instance for the test
        when(personsService.createNewPerson(person)).thenReturn(false);

        // Act
        ResponseEntity<Object> response = personController.createPerson(person);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse((Boolean) response.getBody());
    }
}
