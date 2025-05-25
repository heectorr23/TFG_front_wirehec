package com.wirehec.front_wirehec.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RegisterUserDTO {

    private String nombreEmpleado;

    private String nifEmpleado;

    private int telefonoEmpleado;

    private String email;

    private String username;

    private String passwordEmpleado;

    private List<RoleDTO> roles;

    private int beneficioEmpleado;

    private LocalDateTime horaEntrada = LocalDateTime.now();

    private LocalDateTime horaSalida = LocalDateTime.now();

    private BigDecimal salario;
}
