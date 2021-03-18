package com.dong.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.dong.util.Prompt;

public class MemberUpdateHandler implements Command {
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[회원 수정]");

    int no = Prompt.inputInt("번호? ");

    out.writeUTF("member/select");
    out.writeInt(1);
    out.writeUTF(Integer.toString(no));
    out.flush();

    // 서버의 응답을 받는다.
    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    String[] fields = in.readUTF().split(",");

    String name = Prompt.inputString(String.format("이름(%s)? ", fields[1]));
    String email = Prompt.inputString(String.format("이메일(%s)? ", fields[2]));
    String photo = Prompt.inputString(String.format("사진(%s)? ", fields[3]));
    String hp = Prompt.inputString(String.format("전화번호(%s)? ", fields[4]));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");
    if(!input.equalsIgnoreCase("Y")) {
      System.out.println("회원정보 변경을 취소하였습니다.");
      return;
    }
    out.writeUTF("member/update");
    out.writeInt(1);
    out.writeUTF(String.format("%d,%s,%s,%s,%s", no, name, email, photo, hp));
    out.flush();

    status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("회원을 변경하였습니다.");

  }
}