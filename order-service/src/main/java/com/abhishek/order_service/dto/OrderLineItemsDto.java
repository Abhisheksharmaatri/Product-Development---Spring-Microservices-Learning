package com.abhishek.order_service.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsDto {
    private Long id;
    private String skuCode;
    private Integer price;
    private Integer quantity;
}
