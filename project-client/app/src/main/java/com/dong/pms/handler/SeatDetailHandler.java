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
            "select "
                + "     t.no,"
                + "     m.no as guest_no,"
                + "     m.name as guest_name,"
                + "     t.mgrade,"
                + "     t.sgrade,"
                + "     t.sno,"
                + "     t.etc"
                + " from pms_seat t"
                + "     inner join pms_member m on t.guest=m.no"
                + " where t.no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "select"
                + "     m.no,"
                + "     m.name"
                + " from pms_member_seat mt"
                + "     inner join pms_member m on mt.member_no=m.no"
                + " where "
                + "     mt.seat_no=?" )) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 좌석이 없습니다.");
          return;
        }

        System.out.printf("회원명: %s\n", rs.getString("guest_name"));
        System.out.printf("회원등급: %s\n", rs.getString("mgrade"));
        System.out.printf("좌석등급: %s\n", Seat.getStatusLabel(rs.getInt("sgrade")));
        System.out.printf("좌석번호: %s\n", rs.getString("sno"));
        System.out.printf("특이사항: %s\n", rs.getString("etc"));
      }
    }
  }
}

