package mocks;

//MOCK
//Iteracción 1.
//R1 - HU1 Como usuario quiero poder ver el estado actual del tiempo en una localización.

import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.OpenWeather;
import modelo.Tiempo.EstadoTiempo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TestGetTiempoActual {

    PanelCliente panelCliente = null;
    Localizacion castellon = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
        castellon = new Localizacion("Castellon", "-0.0576","39.9929");
    }

    //E1 Valido. El usuario quiere ver la información del tiempo.
    @Test
    public void TiempoActualDisponible_Mock() throws EstadoTiempoException {

        //cargamos el tiempo actual de un fichero json
        EstadoTiempo tiempoActualJSON = getJSONEstadoTiempo(getJsonObject("src/test/java/mocks/TiempoActualCastellon.json"));

        OpenWeather servidorMock = Mockito.mock(OpenWeather.class);

        panelCliente.setServidorPorDefecto(servidorMock); //inyectamos el servidor falso

        when(panelCliente.getTiempoActual(castellon)).thenReturn(tiempoActualJSON);

        EstadoTiempo estadoTiempoResultado = panelCliente.getTiempoActual(castellon);

        assertEquals(estadoTiempoResultado.getTemperatura(),34.7,0.1);
        assertEquals(estadoTiempoResultado.getPresion(),1007.45,0.01);
        assertEquals(estadoTiempoResultado.getVelocidadViento(),4.46,0);
        assertEquals(estadoTiempoResultado.getNubosidad(),0,0);

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
