package modelo.Persistencia;

import modelo.Localizacion.Localizacion;
import modelo.Tiempo.EstadoTiempo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by iskynet on 13/12/2018.
 */
public class PersistenciaJSON implements IPersistencia{


    @Override
    public boolean guardarTiempoActual(Localizacion localizacion, String direccion) throws IOException {

        if(!validarNombreFichero(direccion)){
            throw new IOException();
        }

        EstadoTiempo t1 = null;
        try {
            t1 = localizacion.getTiempoActual();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();

        obj.put("temperatura", t1.getTemperatura());
        obj.put("nubosidad", t1.getNubosidad());
        obj.put("velocidadViento", t1.getVelocidadViento());

        //Pasamo el objeto Date a un formato especifico que podamos recuperar en el metodo cargarEstadoActual
        String pattern = "dd-MMMMM-yyyy-HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String fecha = simpleDateFormat.format(t1.getFecha());

        obj.put("fecha", fecha);
        obj.put("presion", t1.getPresion());
        obj.put("nombreLocalizacion", localizacion.getNombre());
        obj.put("latLocalizacion", localizacion.getLat());
        obj.put("logLocalizacion", localizacion.getLog());

        return guardarObjeto(obj, direccion);
    }

    @Override
    public Localizacion cargarEstadoActual(String direccion) throws IOException {

        if(!validarNombreFichero(direccion)){
            throw new IOException();
        }

        Localizacion l1;
        JSONObject jsonObject = getJsonObject(direccion);

        if(!validarJsonTiempoActual(jsonObject)){
            throw new IOException();
        }


        String nombre = jsonObject.get("nombreLocalizacion").toString();
        String lat = jsonObject.get("latLocalizacion").toString();
        String log =  jsonObject.get("logLocalizacion").toString();

        l1 = new Localizacion(nombre, lat, log);

        EstadoTiempo t1 = new EstadoTiempo();

        Double temperatura = (Double) jsonObject.get("temperatura");
        Double nubosidad = (Double) jsonObject.get("nubosidad");
        Double viento = (Double) jsonObject.get("velocidadViento");
        String fechaCadena = (String) jsonObject.get("fecha");
        Double presion = (Double) Double.parseDouble(jsonObject.get("presion").toString());

        //Parseamos el formato de la fecha para convertirlo a un objeto Date.
        DateFormat df = new SimpleDateFormat("dd-MMMMM-yyyy-HH:mm");
        Date fecha = new Date();

        try {
            fecha = df.parse(fechaCadena);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


        t1.setTemperatura(temperatura);
        t1.setNubosidad(nubosidad);
        t1.setVelocidadViento(viento);
        t1.setFecha(fecha);
        t1.setPresion(presion);

        //Estabelcemos el tiempo cargado como actual.
        l1.setTiempoActual(t1);

        return l1;
    }

    @Override
    public boolean guardarListaCiudadesFavoritas(List<Localizacion> listaFavoritos) {
        JSONObject obj = new JSONObject();

        JSONArray arrayObject =  new JSONArray();

        for(Localizacion localizacion: listaFavoritos){
            JSONObject nuevoJSON = new JSONObject();
            nuevoJSON.put("nombreLocalizacion", localizacion.getNombre());
            nuevoJSON.put("latLocalizacion", localizacion.getLat());
            nuevoJSON.put("logLocalizacion", localizacion.getLog());

            arrayObject.add(nuevoJSON);
        }
        obj.put("lugares",arrayObject);

        return guardarObjeto(obj, "src/Mis-lugares-favoritos.json");
    }

    @Override
    public List<Localizacion> cargarCiudadesFavoritas() {
        String dir = "src/Mis-lugares-favoritos.json";
        File fichero = new File(dir);
        if (!fichero.exists()) {
            guardarListaCiudadesFavoritas(new ArrayList<>());
        }
        JSONObject lugaresObj = getJsonObject("src/Mis-lugares-favoritos.json");
        JSONArray listaLugares = (JSONArray) lugaresObj.get("lugares");
        List<Localizacion> listaLocalizaciones = new ArrayList<Localizacion>();

        for(int i=0; i<listaLugares.size();i++){
            JSONObject lugar = (JSONObject) listaLugares.get(i);
            String nombre = lugar.get("nombreLocalizacion").toString();
            String lat =  lugar.get("latLocalizacion").toString();
            String log =  lugar.get("logLocalizacion").toString();
            listaLocalizaciones.add( new Localizacion(nombre, lat, log));
        }

        return listaLocalizaciones;
    }

    /**
     * Este metodo se usa para validar la extención y el nombre del fichero.
     * El tamaño del nombre no podrá ser menor a 1 carácter (6 - 5 de extension)
     * @param direccion Indica la ruta y el nombre del fichero
     */
    private boolean validarNombreFichero(String direccion){
        return direccion.length()>6 && direccion.endsWith(".json");
    }

    /**
     * Este metodo se usa para validar la estructura del fichero a cargar.
     * Comprueba que tiene todas las etiquetas necesarias para generar la Localizacion y el EstadoTiempo
     * @param jsonObject Encapsula los datos
     */
    private boolean validarJsonTiempoActual(JSONObject jsonObject){
        String[] keys = {"nombreLocalizacion","latLocalizacion", "logLocalizacion","temperatura", "nubosidad", "velocidadViento", "fecha", "presion"};
        for(int i=0; i<keys.length;i++){
            if(!jsonObject.containsKey(keys[i])){
                return false;
            }
        }
        return true;
    }


    /**
     * Este metodo se usa para guardar el objeto JSON al fichero con extension .json
     * Si el fichero no existe, lo crea. Si existe maxaca el existente.
     * El tamaño del nombre no podrá ser menor a 1 carácter (6 - 5 de extension)
     * @param nombre Indica la ruta y el nombre del fichero
     * @param obj Objeto JSON con los datos ya parseados.
     */
    private boolean guardarObjeto(JSONObject obj, String nombre){
        try {
            File file = new File(nombre);
            FileWriter fw = null;
            try {
                fw = new FileWriter(nombre);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fw.write(obj.toString());
            fw.flush();
            fw.close();

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Este metodo se usa para generar un objeto JSON a partir de la lectura de un fichero
     * El tamaño del nombre no podrá ser menor a 1 carácter (6 - 5 de extension)
     * @param dir Indica la ruta y el nombre del fichero
     */
    private static JSONObject getJsonObject(String dir) {
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();

        Object object = null;
        try {
            object = parser
                    .parse(new FileReader(dir));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        jsonObject = (JSONObject) object;

        return jsonObject;
    }

}
