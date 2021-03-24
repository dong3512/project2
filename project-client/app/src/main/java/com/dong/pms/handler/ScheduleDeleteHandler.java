package com.dong.pms.handler;

import com.dong.driver.Statement;
import com.dong.util.Prompt;

public class ScheduleDeleteHandler implements Command{

  Statement stmt;

  public ScheduleDeleteHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[비행일정 삭제]");

    int no = Prompt.inputInt("번호? ");

    stmt.executeQuery("schedule/select", Integer.toString(no));

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("비행일정 삭제를 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("schedule/delete", Integer.toString(no));

    System.out.println("비행일정 삭제를 취소하였습니다.");

  }


}