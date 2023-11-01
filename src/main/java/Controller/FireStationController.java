package Controller;

import Domain.FireStation;
import Domain.dto.firestation.AddressMappingDto;
import Domain.dto.firestation.FireStationHeadCount;
import Domain.dto.medical.PersonMedicalDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FireStationService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FireStationController {
    private final FireStationService fireStationService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
       public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @RequestMapping(path="/flood/stations", method = RequestMethod.GET)
    public ResponseEntity<Object> getPersonsByStation(@RequestParam List<Integer> stations) {
        Map<String, Set<PersonMedicalDto>> listMap = fireStationService.findAllPersonByStation(stations);
        if (listMap != null) {
            logger.info("Stations " + stations + " is queried to get persons.");
            return new ResponseEntity<>(listMap, HttpStatus.OK);
        } else {
            logger.error("Failed to get persons by stations: " + stations);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path="/fire", method = RequestMethod.GET)
    public ResponseEntity<Object> getStationsByAddress(@RequestParam String address) {
        AddressMappingDto addressMappingDto = fireStationService.findStationsByAddress(address);
        if (fireStationService.findStationsByAddress(address) != null) {
            logger.info("Station address " + address + " is queried to get stations.");
            return new ResponseEntity<>(addressMappingDto, HttpStatus.OK);
        } else {
            logger.error("Failed to get stations by address: " + address);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/firestation", method = RequestMethod.GET)
    public ResponseEntity<Object> getPersonsByStationNumber(@RequestParam int stationNumber) {
        FireStationHeadCount persons = fireStationService.findPersonsByStationNumber(stationNumber);
        if (persons != null) {
            logger.info("Station number " + stationNumber + " is queried to get persons.");//Todo: log persons?
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } else {
            logger.error("Failed to get persons by station number: " + stationNumber);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/firestations")
    public List<FireStation> getAllStations() {
        return fireStationService.getAllStations();
    }

    @PostMapping(path = "/firestations")
    public ResponseEntity<Boolean> addStation(@RequestBody FireStation fireStation) {
        Boolean bool = fireStationService.addStation(fireStation);
        if (bool) {
            logger.info("FireStation " + fireStation + " is created!");
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            logger.error("FireStation " + fireStation + " failed to be created (is null or it already exists).");
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/firestations")
    public ResponseEntity<Boolean> updateStation(@RequestBody FireStation fireStation) {
        Boolean bool = fireStationService.updateStation(fireStation);
        if (bool) {
            logger.info("FireStation " + fireStation + " is updated!");
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            logger.error("FireStation " + fireStation + " failed to be updated (is the same data or doesn't exist).");
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/firestations")
    public ResponseEntity<Boolean> deleteStation(@RequestBody FireStation station) {
        Boolean bool = fireStationService.deleteStation(station);
        if (bool) {
            logger.info("Station " + station + " is deleted!");
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            logger.error("Station " + station + " failed to be deleted (is null or doesn't exist).");
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
