package schedulerdto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class GetRecordsRequestDto {
    int monthFrom;
    int monthTo;
    int yearFrom;
    int yearTo;
}
