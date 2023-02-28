package in.ap.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.ap.entities.Eligiblity_Details;
import in.ap.repo.Eligiblity_Repo;
import in.ap.request.SearchRequest;
import in.ap.response.SearchResponse;
import in.ap.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private Eligiblity_Repo eligiblity_Repo;

	@Override
	public List<String> getUniquePlanName() {

		return eligiblity_Repo.getByPlanName();
	}

	@Override
	public List<String> getUniquePlanStatus() {
		return eligiblity_Repo.getByPlanStatus();
	}

	@Override
	public List<SearchResponse> Search(SearchRequest searchRequest) {

		List<SearchResponse> sr = new ArrayList<>();

		Eligiblity_Details eligiblity_Details = new Eligiblity_Details();

		String planName = searchRequest.getPlanName();
		if (planName != null && !planName.equals("")) {
			eligiblity_Details.setPlanName(planName);
		}

		String planStatus = searchRequest.getPlanStatus();
		if (planStatus != null && !planStatus.equals("")) {
			eligiblity_Details.setPlanStatus(planStatus);
		}

		LocalDate planStartDate = searchRequest.getPlanStartDate();
		if (planStartDate != null) {
			eligiblity_Details.setPlanStartDate(planStartDate);
		}

		LocalDate planEndDate = searchRequest.getPlanEndDate();
		if (planEndDate != null) {
			eligiblity_Details.setPlanStartDate(planEndDate);
		}

		Example<Eligiblity_Details> example = Example.of(eligiblity_Details);

		List<Eligiblity_Details> eligiblity = eligiblity_Repo.findAll(example);
		for (Eligiblity_Details e : eligiblity) {
			SearchResponse searchResponse = new SearchResponse();
			BeanUtils.copyProperties(e, searchResponse);
			sr.add(searchResponse);

		}
		return sr;
	}

	@Override
	public void genrateExcel(HttpServletResponse response) throws IOException {

		List<Eligiblity_Details> eligiblity = eligiblity_Repo.findAll();

		// createsd workbook --
		HSSFWorkbook workbook = new HSSFWorkbook();

		// created sheet using workbook
		HSSFSheet sheet = workbook.createSheet();

		// created row using sheet
		HSSFRow headerRow = sheet.createRow(0);

		// created cells using row
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Mobile");
		headerRow.createCell(2).setCellValue("Email");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("SSN");

		int i = 1;
		for (Eligiblity_Details e : eligiblity) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(e.getName());
			row.createCell(1).setCellValue(e.getMobile());
			row.createCell(2).setCellValue(e.getEmail());
			row.createCell(3).setCellValue(String.valueOf(e.getGender()));
			row.createCell(4).setCellValue(e.getSsn());
			i++;
		}

		// getting outputStrem from method parameter
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

	@Override
	public void genratePdf(HttpServletResponse response) throws DocumentException, IOException {

		List<Eligiblity_Details> entities = eligiblity_Repo.findAll();

		Document document = new Document(PageSize.A4);

		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);

		// Creating paragraph
		Paragraph paragraph = new Paragraph("Search Report", fontTiltle);

		// Aligning the paragraph in document
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		// Adding the created paragraph in document
		document.add(paragraph);

		// Creating a table of 3 columns
		PdfPTable table = new PdfPTable(5);

		// Setting width of table, its columns and spacing
		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 5, 5, 5, 5, 5 });
		table.setSpacingBefore(5);

		// Create Table Cells for table header
		PdfPCell cell = new PdfPCell();

		// Setting the background color and padding
		cell.setBackgroundColor(CMYKColor.MAGENTA);
		cell.setPadding(5);

		// Creating font
		// Setting font style and size
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.WHITE);

		// Adding headings in the created table cell/ header
		// Adding Cell to table
		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Mobile", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("SSN", font));
		table.addCell(cell);

		// Iterating over the list of students
		for (Eligiblity_Details entity : entities) {
			// Adding student name
			table.addCell(entity.getName());
			// Adding student mobile
			table.addCell(String.valueOf(entity.getMobile()));
			// Adding student email
			table.addCell(entity.getEmail());
			// Adding student Gender
			table.addCell(String.valueOf(entity.getGender()));

			// Adding student SSn
			table.addCell(String.valueOf(entity.getSsn()));

		}
		// Adding the created table to document
		document.add(table);

		// Closing the document
		document.close();

	}

}
