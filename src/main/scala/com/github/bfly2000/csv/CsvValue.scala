package com.github.bfly2000.csv

/**
 * User: ben
 */
trait ReadableValue[T] {
  def read(s: String): T
}

trait WritableValue[T] {
  def write(v: T): String
}

object CsvValue {
  def readableValue[T](f: String => T): ReadableValue[T] = new ReadableValue[T] {
    def read(s: String): T = f(s)
  }

  def writableValue[T](f: T => String): WritableValue[T] = new WritableValue[T] {
    def write(v: T): String = f(v)
  }

  def csvValue[T](rf: String => T, wf: T => String) = new ReadableValue[T] with WritableValue[T] {
    def read(s: String): T = rf(s)

    def write(v: T): String = wf(v)
  }
}