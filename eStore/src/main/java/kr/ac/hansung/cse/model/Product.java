package kr.ac.hansung.cse.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {
	 
	private int id;
	
	// 어노테이션으로 데이터 검증(data validator)을 위한 검증 조건(제약 조건)을 설정해 줘야함
	// NotEmpty 어노테이션으로 데이터 null값 검증 
	@NotEmpty(message="The product name must not be empty")
	private String name;
	
	private String category;
	
	// Min 어노테이션으로 데이터 최소값 지정
	@Min(value=0, message="The product price must not be less than zero")
	private int price;
	
	@NotEmpty(message="The product manufacturer must not be empty")
	private String manufacturer;

	@Min(value=0, message="The product unitInStock must not be less than zero")
	private int unitInStock;
	
	private String description;
}
