package Domain.dto.firestation;

import Domain.Person;
import Domain.dto.person.PersonBasicContactInfo;

import java.util.Set;

//Coverage DTO
public class FireStationHeadCount {
    private Set<PersonBasicContactInfo> personSet;
    private int total_adult;
    private int total_child;

    public FireStationHeadCount() {
    }

    public FireStationHeadCount(Set<PersonBasicContactInfo> personSet, int total_adult, int total_child) {
        this.personSet = personSet;
        this.total_adult = total_adult;
        this.total_child = total_child;
    }

    public Set<PersonBasicContactInfo> getPersonSet() {
        return personSet;
    }

    public void setPersonSet(Set<PersonBasicContactInfo> personSet) {
        this.personSet = personSet;
    }

    public int getTotal_adult() {
        return total_adult;
    }

    public void setTotal_adult(int total_adult) {
        this.total_adult = total_adult;
    }

    public int getTotal_child() {
        return total_child;
    }

    public void setTotal_child(int total_child) {
        this.total_child = total_child;
    }
}
