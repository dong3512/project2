package com.dong.pms.handler;

import com.dong.driver.Statement;
import com.dong.util.Prompt;

public class BoardUpdateHandler implements Command{

  Statement stmt;

  public BoardUpdateHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[칭찬게시글 수정]");

    int no = Prompt.inputInt("번호? ");

    String[] fields = stmt.executeQuery("board/update", Integer.toString(no)).next().split(",");

    String title = Prompt.inputString(String.format("제목(%s)? ", fields[1]));
    String content = Prompt.inputString(String.format("내용(%s)? ", fields[2]));
    String message = Prompt.inputString(String.format("메시지(%s)? ", fields[3]));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("게시글 변경을 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("board/update", String.format("%d, %s, %s, %s",no , title, content, message));

    System.out.println("게시글을 변경하였습니다.");
  }


}

