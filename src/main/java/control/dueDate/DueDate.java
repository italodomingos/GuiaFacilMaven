package control.dueDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DueDate {
  Date beginDate;
  
  public List workingDays15() throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    List<String> dateList = new ArrayList();
    List<String> temp = workingDays();
    int stop = 0;
    dateList.add(dateFormat.format(new Date()));
    for (int i = 0; i < workingDays().size(); i++) {
      if (dateFormat.parse(temp.get(i)).compareTo(new Date()) > 0 && stop < 14) {
        dateList.add(temp.get(i));
        stop++;
      } else if (stop == 15) {
        break;
      } 
    } 
    return dateList;
  }
  
  public List<String> workingDays() throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Calendar workingDays = Calendar.getInstance();
    Calendar endWorkingDays = Calendar.getInstance();
    endWorkingDays.add(5, 30);
    List<String> listWorkingDays = new ArrayList<>();
    while (workingDays.compareTo(endWorkingDays) < 0) {
      if (workingDays.getTime().getDate() == 5) {
        workingDays.add(5, 2);
      } else {
        workingDays.add(5, 1);
      } 
      listWorkingDays.add(dateFormat.format(workingDays.getTime()));
    } 
    return listWorkingDays;
  }
}
