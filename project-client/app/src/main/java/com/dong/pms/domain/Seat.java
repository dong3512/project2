package com.dong.pms.domain;

public class Seat {

  private int no ;
  private String mgrade;
  private int sgrade ;
  private String sno ;
  private String etc ;

  public Seat() {}

  public Seat(String csv) {
    String[] fields = csv.split(",");
    this.setNo(Integer.parseInt(fields[0]));
    this.setMgrade(fields[1]);
    this.setSgrade(Integer.parseInt(fields[2]));
    this.setSno(fields[3]);
    this.setEtc(fields[4]);
  }

  @Override
  public String toString() {
    return "Seat [no=" + no + ", mgrade=" + mgrade + ", sgrade=" + sgrade + ", sno=" + sno
        + ", etc=" + etc + "]";
  }

  public String toCsvString() {
    return String.format("%d,%s,%d,%s,%s", 
        this.getNo(),
        this.getMgrade(),
        this.getSgrade(),
        this.getSno(),
        this.getEtc());
  }

  public static Seat valueOfCsv(String csv) {
    String[] fields = csv.split(",");
    Seat t = new Seat();
    t.setNo(Integer.parseInt(fields[0]));
    t.setMgrade(fields[1]);
    t.setSgrade(Integer.parseInt(fields[2]));
    t.setSno(fields[3]);
    t.setEtc(fields[4]);
    return t;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + no;
    result = prime * result + sgrade;
    result = prime * result + ((sno == null) ? 0 : sno.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Seat other = (Seat) obj;
    if (no != other.no)
      return false;
    if (sgrade != other.sgrade)
      return false;
    if (sno == null) {
      if (other.sno != null)
        return false;
    } else if (!sno.equals(other.sno))
      return false;
    return true;
  }


  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
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
