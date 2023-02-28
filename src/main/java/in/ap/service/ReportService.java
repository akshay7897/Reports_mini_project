package in.ap.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import in.ap.request.SearchRequest;
import in.ap.response.SearchResponse;

public interface ReportService {
	
	
	public List<String> getUniquePlanName();
	
	public List<String> getUniquePlanStatus();
	
	public List<SearchResponse> Search(SearchRequest searchRequest);
	
	public void genrateExcel(HttpServletResponse response);
	
	public void genratePdf(HttpServletResponse response);


}
