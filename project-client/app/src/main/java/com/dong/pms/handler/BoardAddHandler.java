package com.dong.pms.handler;

import java.sql.Date;
import com.dong.driver.Statement;
import com.dong.pms.domain.Board;
import com.dong.util.Prompt;

public class BoardAddHandler implements Command {

  Statement stmt;

  public BoardAddHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[칭찬게시글 등록]");

    Board b = new Board();

    b.setTitle(Prompt.inputString("제목"));
    b.setContent(Prompt.inputString("내용"));
    b.setMessage(Prompt.inputString("전하고싶은말"));
    b.setWriter(Prompt.inputString("작성자"));
    b.setRegisteredDate(new Date(System.currentTimeMillis()));

    stmt.executeUpdate("board/insert", String.format("%s,%s,%s,%s,%s",
        b.getTitle(),b.getContent(),b.getMessage(),b.getWriter(),b.getRegisteredDate()));

    System.out.println("게시글을 등록하였습니다.");
  }
}

