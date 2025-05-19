package com.wirehec.front_wirehec.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDTO {
    private Long idProveedor;

    private String nombreProveedor;

    private int cifProveedor;

    private String emailProveedor;

    private String categoriaProveedor;

    private String productoProveedor;
}
