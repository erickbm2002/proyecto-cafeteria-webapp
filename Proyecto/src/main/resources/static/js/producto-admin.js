
let productos = [];

// Inicializar cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', function() {
    console.log('🚀 Script productos-admin.js cargado');
    
    // Cargar productos desde el atributo data
    const productosData = document.getElementById('productos-data');
    if (productosData) {
        try {
            productos = JSON.parse(productosData.textContent);
            console.log('✅ Productos cargados:', productos.length);
        } catch (e) {
            console.error('❌ Error al parsear productos:', e);
        }
    }
    
    // Event listener para limpiar modal al cerrar
    const modal = document.getElementById('modalProducto');
    if (modal) {
        modal.addEventListener('hidden.bs.modal', limpiarFormulario);
    }
});

function editarProducto(id) {
    console.log('📝 Editando producto ID:', id);
    
    const producto = productos.find(p => p.idProducto == id);
    if (!producto) {
        console.error('❌ Producto no encontrado:', id);
        return;
    }

    console.log('✅ Producto encontrado:', producto);

    document.getElementById('modalProductoLabel').textContent = 'Editar Producto';
    document.getElementById('idProducto').value = producto.idProducto;
    document.getElementById('nombre').value = producto.nombre;
    document.getElementById('descripcion').value = producto.descripcion || '';
    document.getElementById('precio').value = producto.precio;
    document.getElementById('activo').checked = producto.activo;
    
    if (producto.categoria && producto.categoria.idCategoria) {
        document.getElementById('categoriaId').value = producto.categoria.idCategoria;
    }
    
    if (producto.nombreImagen) {
        document.getElementById('nombreImagenActual').value = producto.nombreImagen;
        document.getElementById('file').required = false;
        document.getElementById('preview').src = '/img/' + producto.nombreImagen;
        document.getElementById('imagePreview').style.display = 'block';
    }
}

function previewImage(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('preview').src = e.target.result;
            document.getElementById('imagePreview').style.display = 'block';
        };
        reader.readAsDataURL(file);
    }
}

function confirmarEliminar(id, nombre) {
    console.log('🗑️ Confirmar eliminar:', id, nombre);
    document.getElementById('nombreProductoEliminar').textContent = nombre;
    document.getElementById('btnConfirmarEliminar').href = '/admin/administrar/productos/eliminar/' + id;
}

function limpiarFormulario() {
    console.log('🧹 Limpiando formulario');
    document.getElementById('formProducto').reset();
    document.getElementById('idProducto').value = '';
    document.getElementById('nombreImagenActual').value = '';
    document.getElementById('modalProductoLabel').textContent = 'Agregar Producto';
    document.getElementById('imagePreview').style.display = 'none';
    document.getElementById('file').required = true;
    document.getElementById('activo').checked = true;
}