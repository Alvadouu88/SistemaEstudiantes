document.addEventListener("DOMContentLoaded", () => {
    // Instancias de modales de Bootstrap
    const elemModalDocente = document.getElementById("docenteModal");
    const elemModalConfirmacion = document.getElementById("confirmacionModal");
    const modalDocente = new bootstrap.Modal(elemModalDocente);
    const modalConfirmacion = new bootstrap.Modal(elemModalConfirmacion);

    // Elementos del DOM
    const formDocente = document.getElementById("formDocente");
    const formAccion = document.getElementById("formAccion");
    const docenteId = document.getElementById("docenteId");
    const codigo = document.getElementById("codigo");
    const tipoDocumento = document.getElementById("tipoDocumento");
    const numeroDocumento = document.getElementById("numeroDocumento");
    const nombres = document.getElementById("nombres");
    const apellidos = document.getElementById("apellidos");
    const correo = document.getElementById("correo");
    const telefono = document.getElementById("telefono");
    const activo = document.getElementById("activo");

    const tituloDocenteModal = document.getElementById("tituloDocenteModal");
    const tituloConfirmacion = document.getElementById("tituloConfirmacion");
    const mensajeConfirmacion = document.getElementById("mensajeConfirmacion");
    const btnConfirmarAccion = document.getElementById("btnConfirmarAccion");

    // Función auxiliar para mostrar el modal de confirmación
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
        formDocente.classList.remove("was-validated");
        formDocente.querySelectorAll(".is-invalid").forEach(el => el.classList.remove("is-invalid"));
    };

    // Botón "+ Añadir docente"
    document.getElementById("btnNuevoDocente").addEventListener("click", () => {
        formDocente.reset();
        limpiarValidaciones();
        docenteId.value = "";
        tipoDocumento.value = "DNI";
        activo.checked = true;
        tituloDocenteModal.textContent = "Nuevo docente";
        modalDocente.show();
    });

    // Botones "Editar"
    document.querySelectorAll(".btn-editar").forEach((boton) => {
        boton.addEventListener("click", () => {
            formDocente.reset();
            limpiarValidaciones();
            docenteId.value = boton.dataset.id;
            codigo.value = boton.dataset.codigo;
            tipoDocumento.value = boton.dataset.tipoDocumento;
            numeroDocumento.value = boton.dataset.numeroDocumento;
            nombres.value = boton.dataset.nombres;
            apellidos.value = boton.dataset.apellidos;
            correo.value = boton.dataset.correo || "";
            telefono.value = boton.dataset.telefono || "";
            activo.checked = boton.dataset.activo === "true";
            tituloDocenteModal.textContent = "Editar docente";
            modalDocente.show();
        });
    });

    // Submit del formulario de Docente
    formDocente.addEventListener("submit", (evento) => {
        evento.preventDefault();

        if (!formDocente.checkValidity()) {
            formDocente.classList.add("was-validated");
            return;
        }

        const editando = docenteId.value !== "";
        const nombreDocente = `${nombres.value.trim()} ${apellidos.value.trim()}`;

        mostrarConfirmacion(
            editando ? "¿Modificar docente?" : "¿Registrar docente?",
            editando
                ? `Se actualizará la información de “${nombreDocente}”.`
                : `Se registrará a “${nombreDocente}” en la plana docente.`,
            editando ? "Sí, modificar" : "Sí, registrar",
            "btn-agregar",
            () => formDocente.submit()
        );
    });

    // Botones "Desactivar"
    document.querySelectorAll(".btn-desactivar").forEach((boton) => {
        boton.addEventListener("click", () => {
            const id = boton.dataset.id;
            const nombreDocente = boton.dataset.nombre;

            mostrarConfirmacion(
                "¿Desactivar docente?",
                `“${nombreDocente}” dejará de estar disponible para ser asignado a secciones. Sus registros previos se conservarán.`,
                "Sí, desactivar",
                "btn-desactivar",
                () => {
                    formAccion.action = `${formAccion.dataset.baseUrl}${id}/desactivar`;
                    formAccion.submit();
                }
            );
        });
    });

    // Reabrir modal si la validación en el servidor devolvió errores (BindingResult)
    if (document.body.dataset.abrirModal === "true") {
        tituloDocenteModal.textContent = docenteId.value === "" ? "Nuevo docente" : "Editar docente";
        modalDocente.show();
    }
});