package com.dong.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.dong.pms.domain.Schedule;
import com.dong.util.Prompt;

public class ScheduleAddHandler implements Command{
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[비행일정]");

    Schedule s = new Schedule();

    s.setDestination(Prompt.inputString("목적지: "));
    s.setAirno(Prompt.inputString("항공기번호: "));
    s.setDtime(Prompt.inputTime("출발시간: "));
    s.setAtime(Prompt.inputTime("도착시간: "));

    s.setName(MemberValidator.inputMember("탑승자?(취소: 빈 문자열) ", in, out));
    if (s.getName() == null) {
      System.out.println("비행일정 입력을 취소합니다.");
      return;
    }
    s.setPilot(Prompt.inputString("조종사: "));

    out.writeUTF("schedule/insert");
    out.writeInt(1);
    out.writeUTF(String.format("%s,%s,%s,%s,%s,%s", 
        s.getDestination(),
        s.getAirno(),
        s.getDtime(),
        s.getAtime(),
        s.getName(),
        s.getPilot()));
    out.flush();

    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }
    System.out.println("비행일정을 등록했습니다.");

  }

}