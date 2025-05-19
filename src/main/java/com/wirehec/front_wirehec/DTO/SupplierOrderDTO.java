package com.wirehec.front_wirehec.DTO;

import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierOrderDTO {

    private Long  idPedidoProveedor;

    private Date FechaPedido;

    private Date FechaEntrega;
}