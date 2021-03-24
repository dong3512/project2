package com.dong.pms.handler;

import com.dong.driver.Statement;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatAddHandler implements Command{
  Statement stmt;
  public SeatAddHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[좌석 등록]");

    Seat t = new Seat();

    t.setMgrade(Prompt.inputString("회원등급: "));
    t.setSgrade(Prompt.inputInt("좌석등급:\n0:퍼스트클래스\n1:비즈니스클래스\n2:이코노미클래스\n"));
    t.setSno(Prompt.inputString("좌석번호: "));
    t.setEtc(Prompt.inputString("특이사항: "));

    stmt.executeUpdate("seat/insert",
        String.format("%s,%s,%s,%s",
            t.getMgrade(),
            t.getSgrade(),
            t.getSno(),
            t.getEtc()));

    System.out.println("좌석을 등록했습니다.");
  }

}


