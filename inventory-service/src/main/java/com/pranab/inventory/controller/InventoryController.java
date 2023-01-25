package com.pranab.inventory.controller;

import com.pranab.inventory.dto.InventoryResponse;
import com.pranab.inventory.model.Inventory;
import com.pranab.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean inStockSingle(@PathVariable("code") String code){
            return inventoryService.inStock(code);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> inStock(@RequestParam List<String> code){
        return inventoryService.isInStock(code);
    }
}
