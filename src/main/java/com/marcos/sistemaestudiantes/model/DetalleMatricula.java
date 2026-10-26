package com.marcos.sistemaestudiantes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "detalle_matricula",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_matricula", "id_seccion"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleMatricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_seccion", nullable = false)
    private Seccion seccion;

    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estado = "INSCRITO";
}
