package com.dong.pms.table;

import java.io.File;
import java.util.List;
import com.dong.pms.domain.Seat;
import com.dong.util.JsonFileHandler;
import com.dong.util.Request;
import com.dong.util.Response;

public class SeatTable implements DataTable {

  File jsonFile = new File("seats.json");
  List<Seat> list;

  public SeatTable() {
    list = JsonFileHandler.loadObjects(jsonFile, Seat.class);
  }

  @Override
  public void service(Request request, Response response) throws Exception {
    Seat seat = null;
    String[] fields = null;

    switch (request.getCommand()) {
      case "seat/insert":

        fields = request.getData().get(0).split(",");
        seat = new Seat();

        if (list.size() > 0) {
          seat.setNo(list.get(list.size() - 1).getNo() + 1);
        } else {
          seat.setNo(1);
        }

        seat.setMgrade(fields[0]);
        seat.setSgrade(Integer.parseInt(fields[1]));
        seat.setSno(fields[2]);
        seat.setEtc(fields[3]);

        list.add(seat);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;

    }
  }
}
