package com.dong.pms.domain;

import java.sql.Time;

public class Schedule {
  private int no;
  private String destination ;
  private String airno ;
  private Member guest;
  private Time dtime ;
  private Time atime ;
  private String pilot;

  @Override
  public String toString() {
    return "Schedule [no=" + no + ", destination=" + destination + ", airno=" + airno + ", guest="
        + guest + ", dtime=" + dtime + ", atime=" + atime + ", pilot=" + pilot + "]";
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getDestination() {
    return destination;
  }
  public void setDestination(String destination) {
    this.destination = destination;
  }
  public String getAirno() {
    return airno;
  }
  public void setAirno(String airno) {
    this.airno = airno;
  }
  public Member getGuest() {
    return guest;
  }
  public void setGuest(Member guest) {
    this.guest = guest;
  }
  public Time getDtime() {
    return dtime;
  }
  public void setDtime(Time dtime) {
    this.dtime = dtime;
  }
  public Time getAtime() {
    return atime;
  }
  public void setAtime(Time atime) {
    this.atime = atime;
  }
  public String getPilot() {
    return pilot;
  }
  public void setPilot(String pilot) {
    this.pilot = pilot;
  }



}
