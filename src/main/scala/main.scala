import PdfHandle._
import ExcelHandle._

import java.io.File

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
          val excelFile = buildNameFile(data.albaranNum)
          book.map(b => writeExcelToFile(b, excelFile)).getOrElse("No book created")

        case 2 =>
          println("Reading casio pdf ...")
          val data = getCasioData(file)

          println("Building casio excel ...")
          //casio list has to be flattened
          val book = Some(buildCasioExcel(data.albaranNum, data.albaranDate, data.productList.flatten))

          //checking if the file already exists, if so we'll change the name to keep both
          val excelFile = buildNameFile(data.albaranNum)
          book.map(b => writeExcelToFile(b, excelFile)).getOrElse("No book created")
      }

      println("FINISHED CONVERSION =======================================")

    }

  } getOrElse (println(parser.usage))

  private def buildNameFile(name: String): String = {
    val fileName = s"${name}.xls"
    val file = new File(fileName)
    if(file.exists) s"${name}_2.xls" else fileName
  }

}
