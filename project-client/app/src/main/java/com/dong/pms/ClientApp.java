package com.dong.pms;

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
import com.dong.pms.handler.MemberValidator;
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
    try {
      app.execute();

    }catch (Exception e) {
      System.out.println("클라이언트 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  public ClientApp(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;
  }

  public void execute() throws Exception{

    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddHandler());
    commandMap.put("/board/list", new BoardListHandler());
    commandMap.put("/board/detail", new BoardDetailHandler());
    commandMap.put("/board/update", new BoardUpdateHandler());
    commandMap.put("/board/delete", new BoardDeleteHandler());
    commandMap.put("/board/search", new BoardSearchHandler());

    commandMap.put("/member/add", new MemberAddHandler(stmt));
    commandMap.put("/member/list", new MemberListHandler(stmt));
    commandMap.put("/member/detail", new MemberDetailHandler(stmt));
    commandMap.put("/member/update", new MemberUpdateHandler(stmt));
    commandMap.put("/member/delete", new MemberDeleteHandler(stmt));

    MemberValidator memberValidator = new MemberValidator(stmt);

    commandMap.put("/schedule/add", new ScheduleAddHandler(stmt, memberValidator));
    commandMap.put("/schedule/list", new ScheduleListHandler(stmt));
    commandMap.put("/schedule/detail", new ScheduleDetailHandler(stmt));
    commandMap.put("/schedule/update", new ScheduleUpdateHandler(stmt, memberValidator));
    commandMap.put("/schedule/delete", new ScheduleDeleteHandler(stmt));

    commandMap.put("/seat/add", new SeatAddHandler(stmt));
    commandMap.put("/seat/list", new SeatListHandler(stmt));
    commandMap.put("/seat/detail", new SeatDetailHandler(stmt));
    commandMap.put("/seat/update", new SeatUpdateHandler(stmt));
    commandMap.put("/seat/delete", new SeatDeleteHandler(stmt));

    try{

      System.out.println("[항공사 회원관리프로그램]");

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

              stmt.executeUpdate("quit");

              System.out.println("사용해주셔서 감사합니다.");
              return;
            default:
              Command commandHandler = commandMap.get(command);

              if (commandHandler == null) {
                System.out.println("실행할 수 없는 명령입니다.");
              } else {
                commandHandler.service();
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
    stmt.close();
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

