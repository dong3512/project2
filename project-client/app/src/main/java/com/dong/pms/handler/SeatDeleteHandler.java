package com.dong.pms.handler;

import com.dong.driver.Statement;
import com.dong.util.Prompt;

public class SeatDeleteHandler implements Command{
  Statement stmt;
  public SeatDeleteHandler(Statement stmt) {
    this.stmt = stmt;
  }
  @Override
  public void service() throws Exception {
    System.out.println("[좌석정보 삭제]");

    int no = Prompt.inputInt("번호? ");


    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N)");

    if(!input.equalsIgnoreCase("Y")) {
      System.out.println("좌석정보 삭제를 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("seat/delete", Integer.toString(no));

    System.out.println("좌석정보를 삭제하였습니다.");
  }

}


