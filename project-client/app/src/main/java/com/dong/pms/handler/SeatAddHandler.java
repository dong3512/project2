package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatAddHandler implements Command{

  @Override
  public void service() throws Exception {
    System.out.println("[좌석 등록]");

    Seat t = new Seat();

    t.setMgrade(Prompt.inputString("회원등급: "));
    t.setSgrade(Prompt.inputInt("좌석등급:\n0:퍼스트클래스\n1:비즈니스클래스\n2:이코노미클래스\n"));
    t.setSno(Prompt.inputString("좌석번호: "));
    t.setEtc(Prompt.inputString("특이사항: "));

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "insert into pms_seat(mgrade,sgrade,sno,etc) values(?,?,?,?)");) {

      stmt.setString(1, t.getMgrade());
      stmt.setInt(2, t.getSgrade());
      stmt.setString(3, t.getSno());
      stmt.setString(4, t.getEtc());
      stmt.executeUpdate();

      System.out.println("좌석을 등록했습니다.");
    }
  }
}
