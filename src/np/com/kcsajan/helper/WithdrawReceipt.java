package np.com.kcsajan.helper;

import java.io.File;
import java.io.IOException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

public class WithdrawReceipt {

	public void createPdf(File dest, String fullName, Integer accountNumber, Integer balance, Integer remainingBalance)
			throws IOException {
		// Initialize PDF writer
		PdfWriter writer = new PdfWriter(dest);

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);

		// Initialize document
		Document document = new Document(pdf);

		String header = "*** MyBank Pvt. Limited ***\n================================";
		Paragraph headerParagraph = new Paragraph(header);
		headerParagraph.setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(20).setFixedLeading(20);
		document.add(headerParagraph);

		String subHeader = "*** ATM TRANSACTION ***";
		Paragraph subHeaderParagraph = new Paragraph(subHeader);
		subHeaderParagraph.setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(18).setFixedLeading(20);
		document.add(subHeaderParagraph);

		String details = "Customer Name : " + fullName + "\nAccount Number : " + accountNumber
				+ "\nRequested Amount : Rs. " + balance + "\nRemaining Balance : Rs. " + remainingBalance;
		Paragraph detailsParagraph = new Paragraph(details);
		detailsParagraph.setTextAlignment(TextAlignment.CENTER).setFontSize(12).setFixedLeading(20).setMarginTop(20);
		document.add(detailsParagraph);

		// Close document
		document.close();
	}

}
