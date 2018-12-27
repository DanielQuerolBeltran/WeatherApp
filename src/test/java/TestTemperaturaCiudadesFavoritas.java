//iteracion3
//R6-HU2 Como usuario, quiero tener la información meteorológica de la temperatura actual de mis lugares favoritos.

import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.IServidor;
import modelo.Servidores.OpenWeather;
import modelo.Tiempo.EstadoTiempo;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class TestTemperaturaCiudadesFavoritas {

    PanelCliente panelCliente = null;
    IServidor server = null;


    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
        panelCliente.setServidorPorDefecto(new OpenWeather());
        server = panelCliente.getServidorPorDefecto();
        panelCliente.addLocalizacionFavoritos(new Localizacion("Culla", "0.4123" , "12.123"));
        panelCliente.addLocalizacionFavoritos(new Localizacion("Benassal","54.67","43.766"));
    }


    @Test
    public void getTemperaturaCiudadesFavoritas() throws EstadoTiempoException {

        List<EstadoTiempo> listTemperaturas = panelCliente.getResumenTiempoCiudades();
        assertEquals(listTemperaturas.size(), 2); //Hay 2 previsiones, unapor cada elemento de los favoritos

    }


    @Test(expected = NullPointerException.class)
    public void getTemperaturaCiudadesFavoritasNoDisponible() throws EstadoTiempoException {

        panelCliente.setServidorPorDefecto(null); //Simulamos que no hay servidor

        List<EstadoTiempo> listTemperaturas = panelCliente.getResumenTiempoCiudades();
        assertEquals(listTemperaturas.size(), 2); //Hay 2 previsiones, unapor cada elemento de los favoritos

    }

}
