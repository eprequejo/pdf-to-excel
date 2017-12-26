import org.apache.poi.xssf.usermodel.{ XSSFWorkbook, XSSFSheet}
import java.io.{ File, FileOutputStream }

object ExcelHandle {

  def buildFosilExcel(albaranNum: String, albaranDate: String, productList: List[String]): XSSFWorkbook = {

    //Blank workbook
    val workbook = new XSSFWorkbook()

    val sheet = createTemplate(workbook)

    //Iterate over data and write to sheet
    productList.zipWithIndex.foreach{ case(listData, rowNum) =>

      val row = sheet.createRow(rowNum + 1)

      val data = listData.split("\\s{2,}")

      val albaranNumCell = row.createCell(0)
      albaranNumCell.setCellValue(albaranNum)

      val referenciaCell = row.createCell(3)
      referenciaCell.setCellValue(data(0))

      val descriptionCell = row.createCell(4)
      val description = data(1).replaceAll("\\d","").replaceAll(",", "")
      descriptionCell.setCellValue(description)

      val unitsCell = row.createCell(5)
      unitsCell.setCellValue(data(2).toInt)

      val albaranDateCell = row.createCell(6)
      albaranDateCell.setCellValue(albaranDate.toString)

      val costCell = row.createCell(9)
      val cost = data(3).replace('.', ',')
      costCell.setCellValue(cost.toString)
    }

    workbook
  }

  def buildSwatchExcel(albaranNum: String, albaranDate: String, productList: List[List[String]]): XSSFWorkbook = {

    //Blank workbook
    val workbook = new XSSFWorkbook()

    val sheet = createTemplate(workbook)

    //Iterate over data and write to shee
    productList.zipWithIndex.foreach{ case(listData, rowNum) =>

      val row = sheet.createRow(rowNum + 1)

      //list of string with three elements
      val headElement = listData.head.split("\\s+")
      val description = listData(1).split("\\s+").toList.tail.mkString(" ")

      val albaranNumCell = row.createCell(0)
      albaranNumCell.setCellValue(albaranNum.toInt) //todo exception if it's not an int

      val referenciaCell = row.createCell(3)
      referenciaCell.setCellValue(headElement(3).toString)

      val descriptionCell = row.createCell(4)
      descriptionCell.setCellValue(description.toString)

      val unitsCell = row.createCell(5)
      unitsCell.setCellValue(headElement(5).toInt)

      val albaranDateCell = row.createCell(6)
      albaranDateCell.setCellValue(albaranDate.toString)

      val costCell = row.createCell(9)
      val cost = headElement(7).replace('.', ',')
      costCell.setCellValue(cost.toString)
    }

    workbook
  }

  def buildCasioExcel(albaranNum: String, albaranDate: String, productList: List[String]): XSSFWorkbook = {

    //Blank workbook
    val workbook = new XSSFWorkbook()

    val sheet = createTemplate(workbook)

    //Iterate over data and write to shee
    productList.zipWithIndex.foreach{ case(listData, rowNum) =>

      val row = sheet.createRow(rowNum + 1)

      val data = listData.split("\\s{2,}")// split more than one space //listData.split("\\s+")

      val albaranNumCell = row.createCell(0)
      albaranNumCell.setCellValue(albaranNum.toInt)

      val referenciaCell = row.createCell(3)
      referenciaCell.setCellValue(data(0).toString)

      val descriptionCell = row.createCell(4)
      descriptionCell.setCellValue(data(1).toString)

      val unitsCell = row.createCell(5)
      unitsCell.setCellValue(data(2).toInt)

      val albaranDateCell = row.createCell(6)
      albaranDateCell.setCellValue(albaranDate.toString)

      val costCell = row.createCell(9)
      val cost = data(3).replace('.', ',')
      costCell.setCellValue(cost.toString)
    }

    workbook
  }

  private def createTemplate(workbook: XSSFWorkbook): XSSFSheet = {

    //Create a blank sheet
    val sheet = workbook.createSheet("factura")

    //head titles
    val rowHead = sheet.createRow(0)
    rowHead.createCell(0).setCellValue("Nº ALBARAN")
    rowHead.createCell(3).setCellValue("REFERENCIA")
    rowHead.createCell(4).setCellValue("DESCRIPCION")
    rowHead.createCell(5).setCellValue("UNIDADES")
    rowHead.createCell(6).setCellValue("FECHA")
    rowHead.createCell(9).setCellValue("COSTE")

    sheet
  }

  def writeExcelToFile(workbook: XSSFWorkbook, fileName: String) = {

    val out = new FileOutputStream(new File(fileName))
    workbook.write(out)
    out.close()
    System.out.println(s"${fileName} written successfully on disk")
  }

}
