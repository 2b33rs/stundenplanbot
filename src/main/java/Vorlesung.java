import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Vorlesung {

    private int tag;
    private int block;
    private Date startTime;
    private Date endTime;
    private String subject;
    private String teacher;
    private String room;
    private String remark;

}
