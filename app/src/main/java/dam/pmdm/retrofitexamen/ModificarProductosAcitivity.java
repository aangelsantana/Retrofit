package dam.pmdm.retrofitexamen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dam.pmdm.retrofitexamen.controller.ProductoController;
import dam.pmdm.retrofitexamen.databinding.ActivityModificarProductosAcitivityBinding;
import dam.pmdm.retrofitexamen.model.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificarProductosAcitivity extends AppCompatActivity {


    //private ProgressBar progressBar;
    private ActivityModificarProductosAcitivityBinding binding; // Objeto para la vinculación de vistas (View Binding)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       //progressBar = findViewById(R.id.progressBar);

        binding = ActivityModificarProductosAcitivityBinding.inflate(getLayoutInflater()); // Inicializa el binding
        setContentView(binding.getRoot()); // Establece la vista para la actividad

        // Se obtiene una instancia de PedidoController para realizar las peticiones a la API
        ProductoController api = ProductoController.retrofit.create(ProductoController.class);

        // Se asignan acciones a los botones
        binding.btnBuscar.setOnClickListener(v -> {
           //progressBar.setVisibility(View.VISIBLE);
            buscarProducto(api);
        }); // Al hacer clic en "Buscar", se ejecuta buscarPedido()

        binding.btnModificar.setOnClickListener(v -> {
           //progressBar.setVisibility(View.VISIBLE);
            modificarProducto(api);
        });

    }

    private void modificarProducto(ProductoController api) {
        // Obtiene el ID del pedido ingresado en el campo de texto y lo convierte a entero
        String ean = binding.etProductoEan.getText().toString().trim();

        // Obtiene los valores de los campos de texto
        String nombre = binding.etModNombre.getText().toString();
        String precio = binding.etModPrecio.getText().toString(); // Precio
        String marca = binding.etModMarca.getText().toString();
        String categoria = binding.etModCategoria.getText().toString();

        Producto producto = null; // Variable para almacenar el pedido a modificar

        // Validación de los datos ingresados (ningún campo debe estar vacío)
        if (!nombre.isEmpty()) {
            if (!precio.isEmpty()) {
                if (!marca.isEmpty()) {
                    if (!categoria.isEmpty()) {
                        // Si todos los campos obligatorios están completos, se crea un objeto Pedido con los datos ingresados
                        producto = new Producto(
                                ean,
                                nombre,
                                Double.parseDouble(precio),
                                marca,
                                categoria
                        );
                    } else {
                        Toast.makeText(this, "La categoria no puede estar vacío", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "La marca no puede estar vacia", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "El precio no puede estar vacio", Toast.LENGTH_SHORT).show();
            }

            // Si el pedido es válido, se envía la solicitud de modificación a la API
            if (producto != null) {
                api.modificarProducto(ean, producto).enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call, Response<Producto> response) {
                        // Si la respuesta es exitosa
                        if (response.isSuccessful()) {
                            habilitarBotones(false); // Deshabilita los botones después de modificar
                            limpiarDatos(); // Limpia los campos de entrada
                            Toast.makeText(ModificarProductosAcitivity.this, "Modificación realizada con éxito", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ModificarProductosAcitivity.this, "Error al modificar el producto", Toast.LENGTH_SHORT).show();
                           // progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {
                        // Maneja el error en caso de fallo de conexión
                    }
                });
            }
        }else {
            Toast.makeText(this, "El nombre no puede estar vacio", Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarProducto(ProductoController api) {
        // Obtiene el ID del pedido desde el campo de entrada
        String ean = binding.etProductoEan.getText().toString().trim();

        // Llama a la API para obtener los datos del pedido por ID
        api.obtenerProductoPorEan(ean).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                // Si la respuesta es exitosa y el cuerpo de la respuesta no es nulo
                if (response.isSuccessful() && response.body() != null) {
                    Producto producto = response.body(); // Obtiene el pedido desde la respuesta
                    Log.d("Prueba", "PRODUCTO: " + producto.getNombre() + " - " + producto.getPrecio() + " - " + producto.getMarca());

                    // Llena los campos de texto con los datos obtenidos del pedido
                    binding.etModNombre.setText(String.valueOf(producto.getNombre()));
                    binding.etModPrecio.setText(String.valueOf(producto.getPrecio()));
                    binding.etModMarca.setText(producto.getMarca());
                    binding.etModCategoria.setText(producto.getCategoria());

                    habilitarBotones(true); // Habilita los botones para modificar
                } else {
                    Toast.makeText(ModificarProductosAcitivity.this, "El producto no se ha encontrado", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                // Muestra un mensaje si hay un error en la conexión
                Toast.makeText(ModificarProductosAcitivity.this, "Error al buscar pedido: " + t.getMessage(), Toast.LENGTH_SHORT).show();
               //progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void habilitarBotones(boolean b) {
        binding.btnModificar.setEnabled(b); // Habilita o deshabilita el botón de modificar
        binding.etModNombre.setEnabled(b);
        binding.etModPrecio.setEnabled(b);
        binding.etModMarca.setEnabled(b);
        binding.etModCategoria.setEnabled(b);
    }

    private void limpiarDatos() {
        binding.etProductoEan.setText("");
        binding.etModNombre.setText("");
        binding.etModPrecio.setText("");
        binding.etModMarca.setText("");
        binding.etModCategoria.setText("");
    }


}