package in.ap.response;

import lombok.Data;

@Data
public class SearchResponse {
	
	private Integer eligiblityId;		
	private String name;				
	private Long mobile;				
	private String email;				
	private Character gender;				
	private String ssn;	
	private String planName;			
	private String planStatus;	

}
