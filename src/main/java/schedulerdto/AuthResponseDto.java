package schedulerdto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthResponseDto {
    boolean registration;
    String status;
    String token;
}
