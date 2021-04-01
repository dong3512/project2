package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.dong.pms.domain.Member;
import com.dong.util.Prompt;

public class MemberAddHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[회원 등록]");

    Member m = new Member();

    m.setName(Prompt.inputString("이름" ));
    m.setEmail(Prompt.inputString("이메일"));
    m.setPassword(Prompt.inputString("암호"));
    m.setPhoto(Prompt.inputString("사진"));
    m.setHp(Prompt.inputString("전화번호"));

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "insert into pms_member(name, email, password, photo, hp) values(?,?,password(?),?,?)");) {

      stmt.setString(1, m.getName());
      stmt.setString(2, m.getEmail());
      stmt.setString(3, m.getPassword());
      stmt.setString(4, m.getPhoto());
      stmt.setString(5, m.getHp());
      stmt.executeUpdate();

      System.out.println("회원을 등록하였습니다.");

    }
  }
}