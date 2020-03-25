package kr.ac.hansung.cse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */

// servlet-context.xml의 <context:component-scan />이 컴포넌트 스캔을 하여 
// 아래의 컨트롤러 어노테이션이 있는 부분을 빈으로 등록해준다. 
@Controller
public class HomeController {
	
	
	//리퀘스트가 루트로 들어오면 home.jsp로 넘어간다. 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		//루트로 리퀘스트가 들어오면 home.jsp로 넘어간다. 
		return "home";
	}
	
}
