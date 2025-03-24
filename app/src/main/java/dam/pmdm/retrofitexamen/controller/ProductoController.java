package dam.pmdm.retrofitexamen.controller;

import java.util.List;

import dam.pmdm.retrofitexamen.model.Producto;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductoController {



    // URL base de la API (en este caso, usando la IP del emulador Android para localhost)
    String API_URL =  "http://10.0.2.2:8080/";
    // NOTA: "10.0.2.2" es la dirección que permite al emulador acceder al localhost de la máquina host.

    // Instancia de Retrofit para gestionar las solicitudes HTTP
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL) // Establecemos la URL base de la API
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor de JSON a objetos Java
            .build();

    // Método GET para obtener la lista de todos los productos
    @GET("productos/")
    Call<List<Producto>> obtenerTodosLosProductos();



    // Método GET para obtener un producto específico por su EAN
    @GET("productos/buscarproducto/{ean}") // {id} es un parámetro dinámico en la URL
    Call<Producto> obtenerProductoPorEan(@Path("ean") String ean);
    // Se reemplaza {id} en la URL con el valor del parámetro `id` que se pasa en la llamada.

    // Método POST para crear un nuevo pedido en la base de datos
    @POST("productos/crearproducto/")
    Call<Producto> crearProducto(@Body Producto producto);
    // Se envía un objeto Pedido en el cuerpo de la solicitud para que la API lo registre en la base de datos.

    @PUT("productos/actualizarproducto/{ean}")
    Call<Producto> modificarProducto(@Path("ean") String ean, @Body Producto producto);

    @DELETE("productos/eliminarproducto/{ean}")
    Call<Producto> eliminarProducto(@Path("ean") String ean);



}
