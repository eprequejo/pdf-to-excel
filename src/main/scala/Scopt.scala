
case class ConfigScopt(filePath: Option[String] = None, mode: Option[Int] = None)

object Scopt {

  def getArgs = {
    new scopt.OptionParser[ConfigScopt]("pdftoexcel") {
      head("scopt", "3.x")
      opt[String]('f', "file") action { (x, c) => c.copy(filePath = Option(x)) } text("Path to the input file to process")
      opt[Int]('m', "mode") action { 
        (x, c) => c.copy(mode = Option(x)) 
      } validate { x =>
        if (x == 1 || x == 2) success else failure("Option -m must be either 1 for Swatch or 2 for Casio")
      } text("Mode to work: 1 -> Swatch ; 2 -> Casio")
      help("help") text("Prints this usage text")
    }
  }

}

