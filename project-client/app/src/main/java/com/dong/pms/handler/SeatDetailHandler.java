package com.dong.pms.handler;

import com.dong.driver.Statement;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatDetailHandler implements Command{
  Statement stmt;
  public SeatDetailHandler(Statement stmt) {
    this.stmt = stmt;
  }
  @Override
  public void service() throws Exception {
    System.out.println("[좌석 상세보기]");

    int no = Prompt.inputInt("번호? ");


    String[] fields = stmt.executeQuery("seat/select",Integer.toString(no)).next().split(",");

    System.out.printf("회원등급: %s\n", fields[1]);
    System.out.printf("좌석등급: %s\n", Seat.getStatusLabel(Integer.parseInt(fields[2])));
    System.out.printf("좌석번호: %s\n", fields[3]);
    System.out.printf("특이사항: %s\n", fields[4]);
  }
}


