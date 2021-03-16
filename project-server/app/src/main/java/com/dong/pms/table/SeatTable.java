package com.dong.pms.table;

import com.dong.pms.domain.Seat;

public class SeatTable implements DataTable {

  File jsonFile = new File("seats.json");
  List<Seat> list;
}
