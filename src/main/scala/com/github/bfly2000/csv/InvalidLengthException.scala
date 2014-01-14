package com.github.bfly2000.csv

/**
 * Created with IntelliJ IDEA.
 * User: ben
 */
sealed trait CsvParseException extends Exception

class InvalidLengthException(val expectedLength: Int, val actualLength: Int) extends CsvParseException {
  val message = s"invalid csv row length $actualLength, expected length of $expectedLength"
}
