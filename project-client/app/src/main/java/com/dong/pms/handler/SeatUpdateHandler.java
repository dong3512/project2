package com.dong.pms.handler;

import com.dong.driver.Statement;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatUpdateHandler implements Command{
  Statement stmt;
  public SeatUpdateHandler(Statement stmt) {
    this.stmt = stmt;
  }
  @Override
  public void service() throws Exception {
    System.out.println("[좌석정보 수정]");

    int no = Prompt.inputInt("번호? ");


    String[] fields = stmt.executeQuery("seat/select", Integer.toString(no)).next().split(",");

    String mgrade = Prompt.inputString(String.format("회원등급(%s)? ", fields[1]));
    int sgrade = Prompt.inputInt(String.format("좌석등급(%s)?\n0: 퍼스트 \n1: 비즈니스\n2: 이코노미\n> ",
        Seat.getStatusLabel(Integer.parseInt(fields[2]))));
    String sno = Prompt.inputString(String.format("좌석번호(%s)?(취소: 빈 문자열) ", fields[3]));
    String etc = Prompt.inputString(String.format("특이사항(%s)? ", fields[4]));
    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("좌석정보 변경을 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("seat/update", 
        String.format("%s,%s,%s,%s,%s", no, mgrade, sgrade, sno, etc));

    System.out.println("좌석정보를 변경하였습니다.");
  }

}


