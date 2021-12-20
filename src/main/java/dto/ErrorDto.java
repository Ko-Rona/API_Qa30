package dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class ErrorDto {

    int code;
    String details;
    String message;
}
