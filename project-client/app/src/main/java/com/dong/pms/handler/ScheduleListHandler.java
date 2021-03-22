package com.dong.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ScheduleListHandler implements Command{
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[비행일정 목록]");

    out.writeUTF("schedule/selectall");
    out.writeInt(0);
    out.flush();

    String status = in.readUTF();
    int length = in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    for (int i = 0; i < length; i++) {
      String[] fields = in.readUTF().split(",");
      System.out.printf("%s, %s, %s, %s, %s, %s\n",
          fields[0], 
          fields[1], 
          fields[2],
          fields[3],
          fields[4],
          fields[5]);

    }
  }
}