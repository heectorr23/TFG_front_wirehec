package com.wirehec.front_wirehec.DTO;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDetailDTO {

    private Long idDetalleProveedor;

    private SupplierDTO supplier;

    private List<SupplierOrderDTO> supplierOrders;

    private Long idProduct;
}