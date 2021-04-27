package com.dong.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dong.pms.domain.Member;
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
            "select "
                + "     t.no,"
                + "     m.no as guest_no,"
                + "     m.name as guest_name,"
                + "     t.mgrade,"
                + "     t.sgrade,"
                + "     t.sno,"
                + "     t.etc"
                + " from pms_seat t"
                + "     inner join pms_member m on t.guest=m.no"
                + " where t.no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "select" 
                + "    m.no,"
                + "    m.name"
                + " from pms_member_seat mt"
                + "     inner join pms_member m on mt.member_no=m.no"
                + " where "
                + "     mt.seat_no=?");
        PreparedStatement stmt3 = con.prepareStatement(
            "update pms_seat set"
                + " guest=?,"
                + " mgrade=?,"
                + " sgrade=?,"
                + " sno=?,"
                + " etc=?"
                + " where no=?");
        PreparedStatement stmt4 = con.prepareStatement( 
            "delete from pms_member_seat where seat_no=?");
        PreparedStatement stmt5 = con.prepareStatement(
            "insert into pms_member_seat(member_no,seat_no) values(?,?)")){

      con.setAutoCommit(false);

      Seat t = new Seat();

      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 좌석이 없습니다.");
          return;
        }
        t.setNo(no); 

        t.setGuest(memberValidator.inputMember(
            String.format("회원명?(%s)(취소: 빈 문자열) ",rs.getString("guest_name"))));

        if (t.getGuest() == null) {
          System.out.println("좌석 변경을 취소합니다.");
          return;
        }

        t.setMgrade(Prompt.inputString(
            String.format("회원등급(%s)? ", rs.getString("mgrade"))));
        t.setSgrade(Prompt.inputInt(
            String.format(
                "좌석등급(%s)?\n0: 퍼스트클래스\n1: 비즈니스\n2: 이코노미\n> ", 
                Seat.getStatusLabel(t.getSgrade()))));
        t.setSno(Prompt.inputString(
            String.format("좌석번호(%s)? ", rs.getString("sno"))));
        t.setEtc(Prompt.inputString(
            String.format("특이사항(%s)? ", rs.getString("etc"))));

        StringBuilder strings = new StringBuilder();
        stmt2.setInt(1, no);
        try (ResultSet partyRs = stmt2.executeQuery()) {
          while (partyRs.next()) {
            if (strings.length() > 0) {
              strings.append(",");
            }
            strings.append(partyRs.getString("name"));
          }
        }

        t.setParty(memberValidator.inputMembers(
            String.format("일행(%s)?(완료: 빈 문자열) ", strings)));

        String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");
        if (!input.equalsIgnoreCase("Y")) {
          System.out.println("좌석정보 변경을 취소하였습니다.");
          return;
        }

        stmt3.setInt(1, t.getGuest().getNo());
        stmt3.setString(2, t.getMgrade());
        stmt3.setInt(3, t.getSgrade());
        stmt3.setString(4, t.getSno());
        stmt3.setString(5, t.getEtc());
        stmt3.setInt(6, t.getNo());
        stmt3.executeUpdate();

        stmt4.setInt(1, t.getNo());
        stmt4.executeUpdate();

        for (Member member : t.getParty()) {
          stmt5.setInt(1, member.getNo());
          stmt5.setInt(2, t.getNo());
          stmt5.executeUpdate();
        }

        con.commit();

        System.out.println("좌석정보를 변경하였습니다.");
      }

    }
  }
}

