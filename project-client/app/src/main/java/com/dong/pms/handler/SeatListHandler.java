package com.dong.pms.handler;

import java.util.Iterator;
import java.util.List;
import com.dong.pms.domain.Seat;

public class SeatListHandler extends AbstractSeatHandler{

  public SeatListHandler(List<Seat> seatList) {
    super(seatList);
  }

  @Override
  public void service()  {
    System.out.println("[좌석 목록]");

    Iterator<Seat> iterator = seatList.iterator();

    while (iterator.hasNext()) {
      Seat t = iterator.next();
      System.out.printf("%s, %s, %s, %s, %s\n",t.getNo(),t.getMgrade(),gradeLabel(t.getSgrade()),t.getSno(), t.getEtc());
    }
  }


}



