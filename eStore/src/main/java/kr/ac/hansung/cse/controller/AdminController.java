package kr.ac.hansung.cse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductService productService;

	// request가 /admin으로 오면 아래의 adminPage()가 실행된다.
	@RequestMapping
	public String adminPage() {
		// admin은 Tiles definition의 이름
		// Tiles definition에 정의된 이름과 동일해야 함
		return "admin";
	}

	// "/productInventory"로 리퀘스트가 들어오면 해당 함수(getProducts)가 실행되고
	@RequestMapping("/productInventory")
	public String getProducts(Model model) {
		// Service 레이어에 있는 getProducts()를 호출해서 Product형 리스트로 가져와 model에 저장한다.
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products);

		// tiles를 사용하기때문에 productInventory라는 definition으로 넘어간다.
		return "productInventory";
	}

	// "/productInventory/addProduct"로 리퀘스트가 들어오면 해당 함수(addProduct)가 실행되고
	// 여기에서는 Get 메소드만 처리해줌
	@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.GET)
	public String addProduct(Model model) {
		// Product 객체를 생성해 
		Product product = new Product();
		
		// 여기에서 미리 카테고리에 컴퓨터를 넣어 놓으면 web form에서 기본으로 카테고리가 컴퓨터로 설정된 상태로 보여짐
		product.setCategory("컴퓨터");
		
		// model에 넣어준다. 
		model.addAttribute("product",product);
		
		// tiles를 사용하기때문에 addProduct라는 definition으로 넘어간다.
		return "addProduct";
	}
	
	// "/productInventory/addProduct"로 리퀘스트가 들어오면 해당 함수(addProduct)가 실행되고
	// 여기에서는 Post 메소드만 처리해줌
	// web form data -> 스프링이 객체를 만들어 이 객체에 form data를 다 채워서 객체에 대한 주소값을
	// 이 함수의 매개변수 product로 넣어줌. 이것을 data binding이라고 함. 
		@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.POST)
		public String addProductPost(Product product) { 
			// 이 함수의 매개변수로 product를 넣어주는 이유 : 
			// web form을 서브밋 하면 그 값이 객체에 다 저장한 다음 그 객체에 대한 주소값을 스프링이 여기에 넘겨줌 
			// web form을 받아서 처리 해 줘야 하는 부분임 
			// 이제 스프링이 넣어준 객체에 저장된 정보를 DB에 넣어주는 작업을 이 함수에서 하면 됨.
			// controller -> service -> dao를 통해 DB 접근  
			if ( !productService.addProduct(product) )
				System.out.println("Adding Product cannot be done");
		
			// 여기서 바로 productInventory로 가면 products에 넣어주는 작업을 여기서 해주지 않기 때문에 오류 발생 
			// 따라서 /admin/productInventory으로 리다이렉트 시켜서
			// getProducts(Model model)에서 다시 DB로부터 조회를 해서 products를 집어넣어줌 
			// 그 후 productInventory를 해주므로 제대로 된 결과가 나온다.
			
			return "redirect:/admin/productInventory";
			
		}
}
