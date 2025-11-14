package ua.gov.court.supreme.contractwork.model;

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String shortName;
    private String position;
    private String login;
    private String password;

    public User(long id, String firstName, String lastName, String middleName, String shortName, String position, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.shortName = shortName;
        this.position = position;
        this.login = login;
        this.password = password;
    }

    public User(long id, String firstName, String lastName, String middleName, String shortName, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.shortName = shortName;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getPosition() {
        return position;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
