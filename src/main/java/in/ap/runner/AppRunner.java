package in.ap.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.ap.entities.Eligiblity_Details;
import in.ap.repo.Eligiblity_Repo;

@Component
public class AppRunner implements ApplicationRunner{
	
	@Autowired
	private Eligiblity_Repo repo;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Eligiblity_Details e=new Eligiblity_Details();
		e.setEligiblityId(111);
		e.setName("Akshay");
		e.setMobile(9130804535l);
		e.setEmail("akshaypatil781997@gmail.com");
		e.setGender('M');
		e.setPlanName("snap");
		e.setPlanStatus("Approved");
		e.setCreatedBy("ap");
		repo.save(e);
		
		Eligiblity_Details e1=new Eligiblity_Details();
		e1.setEligiblityId(222);
		e1.setName("Shubham");
		e1.setMobile(7770008436l);
		e1.setEmail("shubham@gmail.com");
		e1.setGender('M');
		e1.setPlanName("ccp");
		e1.setPlanStatus("pending");
		e1.setCreatedBy("ram");
		repo.save(e1);
		
		Eligiblity_Details e2=new Eligiblity_Details();
		e2.setEligiblityId(333);
		e2.setName("sita");
		e2.setMobile(8529631470l);
		e2.setEmail("sita7@gmail.com");
		e2.setGender('F');
		e2.setPlanName("cam");
		e2.setPlanStatus("Denied");
		e2.setCreatedBy("smith");
		repo.save(e2);
		
		
		
	}

}
