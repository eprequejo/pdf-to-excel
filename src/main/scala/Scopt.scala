
case class ConfigScopt(filePath: Option[String] = None, mode: Option[Int] = None)

object Scopt {

  def getArgs = {
    new scopt.OptionParser[ConfigScopt]("pdftoexcel") {

      help("help") text ("display usage information")

      opt[String]("file") abbr ("f") valueName ("<file>") text ("Path to the input file to process") action { (v, conf) =>
        conf.copy(filePath = Some(v))
      }
      opt[Int]("mode") abbr ("m") valueName ("<mode>") text ("Mode: 1 -> Swatch ; 2 -> Casio") action { (v, conf) =>
        conf.copy(mode = Some(v))
      } validate { x =>
        if (x == 1 || x == 2 || x == 3 || x == 4) success
        else failure("Option -m must be either 1 for Swatch or 2 for Casio or 3 for Fosil or 4 for Swarovsky")
      }
    }
  }

}
