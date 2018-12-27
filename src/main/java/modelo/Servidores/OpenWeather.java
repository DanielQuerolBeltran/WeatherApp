package modelo.Servidores;

import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.Servidores.HTTPGet.IHttp;
import modelo.Servidores.HTTPGet.PeticionJSON;
import modelo.Tiempo.EstadoTiempo;


import modelo.Tiempo.Prevision;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Daniel on 13/12/2018.
 */
public class OpenWeather  implements IServidor{

    private static final String KEY = "1ca3e139f69814b207a823edafd6ff19"; //Key desarrollador API
    private IHttp peticion= null;

    public OpenWeather(){
        peticion = new PeticionJSON();
    }

    @Override
    public EstadoTiempo getTiempoActual(Localizacion localizacion) throws EstadoTiempoException {

        String url = generarURLTiempoActual(localizacion);

        JSONObject jsonObject = peticion.doHttpGet(url);


        String codRespuesta = jsonObject.get("cod").toString();
        if(!codRespuesta.equals("200")){ //Si el código de respuesta no es correcto salta la excepción.
            throw new EstadoTiempoException();
        }

        EstadoTiempo tiempoActual;
       // try {
            tiempoActual = getJSONEstadoTiempo(jsonObject);
       // }catch (Exception e){
       //     throw new EstadoTiempoException();
       // }
        return tiempoActual;
    }

    /**
     * Este metodo genera un EstadoTiempo a partir de un objeto JSON.
     * @param  jsonObject clase que encapsula la información del tiempo
     * @return EstadoTiempo
     */
    private EstadoTiempo getJSONEstadoTiempo(JSONObject jsonObject) {
        EstadoTiempo estadoTiempo = new EstadoTiempo();

        Long fechaLong = (long) jsonObject.get("dt")*1000L;

        Date date = new Date(fechaLong);

        estadoTiempo.setFecha(date);

        //Extracción datos temperatura, presion, humedad
        JSONObject jsonTemp = (JSONObject) jsonObject.get("main");
        estadoTiempo.setTemperatura((Double) jsonTemp.get("temp") - 273.15); //Kevin a Celsius
        estadoTiempo.setUnidadesTemperatura('C');
        estadoTiempo.setPresion(Double.parseDouble(jsonTemp.get("pressure").toString()));
        estadoTiempo.setHumedad((Long) jsonTemp.get("humidity"));

        //Extracción datos nuvosidad
        jsonTemp = (JSONObject) jsonObject.get("clouds");
        Long clounds = (long) jsonTemp.get("all");
        estadoTiempo.setNubosidad((double) clounds);

        //Extracción datos viento
        jsonTemp = (JSONObject) jsonObject.get("wind");
        estadoTiempo.setVelocidadViento((Double) jsonTemp.get("speed"));



        return estadoTiempo;
    }

    /**
     * Este metodo se usa para generar la URL que hará la petición a la API
     * @param  localizacion clase que encapsula la información de la localzacion
     * @return URL. Si se tiene la ID se hace la peticion por ID, en otro caso por coordenadas.
     */
    private String generarURLTiempoActual(Localizacion localizacion) {
       // if(localizacion.getKeyOpenWeather()!=null){

         //   return "http://api.openweathermap.org/data/2.5/weather?id="+localizacion.getKeyOpenWeather()+"&APPID="+KEY;
        //}else{
            return "http://api.openweathermap.org/data/2.5/weather?lat="+localizacion.getLat()+"&lon="+localizacion.getLog()+"&APPID="+KEY;
        //}

    }

    @Override
    public List<Localizacion> buscarPorNombre(String nombre) {
        List<Localizacion> resultado = new ArrayList<>();
        String nombrelocalizacionURL = nombre.replaceAll(" ", "%20");
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+nombrelocalizacionURL+"&APPID="+KEY;
        JSONObject jsonObject = peticion.doHttpGet(url);

        System.out.println(jsonObject);
        String codRespuesta = jsonObject.get("cod").toString();
        if(codRespuesta.equals("200")){ //Comprobamos que la respuesta haya sido correcta. (200 = se ha encontrado algo)
            Localizacion localizacion = jsonToLocalizacion(jsonObject);
            resultado.add(localizacion);
        }
        return resultado;
    }

