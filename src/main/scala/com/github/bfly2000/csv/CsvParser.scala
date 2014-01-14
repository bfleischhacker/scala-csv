package com.github.bfly2000.csv

import scala.language.experimental.macros

/**
 * @author ben
 */
trait CsvParser[T] {
  val columns: Seq[String]

//  protected lazy val order: Seq[Option[Int]] = columns.zipWithIndex.map(_._2).map(Some.apply)

  def parse(line: Seq[String]): Either[CsvParseException, T]

  //
  //  def withHeader(header: Seq[String]): HeaderReorderedCsvParser[T] =
  //    new HeaderReorderedCsvParser[T](this, header)
}

object CsvParser {
  def apply[T]: CsvParser[T] = macro Macros.materializeCsvParser_impl[T]
}

//
//class HeaderReorderedCsvParser[T](csvParser: CsvParser[T], val header: Seq[String]) extends CsvParser[T] {
//  private[this] lazy val order: Seq[Option[Int]] = header.map(columns.zipWithIndex.toMap.get)
//
//  private[this] def reorder(line: Seq[String]): Seq[String] = line.zip(order).flatMap {
//    case (col, Some(idx)) => Some(col -> idx)
//    case (_, None) => None
//  }.sortBy(_._2).map(_._1)
//
//  lazy val columns: Seq[String] = csvParser.columns
//
//  def parse(line: Seq[String]): T = csvParser.parse(reorder(line))
//}

