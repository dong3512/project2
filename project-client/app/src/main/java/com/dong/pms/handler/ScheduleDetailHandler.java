package com.dong.pms.handler;

import java.util.List;
import com.dong.pms.domain.Schedule;
import com.dong.util.Prompt;

public class ScheduleDetailHandler implements Command{

  public ScheduleDetailHandler(List<Schedule> scheduleList) {
    super(scheduleList);
  }


  @Override
  public void service(){
    System.out.println("[비행일정 상세조회]");

    int no = Prompt.inputInt("번호? ");

    Schedule schedule = findByNo(no);
    if(schedule == null) {
      System.out.println("해당 번호의 일정이 없습니다.");
      return;
    }

    System.out.printf("목적지: %s\n",schedule.getDestination());
    System.out.printf("항공기번호: %s\n",schedule.getAirno());
    System.out.printf("출발시간: %s\n", schedule.getDtime());
    System.out.printf("도착시간: %s\n", schedule.getAtime());
    System.out.printf("탑승자이름: %s\n", schedule.getName());
    System.out.printf("조종사: %s\n", schedule.getPilot());
  }


}