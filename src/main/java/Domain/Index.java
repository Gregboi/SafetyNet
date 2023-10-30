package Domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class Index {
    List<Person> people = new ArrayList<>();
    List<FireStation> fireStations = new ArrayList<>();
    List<MedicalRecords> medicalRecords = new ArrayList<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void sourceOfTruth() {
        try {
            var allInfo = mapper.readValue(new URL("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json"), Index.class);
            this.people = allInfo.getPeople();
            this.fireStations = allInfo.getFireStations();
            this.medicalRecords = allInfo.getMedicalRecords();

        } catch (IOException e) {
                    e.printStackTrace();
        }
    }


    public List<Person> getPeople() {return people;}

    public void setPeople(List<Person> people) {this.people = people;}

    public List<MedicalRecords> getMedicalRecords(){return medicalRecords;}

    public void setMedicalRecords(List<MedicalRecords> medicalRecords) {this.medicalRecords = medicalRecords;}

    public List<FireStation> getFireStations(){return fireStations;}

    public  void setFireStations(List<FireStation> fireStations) {this.fireStations = fireStations;}
}

