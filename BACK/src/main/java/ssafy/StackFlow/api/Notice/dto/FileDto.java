package ssafy.StackFlow.api.Notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
}