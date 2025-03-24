package dam.pmdm.retrofitexamen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dam.pmdm.retrofitexamen.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //private ProgressBar progressBar;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Usamos el binding generado automáticamente para asignar la vista a la actividad.
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuración para el botón que permite ver todos los pedidos
        binding.btnListarProductos.setOnClickListener(v -> {
            // Creamos un Intent para navegar a ListarPedidosActivity
            Intent i = new Intent(MainActivity.this, ListarProductosActivity.class);
            // Iniciamos la nueva actividad
            startActivity(i);
        });

        // Configuración para el botón que permite consultar un pedido por su ID
        binding.btnConsultarProducto.setOnClickListener(v -> {
            // Creamos un Intent para navegar a DetallePedidoActivity
            Intent i = new Intent(MainActivity.this, DetalleProductoActivity.class);
            // Iniciamos la nueva actividad
            startActivity(i);
        });

        // Configuración para el botón que permite crear un nuevo pedido
        binding.btnCrearProducto.setOnClickListener(v -> {
            // Creamos un Intent para navegar a CrearPedidoActivity
            Intent i = new Intent(MainActivity.this, CrearProductoActivity.class);
            // Iniciamos la nueva actividad
            startActivity(i);
        });


        binding.btnModificarProducto.setOnClickListener(v -> {
            // Crea un Intent para abrir la actividad ModificarPedidos
            Intent i = new Intent(MainActivity.this, ModificarProductosAcitivity.class);
            startActivity(i); // Inicia la nueva actividad
        });


        binding.btnEliminarProducto.setOnClickListener(v -> {
            // Crea un Intent para abrir la actividad ModificarPedidos
            Intent i = new Intent(MainActivity.this, EliminarProductoActivity.class);
            startActivity(i); // Inicia la nueva actividad
        });

    }
}