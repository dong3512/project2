package com.dong.pms.handler;

import java.util.Iterator;
import java.util.List;
import com.dong.pms.domain.Schedule;

public class ScheduleListHandler implements Command{

  public ScheduleListHandler(List<Schedule> scheduleList) {
    super(scheduleList);
  }


  @Override
  public void service()  {
    System.out.println("[비행일정 목록]");

    Iterator<Schedule> iterator = scheduleList.iterator();

    while (iterator.hasNext()) {
      Schedule s = iterator.next();
      System.out.printf("%s, %s, %s, %s, %s, %s, %s\n",
          s.getNo(), s.getDestination(), s.getAirno(), s.getDtime(), s.getAtime(), s.getName(), s.getPilot());
    }
  }


}