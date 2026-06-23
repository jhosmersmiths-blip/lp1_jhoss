/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


$(document).ready(function () {
cargarProductos();
        verificarSesion();
        });
        function cargarProductos() {
        fetch('AppController?action=listarProductos')
                .then(res => res.json())
                .then(productos => {
                const contenedor = $('#lista-productos');
                        contenedor.empty();
                        productos.forEach(p => {
                        contenedor.append(`
                   <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                <div class="card h-100 shadow-sm border-0">
                    <img src="${p.imagen}" alt="${p.nombre}" class="car-img-top p-2"/>
                    <div class="card-body d-flex flex-column">
                        <h6 class="card-title fw-bold">${p.nombre}</h6>
                        <p class="card-text text-muted small flex-grow-1">${p.descripcion}</p>
                        <div class="d-flex justify-content-between align-items-center mt-3">
                            <span class="fs-5 fw-bold text-info">${p.precio.toFixed(2)}</span>
                            <span class="badge ${p.stock > 0} 'bg-light text-success':
                                         'bg-light text-danger'}">${p.stock}</span> 
                        </div>
                        <button onclick="agregarCarrito(${p.id_producto})" class="btn btn-info text-white w-100 mt-3">
                            <i class="bi bi-cart-plus me-2">Agregar</i>
                        </button>
                    </div> 
                </div>
            </div> `);
                        });
                }).catch(err => console.log("Error al cargar productos", err));
                }

function agregarCarrito(id) {
console.log(id);
        fetch(`AppController?action=AddCarrito&id=${id}`, {method: 'POST'})
        .then(res => res.json())
        .then(data => {
        if (data.success) {
        $('#cart-count').text(data.cartCount);
                Swal.fire({
                toast: true,
                        position: 'top-end',
                        icon: 'success',
                        title: 'Producto agregado',
                        showConfirmButton: false,
                        timer: 1500
                });
        }
        });
        }
function cargarCarrito() {
const tabla = $('#tabla-carrito tbody');
        if (tabla.length === 0)
        return;
        fetch('AppController?action=listarCarrito')
        .then(res => res.json())
        .then(data => {
        tabla.empty();
                if (data.items.length === 0) {
        tabla.append('<tr><td colspan="6" class="text-center py-4">El carrito está vacío</td></tr>');
                $('#resumen-total, #resumen-subtotal').text('$0.00');
                return;
        }

        data.items.forEach((item, index) => {
        tabla.append(`
                    <tr>
                        <td>${index + 1}</td>
                        <td><span class="fw-bold">${item.nombre}</span></td>
                        <td>$${item.precioCompra.toFixed(2)}</td>
                        <td>
                            <span class="badge bg-light text-dark border p-2">${item.cantidad}</span>
                        </td>
                        <td class="fw-bold">$${item.subTotal.toFixed(2)}</td>
                        <td>
                            <button class="btn btn-link text-danger p-0" onclick="eliminarItemCarrito(${item.idProducto})">
                                <i class="bi bi-x-circle-fill"></i>
                            </button>
                        </td>
                    </tr>
                `);
        });
                $('#resumen-total, #resumen-subtotal').text(`$${data.total.toFixed(2)}`);
                $('#cart-count').text(data.items.length);
        });
        }
function actualizarContadorCarrito() {
fetch('AppController?action=listarCarrito')
        .then(res => res.json())
        .then(data => {
        $('#cart-count').text(data.items ? data.items.length : 0);
        });
        }

function verificarSesion() {
const user = JSON.parse(sessionStorage.getItem("usuario"));
        if (user) {
// 1. Intercambio de botones
$('#btn-login-modal').addClass('d-none');
        $('#user-profile').removeClass('d-none');
        $('#user-name').text(user.persona.nombre);
        // 2. Verificación de Rol
        if (user.rol === "ADMIN") {
console.log("Intentando mostrar menú de Admin...");
        // Usamos un pequeño delay para asegurar que el navegador ya renderizó el header
        setTimeout(() => {
        const link = $('#link-admin');
                const sep = $('#separator-admin');
                if (link.length > 0) {
        link.removeClass('d-none').attr('style', 'display: block !important');
                sep.removeClass('d-none').attr('style', 'display: block !important');
                console.log("✅ Menú de Admin mostrado con éxito");
        } else {
        console.error("❌ Error: No se encontró el ID #link-admin en el DOM");
        }
        }, 300);
}
}
}

