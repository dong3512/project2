package com.dong.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.dong.pms.handler.BoardAddHandler;
import com.dong.pms.handler.BoardDeleteHandler;
import com.dong.pms.handler.BoardDetailHandler;
import com.dong.pms.handler.BoardListHandler;
import com.dong.pms.handler.BoardSearchHandler;
import com.dong.pms.handler.BoardUpdateHandler;
import com.dong.pms.handler.Command;
import com.dong.pms.handler.MemberAddHandler;
import com.dong.pms.handler.MemberDeleteHandler;
import com.dong.pms.handler.MemberDetailHandler;
import com.dong.pms.handler.MemberListHandler;
import com.dong.pms.handler.MemberUpdateHandler;
import com.dong.pms.handler.ScheduleAddHandler;
import com.dong.pms.handler.ScheduleDeleteHandler;
import com.dong.pms.handler.ScheduleDetailHandler;
import com.dong.pms.handler.ScheduleListHandler;
import com.dong.pms.handler.ScheduleUpdateHandler;
import com.dong.pms.handler.SeatAddHandler;
import com.dong.pms.handler.SeatDeleteHandler;
import com.dong.pms.handler.SeatDetailHandler;
import com.dong.pms.handler.SeatListHandler;
import com.dong.pms.handler.SeatUpdateHandler;
import com.dong.util.Prompt;

public class ClientApp {

  ArrayDeque<String> commandStack = new ArrayDeque<>();
  LinkedList<String> commandQueue = new LinkedList<>();

  String serverAddress;
  int port;

  public static void main(String[] args) {
    ClientApp app = new ClientApp("localhost", 8888);
    app.execute();
  }

  public ClientApp(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;
  }

  public void execute() {


    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddHandler());
    commandMap.put("/board/list", new BoardListHandler());
    commandMap.put("/board/detail", new BoardDetailHandler());
    commandMap.put("/board/update", new BoardUpdateHandler());
    commandMap.put("/board/delete", new BoardDeleteHandler());

    commandMap.put("/member/add", new MemberAddHandler());
    commandMap.put("/member/list", new MemberListHandler());
    commandMap.put("/member/detail", new MemberDetailHandler());
    commandMap.put("/member/update", new MemberUpdateHandler());
    commandMap.put("/member/delete", new MemberDeleteHandler());

    commandMap.put("/schedule/add", new ScheduleAddHandler());
    commandMap.put("/schedule/list", new ScheduleListHandler());
    commandMap.put("/schedule/detail", new ScheduleDetailHandler());
    commandMap.put("/schedule/update", new ScheduleUpdateHandler());
    commandMap.put("/schedule/delete", new ScheduleDeleteHandler());

    commandMap.put("/seat/add", new SeatAddHandler());
    commandMap.put("/seat/list", new SeatListHandler());
    commandMap.put("/seat/detail", new SeatDetailHandler());
    commandMap.put("/seat/update", new SeatUpdateHandler());
    commandMap.put("/seat/delete", new SeatDeleteHandler());

    commandMap.put("/board/search", new BoardSearchHandler());

    try (Socket socket = new Socket(this.serverAddress, this.port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream())){

      System.out.println("[항공사 회원관리프로그램]");

      loop:
        while(true) {
          String command = com.dong.util.Prompt.inputString("명령> ");

          if (command.length() == 0)
            continue;

          commandStack.push(command);
          commandQueue.offer(command);

          try {
            switch (command) {
              case "history":
                printCommandHistory(commandStack.iterator());
                break;
              case "history2":
                printCommandHistory(commandQueue.iterator());
                break;
              case "quit":
              case "exit":

                out.writeUTF("quit");
                out.writeInt(0);
                out.flush();

                in.readUTF();
                in.readInt();

                System.out.println("사용해주셔서 감사합니다.");
                break loop;
              default:
                Command commandHandler = commandMap.get(command);

                if (commandHandler == null) {
                  System.out.println("실행할 수 없는 명령입니다.");
                } else {
                  commandHandler.service(in, out);
                }
            }
          } catch (Exception e) {
            System.out.println("------------------------------------------");
            System.out.printf("명령어 실행 중 오류 발생: %s\n", 
                e.getMessage());
            System.out.println("------------------------------------------");
          }
          System.out.println();
        }

    } catch (Exception e) {
      System.out.println("서버와 통신 하는 중에 오류 발생!");
    }

    Prompt.close();
  }

  static void printCommandHistory(Iterator<String> iterator) {

    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++count % 5) == 0) {
        String input = Prompt.inputString(": ");
        if (input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }
}

