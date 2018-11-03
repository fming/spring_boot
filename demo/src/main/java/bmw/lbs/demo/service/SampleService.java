package bmw.lbs.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
	
	@Async
	public void doA() {
		
	}
	
	private void doB() {
		
	}
	
	public void doC() {
		doA();
	}
	
	
	@Async
	public Integer doD() {
		return 5;
	}
	

}
