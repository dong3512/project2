package com.dong.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.dong.util.Prompt;

public class ScheduleUpdateHandler implements Command{
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[비행일정 수정]");

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

    String destination = Prompt.inputString(String.format("목적지(%s) ",fields[1]));
    String airno = Prompt.inputString(String.format("항공기번호(%s)", fields[2]));
    String dtime = Prompt.inputString(String.format("출발시간(%s)", fields[3]));
    String atime = Prompt.inputString(String.format("도착시간(%s) ", fields[4]));
    String name = MemberValidator.inputMember(String.format("탑승자(%s)?(취소: 빈 문자열) ", fields[5]), in, out);
    if (name == null) {
      System.out.println("비행일정 수정을 취소합니다.");
      return;
    }
    String pilot = Prompt.inputString(String.format("조종사(%s) ", fields[6]));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");

    if(!input.equalsIgnoreCase("Y")) {
      System.out.println("비행일정 변경을 취소하였습니다.");
      return;
    }

    out.writeUTF("schedule/update");
    out.writeInt(1);
    out.writeUTF(String.format("%d,%s,%s,%s,%s,%s,%s",
        no, destination, airno, dtime, atime, name, pilot));
    out.flush();

    status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("비행일정을 변경하였습니다.");
  }

}
