package com.dong.pms.handler;

import java.util.List;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatUpdateHandler extends AbstractSeatHandler{

  public SeatUpdateHandler(List<Seat> seatList) {
    super(seatList);
  }

  @Override
  public void service(){
    System.out.println("[좌석정보 수정]");

    int no = Prompt.inputInt("번호? ");

    Seat seat = findByNo(no);
    if (seat == null) {
      System.out.println("해당 번호의 좌석이 없습니다.");
      return;
    }

    String mgrade = Prompt.inputString(String.format("회원등급(%s)? ", seat.getMgrade()));
    int sgrade = Prompt.inputInt(String.format("좌석등급(%s)?\n0: 퍼스트 \n1: 비즈니스\n2: 이코노미", gradeLabel(seat.getSgrade())));
    String sno = Prompt.inputString(String.format("좌석번호(%s)?(취소: 빈 문자열) ", seat.getSno()));
    if(sno == null) {
      System.out.println("작업 변경을 취소합니다.");
      return;
    }

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");

    if (input.equalsIgnoreCase("Y")) {
      seat.setMgrade(mgrade);
      //                seat.sgrade = sgrade; 오류의 이유를 모르겠습니다 ㅠ switch문이 들어가있긴함 ㅎ
      seat.setSno(sno);
      System.out.println("게시글을 변경하였습니다.");
    }else {
      System.out.println("게시글 변경을 취소하였습니다.");
    }
  }

}


