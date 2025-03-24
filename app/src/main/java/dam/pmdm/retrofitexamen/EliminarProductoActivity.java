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
import dam.pmdm.retrofitexamen.databinding.ActivityDetalleProductoBinding;
import dam.pmdm.retrofitexamen.databinding.ActivityEliminarProductoBinding;
import dam.pmdm.retrofitexamen.model.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EliminarProductoActivity extends AppCompatActivity {

    //private ProgressBar progressBar;
    private ActivityEliminarProductoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEliminarProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Establecemos la vista inflada

        binding.btnEliminarProducto.setOnClickListener(v -> {
            //progressBar.setVisibility(View.VISIBLE);
            buscarProducto();
        });

    }

    private void buscarProducto() {


        String eanTexto = binding.etProductoEan.getText().toString().trim();

        if (eanTexto.isEmpty()) {
            Toast.makeText(this, "Ingrese un EAN válido", Toast.LENGTH_SHORT).show();
            //progressBar.setVisibility(View.GONE);
            return;
        }

        ProductoController api = ProductoController.retrofit.create(ProductoController.class);


        api.obtenerProductoPorEan(eanTexto).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtenemos el pedido de la respuesta
                    Producto producto = response.body();

                    eliminarProducto();
                    Toast.makeText(EliminarProductoActivity.this, "Producto eliminado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EliminarProductoActivity.this, "El producto no existe", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                // Si hay un error en la conexión o servidor, mostramos un mensaje con la causa
                Toast.makeText(EliminarProductoActivity.this, "Error al buscar producto: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void eliminarProducto() {
        String eanTexto = binding.etProductoEan.getText().toString().trim();
        ProductoController api = ProductoController.retrofit.create(ProductoController.class);

        api.eliminarProducto(eanTexto).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtenemos el pedido de la respuesta
                    Producto producto = response.body();

                    Toast.makeText(EliminarProductoActivity.this, "Producto eliminado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EliminarProductoActivity.this, "El producto no existe", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(EliminarProductoActivity.this, "Error al eliminar producto: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}