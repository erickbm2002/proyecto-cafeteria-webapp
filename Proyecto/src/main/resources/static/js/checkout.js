
      $(document).ready(function() {
        // Mostrar/ocultar campos de tarjeta
        function toggleCamposTarjeta() {
          if ($('#tarjeta').is(':checked')) {
            $('#infoTarjeta').slideDown();
          } else {
            $('#infoTarjeta').slideUp();
          }
        }

        $('input[name="metodoPago"]').change(function() {
          toggleCamposTarjeta();
        });

        // Estado inicial
        toggleCamposTarjeta();

        // Validación antes de enviar
        $('#checkoutForm').submit(function(e) {
          const metodoPago = $('input[name="metodoPago"]:checked').val();
          const direccion = $('#direccionEnvio').val().trim();

          if (!direccion) {
            e.preventDefault();
            alert('Por favor ingresa una dirección de entrega');
            return false;
          }

          // Confirmar pedido
          if (!confirm('¿Confirmas que deseas realizar este pedido?')) {
            e.preventDefault();
            return false;
          }
        });

        // Auto-cerrar alertas
        setTimeout(function() {
          $('.alert').fadeOut('slow');
        }, 5000);
      });
