package com.zextras.persistence.converters;

import com.zextras.persistence.mapping.SlcwProperty;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;

class SlcwConverterTests {


  @BeforeEach
  void setUp() {
    Map<String, SlcwProperty> fields = new HashMap<>();
    fields.put("givenName", new SlcwProperty("name", "Name"));
    fields.put("sn", new SlcwProperty("surname", "Surname"));
    fields.put("homePhone", new SlcwProperty("phoneNumber", 675479980));
  }
}