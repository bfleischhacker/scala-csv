package com.github.bfly2000.csv

import org.scalatest.FlatSpec

/**
 * @author ben
 */
class DefaultCsvValueTest extends FlatSpec {
  "A positive boolean value" should "serialize to \"true\"" in {
    assert(BooleanValue.write(true) === "true")
  }

  "A negative boolean value" should "serialize to \"false\"" in {
    assert(BooleanValue.write(false) === "false")
  }

  "The integer 1" should "serialize to \"1\"" in {
    assert(IntegerValue.write(1) === "1")
  }
}
