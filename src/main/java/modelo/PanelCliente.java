package modelo;

import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.Persistencia.IPersistencia;
import modelo.Persistencia.PersistenciaJSON;
import modelo.Servidores.IServidor;
import modelo.Tiempo.EstadoTiempo;
import modelo.Tiempo.Prevision;
import modelo.Util.CoordenadasUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase central.
 */
public class PanelCliente {

    private static PanelCliente panel;
    private IServidor servidorPorDefecto;
    private static List<Localizacion> listaFavoritos;
    private static IPersistencia persistencia;

    private PanelCliente(){

    }

    public static PanelCliente getInstance(){
        if(panel==null){
            persistencia = new PersistenciaJSON();
            listaFavoritos = persistencia.cargarCiudadesFavoritas();
            panel = new PanelCliente();
        }

        return panel;
    }

    /**
     * Este metodo se usa para añadir una localización a la lista de localizaciones favoritas
     * Salta una excepción IlleganArgument si la lista ya contiene la localización
     * @param  localizacion clase que encapsula la información de la localzacion
     * @return true si se ha podido añadir, false si no se ha podido añadir.
     */
    public boolean addLocalizacionFavoritos(Localizacion localizacion){
        if(listaFavoritos.contains(localizacion))
            throw new IllegalArgumentException();
        boolean retorno = listaFavoritos.add(localizacion);
        persistencia.guardarListaCiudadesFavoritas(listaFavoritos);
        return retorno;
    }

    /**Este método se usa para eliminar una localización de la lista de localizaciones favoritas
     * Salta una excepción ClassNotFoundException si la lista no contiene la localización
     * @param localizacion clase que encapsula la información de la la localización
     * @return true si se ha podido añadir, false si no se ha podido borrar
     */
    public boolean removeLocalizacionFavoritos(Localizacion localizacion)  {
        for(Localizacion loc: listaFavoritos) {
            if (loc.getNombre().equals(localizacion.getNombre())){
                boolean retorno = listaFavoritos.remove(loc);
                persistencia.guardarListaCiudadesFavoritas(listaFavoritos);
                return retorno;
            }
        }
        return false;
    }

    /**
     * Este metodo se usa para obtener el servidor que se está usando actualmente.
     * @return IServidor clase del servidor (API)
     */
    public IServidor getServidorPorDefecto(){
        return this.servidorPorDefecto;
    }

    /**
     * Método para establecer el servidor que se va a usar.
     * @param servidorPorDefecto clase del servidor (API)
     */
    public void setServidorPorDefecto(IServidor servidorPorDefecto) {
        this.servidorPorDefecto = servidorPorDefecto;
    }


    /**
    * Método para obtener la lista de ciudades guardadas como favotiras
    * @return List<Localizacion> ciudades.
    * */
    public List<Localizacion> getListaLocalizaciones() {
        return listaFavoritos;
    }

    /**
     * Método para buscar localizaciones usando el cp (codigo postal)
     * Se ha restringido el uso a CP de España.
     * @param cp codigo postal
     * @return List<Localizacion> ciudades.
     * */
    public List<Localizacion> buscarCiudadCP(String cp){
        if(!cp.matches("0[1-9][0-9]{3}|[1-4][0-9]{4}|5[0-2][0-9]{3}")){ //validamos el codigo portal (españa)
            throw new IllegalArgumentException();
        }
        return servidorPorDefecto.buscarPorCP(cp);
    }

    /**
     * Método para obtener el tiempo actual encapsulado en un objeto EstadoTiempo
     * @param localizacion que tiene la información del lugar a buscar.
     * @return EstadoTiempo con la información del tiempo actual.
     * */
    public EstadoTiempo getTiempoActual(Localizacion localizacion) throws EstadoTiempoException {
        EstadoTiempo estadoTiempo = getServidorPorDefecto().getTiempoActual(localizacion);
        localizacion.setTiempoActual(estadoTiempo);
        return estadoTiempo;
    }

    public List<Localizacion> buscarCiudadCoordenadas(String lat, String log){
        if(CoordenadasUtil.validarCoordenadas(lat) || CoordenadasUtil.validarCoordenadas(log)){
            throw new IllegalArgumentException();
        }
        return servidorPorDefecto.buscarPorCoordenadas(lat, log);
    }



    public List<EstadoTiempo> getResumenTiempoCiudades() throws EstadoTiempoException {
        List<EstadoTiempo> estados = new ArrayList<>();
        for(Localizacion loc: listaFavoritos){
            EstadoTiempo estadoTiempo = getTiempoActual(loc);
            estados.add(estadoTiempo);
        }
        return estados;
    }

    public boolean addPuntoInteres(String nombre, String lat, String log) {

        if(nombre==null || nombre.length()<=1 || nombre.length()>35){
            return false;
        }
        if(CoordenadasUtil.validarCoordenadas(lat) || CoordenadasUtil.validarCoordenadas(log)){
            throw new IllegalArgumentException();
        }

        return addLocalizacionFavoritos(new Localizacion(nombre, lat, log));
    }

    public List<Localizacion> buscarCiudadNombre(String nombre) {
        return servidorPorDefecto.buscarPorNombre(nombre);
    }

    public boolean containsCiudad(Localizacion l1) {
        for(Localizacion loc: listaFavoritos) {
            if (loc.getNombre().equals(l1.getNombre())){
                return true;
            }
        }
        return false;
    }

    public Prevision getPrevision(Localizacion localizacion) throws EstadoTiempoException {

        return  servidorPorDefecto.getPrevision(localizacion);
    }

    public void setIPersistencia(IPersistencia persistencia) {
        this.persistencia = persistencia;
    }

    public IPersistencia getIPersistencia() {
        return this.persistencia;
    }
}



