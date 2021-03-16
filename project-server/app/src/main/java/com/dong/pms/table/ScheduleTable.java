package com.dong.pms.table;

import java.io.File;
import java.sql.Time;
import java.util.List;
import com.dong.pms.domain.Schedule;
import com.dong.util.JsonFileHandler;
import com.dong.util.Request;
import com.dong.util.Response;

public class ScheduleTable implements DataTable{

  File jsonFile = new File("schedules.json");
  List<Schedule> list;

  public ScheduleTable() {
    list = JsonFileHandler.loadObjects(jsonFile, Schedule.class);
  }

  @Override
  public void service(Request request, Response response) throws Exception {
    Schedule schedule = null;
    String[] fields = null;

    switch (request.getCommand()) {
      case "schedule/insert":

        fields = request.getData().get(0).split(",");

        schedule = new Schedule();

        if (list.size() > 0) {
          schedule.setNo(list.get(list.size() - 1).getNo() + 1);
        }else {
          schedule.setNo(1);
        }

        schedule.setDestination(fields[0]);
        schedule.setAirno(fields[1]);
        schedule.setName(fields[2]);
        schedule.setDtime(Time.valueOf(fields[3]));
        schedule.setAtime(Time.valueOf(fields[4]));
        schedule.setPilot(fields[5]);

        list.add(schedule);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "schedule/selectall":
        for (Schedule s : list) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s,%s",
              s.getNo(),
              s.getDestination(),
              s.getAirno(),
              s.getName(),
              s.getDtime(),
              s.getAtime(),
              s.getPilot()));
        }
        break;
      case "schedule/select":
        int no = Integer.parseInt(request.getData().get(0));

        schedule = getSchedule(no);
        if (schedule != null) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s,%s",
              schedule.getNo(),
              schedule.getDestination(),
              schedule.getAirno(),
              schedule.getName(),
              schedule.getDtime(),
              schedule.getAtime(),
              schedule.getPilot()));
        }else {
          throw new Exception("해당 번호의 스케줄이 없습니다.");
        }
        break;
      case "schedule/update":
        fields = request.getData().get(0).split(",");

        schedule = getSchedule(Integer.parseInt(fields[0]));
        if (schedule == null) {
          throw new Exception("해당 번호의 스케줄이 없습니다.");
        }

        schedule.setDestination(fields[0]);
        schedule.setAirno(fields[1]);
        schedule.setName(fields[2]);
        schedule.setDtime(Time.valueOf(fields[3]));
        schedule.setAtime(Time.valueOf(fields[4]));
        schedule.setPilot(fields[5]);


        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "schedule/delete":
        no = Integer.parseInt(request.getData().get(0));
        schedule = getSchedule(no);
        if (schedule == null) {
          throw new Exception("해당 번호의 스케줄이 없습니다.");
        }

        list.remove(schedule);
        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      default:
        throw new Exception("해당 명령을 처리할 수 없습니다.");
    }
  }

  private Schedule getSchedule(int scheduleNo) {
    for (Schedule s : list) {
      if (s.getNo() == scheduleNo) {
        return s;
      }
    }
    return null;
  }
}
