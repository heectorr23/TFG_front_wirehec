package com.wirehec.front_wirehec.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ContabilityDTO {

    private Long idContabilidad;

    private Float presupuestomensual;

    private Float beneficio;

    private Float gasto;

}
