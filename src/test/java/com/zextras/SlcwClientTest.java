package com.zextras;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/** Unit test for simple SlcwClient. */
@Execution(ExecutionMode.CONCURRENT)
public class SlcwClientTest {
  /** Rigorous Test :-) */
  @Test
  public void shouldAnswerWithTrue() {
    Assertions.assertTrue(true);
  }
}
