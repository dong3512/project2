package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dong.pms.domain.Seat;

public class SeatListHandler implements Command{

  @Override
  public void service() throws Exception {
    System.out.println("[좌석 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select"
                + "     t.no,"
                + "     m.no as guest_no,"
                + "     m.name as guest_name,"
                + "     t.mgrade,"
                + "     t.sgrade,"
                + "     t.sno,"
                + "     t.etc"
                + "  from pms_seat t"
                + "     inner join pms_member m on t.guest=m.no"
                + " order by sgrade desc");
        PreparedStatement stmt2 = con.prepareStatement(
            "select" 
                + "    m.no,"
                + "    m.name"
                + " from pms_member_seat mt"
                + "     inner join pms_member m on mt.member_no=m.no"
                + " where "
                + "     mt.seat_no=?");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        stmt2.setInt(1, rs.getInt("no"));
        String party = "";
        try (ResultSet memberRs = stmt2.executeQuery()) {
          while (memberRs.next()) {
            if (party.length() > 0) {
              party += "/";
            }
            party += memberRs.getString("name");
          }
        }
        System.out.printf("%d, %s, %s, %s, %s, %s, [%s]\n",
            rs.getInt("no"),
            rs.getString("guest_name"),
            rs.getString("mgrade"),
            Seat.getStatusLabel(rs.getInt("sgrade")),
            rs.getString("sno"),
            rs.getString("etc"),
            party);
      }
    }
  }
}


