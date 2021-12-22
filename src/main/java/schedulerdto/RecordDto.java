package schedulerdto;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class RecordDto {
    int breaks;
    String currency;
    DateDto dateDto;
    int hours;
    int id;
    String timeFrom;
    String timeTo;
    String title;
    double totalSalary;
    String type;
    int wage;

}
