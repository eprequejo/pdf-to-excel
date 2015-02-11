# PDF to Excel Invoices Conversion

PdfToExcel is an app inside of your **Desktop/SPApp** folder

## Steps:

* Copy the pdf to PdfToExcel folder in your Desktop/SPApp
* Open a **terminal**: 
	* Spotlight - type "terminal" or
	* Applications/Utilities/Terminal.app
* Navigate to PdfToExcel folder:
	* `cd Desktop/SPApps/PdfToExcel`
* PdfToExcel app is a .jar file, to run it we type:
	* `java -jar pdftoexcel.jar` with some options
	* `java -jar pdftoexcel.jar --help` for help
* Options:
	* -f or --file: file to convert: `/Users/felipesanchezpombo/Desktop/SPApps/PdfToExcel/name_pdf_file.pdf`
	* -m or --mode: mode to work: `1 for Swatch, 2 for Casio`

## Example:
###  Casio - "document_041211991140.pdf":
 `java -jar pdftoexcel.jar -f "/Users/felipesanchezpombo/Desktop/SPApps/PdfToExcel/document_041211991140.pdf" -m 2`
### Swatch - "Factura Swatch Group España_0100002924_15.01.2015 19.24.04.pdf":
 `java -jar pdftoexcel.jar -f "/Users/felipesanchezpombo/Desktop/SPApps/PdfToExcel/Factura Swatch Group España_0100002924_15.01.2015 19.24.04.pdf" -m 1`