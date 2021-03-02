package jiahao.chen.cargurudemo;

public class UsersModel {

    String Firstname;
    String Lastname;

    public UsersModel() {

    }

    public UsersModel(String firstname, String lastname) {
        this.Firstname = firstname;
        this.Lastname = lastname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        this.Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        this.Lastname = lastname;
    }
}
