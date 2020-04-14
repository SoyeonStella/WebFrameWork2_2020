package kr.ac.hansung.cse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller 
public class ProductController { // controller가 service 호출, service가 dao 호출

	@Autowired
	private ProductService productService;
	
	// " " 안의 경로로 리퀘스트가 들어오면 해당 함수(getProducts)가 실행되고 
	@RequestMapping("/products")
	public String getProducts(Model model) {
		
		// Service 레이어에 있는 getProducts()를 호출해서 Product형 리스트로 가져와 model에 저장한다. 
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products);
		
		// " " 안은 view의 logical name로써 예전에는 /products 로 리퀘스트가 들어오면 products.jsp로 넘어간다.
		// 이젠 tiles를 사용하기때문에 products라는 definition으로 넘어간다.
		return "products";
	
	}
}
