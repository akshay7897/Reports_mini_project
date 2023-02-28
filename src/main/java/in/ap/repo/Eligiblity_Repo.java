package in.ap.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.ap.entities.Eligiblity_Details;

@Repository
public interface Eligiblity_Repo extends JpaRepository<Eligiblity_Details, Integer> {
	
	@Query("select distinct(planName)from Eligiblity_Details")
	public List<String> getByPlanName();
	
	@Query("select distinct(planStatus)from Eligiblity_Details")
	public List<String> getByPlanStatus();
	

}
