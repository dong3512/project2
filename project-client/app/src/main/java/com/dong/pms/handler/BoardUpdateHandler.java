package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dong.pms.domain.Board;
import com.dong.util.Prompt;

public class BoardUpdateHandler implements Command{

  @Override
  public void service() throws Exception {
    System.out.println("[칭찬게시글 수정]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select no,title,content,message from pms_board where no=?");
        PreparedStatement stmt2 =con.prepareStatement(
            "update pms_board set title=?, content=?, message=? where no=?")) {

      Board board = new Board();

      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 게시글이 없습니다.");
          return;
        }

        board.setNo(no);
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("content"));
        board.setMessage(rs.getString("message"));
      }

      board.setTitle(Prompt.inputString(String.format("제목(%s)? ", board.getTitle())));
      board.setContent(Prompt.inputString(String.format("내용(%s)? ", board.getContent())));
      board.setMessage(Prompt.inputString(String.format("메시지(%s)? ", board.getMessage())));

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("게시글 변경을 취소하였습니다.");
        return;
      }

      stmt2.setString(1, board.getTitle());
      stmt2.setString(2, board.getContent());
      stmt2.setString(3, board.getMessage());
      stmt2.setInt(4, board.getNo());
      stmt2.executeUpdate();

      System.out.println("게시글을 변경하였습니다.");
    }

  }
}

