package com.dong.pms.domain;

public class Seat {

  private int no ;
  private String guest;
  private String mgrade;
  private int sgrade ;
  private String sno ;
  private String etc ;

  @Override
  public String toString() {
    return "Seat [no=" + no + ", guest=" + guest + ", mgrade=" + mgrade + ", sgrade=" + sgrade
        + ", sno=" + sno + ", etc=" + etc + "]";
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getGuest() {
    return guest;
  }
  public void setGuest(String guest) {
    this.guest = guest;
  }
  public String getMgrade() {
    return mgrade;
  }
  public void setMgrade(String mgrade) {
    this.mgrade = mgrade;
  }
  public int getSgrade() {
    return sgrade;
  }
  public void setSgrade(int sgrade) {
    this.sgrade = sgrade;
  }
  public String getSno() {
    return sno;
  }
  public void setSno(String sno) {
    this.sno = sno;
  }
  public String getEtc() {
    return etc;
  }
  public void setEtc(String etc) {
    this.etc = etc;
  }

  public static String getStatusLabel(int status) {
    switch (status) {
      case 1:
        return "비즈니스클래스";
      case 2:
        return "이코노미클래스";
      default:
        return "퍼스트클래스";
    }
  }
}
