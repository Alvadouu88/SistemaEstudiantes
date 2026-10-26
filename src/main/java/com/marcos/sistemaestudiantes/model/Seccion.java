package com.marcos.sistemaestudiantes.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

@Entity
@Table(
        name = "seccion",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_plan_curso", "id_periodo", "codigo"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seccion")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plan_curso", nullable = false)
    private PlanCurso planCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_periodo", nullable = false)
    private PeriodoAcademico periodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_docente", nullable = false)
    private Docente docente;

    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    @Column(name = "cupo", nullable = false)
    private Short cupo;

    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estado = "BORRADOR";

    @Formula("(SELECT COUNT(*) FROM detalle_matricula dm WHERE dm.id_seccion = id_seccion AND dm.estado = 'INSCRITO')")
    private Integer matriculados;

    // Recuerda generar los getters y setters para este nuevo campo
    public Integer getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(Integer matriculados) {
        this.matriculados = matriculados;
    }
}