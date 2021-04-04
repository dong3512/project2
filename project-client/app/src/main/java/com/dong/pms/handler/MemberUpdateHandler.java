package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dong.pms.domain.Member;
import com.dong.util.Prompt;

public class MemberUpdateHandler implements Command {
  @Override
  public void service() throws Exception {
    System.out.println("[회원 수정]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select * from pms_member where no=?");
        PreparedStatement stmt2 =con.prepareStatement(
            "update pms_member set name=?,email=?,password=password(?),photo=?,hp=? where no=?")) {

      Member member = new Member();

      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 회원이 없습니다.");
          return;
        }

        member.setNo(no);
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setPhoto(rs.getString("photo"));
        member.setHp(rs.getString("hp"));
      }

      member.setName(Prompt.inputString(String.format("이름(%s)? ", member.getName())));
      member.setEmail(Prompt.inputString(String.format("이메일(%s)? ", member.getEmail())));
      member.setPassword(Prompt.inputString("새암호? "));
      member.setPhoto(Prompt.inputString(String.format("사진(%s)? ", member.getPhoto())));
      member.setHp(Prompt.inputString(String.format("전화번호(%s)? ", member.getHp())));

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");
      if(!input.equalsIgnoreCase("Y")) {
        System.out.println("회원정보 변경을 취소하였습니다.");
        return;
      }

      stmt2.setString(1, member.getName());
      stmt2.setString(2, member.getEmail());
      stmt2.setString(3, member.getPassword());
      stmt2.setString(4, member.getPhoto());
      stmt2.setString(5, member.getHp());
      stmt2.setInt(6, member.getNo());
      stmt2.executeUpdate();

      System.out.println("회원을 변경하였습니다.");

    }
  }
}