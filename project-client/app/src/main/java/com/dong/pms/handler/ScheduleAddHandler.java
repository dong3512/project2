package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.dong.pms.domain.Schedule;
import com.dong.util.Prompt;

public class ScheduleAddHandler implements Command{

  MemberValidator memberValidator;

  public ScheduleAddHandler(MemberValidator memberValidator) {
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

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "insert into pms_schedule(dtn,ano,dtime,atime,name,pilot)"
                + " values(?,?,?,?,?,?)");) {

      stmt.setString(1, s.getDestination());
      stmt.setString(2, s.getAirno());
      stmt.setTime(3, s.getDtime());
      stmt.setTime(4, s.getAtime());
      stmt.setString(5, s.getName());
      stmt.setString(6, s.getPilot());
      stmt.executeUpdate();

      System.out.println("비행일정을 등록했습니다.");

    }

  }
}