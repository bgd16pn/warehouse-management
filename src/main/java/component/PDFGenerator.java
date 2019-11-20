package component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.Address;
import model.Client;
import model.Orders;
import model.Product;

/**
 * This class is responsible with the creation of PDF bills.
 * @author Bogdan Paun
 */
public class PDFGenerator {

	private static final Logger logger = Logger.getLogger(PDFGenerator.class.getName());

	private PDFGenerator() {

	}

	/**
	 * Generates a bill in PDF for the specified order.
	 * 
	 * @param order the order on which the bill is based on
	 * @param product the product purchased in this order
	 * @param client the client that bought the product
	 * @param address the address of the client
	 * @param exportPath the path where this bill will be saved
	 */
	public static void generatePDF(String exportPath, Address address, Client client, Product product, Orders order) {
		Document document = new Document();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {
			document.setPageSize(PageSize.A4);
			PdfWriter.getInstance(document, new FileOutputStream(exportPath));

			document.open();

			/** TITLU */
			Paragraph title = new Paragraph("BILL", new Font(FontFamily.HELVETICA, 30, Font.BOLD));
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			/** DATA EMITERII */
			StringBuilder strBuilder = new StringBuilder("Number: " + (1 + order.getId()) + "\n");
			strBuilder.append("Date: " + df.format(Calendar.getInstance().getTime()));
			Paragraph subtitle = new Paragraph(strBuilder.toString(), new Font(FontFamily.HELVETICA, 13, Font.NORMAL));
			subtitle.setAlignment(Element.ALIGN_CENTER);
			document.add(subtitle);

			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			/** CREATOR */
			StringBuilder sb = new StringBuilder("Name: " + client.getFirstName() + " " + client.getLastName() + "\n");
			sb.append("Email: " + client.getEmail() + "\n").append("Address: " + "Street: " + address.getStreet())
					.append(", No. " + address.getNumber() + "\n");
			if (!address.getFlat().equals("")) {
				sb.append("Flat: " + address.getFlat()).append(", Apartment: " + address.getApartment() + "\n");
			}
			sb.append("City: " + address.getCity() + ", County: " + address.getCounty() + "\n")
					.append("Country: " + address.getCountry() + ", Postal code: " + address.getPostalCode() + "\n");
			Paragraph creator = new Paragraph(sb.toString(), new Font(FontFamily.HELVETICA, 10, Font.BOLD));
			creator.setAlignment(Element.ALIGN_LEFT);
			document.add(creator);

			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			String[] header = new String[] { "Index no.", "Product name", "Quantity", "Unit price", "Total" };

			/** TABEL */
			PdfPTable table = new PdfPTable(header.length);
			table.setHeaderRows(1);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10f);

			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.getDefaultCell().setFixedHeight(20);

			Font textFont = new Font(FontFamily.HELVETICA, 14, Font.BOLD);

			PdfPCell c1 = null;
			for (int i = 0; i < header.length; i++) {
				c1 = new PdfPCell(new Phrase(new Chunk(header[i], textFont)));
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				c1.setFixedHeight(50);
				c1.setRowspan(1);
				table.addCell(c1);
			}

			table.addCell("1");
			table.addCell(product.getName());
			table.addCell(String.valueOf(order.getQuantity()));
			table.addCell(String.valueOf(product.getUnitPrice()));
			table.addCell(String.valueOf(order.getPrice()));

			document.add(table);
		} catch (IOException | DocumentException e) {
			logger.log(Level.WARNING, "PDFGenerator: " + e.getMessage());
		} finally {
			document.close();
		}
	}

}
