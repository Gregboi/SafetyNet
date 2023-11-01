package Domain.dto.person;

import Domain.Person;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChildAlertDto {
    private List<AgeDto> children;
    private List<Person> familyMembers;

    public ChildAlertDto() {
    }

    public ChildAlertDto(List<AgeDto> children, List<Person> familyMembers) {
        this.children = children;
        this.familyMembers = familyMembers;
    }

    public List<AgeDto> getChildren() {
        return children;
    }

    public void setChildren(List<AgeDto> children) {
        this.children = children;
    }

    public List<Person> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<Person> familyMembers) {
        this.familyMembers = familyMembers;
    }
}