package mocks;

import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.IServidor;
import modelo.Servidores.OpenWeather;
import modelo.Tiempo.EstadoTiempo;
import modelo.Tiempo.Prevision;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

//MOCK
//Iteracción 2.
// R2 - HU1 Como usuario quiero poder ver el estado actual del tiempo de una ciudad.
public class TestPrevisionTiempo {


    PanelCliente panelCliente = null;
    Localizacion barcelona = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
        OpenWeather servidorMock = Mockito.mock(OpenWeather.class);
        panelCliente.setServidorPorDefecto(servidorMock); //inyectamos el servidor falso
    }

    //E1 Valido. El usuario obtiene la previsión del tiempo para una localizacion.
    @Test
    public void predicionDisponible() throws EstadoTiempoException {


        barcelona = new Localizacion("Barcelona", "41.38","2.18");

        Prevision previsionJSON = cargarPrevision(getJsonObject("src/test/java/mocks/PrevisionBarcelona.json"));

        when(panelCliente.getPrevision(barcelona)).thenReturn(previsionJSON);


        Prevision prevision = panelCliente.getPrevision(barcelona);

        assertEquals(prevision.getPrevision().size(), 5);

        EstadoTiempo estadoTiempo = prevision.getPrevision().get(0); //El primer dia de la prevision

        assertEquals(estadoTiempo.getTemperatura(),11.7,0.1);
        assertEquals(estadoTiempo.getPresion(),1034.85,0.1);
        assertEquals(estadoTiempo.getVelocidadViento(),2.9,0.1);
        assertEquals(estadoTiempo.getNubosidad(),56.0,1);

    }



    public Prevision cargarPrevision(JSONObject jsonObject) throws EstadoTiempoException {





        List<EstadoTiempo> listEstadoTiempo = new ArrayList<>();
        JSONArray jsonArrayForecast = (JSONArray) jsonObject.get("list");


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

    private EstadoTiempo getJSONEstadoTiempo(JSONObject jsonObject) {
        EstadoTiempo estadoTiempo = new EstadoTiempo();

        Long fechaLong = (long) jsonObject.get("dt")*1000L;

        Date date = new Date(fechaLong);
        estadoTiempo.setFecha(date);

        //Extracción datos temperatura, presion, humedad
        JSONObject jsonTemp = (JSONObject) jsonObject.get("main");
        estadoTiempo.setTemperatura((Double) jsonTemp.get("temp") - 273.15); //Kevin a Celsius
        estadoTiempo.setUnidadesTemperatura('C');
        estadoTiempo.setPresion((Double) Double.parseDouble(jsonTemp.get("pressure").toString()));
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
}
