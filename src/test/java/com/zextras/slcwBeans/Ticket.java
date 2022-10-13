package com.zextras.slcwBeans;

import com.zextras.persistence.annotations.Id;
import com.zextras.persistence.annotations.Table;

@Table(property = "ou", name = "tickets")
public class Ticket {
  @Id
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }
}
