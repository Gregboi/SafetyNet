package repository;

import Domain.MedicalRecords;
import Domain.Index;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MedicalRecordRepository {

    Index source;

    @Autowired
    public MedicalRecordRepository(Index source) {this.source = source;}

    public List<MedicalRecords> getAllRecords() {return source.getMedicalRecords();}

    public MedicalRecords findRecordsByPerson(String firstName, String lastName){
        for (MedicalRecords medicalRecords: this.getAllRecords()) {
            if (medicalRecords.getFirstName().equals(firstName) && medicalRecords.getLastName().equals(lastName)){
                return medicalRecords;
            }
        }
        return null;
    }

    public int getAgeByName(String firstName, String lastName){
        MedicalRecords recordsByName = findRecordsByPerson(firstName, lastName);
        Date birthDays = recordsByName.getBirthday();
        return this.getAge(birthDays);
    }

    public int getAge(Date birthDay) {
        LocalDate birthday = birthDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthday, currentDate).getYears();
    }

    public boolean createNewRecord (MedicalRecords record) {
        if (record.getFirstName() == null || record.getLastName() == null) {
            return false;
        }

        List<MedicalRecords> allMedicalRecords = this.getAllRecords();
        for (MedicalRecords eachRecord : allMedicalRecords) {
            if (record.getFirstName().equals(eachRecord.getFirstName()) && record.getLastName().equals(eachRecord.getLastName())) {
                return false;
            }
        }

        allMedicalRecords.add(record);
        return true;
    }

    //Update a record
    //add an update to all the fields
    public boolean updateMedicalRecords (MedicalRecords records){
        if (records.getFirstName() == null || records.getLastName() == null){
            return false;
        }

        List<MedicalRecords> getAllMedicalRecords = this.getAllRecords();
        for (MedicalRecords eachRecord: getAllMedicalRecords){
            if (records.getFirstName().equals(eachRecord.getFirstName()) && records.getFirstName().equals(eachRecord.getLastName())){
                eachRecord.setMedications(records.getMedications());
                eachRecord.setAllergies(records.getAllergies());
                return true;
            }
        }
        return false;
    }

    //Delete a record
    public boolean deleteMedicalRecords(MedicalRecords records){
        if (records.getFirstName() == null || records.getLastName() == null) {
            return false;
        }

        List<MedicalRecords> allRecords = this.getAllRecords();
        Iterator<MedicalRecords> iterator = allRecords.iterator();
        while (iterator.hasNext()){
            MedicalRecords medicalRecords = iterator.next();
            if (medicalRecords.getFirstName().equals(records.getFirstName()) && medicalRecords.getLastName().equals(records.getLastName())){
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
