package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dong.util.Prompt;

public class BoardDetailHandler implements Command{

  @Override
  public void service() throws Exception {
    System.out.println("[칭찬게시글 상세보기]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select * from pms_board where no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 게시글이 없습니다.");
          return;
        }

        System.out.printf("제목: %s\n", rs.getString("title"));
        System.out.printf("내용: %s\n", rs.getString("content"));
        System.out.printf("전하고싶은말: %s\n", rs.getString("message"));
        System.out.printf("등록일: %s\n", rs.getDate("cdt"), rs.getTime("cdt"));
        System.out.printf("작성자: %s\n", rs.getString("writer"));
        System.out.printf("조회수: %d\n", rs.getInt("vw_cnt"));
      }
    }
  }
}

