package dam.pmdm.retrofitexamen;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dam.pmdm.retrofitexamen.controller.ProductoController;
import dam.pmdm.retrofitexamen.databinding.ActivityCrearProductoBinding;
import dam.pmdm.retrofitexamen.model.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearProductoActivity extends AppCompatActivity {

    //private ProgressBar progressBar;
    private ActivityCrearProductoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      // progressBar = findViewById(R.id.progressBar);

        // Inflamos la vista usando View Binding
        binding = ActivityCrearProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Establecemos la vista inflada

        // Configuramos el botón para crear un nuevo pedido cuando se haga clic
        binding.btnCrear.setOnClickListener(v -> {
        // progressBar.setVisibility(View.VISIBLE);
            crearProducto();
        });

    }

    private void crearProducto() {

        // Obtener los datos ingresados por el usuario
        String ean = binding.etEan.getText().toString(); // EAN
        String nombre = binding.etNombre.getText().toString(); // Nombre
        String precioTexto = binding.etPrecio.getText().toString(); // Precio
        double precioNumero = Double.parseDouble(precioTexto);
        String marca = binding.etMarca.getText().toString(); // Marca
        String categoria = binding.etCategoria.getText().toString(); // Categoria


        Producto nuevoProducto = new Producto(ean, nombre, precioNumero, marca, categoria);

        ProductoController api = ProductoController.retrofit.create(ProductoController.class);

        api.crearProducto(nuevoProducto).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) { // Si la solicitud fue exitosa
                    Toast.makeText(CrearProductoActivity.this, "Producto creado con éxito", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad después de crear el producto
                } else {
                    Toast.makeText(CrearProductoActivity.this, "Error al crear el producto", Toast.LENGTH_SHORT).show();
                    // progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                // Si hay un error en la conexión o servidor, mostramos un mensaje con la causa
                Toast.makeText(CrearProductoActivity.this, "Error de conexión/servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // progressBar.setVisibility(View.GONE);

            }
        });



    }
}