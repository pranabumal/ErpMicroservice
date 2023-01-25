package com.pranab.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderLineItemDto {
    private Long id;
    private String code;
    private BigDecimal price;
    private Integer quantity;
}