function logout() {
fetch('AuthController?action=Salir', {method: 'POST'})
        .then(() => {
        sessionStorage.clear();
                window.location.href = "index.html";
        });
        }

function inicializarEventosAuth() {
// LOGIN
$(document).on('submit', '#form-login', function (e) {
e.preventDefault();
        const datos = $(this).serialize(); // action=validar&usuario=...&password=...

        fetch('AuthController?action=validar', {
        method: 'POST',
                body: new URLSearchParams(datos)
        })
        .then(res => res.json())
        .then(data => {
        if (data.success) {
        sessionStorage.setItem("usuario", JSON.stringify(data.userData));
                location.reload(); // Recargar para actualizar el menú
        } else {

        Swal.fire("Error", data.message, "error");
        }
        });
});
        //register
        $(document).on('submit', '#form-register', function (e) {
e.preventDefault();
        const datos = $(this).serialize(); // action=validar&usuario=...&password=...

        fetch('AuthController?action=register', {
        method: 'POST',
                body: new URLSearchParams(datos)
        })
        .then(res => res.json())
        .then(data => {
        if (data.success) {
        Swal.fire("¡Bienvenido!", "Registro Exitoso", "success")
                .then(() => {
                $('#modalRegister').modal('hide');
                        $('#modalLogin').modal('show');
                });
        } else {

        Swal.fire("Error", data.message, "error");
        }
        });
});
        }

function procesarCompra() {
// 1. VALIDACIÓN: ¿El carrito tiene productos?
// Obtenemos el número del contador del header
const cantidadProductos = parseInt($('#cart-count').text()) || 0;
        if (cantidadProductos === 0) {
Swal.fire({
title: 'Carrito Vacío',
        text: "No puedes realizar una compra sin productos. ¡Ve a buscar algo que te guste!",
        icon: 'warning',
        confirmButtonColor: '#0dcaf0',
        confirmButtonText: 'Ir a la tienda'
}).then(() => {
window.location.href = "index.html";
});
        return; // Detenemos la función aquí
}

// 2. VALIDACIÓN: ¿El usuario está logueado?
const user = JSON.parse(sessionStorage.getItem("usuario"));
        if (!user) {
Swal.fire({
title: 'Inicia Sesión',
        text: "Debes estar logueado para finalizar la compra",
        icon: 'info',
        showCancelButton: true,
        confirmButtonColor: '#0dcaf0',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Ir al Login',
        cancelButtonText: 'Cancelar'
}).then((result) => {
if (result.isConfirmed) {
$('#modalLogin').modal('show');
}
});
        return; // Detenemos la función aquí
}

// 3. CONFIRMACIÓN: Si pasó las validaciones anteriores, preguntamos
Swal.fire({
title: '¿Confirmar Compra?',
        text: `Estás por comprar ${cantidadProductos} producto(s). ¿Deseas continuar?`,
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#0dcaf0',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Sí, comprar ahora',
        cancelButtonText: 'Revisar más'
}).then((result) => {
if (result.isConfirmed) {
// Mostramos un cargando mientras el servidor procesa
Swal.fire({
title: 'Procesando pedido...',
        didOpen: () => {
Swal.showLoading()
},
        allowOutsideClick: false
});
        fetch('AppController?action=GenerarCompra', {method: 'POST'})
        .then(res => res.json())
        .then(data => {
        if (data.success) {
        Swal.fire('¡Éxito!', data.message, 'success')
                .then(() => {
                // Limpiamos el contador visualmente y redirigimos
                $('#cart-count').text('0');
                        window.location.href = "index.html";
                });
        } else {
        Swal.fire('Error', data.message, 'error');
        }
        })
        .catch(err => {
        Swal.fire('Error', 'Hubo un problema en la conexión', 'error');
        });
}
});
        }

