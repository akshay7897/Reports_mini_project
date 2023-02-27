package in.ap.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Eligiblity_Details {
	
	@Id
	private Integer eligiblityId;		
	private String name;				
	private Long mobile;				
	private String email;				
	private Character gender;				
	private String ssn;					
	private String planName;			
	private String planStatus;		
	private LocalDate planStartDate;	
	private LocalDate planEndDate;		
	private LocalDate createDate;		
	private LocalDate updateDate;		
	private String createdBy;		
	private String updateBy; 		

}
