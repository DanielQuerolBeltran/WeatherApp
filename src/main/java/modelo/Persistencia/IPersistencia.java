package modelo.Persistencia;

import modelo.Localizacion.Localizacion;
import modelo.Tiempo.EstadoTiempo;

import java.io.IOException;
import java.util.List;

/**
 * Created by iskynet on 13/12/2018.
 */
public interface IPersistencia {

    /**
     * Este metodo se usa para guardar el estado del tiempo de una localizacion.
     * Salta una excepción IlleganArgument si la lista ya contiene la localización
     * @param  localizacion localización del EstadoActual a guardar.
     * @param direccion Indica la ruta absoluta donde se guardará el archivo.
     * @return true si se ha podido guarda, false si no se ha podido guarda.
     */
    boolean guardarTiempoActual(Localizacion localizacion, String direccion) throws IOException;

    /**
     * Este metodo se usa para cargar un EstadoTiempo, clase se encapsula el estaddo del tiempo para una localizacion
     * Salta una excepción IlleganArgument si la lista ya contiene la localización
     * @param direccion Indica la ruta absoluta donde se encuentra el archivo.
     * @return Localizacion El tiempo se encapsula en la Localizacion
     */
    Localizacion cargarEstadoActual(String direccion) throws IOException;


    /**
     * Este método se usa para guardar la lista de ciudades favoritas en un fichero JSON
     * @return true si se ha podido guarda, false si no se ha podido guarda.
     */
    boolean guardarListaCiudadesFavoritas(List<Localizacion> listaFavoritos);

    /**
     * Este método se usa para cargar las ciudades favoritas de un fichero JSON
     * @return lista de Localizaciones
     */
    List<Localizacion> cargarCiudadesFavoritas();

}
