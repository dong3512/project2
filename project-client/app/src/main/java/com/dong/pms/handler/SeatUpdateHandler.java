package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatUpdateHandler implements Command{

  MemberValidator memberValidator;

  public SeatUpdateHandler(MemberValidator memberValidator) {
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[좌석정보 수정]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select * from pms_seat where no=?");
        PreparedStatement stmt2 =con.prepareStatement(
            "update pms_seat set guest=?,mgrade=?,sgrade=?,sno=?,etc=? where no=?")) {

      Seat t = new Seat();

      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 좌석이 없습니다.");
          return;
        }

        t.setNo(no); 
        t.setGuest(rs.getString("guest"));
        t.setMgrade(rs.getString("mgrade"));
        t.setSgrade(rs.getInt("sgrade"));
        t.setSno(rs.getString("sno"));
        t.setEtc(rs.getString("etc"));
      }

      t.setGuest(memberValidator.inputMember(String.format("회원명?(%s)(취소: 빈 문자열) ",t.getGuest())));
      if (t.getGuest() == null) {
        System.out.println("회원명 등록을 취소하였습니다.");
        return;
      }

      t.setGuest(Prompt.inputString(String.format("회원명(%s)? ", t.getGuest())));
      t.setMgrade(Prompt.inputString(String.format("회원등급(%s)? ", t.getMgrade())));
      t.setSgrade(Prompt.inputInt(String.format("좌석등급(%s)?\n0: 퍼스트 \n1: 비즈니스\n2: 이코노미\n> ",
          Seat.getStatusLabel(t.getSgrade()))));
      t.setSno(Prompt.inputString(String.format("좌석번호(%s)?(취소: 빈 문자열) ", t.getSno())));
      String etc = Prompt.inputString(String.format("특이사항(%s)? ", t.getEtc()));

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");

      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("좌석정보 변경을 취소하였습니다.");
        return;
      }

      stmt2.setString(1, t.getGuest());
      stmt2.setString(2, t.getMgrade());
      stmt2.setInt(3, t.getSgrade());
      stmt2.setString(4, t.getSno());
      stmt2.setString(5, t.getEtc());
      stmt2.setInt(6, t.getNo());
      stmt2.executeUpdate();


      System.out.println("좌석정보를 변경하였습니다.");
    }

  }
}


