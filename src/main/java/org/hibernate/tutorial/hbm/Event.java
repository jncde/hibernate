package org.hibernate.tutorial.hbm;

import java.util.Date;

public class Event {

  private Long   id;

  private String title;
  private Date   date;

  //  private Set    participants = new HashSet ();

  public Event () {
  }

  public Long getId () {
    return id;
  }

  private void setId (Long id) {
    this.id = id;
  }

  //  public Set getParticipants () {
  //    return participants;
  //  }
  //
  //  public void setParticipants (Set participants) {
  //    this.participants = participants;
  //  }

  public Date getDate () {
    return date;
  }

  public void setDate (Date date) {
    this.date = date;
  }

  public String getTitle () {
    return title;
  }

  public void setTitle (String title) {
    this.title = title;
  }
}
