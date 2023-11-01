package Domain.dto.firestation;

import Domain.dto.medical.PersonMedicalDto;

import java.util.List;

public class AddressMappingDto {
    private List<PersonMedicalDto> personMedicalRecordList;
    private List<Integer> stationNumbers;

    public AddressMappingDto() {
    }

    public AddressMappingDto(List<PersonMedicalDto> personMedicalRecordList, List<Integer> stationNumbers) {
        this.personMedicalRecordList = personMedicalRecordList;
        this.stationNumbers = stationNumbers;
    }

    public List<PersonMedicalDto> getPersonWithRecordList() {
        return personMedicalRecordList;
    }

    public void setPersonWithRecordList(List<PersonMedicalDto> listOfPersonRecords) {
        this.personMedicalRecordList = listOfPersonRecords;
    }

    public List<Integer> getStationNumbers() {
        return stationNumbers;
    }

    public void setStationNumbers(List<Integer> stationNumbers) {
        this.stationNumbers = stationNumbers;
    }
}
