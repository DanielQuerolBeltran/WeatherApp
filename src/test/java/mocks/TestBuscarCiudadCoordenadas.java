package mocks;

import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.OpenWeather;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by iskynet on 27/12/2018.
 */
public class TestBuscarCiudadCoordenadas {
    PanelCliente panelCliente = null;
    List<Localizacion> resultadoBusqueda = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
        panelCliente.setServidorPorDefecto(new OpenWeather());
    }

    //E1 Valido. El usuario introduce unas coordenadas validas y que cooresponden a una ciudad.
    @Test
    public void ciudadBuscadaDisponible(){

        //cargamos el tiempo actual de un fichero json
        Localizacion localizacionMock = jsonToLocalizacion(getJsonObject("src/test/java/mocks/BusquedaPorNombre.json"));

        System.out.println(localizacionMock.getNombre());
        List<Localizacion> listaResultado = new ArrayList<>();
        listaResultado.add(localizacionMock);

        OpenWeather servidorMock = Mockito.mock(OpenWeather.class);
        panelCliente.setServidorPorDefecto(servidorMock); //inyectamos el servidor falso

        when(panelCliente.buscarCiudadCoordenadas("40.21","0.03")).thenReturn(listaResultado);

        listaResultado = panelCliente.buscarCiudadCoordenadas("40.21","0.03");

        assertEquals(listaResultado.size(), 1 );
        assertEquals(listaResultado.get(0).getNombre(), "Castellón de la Plana" );

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
}
