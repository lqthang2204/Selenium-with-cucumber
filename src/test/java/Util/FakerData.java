package Util;

import bean.UserDTO;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import java.util.LinkedList;
import java.util.List;

public class FakerData {

    public List<UserDTO> CreateUser(List<UserDTO> list){
        if(list==null){
            list = new LinkedList<>();
        }
        Faker fake = new Faker();
        Name name = fake.name();
        UserDTO user = new UserDTO(name.lastName(), name.firstName(),fake.date().birthday()+"", fake.internet().emailAddress(), fake.phoneNumber().cellPhone()+"", fake.address().fullAddress());
        list.add(user);
        return list;
    }
}
