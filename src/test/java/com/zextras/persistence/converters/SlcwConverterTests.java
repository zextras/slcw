package com.zextras.persistence.converters;

import static org.junit.jupiter.api.Assertions.*;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.zextras.persistence.mapping.entries.SlcwEntry;
import com.zextras.persistence.mapping.SlcwProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SlcwConverterTests {

  private SlcwEntry entry;

  @BeforeEach
  void setUp() {
    entry = new SlcwEntry("dc=example,dc=com");
    final Map<String, SlcwProperty> fields = new HashMap<>();
    fields.put("givenName", new SlcwProperty("name", "Name"));
    fields.put("sn", new SlcwProperty("surname", "Surname"));
    fields.put("homePhone", new SlcwProperty("phoneNumber", 675479980));
    entry.setFields(fields);
  }

  @Test
  void shouldConvertFieldsToAttributes() {
    final List<Attribute> attributes = SlcwConverter.convertFieldsToAttributes(entry);
    assertTrue(attributes.contains(new Attribute("givenName", "Name")));
    assertTrue(attributes.contains(new Attribute("sn", "Surname")));
    assertTrue(attributes.contains(new Attribute("homePhone", "675479980")));
  }

  @Test
  void shouldConvertFieldsToModifications() {
    final List<Modification> modifications = SlcwConverter.convertFieldsToModifications(entry);
    assertTrue(modifications.contains(
        new Modification(ModificationType.REPLACE, "givenName", "Name")));
    assertTrue(modifications.contains(
        new Modification(ModificationType.REPLACE, "sn", "Surname")));
    assertTrue(modifications.contains(
        new Modification(ModificationType.REPLACE, "homePhone", "675479980")));
  }
}