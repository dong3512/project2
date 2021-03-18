package com.dong.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.dong.pms.domain.Member;
import com.dong.util.Prompt;

public class MemberAddHandler implements Command {
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[회원 등록]");

    Member m = new Member();

    m.setName(Prompt.inputString("이름" ));
    m.setEmail(Prompt.inputString("이메일"));
    m.setPhoto(Prompt.inputString("사진"));
    m.setHp(Prompt.inputString("전화번호"));

    out.writeUTF("member/insert");
    out.writeInt(1);
    out.writeUTF(String.format("%s,%s,%s,%s", 
        m.getName(), m.getEmail(),  m.getPhoto(), m.getHp()));
    out.flush();

    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("회원을 등록하였습니다.");
  }

}