package dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class ContactDto {

    String address;
    String description;
    String email;
    int id;
    String lastName;
    String name;
    String phone;

}
