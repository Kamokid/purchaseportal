package com.cst8333.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.cst8333.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProduct() {
		
		Product product = new Product();
		product.setName("Neotetra");
		product.setAlias("Neotetra");
		product.setFullDescription(" hydrosoluble powder\r\n"
				+ "Composition:\r\n"
				+ "Each 1000 gram of hydrosoluble powder contain:\r\n"
				+ "Oxytetracycline HCl 60 g\r\n"
				+ "Neomycin sulphate 40 g\r\n"
				+ "Vitamin A 1,500,000 IU\r\n"
				+ "Vitamin D3 250,000 IU\r\n"
				+ "Vitamin K 500 mg\r\n"
				+ "Vitamin E 350 IU\r\n"
				+ "Vitamin B12 5 mg\r\n"
				+ "Vitamin B2 2 g\r\n"
				+ "Nicotinic acid 10 g\r\n"
				+ "Pantothenic acid 3.5 g\r\n"
				+ "Lactose to 1000 g\r\n"
				+ "Action:\r\n"
				+ "Oxytetracycline HCl with bacteriostatic action against Gram-positive and Gram-negative germs such as E. coli, Pasteurella, Salmonella, Brucella, Streptococcus, Clostridium, Haemophilus,\r\n"
				+ "Corynebacterium, Anthrax, Staphylococcus. Rickettsia, Mycoplasma, Spirochaetes, and\r\n"
				+ "Actinomyces.\r\n"
				+ "The aminoglycoside Neomycine is poorly absorbed after oral medication and acts also against\r\n"
				+ "Gram-positive and Gram-negative germs.\r\n"
				+ "Prevention and treatment of deficiencies in different vitamins.\r\n"
				+ "Indications*:\r\n"
				+ "Prevention and treatment of bacterial infections caused by Gram-positive and Gram-negative germs, e.g. bacterial enteritis and diarrhoea in poultry, calves and piglets.\r\n"
				+ "Administration/dosage**:\r\n"
				+ "In the drinking water:\r\n"
				+ "Prevention : 0.5-0.8 g per l.\r\n"
				+ "Treatment : 1.0-1.5 g per l for 3-5 days.\r\n"
				+ "Advised withdrawal times***:\r\n"
				+ "Meat : 7 days\r\n"
				+ "Eggs : 7 days\r\n"
				+ "Conservation:\r\n"
				+ "Store dry, dark, cool, below 25°C.\r\n"
				+ "Presentation:\r\n"
				+ "100 g- 200 g- 500 g- 1000 g\r\n"
				+ "FOR VETERINARY USE ONLY\r\n"
				+ "");
		
		product.setPrice(50);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		
		Product savedProduct = repo.save(product);
		
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testListAllProducts() {
		Iterable <Product> iterableProducts = repo.findAll();
		iterableProducts.forEach(System.out::println);
	}
	
	@Test
	public void testGetProduct() {
		Integer id = 1;
		Product product = repo.findById(id).get();
		System.out.println(product);
		assertThat(product).isNotNull();
	}
	
	@Test 
	public void testUpdateProduct() {
		Integer id = 1;
		Product product = repo.findById(id).get();;
		product.setPrice(100);
		repo.save(product);
		
		Product updatedProduct = entityManager.find(Product.class, id);
		
		assertThat(updatedProduct.getPrice()).isEqualTo(100);
	}
	
	@Test
	public void testDeleteProduct() {
		Integer id = 1;
		repo.deleteById(id);
		Optional <Product> result = repo.findById(id);
		assertThat(!result.isPresent());
	}
	
	@Test
	public void testSaveProductWithImages() {
		Integer id = 1;
		Product product = repo.findById(id).get();
//		product.setMainImage("main_image.jpg");
		product.addImage("ex_image1.png");
//		product.addImage("ex-image2.png");
//		product.addImage("ex_image_3.png");
		Product  savedProduct = repo.save(product);
		assertThat(savedProduct.getImages().size()).isEqualTo(1);
	}
	
}