function cargarTablaAdmin() {
const tablaElemento = $('#tabla-productos');
        if (tablaElemento.length === 0)
        return;
        if ($.fn.DataTable) {
if ($.fn.DataTable.isDataTable('#tabla-productos')) {
tablaElemento.DataTable().destroy();
}

tablaElemento.DataTable({
"ajax": {
"url": "ProductoController?action=listar",
        "dataSrc": ""
},
        "columns": [
        {
        "data": null,
                "render": function (data, type, row, meta) {
                return meta.row + 1;
                }
        },
        {
        "data": "imagen",
                "render": function (data) {
                return `<img src="${data}" width="50" class="img-thumbnail shadow-sm">`;
                }
        },
        {"data": "nombre"},
        {
        "data": "precio",
                "render": function (data) {
                return `<b>$${data.toFixed(2)}</b>`;
                }
        },
        {"data": "stock"},
        {
        "data": null,
                "render": function (data) {
                return `
                            <div class="btn-group">
                                <button class="btn btn-warning btn-sm" onclick="editarProducto(${data.id_producto})">
                                    <i class="bi bi-pencil-square"></i>
                                </button>
                                <button class="btn btn-danger btn-sm" onclick="eliminarProducto(${data.id_producto})">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        `;
                }
        }
        ],
        "language": {
        "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron resultados",
                "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                "infoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sSearch": "Buscar:",
                "oPaginate": {
                "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                },
                "sProcessing": "Procesando...",
        }
});
}
}
//abrir modal nuevo producto
function abrirModalNuevo() {
    $('#form-producto')[0].reset();
    $('#action').val('guardar');
    $('#tituloModal').text('Nuevo Producto');
    $('#modalProducto').modal('show');
}
$(document).on('submit', '#form-producto', function (e) {
    e.preventDefault();

    // IMPORTANTE: Para enviar archivos usamos FormData
    const formData = new FormData(this);

    fetch('ProductoController', {
        method: 'POST',
        body: formData
    })
            .then(res => res.json())
            .then(res => {
                if (res) {
                    Swal.fire("¡Éxito!", "Producto guardado correctamente", "success");
                    $('#modalProducto').modal('hide');
                    cargarTablaAdmin(); // Recargar la tabla
                } else {
                    Swal.fire("Error", "No se pudo guardar el producto", "error");
                }
            });
});
// FUNCIÓN PARA EDITAR
function editarProducto(id) {
    // 1. Buscamos los datos del producto por ID
    fetch(`ProductoController?action=buscar&id=${id}`)
            .then(res => res.json())
            .then(p => {
                // 2. Llenamos los campos del modal con los datos recibidos
                $('#id_producto').val(p.id_producto);
                $('#nombre').val(p.nombre);
                $('#descripcion').val(p.descripcion);
                $('#precio').val(p.precio);
                $('#stock').val(p.stock);

                // 3. Cambiamos el título y la acción
                $('#action').val('editar');
                $('#tituloModal').text('Editar Producto');

                // 4. Mostramos el modal
                $('#modalProducto').modal('show');
            })
            .catch(err => {
                console.error("Error al buscar producto:", err);
                Swal.fire("Error", "No se pudo obtener los datos del producto", "error");
            });
}

// FUNCIÓN PARA ELIMINAR
function eliminarProducto(id) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "¡No podrás revertir esto!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`ProductoController?action=eliminar&id=${id}`, {method: 'POST'})
                    .then(res => res.json())
                    .then(res => {
                        if (res) {
                            Swal.fire('Eliminado', 'El producto ha sido borrado.', 'success');
                            cargarTablaAdmin();
                        }
                    });
        }
    });
}