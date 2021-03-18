package com.dong.pms.handler;

import java.util.List;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatAddHandler extends AbstractSeatHandler{

  public SeatAddHandler(List<Seat> seatList) {
    super(seatList);
  }


  @Override
  public  void service(){
    System.out.println("[좌석 등록]");

    Seat t = new Seat();

    t.setNo(Prompt.inputInt("회원번호: "));
    t.setMgrade(Prompt.inputString("회원등급: "));
    t.setSgrade(Prompt.inputInt("좌석등급:\n0:퍼스트클래스\n1:비즈니스클래스\n2:이코노미클래스\n"));
    t.setSno(Prompt.inputString("좌석번호: "));
    t.setEtc(Prompt.inputString("특이사항: "));

    seatList.add(t);
    System.out.println("좌석을 등록했습니다.");
  }

}


