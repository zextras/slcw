package com.zextras.persistence.converters;

import com.unboundid.ldap.sdk.Attribute;
import com.zextras.slcwBeans.User;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class SlcwConverterTests {

  @Test
  public void shouldMapBeanToAttributes() {
    final User user = new User();
    user.setId("aaa"); // cn
    user.setDn("aaa,bb,ccc"); //dn -> this is not an attribute
    user.setPhoneNumber(3987654); // homephone
    user.setName("name"); //givenName
    user.setSurname("surname"); //sn
    final List<Attribute> attributes = SlcwConverter.convertBeanToAttributes(user);
    Assert.assertEquals(4, attributes.size());
    attributes.forEach(
        attribute -> {
          switch (attribute.getName()) {
            case "cn":
              Assert.assertEquals("aaa", attribute.getValue());
              break;
            case "homephone":
              Assert.assertEquals("3987654", attribute.getValue());
              break;
            case "givenName":
              Assert.assertEquals("name", attribute.getValue());
              break;
            case "sn":
              Assert.assertEquals("surname", attribute.getValue());
              break;
            case "objectclass":
              Assert.assertEquals("inetOrgPerson", attribute.getValue());
              break;
          }

        });
  }
}