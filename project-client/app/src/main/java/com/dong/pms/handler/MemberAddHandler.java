package com.dong.pms.handler;

import com.dong.driver.Statement;
import com.dong.pms.domain.Member;
import com.dong.util.Prompt;

public class MemberAddHandler implements Command {

  Statement stmt;

  public MemberAddHandler(Statement stmt) {
    this.stmt = stmt;
  }
  @Override
  public void service() throws Exception {
    System.out.println("[회원 등록]");

    Member m = new Member();

    m.setName(Prompt.inputString("이름" ));
    m.setEmail(Prompt.inputString("이메일"));
    m.setPhoto(Prompt.inputString("사진"));
    m.setHp(Prompt.inputString("전화번호"));

    stmt.executeUpdate("member/insert",
        String.format("%s,%s,%s,%s", 
            m.getName(), m.getEmail(), m.getPhoto(), m.getHp()));

    System.out.println("회원을 등록하였습니다.");
  }

}