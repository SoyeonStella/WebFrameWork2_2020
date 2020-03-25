package kr.ac.hansung.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.ProductDao;
import kr.ac.hansung.cse.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao; // ProductService는 Dao 의존성 주입하여 받는다.
	
	public List<Product> getProducts() {
		// dao를 활용해서 getProducts()를 호출한다. 
		return productDao.getProducts();
	}
	
}
