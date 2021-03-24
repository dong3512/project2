package com.dong.pms.handler;

import com.dong.driver.Statement;
import com.dong.util.Prompt;

public class ScheduleUpdateHandler implements Command{

  Statement stmt;
  MemberValidator memberValidator;

  public ScheduleUpdateHandler(Statement stmt, MemberValidator memberValidator) {
    this.stmt = stmt;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[비행일정 수정]");

    int no = Prompt.inputInt("번호? ");

    String[] fields = stmt.executeQuery("schedule/select", Integer.toString(no)).next().split(",");

    String destination = Prompt.inputString(String.format("목적지(%s) ",fields[1]));
    String airno = Prompt.inputString(String.format("항공기번호(%s)", fields[2]));
    String dtime = Prompt.inputString(String.format("출발시간(%s)", fields[3]));
    String atime = Prompt.inputString(String.format("도착시간(%s) ", fields[4]));
    String name = memberValidator.inputMember(String.format("탑승자(%s)?(취소: 빈 문자열) ", fields[5]));
    if (name == null) {
      System.out.println("비행일정 수정을 취소합니다.");
      return;
    }

    String pilot = Prompt.inputString(String.format("조종사(%s) ", fields[6]));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");

    if(!input.equalsIgnoreCase("Y")) {
      System.out.println("비행일정 변경을 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("schedule/update",
        String.format("%d,%s,%s,%s,%s,%s", 
            no, destination, airno, dtime, atime, name, pilot));

    System.out.println("비행일정을 변경하였습니다.");
  }

}
