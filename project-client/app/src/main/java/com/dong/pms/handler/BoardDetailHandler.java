package com.dong.pms.handler;

import java.util.Iterator;
import com.dong.driver.Statement;
import com.dong.util.Prompt;

public class BoardDetailHandler implements Command{

  Statement stmt;

  public BoardDetailHandler(Statement stmt) {
    this.stmt = stmt;

  }
  @Override
  public void service() throws Exception {
    System.out.println("[칭찬게시글 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Iterator<String> results = stmt.executeQuery("board/select", Integer.toString(no));

    String[] fields = results.next().split(",");

    System.out.printf("제목: %s\n", fields[1]);
    System.out.printf("전하고싶은말: %s\n", fields[2]);
    System.out.printf("등록일: %s\n", fields[3]);
    System.out.printf("작성자: %s\n", fields[4]);
    System.out.printf("조회수: %d\n", fields[5]);
  }



}

