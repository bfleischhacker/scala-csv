package com.github.bfly2000.csv

/**
 * @author ben
 */


object Macros {

  import reflect.macros._

  def materializeCsvParser_impl[T: c.WeakTypeTag](c: Context): c.Expr[CsvParser[T]] = {
    import c.universe._

    val tpe = weakTypeOf[T]

    val tpeClass = tpe.typeSymbol.asClass

    val className = tpeClass.fullName

    if (!tpe.typeSymbol.asClass.isCaseClass) {
      c.error(c.enclosingPosition, s"Error building csv parser for: $className, it is not a case class")
    }

    val columnNames = tpe.declarations.collect {
      case m: MethodSymbol if m.isCaseAccessor => m.name.decoded
    }

    val columnTypes = tpe.declarations.collect {
      case m: MethodSymbol if m.isCaseAccessor => m.typeSignature.typeSymbol
    }

    val companion = tpe.typeSymbol.companionSymbol

    def readableValue(tt: Type) = {
      def rv[TT: WeakTypeTag] = c.weakTypeOf[ReadableValue[TT]]
      rv(c.WeakTypeTag(tt))
    }

    //    c.echo(c.enclosingPosition, s"${tq"${readableValue(c.weakTypeOf[String])}"}")

    val parseFuncs = columnTypes.zipWithIndex.map {
      case (columnTpe, idx) => {
//        val readableType = c.inferImplicitValue(readableValue(columnTpe.asType.toType)).symbol
//        q"$readableType.read(line($idx))"
        q"implicitly[ReadableValue[$columnTpe]].read(line($idx))"
      }
    }.toList

    val parserReturnType = c.weakTypeOf[Either[CsvParseException, T]]

    val InvalidLengthException = c.weakTypeOf[InvalidLengthException].typeSymbol.asClass

    c.Expr[CsvParser[T]]( q"""
    new CsvParser[$tpeClass] {
      val columns: Seq[String] = Seq(..$columnNames)
      def parse(line: Seq[String]): $parserReturnType = {
        if (line.size != ${parseFuncs.size}) {
          Left(new $InvalidLengthException(${parseFuncs.size}, line.size))
        } else {
          Right($companion(..$parseFuncs))
        }
      }
    }""")
  }

}
