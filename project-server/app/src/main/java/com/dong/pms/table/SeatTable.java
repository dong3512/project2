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
      case "seat/selectall":
        for (Seat t : list) {
          response.appendData(String.format("%d,%s,%d,%s,%s", 
              t.getNo(),
              t.getMgrade(),
              t.getSgrade(),
              t.getSno(),
              t.getEtc()));
        } 
        break;
      case "seat/select":
        int no = Integer.parseInt(request.getData().get(0));

        seat = getSeat(no);
        if (seat != null) {
          response.appendData(String.format("%d,%s,%d,%s,%s",
              seat.getNo(),
              seat.getMgrade(),
              seat.getSgrade(),
              seat.getSno(),
              seat.getEtc()));
        } else {
          throw new Exception("해당 번호의 좌석이 없습니다.");
        }
        break;
      case "seat/update":
        fields = request.getData().get(0).split(",");

        seat = getSeat(Integer.parseInt(fields[0]));
        if (seat == null) {
          throw new Exception("해당 번호의 좌석이 없습니다.");
        }

        seat.setMgrade(fields[1]);
        seat.setSgrade(Integer.parseInt(fields[2]));
        seat.setSno(fields[3]);
        seat.setEtc(fields[4]);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "seat/delete":
        no = Integer.parseInt(request.getData().get(0));
        seat = getSeat(no);
        if (seat == null) {
          throw new Exception("해당 번호의 좌석이 없습니다.");
        }

        list.remove(seat);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      default:
        throw new Exception("해당 명령을 처리 할 수 없습니다.");
    }
  }

  private Seat getSeat(int seatNo) {
    for (Seat t : list) {
      if (t.getNo() == seatNo) {
        return t;
      }
    }
    return null;
  }
}
