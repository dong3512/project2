package com.dong.pms.domain;

import java.sql.Date;

public class Board {

  private int no;
  private String title;
  private String content;
  private String message;
  private String writer;
  private Date registeredDate;
  private int viewCount;

  public Board() {}

  public Board(String csv) {
    String[] fields = csv.split(",");
    this.setNo(Integer.parseInt(fields[0]));
    this.setTitle(fields[1]);
    this.setContent(fields[2]);
    this.setMessage(fields[3]);
    this.setWriter(fields[4]);
    this.setRegisteredDate(Date.valueOf(fields[5]));
    this.setViewCount(Integer.parseInt(fields[6]));

  }

  @Override
  public String toString() {
    return "Board [no=" + no + ", title=" + title + ", content=" + content
        + ", message=" + message + ", writer=" + writer + ", registeredDate=" + registeredDate
        + ", viewCount=" + viewCount + "]";
  }

  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%S,%d",
        this.getNo(),
        this.getTitle(),
        this.getContent(),
        this.getMessage(),
        this.getWriter(),
        this.getRegisteredDate().toString(),
        this.getViewCount());
  }

  public static Board valueOfCsv(String csv) {
    String [] fields = csv.split(",");
    Board b = new Board();
    b.setNo(Integer.parseInt(fields[0]));
    b.setTitle(fields[1]);
    b.setContent(fields[2]);
    b.setMessage(fields[3]);
    b.setWriter(fields[4]);
    b.setRegisteredDate(Date.valueOf(fields[5]));
    b.setViewCount(Integer.parseInt(fields[6]));
    return b;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + no;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((writer == null) ? 0 : writer.hashCode());
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
    Board other = (Board) obj;
    if (no != other.no)
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (writer == null) {
      if (other.writer != null)
        return false;
    } else if (!writer.equals(other.writer))
      return false;
    return true;
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public String getWriter() {
    return writer;
  }
  public void setWriter(String writer) {
    this.writer = writer;
  }
  public Date getRegisteredDate() {
    return registeredDate;
  }
  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }


}
