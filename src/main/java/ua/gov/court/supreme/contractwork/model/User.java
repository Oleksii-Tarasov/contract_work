package ua.gov.court.supreme.contractwork.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String shortName;
    private String position;
    private String login;
    private String password;

    public User(long id, String lastName, String firstName,  String middleName, String shortName, String position) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.shortName = shortName;
        this.position = position;
    }
}
