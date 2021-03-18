package com.dong.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.sql.Date;
import com.dong.pms.domain.Board;
import com.dong.util.Prompt;

public class BoardAddHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[칭찬게시글 등록]");

    Board b = new Board();

    b.setTitle(Prompt.inputString("제목"));
    b.setContent(Prompt.inputString("내용"));
    b.setMessage(Prompt.inputString("전하고싶은말"));
    b.setWriter(Prompt.inputString("작성자"));
    b.setRegisteredDate(new Date(System.currentTimeMillis()));

    out.writeUTF("board/insert");
    out.writeInt(1);
    out.writeUTF(String.format("%s,%s,%s,%s,%s",
        b.getTitle(), b.getContent(), b.getMessage(),
        b.getWriter(),b.getRegisteredDate()));
    out.flush();

    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("게시글을 등록하였습니다.");
  }


}

