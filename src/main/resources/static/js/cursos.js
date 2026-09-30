document.addEventListener("DOMContentLoaded", () => {
    const elementoModalCurso = document.getElementById("cursoModal");
    const elementoModalConfirmacion = document.getElementById("confirmacionModal");
    const modalCurso = new bootstrap.Modal(elementoModalCurso);
    const modalConfirmacion = new bootstrap.Modal(elementoModalConfirmacion);

    const formCurso = document.getElementById("formCurso");
    const formEliminar = document.getElementById("formEliminar");
    const idCurso = document.getElementById("idCurso");
    const codigo = document.getElementById("codigo");
    const nombre = document.getElementById("nombre");
    const tipo = document.getElementById("tipo");
    const activo = document.getElementById("activo");
    const tituloCursoModal = document.getElementById("tituloCursoModal");
    const tituloConfirmacion = document.getElementById("tituloConfirmacion");
    const mensajeConfirmacion = document.getElementById("mensajeConfirmacion");
    const btnConfirmarAccion = document.getElementById("btnConfirmarAccion");

    const abrirConfirmacion = (titulo, mensaje, textoBoton, claseBoton, accion) => {
        tituloConfirmacion.textContent = titulo;
        mensajeConfirmacion.textContent = mensaje;
        btnConfirmarAccion.textContent = textoBoton;
        btnConfirmarAccion.className = `btn ${claseBoton}`;
        btnConfirmarAccion.onclick = () => {
            modalConfirmacion.hide();
            accion();
        };
        modalConfirmacion.show();
    };

    document.getElementById("btnNuevoCurso").addEventListener("click", () => {
        formCurso.reset();
        formCurso.classList.remove("was-validated");
        idCurso.value = "";
        activo.checked = true;
        tituloCursoModal.textContent = "Nuevo curso";
        modalCurso.show();
    });

    document.querySelectorAll(".btn-editar").forEach((boton) => {
        boton.addEventListener("click", () => {
            formCurso.reset();
            formCurso.classList.remove("was-validated");
            idCurso.value = boton.dataset.id;
            codigo.value = boton.dataset.codigo;
            nombre.value = boton.dataset.nombre;
            tipo.value = boton.dataset.tipo;
            activo.checked = boton.dataset.activo === "true";
            tituloCursoModal.textContent = "Editar curso";
            modalCurso.show();
        });
    });

    formCurso.addEventListener("submit", (evento) => {
        evento.preventDefault();

        if (!formCurso.checkValidity()) {
            formCurso.classList.add("was-validated");
            return;
        }

        const editando = idCurso.value !== "";
        const nombreCurso = nombre.value.trim();
        abrirConfirmacion(
            editando ? "¿Modificar curso?" : "¿Crear curso?",
            editando
                ? `Se actualizará la información de “${nombreCurso}”.`
                : `Se agregará “${nombreCurso}” al catálogo académico.`,
            editando ? "Sí, modificar" : "Sí, crear",
            "btn-agregar",
            () => formCurso.submit()
        );
    });

    document.querySelectorAll(".btn-desactivar").forEach((boton) => {
        boton.addEventListener("click", () => {
            const nombreCurso = boton.dataset.nombre;
            abrirConfirmacion(
                "¿Desactivar curso?",
                `“${nombreCurso}” dejará de estar disponible para nuevas configuraciones. Sus datos históricos se conservarán.`,
                "Sí, desactivar",
                "btn-desactivar",
                () => {
                    formEliminar.action = `${formEliminar.dataset.baseUrl}${boton.dataset.id}/eliminar`;
                    formEliminar.submit();
                }
            );
        });
    });

    if (document.body.dataset.abrirModal === "true") {
        tituloCursoModal.textContent = idCurso.value === "" ? "Nuevo curso" : "Editar curso";
        modalCurso.show();
    }
});
