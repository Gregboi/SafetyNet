package Domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class MedicalRecords {
    private String firstName;
    private String lastName;
    private List<String> medications;
    private List<String> allergies;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date birthday;

    public MedicalRecords() {
    }

    public MedicalRecords(String firstName, String lastName, Date birthday, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = new Date(birthday.getTime());
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return new Date(birthday.getTime());
    }

    public void setBirthday(Date birthday) {
        this.birthday = new Date(birthday.getTime());
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
