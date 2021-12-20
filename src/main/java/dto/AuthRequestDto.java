package dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class AuthRequestDto {

    String email;
    String password;

}
