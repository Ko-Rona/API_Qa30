package schedulerdto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class GetAllRecordsDto {

    List<RecordDto> records;
}
