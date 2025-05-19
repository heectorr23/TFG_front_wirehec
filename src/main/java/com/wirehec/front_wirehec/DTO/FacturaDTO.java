package com.wirehec.front_wirehec.DTO;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacturaDTO {

    private Long id;
    private BigDecimal Precio;
    private String Zona;
    private String Direccion;
}
