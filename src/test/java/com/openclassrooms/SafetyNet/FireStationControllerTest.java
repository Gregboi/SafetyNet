package com.openclassrooms.SafetyNet;
import Controller.FireStationController;
import Domain.dto.firestation.AddressMappingDto;
import Domain.dto.medical.PersonMedicalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.FireStationService;

import java.util.*;

public class FireStationControllerTest {
    @Mock
    private FireStationService fireStationService;

    private FireStationController fireStationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fireStationController = new FireStationController(fireStationService);
    }

    @Test
    public void testGetPersonsByStation() {
        List<Integer> stations = new ArrayList<>();
        stations.add(1);
        Map<String, Set<PersonMedicalDto>> listMap = new LinkedHashMap<>();
        // Initialize listMap with some data

        Mockito.when(fireStationService.findAllPersonByStation(stations)).thenReturn(listMap);

        ResponseEntity<Object> response = fireStationController.getPersonsByStation(stations);

        assert (response.getStatusCodeValue() == HttpStatus.OK.value());
        assert (response.getBody() instanceof Map);
        assert (response.getBody() == listMap);
    }

    @Test
    public void testGetPersonsByStationWhenNotFound() {
        List<Integer> stations = new ArrayList<>();
        stations.add(1);

        Mockito.when(fireStationService.findAllPersonByStation(stations)).thenReturn(null);

        ResponseEntity<Object> response = fireStationController.getPersonsByStation(stations);

        assert (response.getStatusCodeValue() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testGetStationsByAddress() {
        String address = "123 Main St";
        AddressMappingDto addressMappingDto = new AddressMappingDto();
        // Initialize addressMappingDto with some data

        Mockito.when(fireStationService.findStationsByAddress(address)).thenReturn(addressMappingDto);

        ResponseEntity<Object> response = fireStationController.getStationsByAddress(address);

        assert (response.getStatusCodeValue() == HttpStatus.OK.value());
        assert (response.getBody() instanceof AddressMappingDto);
        assert (response.getBody() == addressMappingDto);
    }
}
