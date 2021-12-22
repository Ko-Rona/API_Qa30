package schedulerdto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class DateDto {

    int dayOfMonth;
    String dayOfWeek;
    int month;
    int year;
}
