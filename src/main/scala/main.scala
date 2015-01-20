import PdfHandle._
import ExcelHandle._

import scalaz._
import Scalaz._

object PdfToExcel extends App {

  println("STARTING CONVERSION =======================================")

  val parser = Scopt.getArgs

  parser.parse(args, ConfigScopt()).flatMap { opts =>

    (opts.filePath |@| opts.mode) { case (file, mode) =>

      mode match {
        case 1 =>
          println("Reading swatch pdf ...")
          val data = getSwatchData(file)

          println("Building swatch excel ...")
          val book = Some(buildSwatchExcel(data.albaranNum, data.albaranDate, data.productList))
          val excelFile = s"${data.albaranNum}.xls"
          book.map(b => writeExcelToFile(b, excelFile)).getOrElse("No book created")

        case 2 =>
          println("Reading casio pdf ...")
          val data = getCasioData(file)

          println("Building casio excel ...")
          //casio list has to be flattened
          val book = Some(buildCasioExcel(data.albaranNum, data.albaranDate, data.productList.flatten))
          val excelFile = s"${data.albaranNum}.xls"
          book.map(b => writeExcelToFile(b, excelFile)).getOrElse("No book created")
      }

    }

  } getOrElse (println(parser.usage))

  println("FINISHED CONVERSION =======================================")

}
