package com.dong.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.dong.pms.domain.Seat;
import com.dong.util.Prompt;

public class SeatDetailHandler implements Command{
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[좌석 상세보기]");

    int no = Prompt.inputInt("번호? ");

    out.writeUTF("seat/select");
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

    System.out.printf("회원등급: %s\n", fields[1]);
    System.out.printf("좌석등급: %s\n", Seat.getStatusLabel(Integer.parseInt(fields[2])));
    System.out.printf("좌석번호: %s\n", fields[3]);
    System.out.printf("특이사항: %s\n", fields[4]);
  }
}


