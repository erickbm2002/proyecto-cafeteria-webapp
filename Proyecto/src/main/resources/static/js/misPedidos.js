
      $(document).ready(function() {
        // Filtro por estado
        $('#filtroEstado').change(function() {
          const estado = $(this).val();
          
          if (estado === 'todos') {
            $('.pedido-card').show();
          } else {
            $('.pedido-card').hide();
            $(`.pedido-card[data-estado="${estado}"]`).show();
          }
        });

        // Auto-cerrar alertas
        setTimeout(function() {
          $('.alert').fadeOut('slow');
        }, 5000);
      });

      // Función para confirmar cancelación
      function confirmarCancelacion(idOrden) {
        if (confirm('¿Estás seguro que deseas cancelar este pedido?')) {
          // Aquí iría la llamada AJAX para cancelar
          window.location.href = '/admin/ordenes/cancelar/' + idOrden;
        }
      }
