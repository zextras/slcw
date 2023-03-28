package com.zextras.slcwBeans;

import com.zextras.persistence.annotations.Id;
import com.zextras.persistence.annotations.Table;

@Table(objectClass = "dcObject", property = "ou", name = "tickets")
public class Ticket {
  @Id
  Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
