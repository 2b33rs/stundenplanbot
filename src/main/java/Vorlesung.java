import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
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
    
    @Override
    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

        StringBuilder reply = new StringBuilder();

        reply.append(this.getSubject())
                .append(" ")
                .append(this.getRemark())
                .append("\nProf: ")
                .append(this.getTeacher())
                .append("\nRaum: ")
                .append(this.getRoom())
                .append("\nZeit: ")
                .append(dateFormat.format(this.getStartTime()))
                .append(" von ")
                .append(timeFormat.format(this.getStartTime()))
                .append(" bis ")
                .append(timeFormat.format(this.getEndTime()));

        return reply.toString();
    }

}
