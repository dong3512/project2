package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.dong.pms.domain.Member;
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

    t.setParty(memberValidator.inputMembers("일행?(완료: 빈 문자열) "));

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/projectdb?user=project&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "insert into pms_seat(guest,mgrade,sgrade,sno,etc) values(?,?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS);
        PreparedStatement stmt2 = con.prepareStatement(
            "insert into pms_member_seat(member_no,seat_no) values(?,?)")
        ) {

      con.setAutoCommit(false);

      stmt.setInt(1, t.getGuest().getNo());
      stmt.setString(2, t.getMgrade());
      stmt.setInt(3, t.getSgrade());
      stmt.setString(4, t.getSno());
      stmt.setString(5, t.getEtc());
      stmt.executeUpdate();

      try (ResultSet keyRs = stmt.getGeneratedKeys()) {
        keyRs.next();
        t.setNo(keyRs.getInt(1));
      }

      // 2) 좌석에 일행들을 추가한다.
      for (Member member : t.getParty()) {
        stmt2.setInt(1, member.getNo());
        stmt2.setInt(2, t.getNo());
        stmt2.executeUpdate();
      }

      con.commit();

      System.out.println("좌석을 등록했습니다.");
    }
  }
}
