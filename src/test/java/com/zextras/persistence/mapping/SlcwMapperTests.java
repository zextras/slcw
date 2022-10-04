package com.zextras.persistence.mapping;

import static org.junit.jupiter.api.Assertions.*;

import com.zextras.persistence.SlcwException;
import com.zextras.persistence.converters.SlcwConverter;
import com.zextras.persistence.mapping.entries.SlcwEntry;
import com.zextras.persistence.mapping.mappers.SlcwMapper;
import com.zextras.slcwBeans.Ticket;
import com.zextras.slcwBeans.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SlcwMapperTests {

  private SlcwMapper mapper;
  @BeforeEach
  void setUp() {
    this.mapper = new SlcwMapper();
  }

  @Test
  void shouldMapFromObject() {
    final User user = new User("inetOrgPerson", "Name", "Surname", 6785949);
    final SlcwEntry expectedEntry = new SlcwEntry("dc=example,dc=com");
    mapper.map(user, expectedEntry);
    assertEquals(user.getObjectClass(),
        expectedEntry.getFields().get("objectClass").getPropertyValue());
    assertEquals(user.getPhoneNumber(), expectedEntry.getFields().get("homePhone").getPropertyValue());
    assertEquals(user.getSurname(), expectedEntry.getFields().get("sn").getPropertyValue());
    assertEquals(user.getName(), expectedEntry.getFields().get("givenName").getPropertyValue());
    assertEquals("dc=example,dc=com", expectedEntry.getBaseDn());
    assertEquals(user.getId(), expectedEntry.getId().getPropertyValue());
  }

  @Test
  void shouldMapFromEntry() {
    final User expectedUser = new User("inetOrgPerson", "Name", "Surname", 6785949);
    final SlcwEntry entry = new SlcwEntry("dc=example,dc=com");
    mapper.map(expectedUser, entry);
    entry.setAttributes(SlcwConverter.convertFieldsToAttributes(entry));
    final User actualUser = new User();
    mapper.map(entry, actualUser);
    assertEquals(expectedUser, actualUser);
  }

  @Test
  void shouldThrowExceptionForNotEntityClass() {
    final Ticket ticket = new Ticket();
    final SlcwEntry entry = new SlcwEntry("dc=example,dc=com");
    assertThrows(SlcwException.class, () -> mapper.map(ticket, entry));
  }
}