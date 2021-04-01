package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ScheduleListHandler implements Command{

  @Override
  public void service() throws Exception {
    System.out.println("[비행일정 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select no,dtn,ano,dtime,atime,name,pilot from pms_schedule order by dtime asc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%s, %s, %s, %s, %s, %s, %s\n",
            rs.getInt("no"),
            rs.getString("dtn"),
            rs.getString("ano"),
            rs.getTime("dtime"),
            rs.getTime("atime"),
            rs.getString("name"),
            rs.getString("pilot"));
      }
    }
  }
}