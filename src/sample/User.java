package sample;

public class User {
    private String firstName;
    private String secondName;
    private String country;
    private String gender;
    private String login;
    private String password;

    public User(String firstName, String secondName, String gender, String country, String login, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.gender = gender;
        this.country=country;
        this.login = login;
        this.password = password;
    }
    public User(){

    }
    public String getFirstName() {
        return firstName;
    }

    public String getCountry() {
        return country;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getGender() {
        return gender;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
