package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatAddHandler implements Command{

  MemberValidator memberValidator;

  public SeatAddHandler(MemberValidator memberValidator) {
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[좌석 등록]");

    Seat t = new Seat();
    t.setGuest(memberValidator.inputMember("회원명?(취소: 빈 문자열) "));
    if (t.getGuest() == null) {
      System.out.println("회원명 등록을 취소하였습니다.");
      return;
    }
    t.setMgrade(Prompt.inputString("회원등급: "));
    t.setSgrade(Prompt.inputInt("좌석등급:\n0:퍼스트클래스\n1:비즈니스클래스\n2:이코노미클래스\n"));
    t.setSno(Prompt.inputString("좌석번호: "));
    t.setEtc(Prompt.inputString("특이사항: "));

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "insert into pms_seat(guest,mgrade,sgrade,sno,etc) values(?,?,?,?,?)");) {

      stmt.setString(1, t.getGuest());
      stmt.setString(2, t.getMgrade());
      stmt.setInt(3, t.getSgrade());
      stmt.setString(4, t.getSno());
      stmt.setString(5, t.getEtc());
      stmt.executeUpdate();

      System.out.println("좌석을 등록했습니다.");
    }
  }
}
