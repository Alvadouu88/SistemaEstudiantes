document.addEventListener("DOMContentLoaded", () => {
    // Instancias de modales de Bootstrap
    const elemModalEstudiante = document.getElementById("estudianteModal");
    const elemModalConfirmacion = document.getElementById("confirmacionModal");
    const modalEstudiante = new bootstrap.Modal(elemModalEstudiante);
    const modalConfirmacion = new bootstrap.Modal(elemModalConfirmacion);

    // Elementos del DOM
    const formEstudiante = document.getElementById("formEstudiante");
    const formAccion = document.getElementById("formAccion");
    const estudianteId = document.getElementById("estudianteId");
    const codigo = document.getElementById("codigo");
    const tipoDocumento = document.getElementById("tipoDocumento");
    const numeroDocumento = document.getElementById("numeroDocumento");
    const nombres = document.getElementById("nombres");
    const apellidos = document.getElementById("apellidos");
    const fechaNacimiento = document.getElementById("fechaNacimiento");
    const correo = document.getElementById("correo");
    const telefono = document.getElementById("telefono");
    const estado = document.getElementById("estado");

    const tituloEstudianteModal = document.getElementById("tituloEstudianteModal");
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
        formEstudiante.classList.remove("was-validated");
        formEstudiante.querySelectorAll(".is-invalid").forEach(el => el.classList.remove("is-invalid"));
    };

    // Botón "+ Añadir estudiante"
    document.getElementById("btnNuevoEstudiante").addEventListener("click", () => {
        formEstudiante.reset();
        limpiarValidaciones();
        estudianteId.value = "";
        tipoDocumento.value = "DNI";
        estado.value = "ACTIVO";
        tituloEstudianteModal.textContent = "Nuevo estudiante";
        modalEstudiante.show();
    });

    // Botones "Editar"
    document.querySelectorAll(".btn-editar").forEach((boton) => {
        boton.addEventListener("click", () => {
            formEstudiante.reset();
            limpiarValidaciones();
            estudianteId.value = boton.dataset.id;
            codigo.value = boton.dataset.codigo;
            tipoDocumento.value = boton.dataset.tipoDocumento;
            numeroDocumento.value = boton.dataset.numeroDocumento;
            nombres.value = boton.dataset.nombres;
            apellidos.value = boton.dataset.apellidos;
            fechaNacimiento.value = boton.dataset.fechaNacimiento || "";
            correo.value = boton.dataset.correo || "";
            telefono.value = boton.dataset.telefono || "";
            estado.value = boton.dataset.estado || "ACTIVO";

            tituloEstudianteModal.textContent = "Editar estudiante";
            modalEstudiante.show();
        });
    });

    // Submit del formulario
    formEstudiante.addEventListener("submit", (evento) => {
        evento.preventDefault();

        if (!formEstudiante.checkValidity()) {
            formEstudiante.classList.add("was-validated");
            return;
        }

        const editando = estudianteId.value !== "";
        const nombreCompleto = `${nombres.value.trim()} ${apellidos.value.trim()}`;

        mostrarConfirmacion(
            editando ? "¿Modificar estudiante?" : "¿Registrar estudiante?",
            editando
                ? `Se actualizará la información de “${nombreCompleto}”.`
                : `Se registrará a “${nombreCompleto}” en el sistema.`,
            editando ? "Sí, modificar" : "Sí, registrar",
            "btn-primary",
            () => formEstudiante.submit()
        );
    });

    // Botones "Desactivar" (cambian el estado a INACTIVO de forma rápida)
    document.querySelectorAll(".btn-desactivar").forEach((boton) => {
        boton.addEventListener("click", () => {
            const id = boton.dataset.id;
            const nombreCompleto = boton.dataset.nombre;

            mostrarConfirmacion(
                "¿Desactivar estudiante?",
                `“${nombreCompleto}” pasará a estado Inactivo y no podrá realizar nuevas matrículas.`,
                "Sí, desactivar",
                "btn-danger",
                () => {
                    formAccion.action = `${formAccion.dataset.baseUrl}${id}/desactivar`;
                    formAccion.submit();
                }
            );
        });
    });

    // Reabrir modal si la validación en el servidor devolvió errores (BindingResult)
    if (document.body.dataset.abrirModal === "true") {
        tituloEstudianteModal.textContent = estudianteId.value === "" ? "Nuevo estudiante" : "Editar estudiante";
        modalEstudiante.show();
    }
});