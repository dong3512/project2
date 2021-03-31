package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.dong.pms.domain.Board;
import com.dong.util.Prompt;

public class BoardAddHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[칭찬게시글 등록]");

    Board b = new Board();

    b.setTitle(Prompt.inputString("제목"));
    b.setContent(Prompt.inputString("내용"));
    b.setMessage(Prompt.inputString("전하고싶은말"));
    b.setWriter(Prompt.inputString("작성자"));

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =
            con.prepareStatement("insert into pms_board(title, content, message, writer) values(?,?,?,?)");) {

      stmt.setString(1, b.getTitle());
      stmt.setString(2, b.getContent());
      stmt.setString(3, b.getMessage());
      stmt.setString(4, b.getWriter());
      stmt.executeUpdate();

      System.out.println("게시글을 등록하였습니다.");
    }
  }
}
