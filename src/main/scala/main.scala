import PdfHandle._
import ExcelHandle._

import scalaz._
import Scalaz._

object PdfToExcel extends App {

  val parser = Scopt.getArgs

  parser.parse(args, ConfigScopt()).flatMap { opts =>

    (opts.filePath |@| opts.mode) { case (file, mode) =>

      println("STARTING CONVERSION =======================================")

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

      println("FINISHED CONVERSION =======================================")

    }

  } getOrElse (println(parser.usage))

}
