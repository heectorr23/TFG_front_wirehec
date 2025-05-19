package com.wirehec.front_wirehec.DTO;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDetailDTO {

    private Long idDetalleProveedor;

    private SupplierDTO supplier; // Changed from SupplierEntity to SupplierDTO

    private Set<SupplierOrderDTO> supplierOrders; // Changed from SupplierOrderEntity to SupplierOrderDTO

    private Long idProduct;
}