package com.me.utilservices;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.me.pojo.JobDetails;

public class PdfGenerator extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<JobDetails> jobsList = (List<JobDetails>) model.get("jobs");
        
        doc.add(new Paragraph("Jobs Applied !!"));
         
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {3.0f, 2.0f, 3.0f, 2.0f, 3.0f ,3.0f ,3.0f, 3.0f, 4.0f , 4.0f });
        table.setSpacingBefore(10);
         
        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
       // font.setColor(BaseColor.WHITE);
         
        // define table header cell
        PdfPCell cell = new PdfPCell();
      //  cell.setBackgroundColor(BaseColor.BLUE);
        cell.setPadding(5);
         
        // write table header
        cell.setPhrase(new Phrase("Job id", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Title", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Company Name", font));
        table.addCell(cell);
 
        cell.setPhrase(new Phrase("Type", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Location", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Industry", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Major", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("MinGpa", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Url", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);
         
        // write table row data
        for (JobDetails job : jobsList) {
        	table.addCell(Long.toString(job.getJobId()));
            table.addCell(job.getTitle());
            table.addCell(job.getCompanyName());
            table.addCell(job.getTypeOfJob());
            table.addCell(job.getCountry() +", " +job.getState());
            table.addCell(job.getIndustry());
            table.addCell(job.getMajor());
            table.addCell(Float.toString(job.getMinGpa()));
            table.addCell((job.getJobUrl()== null ? "No Url Provided" : job.getJobUrl()));
            table.addCell(job.getDescription()== null ? "No Description Provided" : job.getDescription());
        }
         
        doc.add(table);
         
    }
		
		
	}
	
	


