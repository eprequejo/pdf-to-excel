# PDF to Excel Invoices Conversion

PdfToExcel is an app inside of your **Desktop/SPApp** folder

## Steps:

* Copy the pdf invoice to PdfToExcel folder in your Desktop/SPApp
* Open a **terminal**: 
	* Spotlight - type "terminal" or
	* Applications/Utilities/Terminal.app
* Navigate to PdfToExcel folder:
	* `cd Desktop/SPApps/PdfToExcel`
* PdfToExcel app is a .jar file, to run it we type:
	* `java -jar pdftoexcel.jar` with some options
	* `java -jar pdftoexcel.jar --help` for help
* Options:
		
		-f <value> | --file <value>
        	Path to the input file to process
  		-m <value> | --mode <value>
        	Mode to work: 1 -> Swatch ; 2 -> Casio
  		--help
        	Prints this usage text

## Example:
###  Casio - "document_041211991140.pdf":
 
 	java -jar pdftoexcel.jar -f document_041211991140.pdf -m 2
 	
### Swatch - "Factura Swatch Group España_0100002924_15.01.2015 19.24.04.pdf":
 	java -jar pdftoexcel.jar -f "Factura Swatch Group España_0100002924_15.01.2015 19.24.04.pdf" -m 1
 	
 (If there are any blank spaces on the invoice name, use "" to enclose it)