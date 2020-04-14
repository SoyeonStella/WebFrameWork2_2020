package kr.ac.hansung.cse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.cse.model.Product;

// 스프링에서 스캔하다가(단, 어느 패키지를 스캔할지를 지정해 줘야한다.) 
// @Component(여기서는 더 명확히  @Repository 사용함)가 있으면 해당되 class를 바탕으로 하여 빈으로 등록해준다.
// @Component() 안의 문자열은 빈에 대한 id이다. 
// bean으로 등록해주면 스프링 컨테이너에 의해 라이프사이클을 관리해줄수있다.
// 이 어노테이션 꼭 기억하기 매우중요  
// service -> @Service, controller -> @Controller
// dao -> @Repository 임!!!
// DAO는 스프링에서 제공하는 JDBC Template을 사용한다. 
// 따라서 JDBC Template 객체를 생성해줘야 한다. 
// 또한 JDBC Template에 DataSource를 주입해주는 부분도 필요하다. 
@Repository
public class ProductDao {

	// JDBC Template을 활용함
	private JdbcTemplate jdbcTemplate;

	@Autowired
	// dataSource가 싱글톤이므로 자료형으로 알아서 찾아서 주입해준다.
	// setter method. 이 메서드 호출시 DataSource를 의존성 주입을 통해서 주입해 준다.
	public void setDataSource(DataSource dataSource) {

		// DataSource를 활용해 jdbcTemplate 인스턴스 생성
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// DB에서 제품 리스트를 가져와주는 메서드
	public List<Product> getProducts() {
		String sqlStatement = "select * from product";

		// sqlStatement 인자로 database를 조회하게 되면
		// 레코드를 객체로 맵핑해 주어야한다 -> 익명클래스 인자인 RowMapper의 mapRow 메서드에 개발자가 정의해줘야함
		// 여러게의 레코드를 객체로 가져올 것이므로 query 메서드 사용 
		return jdbcTemplate.query(sqlStatement, new RowMapper<Product>() { // 익명클래스

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

				Product product = new Product();

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getInt("price"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setUnitInStock(rs.getInt("unitInStock"));
				product.setDescription(rs.getString("description"));

				return product;
			}

		});
	}

	// DB에 제품을 추가해주는 메서드 
	public Boolean addProduct(Product product) {

		int id = product.getId();
		String name = product.getName();
		String category = product.getCategory();
		int price = product.getPrice();
		String manufacturer = product.getManufacturer();
		int unitInStock = product.getUnitInStock();
		String description = product.getDescription();

		String sqlStatement = "insert into product (id, name, category, price, manufacturer, unitInStock, description) "
				+ "values (?, ?, ?, ?, ?, ?, ?)";

		return (jdbcTemplate.update(sqlStatement,
				new Object[] { id, name, category, price, manufacturer, unitInStock, description }) == 1);
	}

	// 특정 id에 해당하는 제품을 DB에서 삭제하는 메서드
	public boolean deleteProduct(int id) {

		String sqlStatement = "delete from product where id=?";

		return (jdbcTemplate.update(sqlStatement, new Object[] { id }) == 1);
	}

	// DB에서 특정 id에 해당하는 제품을 가져다주는 메서드
	public Product getProductById(int id) {
		String sqlStatement = "select * from product where id=?";
		
		// sqlStatement 인자로 database를 조회하게 되면
		// 레코드를 객체로 맵핑해 주어야한다 -> 익명클래스 인자인 RowMapper의 mapRow 메서드에 개발자가 정의해줘야함
		// 여기서는 하나의 객체만 가져오므로 query가 아닌 queryForObject 메서드를 사용해야 함 
		// new Object[] {id}에는 sql문에서 placeholder로 받아온 id를 넣어준다. 
		return jdbcTemplate.queryForObject(sqlStatement, new Object[] {id}, new RowMapper<Product>() { // 익명클래스

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

				Product product = new Product();

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getInt("price"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setUnitInStock(rs.getInt("unitInStock"));
				product.setDescription(rs.getString("description"));

				return product;
			}

		});

	}

	// DB에 제품 내용 수정 사항을 반영해주는 메서드
	public boolean updateProduct(Product product) {
		int id = product.getId();
		String name = product.getName();
		String category = product.getCategory();
		int price = product.getPrice();
		String manufacturer = product.getManufacturer();
		int unitInStock = product.getUnitInStock();
		String description = product.getDescription();

		String sqlStatement = "update product set name=?, category=?, price=?, manufacturer=?, unitInStock=?, description=? "
				+ "where id=? ";

		return (jdbcTemplate.update(sqlStatement,
				new Object[] { name, category, price, manufacturer, unitInStock, description, id }) == 1);
	}
}
