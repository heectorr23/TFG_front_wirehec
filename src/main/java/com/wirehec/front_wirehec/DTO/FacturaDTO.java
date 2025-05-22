package com.wirehec.front_wirehec.DTO;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacturaDTO {

    private Long id;
    private BigDecimal precio;
    private String zona;
    private String direccion;
}
