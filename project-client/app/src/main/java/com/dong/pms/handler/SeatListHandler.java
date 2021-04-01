package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dong.driver.Statement;
import com.dong.pms.domain.Seat;

public class SeatListHandler implements Command{
  Statement stmt;
  public SeatListHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[좌석 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select no,mgrade,sgrade,sno,etc from pms_schedule order by sgrade desc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%d, %s, %d, %s, %s\n",
            rs.getInt("no"),
            rs.getString("mgrade"),
            Seat.getStatusLabel(rs.getInt("sgrade")),
            rs.getString("sno"),
            rs.getString("etc"));
      }
    }
  }
}


