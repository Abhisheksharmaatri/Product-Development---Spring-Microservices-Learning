package com.abhishek.inventory_service.service;


import com.abhishek.inventory_service.dto.InventoryResponse;
import com.abhishek.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    public Boolean isInStock(String skuCode){
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    public List<InventoryResponse> isInStock(List<String> skuCode){

        return inventoryRepository
                .findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory ->
                    InventoryResponse
                            .builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0)
                            .build()
        ).toList();
    }
}
