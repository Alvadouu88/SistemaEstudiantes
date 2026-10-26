document.addEventListener("DOMContentLoaded", () => {
    // Instancias de modales de Bootstrap
    const elemModalCurso = document.getElementById("cursoModal");
    const elemModalConfirmacion = document.getElementById("confirmacionModal");
    const modalCurso = new bootstrap.Modal(elemModalCurso);
    const modalConfirmacion = new bootstrap.Modal(elemModalConfirmacion);

    // Elementos del DOM
    const formCurso = document.getElementById("formCurso");
    const formAccion = document.getElementById("formAccion");
    const cursoId = document.getElementById("cursoId");
    const codigo = document.getElementById("codigo");
    const nombre = document.getElementById("nombre");
    const tipo = document.getElementById("tipo");
    const activo = document.getElementById("activo");
    const tituloCursoModal = document.getElementById("tituloCursoModal");
    const tituloConfirmacion = document.getElementById("tituloConfirmacion");
    const mensajeConfirmacion = document.getElementById("mensajeConfirmacion");
    const btnConfirmarAccion = document.getElementById("btnConfirmarAccion");

    // Función auxiliar para mostrar modal de confirmación
    const mostrarConfirmacion = (titulo, mensaje, textoBoton, claseBoton, accion) => {
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

    // Resetear clases de validación de HTML/Spring
    const limpiarValidaciones = () => {
        formCurso.classList.remove("was-validated");
        formCurso.querySelectorAll(".is-invalid").forEach(el => el.classList.remove("is-invalid"));
    };

    // Botón "+ Añadir curso"
    document.getElementById("btnNuevoCurso").addEventListener("click", () => {
        formCurso.reset();
        limpiarValidaciones();
        cursoId.value = "";
        activo.checked = true;
        tituloCursoModal.textContent = "Nuevo curso";
        modalCurso.show();
    });

    // Botones "Editar"
    document.querySelectorAll(".btn-editar").forEach((boton) => {
        boton.addEventListener("click", () => {
            formCurso.reset();
            limpiarValidaciones();
            cursoId.value = boton.dataset.id;
            codigo.value = boton.dataset.codigo;
            nombre.value = boton.dataset.nombre;
            tipo.value = boton.dataset.tipo;
            activo.checked = boton.dataset.activo === "true";
            tituloCursoModal.textContent = "Editar curso";
            modalCurso.show();
        });
    });

    // Submit del formulario de Curso
    formCurso.addEventListener("submit", (evento) => {
        evento.preventDefault();

        if (!formCurso.checkValidity()) {
            formCurso.classList.add("was-validated");
            return;
        }

        const editando = cursoId.value !== "";
        const nombreCurso = nombre.value.trim();

        mostrarConfirmacion(
            editando ? "¿Modificar curso?" : "¿Crear curso?",
            editando
                ? `Se actualizará la información de “${nombreCurso}”.`
                : `Se agregará “${nombreCurso}” al catálogo académico.`,
            editando ? "Sí, modificar" : "Sí, crear",
            "btn-agregar",
            () => formCurso.submit()
        );
    });

    // Botones "Desactivar"
    document.querySelectorAll(".btn-desactivar").forEach((boton) => {
        boton.addEventListener("click", () => {
            const id = boton.dataset.id;
            const nombreCurso = boton.dataset.nombre;

            mostrarConfirmacion(
                "¿Desactivar curso?",
                `“${nombreCurso}” dejará de estar disponible para nuevas configuraciones. Sus datos históricos se conservarán.`,
                "Sí, desactivar",
                "btn-desactivar",
                () => {
                    formAccion.action = `${formAccion.dataset.baseUrl}${id}/desactivar`;
                    formAccion.submit();
                }
            );
        });
    });

    // Reabrir modal en caso de error retornado por Spring controller (BindingResult)
    if (document.body.dataset.abrirModal === "true") {
        tituloCursoModal.textContent = cursoId.value === "" ? "Nuevo curso" : "Editar curso";
        modalCurso.show();
    }
});