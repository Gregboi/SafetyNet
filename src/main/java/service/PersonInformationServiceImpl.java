package service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PersonInformationServiceImpl implements PersonInformationService{

    String apiUrl = "http://localhost:8080/person";

    @Override
    public String getPersonsAddress(String address) {
        try {
            RestTemplate template = new RestTemplate();

            ResponseEntity<ObjectNode> response = template.getForEntity(apiUrl + address, ObjectNode.class);

            ObjectNode jsonObject = response.getBody();

            return jsonObject.path("address").asText();
        } catch (Exception e) {
            System.out.println("Something went wrong while calling Person API" + e.getMessage());
        }
        return null;
    }

    @Override
    public String getPersonsCity(String city) {
        try {
            RestTemplate cityTemplate = new RestTemplate();

            ResponseEntity<ObjectNode> cityResponse = cityTemplate.getForEntity(apiUrl + city, ObjectNode.class);

            ObjectNode jsonCityObject = cityResponse.getBody();

            return jsonCityObject.path("city").asText();
        } catch (Exception e){
            System.out.println("Something went wrong while calling the City Person API");
        }
        return null;
    }

    @Override
    public String getPersonsZip(String zip) {
        try {
            RestTemplate zipTemplate = new RestTemplate();

            ResponseEntity<ObjectNode> zipResponse = zipTemplate.getForEntity(apiUrl + zip, ObjectNode.class);

            ObjectNode jsonZipObject = zipResponse.getBody();

            return jsonZipObject.path("city").asText();
        } catch (Exception e){
            System.out.println("Something went wrong while calling the City Person API");
        }
        return null;
    }

    @Override
    public String getPersonsPhone(String phone) {
        try {
            RestTemplate phoneTemplate = new RestTemplate();

            ResponseEntity<ObjectNode> zipResponse = phoneTemplate.getForEntity(apiUrl + phone, ObjectNode.class);

            ObjectNode jsonPhoneObject = zipResponse.getBody();

            return jsonPhoneObject.path("phone").asText();
        } catch (Exception e){
            System.out.println("Something went wrong while calling the City Person API");
        }
        return null;
    }

    @Override
    public String getPersonsEmail(String email) {
        try {
            RestTemplate emailTemplate = new RestTemplate();

            ResponseEntity<ObjectNode> emailResponse = emailTemplate.getForEntity(apiUrl + email, ObjectNode.class);

            ObjectNode jsonEmailObject = emailResponse.getBody();

            return jsonEmailObject.path("email").asText();
        } catch (Exception e){
            System.out.println("Something went wrong while calling the City Person API");
        }
        return null;
    }
}