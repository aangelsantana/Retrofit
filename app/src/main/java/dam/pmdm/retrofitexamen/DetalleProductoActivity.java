package dam.pmdm.retrofitexamen;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dam.pmdm.retrofitexamen.controller.ProductoController;
import dam.pmdm.retrofitexamen.databinding.ActivityDetalleProductoBinding;
import dam.pmdm.retrofitexamen.model.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleProductoActivity extends AppCompatActivity {

   // private ProgressBar progressBar;

    private ActivityDetalleProductoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // progressBar = findViewById(R.id.progressBar);


        // Inflamos la vista usando View Binding
        binding = ActivityDetalleProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Establecemos la vista inflada

        // Configuramos el botón para buscar el pedido por su ID
        binding.btnBuscar.setOnClickListener(v -> {
            //progressBar.setVisibility(View.VISIBLE);
            buscarProducto();
        });

    }

    private void buscarProducto() {

        // Obtenemos el ID del EditText y lo convertimos a número
        String eanTexto = binding.etProductoEan.getText().toString().trim();

        if (eanTexto.isEmpty()) {
            Toast.makeText(this, "Ingrese un EAN válido", Toast.LENGTH_SHORT).show();
           // progressBar.setVisibility(View.GONE);
            return;
        }

        ProductoController api = ProductoController.retrofit.create(ProductoController.class);


        api.obtenerProductoPorEan(eanTexto).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtenemos el pedido de la respuesta
                    Producto producto = response.body();

                    // Construimos la información para mostrar en la pantalla
                    String info = "EAN: " + producto.getEan() +
                            "\nNombre: " + producto.getNombre() +
                            "\nPrecio: " + producto.getPrecio() +
                            "\nMarca: " + producto.getMarca() +
                            "\nCategoria: " + producto.getCategoria();

                    // Mostramos la información en el TextView
                    binding.tvResultado.setText(info);
                } else {
                    binding.tvResultado.setText("Producto no encontrado");
                   // progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                // Si hay un error en la conexión o servidor, mostramos un mensaje con la causa
                Toast.makeText(DetalleProductoActivity.this, "Error al buscar producto: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
            }
        });
    }
}