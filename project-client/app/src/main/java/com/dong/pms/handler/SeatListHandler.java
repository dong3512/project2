package com.dong.pms.handler;

import java.util.Iterator;
import com.dong.driver.Statement;
import com.dong.pms.domain.Seat;

public class SeatListHandler implements Command{
  Statement stmt;
  public SeatListHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[좌석 목록]");

    Iterator<String> results = stmt.executeQuery("seat/selctall");


    while (results.hasNext()) {
      String[] fields = results.next().split(",");
      System.out.printf("%s, %s, %s, %s, %s\n",
          fields[0], 
          fields[1], 
          Seat.getStatusLabel(Integer.parseInt(fields[2])),
          fields[3],
          fields[4]);
    }
  }


}



