package com.commerce.entity.dto;

import com.commerce.entity.Product;
import lombok.Data;


@Data
public class ProductDtoResponse {

    private Long productId;
    private String name;
    private double price;
    private  Long serialNumber;
    private String vendor;
    private int counter;

    public static ProductDtoResponse mapToProductDto(Product product){
        ProductDtoResponse dto= new ProductDtoResponse();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCounter(product.getCounter());
        dto.setVendor(product.getVendor());
        dto.setSerialNumber(product.getSerialNumber());

        return dto;
    }

}
