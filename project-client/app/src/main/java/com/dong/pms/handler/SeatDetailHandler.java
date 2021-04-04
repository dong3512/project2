package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatDetailHandler implements Command{
  @Override
  public void service() throws Exception {
    System.out.println("[좌석 상세보기]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select * from pms_seat where no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 작업이 없습니다.");
          return;
        }

        System.out.printf("회원등급: %s\n", rs.getString("mgrade"));
        System.out.printf("좌석등급: %s\n", Seat.getStatusLabel(rs.getInt("sgrade")));
        System.out.printf("좌석번호: %s\n", rs.getString("sno"));
        System.out.printf("특이사항: %s\n", rs.getString("etc"));
      }
    }
  }
}

