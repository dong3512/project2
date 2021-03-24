package com.dong.pms.handler;

import com.dong.driver.Statement;
import com.dong.util.Prompt;

public class MemberUpdateHandler implements Command {
  Statement stmt;
  public MemberUpdateHandler(Statement stmt) {
    this.stmt = stmt;
  }
  @Override
  public void service() throws Exception {
    System.out.println("[회원 수정]");

    int no = Prompt.inputInt("번호? ");

    String[] fields = stmt.executeQuery("member/select", Integer.toString(no)).next().split(",");

    String name = Prompt.inputString(String.format("이름(%s)? ", fields[1]));
    String email = Prompt.inputString(String.format("이메일(%s)? ", fields[2]));
    String photo = Prompt.inputString(String.format("사진(%s)? ", fields[3]));
    String hp = Prompt.inputString(String.format("전화번호(%s)? ", fields[4]));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");
    if(!input.equalsIgnoreCase("Y")) {
      System.out.println("회원정보 변경을 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("member/update",
        String.format("%d,%s,%s,%s,%s", no, name, email, photo, hp));
    System.out.println("회원을 변경하였습니다.");

  }
}