    /**
     * Este metodo se usa para generar la URL que hará la petición a la API
     * @param  jsonObject encapsula los datos de la respuesta de la API
     * @return Localizacion objeto localizador con los datos del jsonObject
     */
    private Localizacion jsonToLocalizacion(JSONObject jsonObject) {
        JSONObject object = (JSONObject) jsonObject.get("coord");
        String lat = object.get("lat").toString();
        String log = object.get("lon").toString();
        String name = (String) jsonObject.get("name");
        String id = jsonObject.get("id").toString();
        Localizacion localizacion = new Localizacion(name, lat, log);
        localizacion.setKeyOpenWeather(id);
        return localizacion;
    }

    @Override
    public Prevision getPrevision(Localizacion localizacion) throws EstadoTiempoException {



        String url = generarURLPrevision(localizacion);
        JSONObject jsonObject = peticion.doHttpGet(url);



        List<EstadoTiempo> listEstadoTiempo = new ArrayList<>();
        JSONArray jsonArrayForecast = (JSONArray) jsonObject.get("list");

        String codRespuesta = jsonObject.get("cod").toString();
        if(!codRespuesta.equals("200")){ //Si el código de respuesta no es correcto salta la excepción.
            throw new EstadoTiempoException();
        }

        //Open proporciona info cada 3h, por eso solo cojemos una de cada 8 previsiones. Resultado,
        // por tanto una prevision por dia

        for (int i = 0; i < 40; i += 8) {
            JSONObject jsonObjectList = (JSONObject) jsonArrayForecast.get(i);
            EstadoTiempo estadoTiempo;
            try {
                estadoTiempo = getJSONEstadoTiempo(jsonObjectList);
            }catch (Exception e){
                throw new EstadoTiempoException();
            }



            listEstadoTiempo.add(estadoTiempo);
        }
        Prevision prevision = new Prevision();
        prevision.setPrevision(listEstadoTiempo);
        return prevision;
    }

    @Override
    public List<Localizacion> buscarPorCP(String cp) throws AssertionError {

        String url = "http://api.openweathermap.org/data/2.5/weather?zip="+cp+",es&APPID="+KEY;
        JSONObject jsonObject = peticion.doHttpGet(url);
        List<Localizacion> localizaciones = new ArrayList<>();
        String codRespuesta = jsonObject.get("cod").toString();

        if(!codRespuesta.equals("200")){
            throw new IllegalArgumentException();
        }

        Localizacion localizacion = jsonToLocalizacion(jsonObject);
        localizaciones.add(localizacion);

        return localizaciones;
    }

    @Override
    public List<Localizacion> buscarPorCoordenadas(String lat, String log) throws IllegalArgumentException {

        String url = "http://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+log+"&APPID="+KEY;

        JSONObject jsonObject = peticion.doHttpGet(url);


        List<Localizacion> localizaciones = new ArrayList<>();
        String codRespuesta = jsonObject.get("cod").toString();

        if(!codRespuesta.equals("200")){
            throw new IllegalArgumentException();
        }

        Localizacion localizacion = jsonToLocalizacion((JSONObject) jsonObject.get("city"));
        localizaciones.add(localizacion);

        return localizaciones;
    }




    /**
     * Este metodo se usa para generar la URL que hará la petición a la API
     * @param  localizacion clase que encapsula la información de la localzacion
     * @return URL. Si se tiene la ID se hace la peticion por ID, en otro caso por coordenadas.
     */
    private String generarURLPrevision(Localizacion localizacion) {
        if(localizacion.getKeyOpenWeather()!=null){
            return "http://api.openweathermap.org/data/2.5/forecast?id="+localizacion.getKeyOpenWeather()+"&APPID="+KEY;
        }else{
            return "http://api.openweathermap.org/data/2.5/forecast?lat="+localizacion.getLat()+"&lon="+localizacion.getLog()+"&APPID="+KEY;
        }

    }
}
