package brocodex.fbot.component;

import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.model.transaction.Transaction;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class PdfReportGenerator {
    public File generatePdfReport(List<TransactionDTO> transactionList) {
        File pdfFile = null;
        try {
            pdfFile = File.createTempFile("transaction-report", ".pdf");
            PdfWriter pdfWriter = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Transactions Report").setBold().setFontSize(16));

            float[] columns = {1, 2, 3, 4, 5};
            Table table = new Table(columns);
            table.addHeaderCell("ID");
            table.addHeaderCell("Amount");
            table.addHeaderCell("Type");
            table.addHeaderCell("Category");
            table.addHeaderCell("Transaction Date");

            transactionList.forEach(t -> {
                table.addCell(String.valueOf(t.getId()));
                table.addCell(String.valueOf(t.getAmount()));
                table.addCell(t.getType());
                table.addCell(t.getCategoryName());
                table.addCell(t.getTransactionDate().toString());
            });

            document.add(table);
            document.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return pdfFile;
    }
}
