package com.dong.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.dong.util.Prompt;

public class ScheduleDetailHandler implements Command{
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[비행일정 상세조회]");

    int no = Prompt.inputInt("번호? ");

    out.writeUTF("schedule/select");
    out.writeInt(1);
    out.writeUTF(Integer.toString(no));
    out.flush();
    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    String[] fields = in.readUTF().split(",");

    System.out.printf("목적지: %s\n",fields[1]);
    System.out.printf("항공기번호: %s\n",fields[2]);
    System.out.printf("출발시간: %s\n", fields[3]);
    System.out.printf("도착시간: %s\n", fields[4]);
    System.out.printf("탑승자이름: %s\n", fields[5]);
    System.out.printf("조종사: %s\n", fields[6]);
  }


}