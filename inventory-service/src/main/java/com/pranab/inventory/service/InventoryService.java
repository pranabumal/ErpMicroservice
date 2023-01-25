package com.pranab.inventory.service;

import com.pranab.inventory.dto.InventoryResponse;
import com.pranab.inventory.model.Inventory;
import com.pranab.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> code){
       return inventoryRepository.findByCodeIn(code).stream()
               .map(inventory ->
                   InventoryResponse.builder()
                           .code(inventory.getCode())
                           .isInStock(inventory.getQuantity()>0).build()
               ).toList();
    }

    @Transactional(readOnly = true)
    public boolean inStock(String code){
        return inventoryRepository.findByCode(code).isPresent();
    }
}
