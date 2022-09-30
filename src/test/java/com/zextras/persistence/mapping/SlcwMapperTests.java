package com.zextras.persistence.mapping;

import static org.junit.jupiter.api.Assertions.*;

import com.zextras.persistence.converters.SlcwConverter;
import com.zextras.slcwBeans.User;
import org.junit.jupiter.api.Test;

class SlcwMapperTests {

  @Test
  void shouldMapFromObject() {
    SlcwMapper mapper = new SlcwMapper();
    User user = new User("inetOrgPerson", "Name", "Surname", 6785949);
    SlcwEntry expectedEntry = new SlcwEntry("dc=example,dc=com");
    mapper.map(user, expectedEntry);
    assertEquals(user.getObjectClass(),
        expectedEntry.getFields().get("objectClass").getFiledValue());
    assertEquals(user.getPhoneNumber(), expectedEntry.getFields().get("homePhone").getFiledValue());
    assertEquals(user.getSurname(), expectedEntry.getFields().get("sn").getFiledValue());
    assertEquals(user.getName(), expectedEntry.getFields().get("givenName").getFiledValue());
    assertEquals("dc=example,dc=com", expectedEntry.getBaseDn());
    assertEquals(user.getUid(), expectedEntry.getId().getFiledValue());
  }

  @Test
  void shouldMapFromEntry() {
    SlcwMapper mapper = new SlcwMapper();
    User expectedUser = new User("inetOrgPerson", "Name", "Surname", 6785949);
    SlcwEntry entry = new SlcwEntry("dc=example,dc=com");
    mapper.map(expectedUser, entry);
    entry.setAttributes(SlcwConverter.convertFieldsToAttributes(entry));
    User actualUser = new User();
    mapper.map(entry, actualUser);
    assertEquals(expectedUser, actualUser);
  }
}