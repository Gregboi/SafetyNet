package Domain.dto.firestation;

import Domain.dto.medical.PersonMedicalDto;

public class FireStationDto {
    private String address;
    private PersonMedicalDto personMedicalDto;

    public FireStationDto() {
    }

    public FireStationDto(String address, PersonMedicalDto personMedicalDto) {
        this.address = address;
        this.personMedicalDto = personMedicalDto;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PersonMedicalDto getPersonWithRecord() {
        return personMedicalDto;
    }

    public void setPersonWithRecord(PersonMedicalDto personWithRecord) {
        this.personMedicalDto = personMedicalDto;
    }
}
