package in.ap.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
	public void genrateExcel(HttpServletResponse response) {

		List<Eligiblity_Details> eligiblity = eligiblity_Repo.findAll();
		
		
		// createsd workbook --
		HSSFWorkbook workbook=new HSSFWorkbook();
		
		// created sheet using workbook
		HSSFSheet sheet = workbook.createSheet();
		
		// created row using sheet
		HSSFRow headerRow = sheet.createRow(0);
		
		
		//created cells using row
		headerRow.createCell(0).setCellValue("name");
		headerRow.createCell(1).setCellValue("mobile");
		headerRow.createCell(2).setCellValue("email");
		headerRow.createCell(3).setCellValue("gender");
		headerRow.createCell(3).setCellValue("ssn");
		
		int i=1;
		eligiblity.forEach(e->{
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(e.getName());
			row.createCell(1).setCellValue(e.getMobile());
			row.createCell(2).setCellValue(e.getEmail());
			row.createCell(3).setCellValue(e.getGender());
			row.createCell(4).setCellValue(e.getSsn());
			
		});



	}

	@Override
	public void genratePdf(HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
