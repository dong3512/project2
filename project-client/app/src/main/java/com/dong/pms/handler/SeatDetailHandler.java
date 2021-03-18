package com.dong.pms.handler;

import java.util.List;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatDetailHandler extends AbstractSeatHandler{

  public SeatDetailHandler(List<Seat> seatList) {
    super(seatList);
  }

  @Override
  public void service(){
    System.out.println("[좌석 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Seat seat = findByNo(no);
    if( seat == null) {
      System.out.println("해당 번호의 좌석이 없습니다.");
      return;
    }

    System.out.printf("회원등급: %s\n", seat.getMgrade());
    System.out.printf("좌석등급: %s\n", seat.getSgrade());
    System.out.printf("좌석번호: %s\n", seat.getSno());
    System.out.printf("특이사항: %s\n", seat.getEtc());
  }
}


