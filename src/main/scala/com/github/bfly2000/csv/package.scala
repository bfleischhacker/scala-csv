package com.github.bfly2000

import com.github.bfly2000.csv.CsvValue

/**
 * User: ben
 */
trait LowPriorityImplicit {

  import CsvValue.csvValue

  //Default Implementations of primitive values
  implicit val BooleanValue = csvValue[Boolean](_.toBoolean, _.toString)
  implicit val StringValue  = csvValue[String](identity, identity)
  implicit val IntegerValue = csvValue[Int](_.toInt, _.toString)
  implicit val FloatValue   = csvValue[Float](_.toFloat, _.toString)
  implicit val DoubleValue  = csvValue[Double](_.toDouble, _.toString)
  implicit val BigIntValue  = csvValue[BigInt](BigInt.apply, _.toString)


}

trait Implicits extends LowPriorityImplicit {}

package object csv extends Implicits {
}
