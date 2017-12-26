import PdfHandle._
import ExcelHandle._
import java.io.File

object PdfToExcel extends App {

  val parser = Scopt.getArgs

  parser.parse(args, ConfigScopt()).map { opts =>

    (opts.filePath, opts.mode) match {

      case (Some(file), Some(mode)) =>

        println("STARTING CONVERSION =======================================")

        mode match {
          case 1 => runSwatchJob(file)
          case 2 => runCasioJob(file)
          case 3 => runFosilJob(file)
        }

        println("FINISHED CONVERSION =======================================")

      case _ => println("Error: file or mode not defined")

    }

  } getOrElse (println(parser.usage))

  private def runSwatchJob(file: String): Unit = {
    println("Reading swatch pdf ...")
    val data = getSwatchData(file)

    println("Building swatch excel ...")
    val book = Some(buildSwatchExcel(data.albaranNum, data.albaranDate, data.productList))
    val excelFile = buildNameFile(data.albaranNum)
    book.map(b => writeExcelToFile(b, excelFile)).getOrElse("No book created")
  }

  private def runCasioJob(file: String): Unit = {
    println("Reading casio pdf ...")
    val data = getCasioData(file)

    println("Building casio excel ...")
    //casio list has to be flattened
    val book = Some(buildCasioExcel(data.albaranNum, data.albaranDate, data.productList.flatten))

    //checking if the file already exists, if so we'll change the name to keep both
    val excelFile = buildNameFile(data.albaranNum)
    book.map(b => writeExcelToFile(b, excelFile)).getOrElse("No book created")
  }

  private def runFosilJob(file: String): Unit = {
    println("Reading fosil pdf ...")
    val data = getFosilData(file)

    println("Building fosil excel ...")
    val book = Some(buildFosilExcel(data.albaranNum, data.albaranDate, data.productList.flatten))
    val excelFile = buildNameFile(data.albaranNum)
    book.map(b => writeExcelToFile(b, excelFile)).getOrElse("No book created")
  }

  private def buildNameFile(name: String): String = {
    val fileName = s"${name}.xls"
    val file = new File(fileName)
    if(file.exists) s"${name}_2.xls" else fileName
  }

}
