package com.github.bfly2000.csv

import org.scalatest.FlatSpec

/**
 * @author ben
 */
class CsvParserTest extends FlatSpec {

  case class A(p1: Boolean, p2: Int)

  "A CsvParser for class A parse a row with columns ordered in the same order as the case class" should "" in {

    val parserA = CsvParser[A]

    assert(parserA.parse(Seq("true", "1")) === Right(A(true, 1)))
    assert(parserA.columns == Seq("p1", "p2"))
  }

}

//TODO: Find a way to force default implementations to low priority, otherwise ther will be conflicts
//
//class OverridenBooleanReadableTest extends FlatSpec {
//
//  implicit val CustomBooleanReadableValue = CsvValue.readableValue {
//    case "1" => true
//    case "0" => false
//  }
//
//  case class A(p1: Boolean)
//
//
//  "a CsvParser with an custom boolean ReadableValue definition in scope" should "sucessfully parse 1 as true" in {
//
//    val parserA = CsvParser[A]
//    assert(parserA.parse(Seq("1")) === Right(A(true)))
//  }
//}

class CustomBooleanReadableTest extends FlatSpec {


  sealed trait MyBoolean

  object True extends MyBoolean

  object False extends MyBoolean

  implicit val MyBooleanReadableValue = CsvValue.readableValue {
    case "1" => True
    case "0" => False
  }

  case class A(p1: MyBoolean, p2: Int)

  val parserA = CsvParser[A]
  "A CsvParser with a custom typeclass for ReadableValue[MyBoolean]" should "successfully parse row [1]" in {
    assert(parserA.parse(Seq("1", "1")) === Right(A(True, 1)))
  }

}
