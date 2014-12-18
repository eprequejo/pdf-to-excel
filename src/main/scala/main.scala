import PdfHandle._
import ExcelHandle._

object PdfToExcel extends App {

  val mode = 2 //1 -> swatch 2 -> casio
  val fileName = "document_907611641796"

  val pdfFile = s"${fileName}.pdf"

  mode match {
    case 1 =>
      println("Reading swatch pdf ...")
      val data = getSwatchData(pdfFile)

      println("Building swatch excel ...")
      val book = Some(buildSwatchExcel(data.albaranNum, data.albaranDate, data.productList))
      val excelFile = s"${data.albaranNum}.xls"
      book.map(b => writeExcelToFile(b, excelFile)).getOrElse("No book created")

    case 2 =>
      println("Reading casio pdf ...")
      val data = getCasioData(pdfFile)

      println("Building casio excel ...")
      //casio list has to be flattened
      val book = Some(buildCasioExcel(data.albaranNum, data.albaranDate, data.productList.flatten))
      val excelFile = s"${data.albaranNum}.xls"
      book.map(b => writeExcelToFile(b, excelFile)).getOrElse("No book created")

    case _ =>
      println("Mode not found")
      None
    }

}
