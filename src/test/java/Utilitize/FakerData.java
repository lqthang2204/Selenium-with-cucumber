package Utilitize;

import bean.UserAddress;
import bean.UserDTO;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class FakerData {

    public List<UserDTO> CreateUser(List<UserDTO> list) throws ParseException {
        if (list == null) {
            list = new LinkedList<>();
        }
        Faker fakeUser = new Faker();
        UserDTO user = new UserDTO();
        UserAddress address = new UserAddress();
        Name name = fakeUser.name();
        Address addressFaker = fakeUser.address();
        user.setFirstName(name.firstName());
        user.setLastName(name.lastName());
        user.setMiddleName(name.nameWithMiddle());
        user.setFullName(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName());
        user.setPrefix(name.prefix());
        user.setSuffix(name.suffix());
        address.setCity(addressFaker.city());
        address.setState(addressFaker.state());
        address.setZip(addressFaker.zipCode());
        address.setPhoneNumber(fakeUser.phoneNumber().phoneNumber());
        address.setStreetOne(addressFaker.streetAddress());
        user.setDob(Util.convertDobToMiliseconds(fakeUser.date().birthday()));
        user.setEmail(fakeUser.bothify(name.fullName().replace(" ", "") + "Test@gmail.com"));
        user.setPassword(fakeUser.internet().password(6, 9, true, true, true));
        user.setUserAddresses(address);
        list.add(user);
        return list;
    }
}
