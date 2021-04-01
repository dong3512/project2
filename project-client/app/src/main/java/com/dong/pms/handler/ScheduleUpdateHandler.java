package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dong.pms.domain.Schedule;
import com.dong.util.Prompt;

public class ScheduleUpdateHandler implements Command{

  MemberValidator memberValidator;

  public ScheduleUpdateHandler( MemberValidator memberValidator) {
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[비행일정 수정]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select * from pms_schedule where no=?");
        PreparedStatement stmt2 =con.prepareStatement(
            "update pms_schedule set dtn=?,ano=?,dtime=?,atime=?,name=?,pilot=? where no=?")) {

      Schedule schedule = new Schedule();

      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 비행일정이 없습니다.");
          return;
        }

        schedule.setNo(no);
        schedule.setDestination(rs.getString("dtn"));
        schedule.setAirno(rs.getString("ano"));
        schedule.setDtime(rs.getTime("dtime"));
        schedule.setDtime(rs.getTime("atime"));
        schedule.setName(rs.getString("name"));
        schedule.setPilot(rs.getString("pilot"));

        schedule.setDestination(Prompt.inputString(String.format("목적지(%s) ",rs.getString("dtn"))));
        schedule.setAirno(Prompt.inputString(String.format("항공기번호(%s)", rs.getString("ano"))));
        schedule.setDtime(Prompt.inputTime(String.format("출발시간(%s)", rs.getTime("dtime"))));
        schedule.setAtime(Prompt.inputTime(String.format("도착시간(%s) ", rs.getTime("atime"))));
        schedule.setName(memberValidator.inputMember(
            String.format("탑승자(%s)?(취소: 빈 문자열) ", schedule.getName())));
        if (schedule.getName() == null) {
          System.out.println("비행일정 수정을 취소합니다.");
          return;
        }

        schedule.setPilot(Prompt.inputString(String.format("조종사(%s) ", rs.getString("pilot"))));

        String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");
        if(!input.equalsIgnoreCase("Y")) {
          System.out.println("비행일정 변경을 취소하였습니다.");
          return;
        }

        stmt2.setString(1, schedule.getDestination());
        stmt2.setString(2, schedule.getAirno());
        stmt2.setTime(3, schedule.getDtime());
        stmt2.setTime(4, schedule.getAtime());
        stmt2.setString(5, schedule.getName());
        stmt2.setString(6, schedule.getPilot());
        stmt2.setInt(7, schedule.getNo());
        stmt2.executeUpdate();

        System.out.println("비행일정을 변경하였습니다.");
      }

    }
  }
}