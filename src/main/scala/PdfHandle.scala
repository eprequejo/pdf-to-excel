import com.itextpdf.text.pdf.parser.PdfTextExtractor
import com.itextpdf.text.pdf.PdfReader

import java.io.FileWriter

import org.apache.commons.lang3.StringUtils.{ contains }

import scala.collection.JavaConversions._
import scala.io.Source

object PdfHandle {

  //SWATCH/////////////////////////////////////
  def getSwatchData(dir: String): ModelData = {
    val reader = new PdfReader(dir)

    val (albaranNum, albaranDate) = getSwatchAlbaranNumAndDate(reader)
    println(s"albaran num: ${albaranNum} - albaran date: ${albaranDate}")
    val productList = getSwatchProductList(reader)
    println(s"${productList.length} product lines to proccess")

    ModelData(albaranNum, albaranDate, productList)
  }

  private def getSwatchAlbaranNumAndDate(reader: PdfReader): (String, String) = {

    val lines = pdfPageReader(reader, 1)

    val data = lines
      .dropWhile(_ != "Enviado por Número envío Condiciones de envío Número de albarán/Fecha de albarán")
      .takeWhile(_ != "N° Pedido / Linea Referencia Unidades Unidades PVP Precio Precio neto Total neto")
      .drop(1)

    val arrayData = data.headOption.map(_.split("\\s+"))
    arrayData.map(s => (s(s.size - 3), s(s.size - 1))).getOrElse{
      println("Error getting swatch albaran num and date, arrayData is none")
      ("", "")
    }
  }

  private def getSwatchProductList(reader: PdfReader): List[List[String]] = {

    val numPages = reader.getNumberOfPages
    println(s"Number of pdf pages: ${numPages}")

    val list = for {
      page <- List.range(1, numPages + 1)
    } yield {

      val lines = pdfPageReader(reader, page)

      lines
        .dropWhile(_ != "Fecha pedido Código de barrras / número de máquina EUR EUR")
        .takeWhile(_ != "__________________________________________________________________________")
        .drop(1)
        .grouped(3).toList //.take(1) //testing
    }

    if(list.isEmpty) {
      println("No data to process")
      List()
    } else list.reduce(_ ++ _)

  }

  //CASIO/////////////////////////////////////////
  def getCasioData(dir: String): ModelData = {

    val reader = new PdfReader(dir)

    val albaranNum = getCasioAlbaranNum(reader)
    println(s"albaran num: ${albaranNum}")
    val albaranDate = getCasioAlbaranDate(reader)
    println(s"albaran date: ${albaranDate}")
    val productList = getCasioProductList(reader)
    productList.foreach(s => s.foreach(println))
    //casio product list has to be flattened
    println(s"${productList.flatten.length} product lines to proccess")

    ModelData(albaranNum, albaranDate, productList)
  }

  private def getCasioAlbaranNum(reader: PdfReader): String = {
    val lines = pdfPageReader(reader, 1)

    val data = lines
      .dropWhile(_ != "No. pedido Fecha de pedido Descripción de pedido No. Cliente No. Factura Fecha de envío-factura")
      .drop(8)

    val arrayData = data.headOption.map(_.split("\\s+"))
    arrayData.map(s => s(2)).getOrElse{
      println("Error getting casio albaran num, arrayData is none")
      ""
    }
  }

  private def getCasioAlbaranDate(reader: PdfReader): String = {
    val lines = pdfPageReader(reader, 1)

    val data = lines
      .dropWhile(_ != "No. pedido Fecha de pedido Descripción de pedido No. Cliente No. Factura Fecha de envío-factura")
      .drop(3)

    val arrayData = data.headOption.map(_.split("\\s+"))
    arrayData.map(s => s(s.size-1)).getOrElse{
      println("Error getting casio albaran date, arrayData is none")
      ""
    }
  }

  private def getCasioProductList(reader: PdfReader): List[List[String]] = {

    val numPages = reader.getNumberOfPages
    println(s"Number of pdf pages: ${numPages}")

    for {
      page <- List.range(1, numPages + 1)
    } yield {

      val lines = pdfPageReader(reader, page)

      val init =
      if(lines.exists( l => l.contains("PEDIDO URGENTE"))) lines.dropWhile(!_.contains("PEDIDO URGENTE")).drop(2)
      else if(lines.exists( l => l.contains("Brought Forward"))) lines.dropWhile(!_.contains("Brought Forward")).drop(1)
      else lines.dropWhile(_ != "No. Artículo Descripción No. Artículo cliente Cantidad Precio unitario Total").drop(2)

      val content =
      if(init.exists( l => l.contains("Order Total"))) init.takeWhile(!_.contains("Order Total")).dropRight(1)
      else if(init.exists( l => l.contains("Carried Forward"))) init.takeWhile(!_.contains("Carried Forward"))
      else List()

      content

    }
  }

  private def pdfPageReader(reader: PdfReader, page: Int): List[String] = {
    println(s"Reading page ${page} ...")

    val data: String = PdfTextExtractor.getTextFromPage(reader, page)
    data.split('\n').toList

  }

  case class ModelData(albaranNum: String, albaranDate: String, productList: List[List[String]])

}
