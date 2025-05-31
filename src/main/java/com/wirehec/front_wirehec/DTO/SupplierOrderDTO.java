package com.wirehec.front_wirehec.DTO;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierOrderDTO {

    private Long idPedidoProveedor;

    private LocalDate fechaPedido;

    private LocalDate fechaEntrega;
}