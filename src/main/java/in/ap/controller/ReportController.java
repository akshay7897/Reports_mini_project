package in.ap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import in.ap.request.SearchRequest;
import in.ap.response.SearchResponse;
import in.ap.service.ReportService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ReportController {

	Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private ReportService service;

	@GetMapping("/plannames")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getAllPlanNames() {
		logger.info("getAll plan method started");
		List<String> uniquePlanName = service.getUniquePlanName();
		logger.info("getAll plan method closed");
		return uniquePlanName;

	}

	@GetMapping("/statuses")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getPlanStatuses() {

		List<String> uniquePlanStatus = service.getUniquePlanStatus();
		return uniquePlanStatus;
	}

	@PostMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	public List<SearchResponse> getSearch(@RequestBody SearchRequest searchRequest) {

		List<SearchResponse> search = service.Search(searchRequest);
		return search;
	}
	
	@GetMapping("/excel")
	public void getExcelReport(HttpServletResponse response) throws IOException {
		
		response.setContentType("application/octet-strem");
		
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename=data.xls";
		
		response.setHeader(headerKey, headerValue);
		
		service.genrateExcel(response);
		
	}
	
	@GetMapping("/pdf")
	public void getPdfReport(HttpServletResponse response) throws DocumentException, IOException {
		
		response.setContentType("application/pdf");
		String headerKey="Content-Dicomposition";
		String headerValue="attachment;filename=data.pdf";
		response.setHeader(headerKey, headerValue);
		service.genratePdf(response);
		
	}
	

}
