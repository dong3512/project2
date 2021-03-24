package com.dong.pms.handler;

import com.dong.driver.Statement;
import com.dong.pms.domain.Schedule;
import com.dong.util.Prompt;

public class ScheduleAddHandler implements Command{

  Statement stmt;
  MemberValidator memberValidator;

  public ScheduleAddHandler(Statement stmt,MemberValidator memberValidator) {
    this.stmt = stmt;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[비행일정]");

    Schedule s = new Schedule();

    s.setDestination(Prompt.inputString("목적지: "));
    s.setAirno(Prompt.inputString("항공기번호: "));
    s.setDtime(Prompt.inputTime("출발시간: "));
    s.setAtime(Prompt.inputTime("도착시간: "));

    s.setName(memberValidator.inputMember("탑승자?(취소: 빈 문자열) "));
    if (s.getName() == null) {
      System.out.println("비행일정 입력을 취소합니다.");
      return;
    }

    s.setPilot(Prompt.inputString("조종사: "));

    stmt.executeUpdate("schedule/insert",
        String.format("%s,%s,%s,%s,%s,%s",
            s.getDestination(),
            s.getAirno(),
            s.getDtime(),
            s.getAtime(),
            s.getName(),
            s.getPilot()));

    System.out.println("비행일정을 등록했습니다.");

  }

}