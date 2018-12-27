package modelo.Servidores;

import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.Tiempo.EstadoTiempo;
import modelo.Tiempo.Prevision;

import java.util.List;


public interface IServidor {
    /**
     * Este metodo se usa para obtener el EstadoTiempo para una Localizacion
     * @param  localizacion clase que encapsula la información de la localzacion
     * @return EstadoTiempo clase que encapsula la informacion del estado del tiempo.
     */
    EstadoTiempo getTiempoActual(Localizacion localizacion) throws EstadoTiempoException;

    /**
     * Este metodo se usa para obtener la lista de lugares que coinciden con el nombre especificado.
     * @param  nombre Nombre del lugar a buscar.
     * @return List<Localizacion> lista de lugares que coinciden con el nombre
     */
    List<Localizacion> buscarPorNombre(String nombre);


    /**
     * Este metodo se usa para obtener la prevision para una localizacion concreta
     * @param  localizacion  Lugar a consultar.
     * @return List<EstadoTiempo> lista EstadosTiempo que encapsulan el tiempo para cada dia de la prevision.
     */
    Prevision getPrevision(Localizacion localizacion) throws EstadoTiempoException;

    /**
     * Este metodo se usa para obtener una localizacion por su Codigo postal
     * @param cp Código Postal
     * @return List<Localizacion> lista localizaciones con aquellas que coinciden en el numero postal
     */
    List<Localizacion> buscarPorCP(String cp) throws IllegalArgumentException;

    /**
     * Este metodo se usa para obtener una localizacion por su Codigo postal
     * @param v,vl v longitud vl latitud
     * @return List<Localizacion> lista localizaciones con aquellas que coinciden en las coordenadas
     */
    List<Localizacion> buscarPorCoordenadas(String v, String v1)throws IllegalArgumentException;

}
