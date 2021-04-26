package com.dong.pms.domain;

import java.util.List;

public class Seat {

  private int no ;
  private Member guest;
  private String mgrade;
  private int sgrade ;
  private String sno ;
  private String etc ;
  private List<Member> party;

  @Override
  public String toString() {
    return "Seat [no=" + no + ", guest=" + guest + ", mgrade=" + mgrade + ", sgrade=" + sgrade
        + ", sno=" + sno + ", etc=" + etc + ", party=" + party + "]";
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public Member getGuest() {
    return guest;
  }
  public void setGuest(Member guest) {
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
  public List<Member> getParty() {
    return party;
  }
  public void setParty(List<Member> party) {
    this.party = party;
  }

  public static String getStatusLabel(int sgrade) {
    switch (sgrade) {
      case 1:
        return "퍼스트";
      case 2:
        return "비즈니스";
      default:
        return "이코노미";
    }
  }

}
