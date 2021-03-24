package com.dong.pms;

import java.net.ServerSocket;
import java.util.HashMap;
import com.dong.pms.table.BoardTable;
import com.dong.pms.table.DataTable;
import com.dong.pms.table.MemberTable;
import com.dong.pms.table.ScheduleTable;
import com.dong.pms.table.SeatTable;

public class ServerApp {

  int port;
  HashMap<String,DataTable> tableMap = new HashMap<>();

  public static void main(String[] args) {
    ServerApp app = new ServerApp(8888);
    app.service();
  }

  public ServerApp(int port) {
    this.port = port;
  }

  public void service() {

    tableMap.put("board/", new BoardTable());
    tableMap.put("member/", new MemberTable());
    tableMap.put("schedule/", new ScheduleTable());
    tableMap.put("seat/", new SeatTable());

    try (ServerSocket serverSocket = new ServerSocket(this.port)){

      System.out.println("서버 실행!");

      while (true) {
        new StatementHandlerThread(serverSocket.accept(),tableMap).start();
      }

    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

}
