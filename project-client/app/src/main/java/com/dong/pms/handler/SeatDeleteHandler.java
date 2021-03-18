package com.dong.pms.handler;

import java.util.List;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatDeleteHandler extends AbstractSeatHandler{

  public SeatDeleteHandler(List<Seat> seatList) {
    super(seatList);
  }
  @Override
  public void service(){
    System.out.println("[좌석정보 삭제]");

    int no = Prompt.inputInt("번호? ");

    Seat seat = findByNo(no);
    if (seat == null) {
      System.out.println("해당 번호의 좌석이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N)");

    if(input.equalsIgnoreCase("Y")) {
      seatList.remove(seat);

      System.out.println("게시글을 삭제하였습니다.");
    }else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }
  }

}


