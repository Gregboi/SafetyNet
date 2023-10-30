package Controller;

import Domain.MedicalRecords;
import Domain.dto.ChildAlertDto;
import Domain.dto.IdentityDto;
import Domain.dto.PersonMedicalDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.MedicalRecordService;

import java.util.List;

public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public MedicalRecordController(MedicalRecordService recordService) {
        this.medicalRecordService = recordService;
    }

    @GetMapping("/medicalRecords")
    public List<MedicalRecords> getRecords(){
        return medicalRecordService.getAllRecords();
    }

    @RequestMapping(path = "/personInfo", method = RequestMethod.GET)
    public ResponseEntity<Object> getPersonByName(@RequestParam String firstName, @RequestParam String lastName){
        PersonMedicalDto getPersonalRecords = (PersonMedicalDto) medicalRecordService.getAPersonMedicalRecord(firstName, lastName);
             if (getPersonalRecords != null){
                 logger.info("Query to get person with record by firstName: " + firstName + ", lastName: " + lastName);
                 return new ResponseEntity<>(getPersonalRecords, HttpStatus.OK);
             } else {
               logger.error("Failed to get person with firstName: " + firstName + ", lastName: " + lastName);
                 return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
         }
    }



    @RequestMapping(path = "/childAlert", method = RequestMethod.GET)
    public ResponseEntity<Object> getChildrenAlert(@RequestParam String address) {
        ChildAlertDto child = medicalRecordService.findChildrenByAddress(address);
        if (child != null) {
            logger.info("Query to get children alert by address: " + address);
            return new ResponseEntity<>(child, HttpStatus.OK);
        } else {
            logger.error("Failed to get children alert by address: " + address);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/medicalRecords")
    public ResponseEntity<Boolean> createRecord(@RequestBody MedicalRecords records) {
        Boolean bool = medicalRecordService.addMedicalRecords(records);
        if (bool) {
            logger.info("Record " + records + " is created!");
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            logger.error("Failed to create record: " + records);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping( "/medicalRecords")
    public ResponseEntity<Boolean> updateRecord(@RequestBody MedicalRecords records) {
        Boolean bool = medicalRecordService.updateMedicalRecord(records);
        if (bool) {
            logger.info("Record " + records + " is updated!");
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            logger.error("Failed to update record: " + records);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping( "/medicalRecords")
    public ResponseEntity<Boolean> deleteRecord(@RequestBody MedicalRecords records) {
        Boolean bool = medicalRecordService.deleteMedicalRecords(records);
        if (bool) {
            logger.info("Records " + records + " is deleted!");
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            logger.error("Failed to delete record: " + records);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
