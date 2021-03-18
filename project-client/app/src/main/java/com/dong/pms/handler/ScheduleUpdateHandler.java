package com.dong.pms.handler;

import java.util.List;
import com.dong.pms.domain.Schedule;
import com.dong.util.Prompt;

public class ScheduleUpdateHandler implements Command{

  private MemberValidator memberValidatorHandler;

  public ScheduleUpdateHandler(List<Schedule> scheduleList, MemberValidator memberValidatorHandler) {
    super(scheduleList);
    this.memberValidatorHandler = memberValidatorHandler;
  }


  @Override
  public void service(){
    System.out.println("[비행일정 수정]");

    int no = Prompt.inputInt("번호? ");

    Schedule schedule = findByNo(no);

    if(schedule == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    String destination = Prompt.inputString(String.format("목적지(%s) ",schedule.getDestination()));
    String dtime = Prompt.inputString(String.format("출발시간(%s)", schedule.getDtime()));
    String atime = Prompt.inputString(String.format("도착시간(%s) ", schedule.getAtime()));
    String name = memberValidatorHandler.inputMember(String.format("탑승자(%s)?(취소: 빈 문자열) ", schedule.getName()));
    if (name == null) {
      System.out.println("비행일정 수정을 취소합니다.");
      return;
    }

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");

    if(input.equalsIgnoreCase("Y")) {
      schedule.setDestination(destination);
      //       schedule.dtime = dtime;
      //       schedule.atime = atime;
      // time은 변경이 안된다합니다요 ㅠㅠ
      schedule.setName(name);

      System.out.println("비행일정을 변경하였습니다.");
    }else {
      System.out.println("프로젝트 변경을 취소하였습니다.");
    }
  }


}