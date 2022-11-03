package Util;

import bean.UserDTO;
import com.github.javafaker.Faker;

public class FakerData {

    public UserDTO CreateUser(){
        Faker fake = new Faker();
        UserDTO user = new UserDTO(fake.name().lastName(), fake.name().firstName(),fake.date().birthday()+"", fake.internet().emailAddress(), fake.phoneNumber()+"", fake.address().fullAddress());
        return user;
    }
}
