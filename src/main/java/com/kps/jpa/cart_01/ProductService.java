package com.kps.jpa.cart_01;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

	@Autowired
	ProductDao productDao;
	
	public void createMultiProducts(List<ProductRequestDTO> products) {
		Iterable<Product> _productEntities = products.stream()
				.map(p->{
					return Product.builder()
							.productName(p.getProductName())
							.inStocks(p.getInStocks())
							.price(p.getPrice())
							.entryDate(new Date())
							.description(p.getDescription())
							.build();
				}).toList();
		productDao.saveAll(_productEntities);
	}

	public void createSingleProduct(ProductRequestDTO product) {
		Product _productEntity = Product.builder()
							.productName(product.getProductName())
							.inStocks(product.getInStocks())
							.price(product.getPrice())
							.entryDate(new Date())
							.description(product.getDescription())
							.build();
				
		productDao.save(_productEntity);
	}
	
	public List<ProductResponseDTO> getAllProducts(){
		List<Product> products = productDao.findAll();
		List<ProductResponseDTO> result = products.stream()
				.map(p->{
					return ProductResponseDTO.builder()
							.productName(p.getProductName())
							.inStocks(p.getInStocks())
							.price(p.getPrice())
							.entryDate(new Date())
							.description(p.getDescription())
							.build();
				}).toList();
		return result;
	}
}
