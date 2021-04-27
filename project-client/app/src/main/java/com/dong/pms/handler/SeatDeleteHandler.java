package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.dong.util.Prompt;

public class SeatDeleteHandler implements Command{
  @Override
  public void service() throws Exception {
    System.out.println("[좌석정보 삭제]");

    int no = Prompt.inputInt("번호? ");

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N)");

    if(!input.equalsIgnoreCase("Y")) {
      System.out.println("좌석정보 삭제를 취소하였습니다.");
      return;
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "delete from pms_member_seat where seat_no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "delete from pms_seat where no=?")) {

      con.setAutoCommit(false);

      stmt.setInt(1, no);
      stmt.executeUpdate();

      stmt2.setInt(1, no);
      if (stmt2.executeUpdate() == 0) {
        System.out.println("해당 번호의 좌석정보가 없습니다.");

      } else {
        con.commit();
        System.out.println("좌석정보를 삭제하였습니다.");
      }
    }
  }
}

