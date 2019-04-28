package dnr.dnr;

/**
 * Created by IFEANYI on 4/4/2018.
 */

//dnr user class
public class DNRuser {

    //class attributes
    String name;
    String address;
    String phone;
    String pass;

//empty constructor
    public DNRuser() {
    }

    //main constructor
    public DNRuser(String name, String address, String phone, String pass) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.pass = pass;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
