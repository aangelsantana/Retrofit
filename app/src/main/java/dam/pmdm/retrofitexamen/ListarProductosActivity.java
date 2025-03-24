package dam.pmdm.retrofitexamen;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import dam.pmdm.retrofitexamen.controller.ProductoController;
import dam.pmdm.retrofitexamen.databinding.ActivityListarProductosBinding;
import dam.pmdm.retrofitexamen.model.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarProductosActivity extends AppCompatActivity {


    private ActivityListarProductosBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Inflamos la vista con View Binding
        binding = ActivityListarProductosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Llamamos al método para cargar los productos


        ProductoController api = ProductoController.retrofit.create(ProductoController.class);

        // Llamada asíncrona para obtener los productos
        api.obtenerTodosLosProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) { //&& response.body() != null
                    // Obtenemos la lista de productos
                    List<Producto> productos = response.body();

                    if (productos.isEmpty()) {
                        Toast.makeText(ListarProductosActivity.this, "No hay productos disponibles", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Creamos un adaptador para el ListView
                    ArrayAdapter<Producto> adapter = new ArrayAdapter<>(
                            ListarProductosActivity.this,
                            android.R.layout.simple_list_item_1,
                            productos
                    );

                    // Asignamos el adaptador al ListView
                    binding.listViewProductos.setAdapter(adapter);
                } else {
                    Toast.makeText(ListarProductosActivity.this, "Error al cargar pedidos: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("ListarProductosActivity", "Respuesta fallida: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                // Manejamos los errores de conexión
                Toast.makeText(ListarProductosActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ListarProductosActivity", "Error al cargar productos: ", t);
            }
        });


    }
}