package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dong.util.Prompt;

public class ScheduleDetailHandler implements Command{

  @Override
  public void service() throws Exception {
    System.out.println("[비행일정 상세조회]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select * from pms_schedule where no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()){
        if (!rs.next()) {
          System.out.println("해당 번호의 프로젝트가 없습니다.");
          return;
        }

        System.out.printf("목적지: %s\n",rs.getString("dtn"));
        System.out.printf("항공기번호: %s\n",rs.getString("ano"));
        System.out.printf("출발시간: %s\n", rs.getTime("dtime"));
        System.out.printf("도착시간: %s\n", rs.getTime("atime"));
        System.out.printf("탑승자이름: %s\n", rs.getString("guest"));
        System.out.printf("조종사: %s\n", rs.getString("pilot"));
      }
    }
  }
}