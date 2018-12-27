import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.IServidor;
import modelo.Servidores.OpenWeather;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

//Iteracion 3
//R1-HU2bComo usuario quiero poder elegir una ciudad introduciendo unas coordenadas.
public class TestBuscarCiudadCoordenadas {

    IServidor server = null;
    PanelCliente panelCliente = null;
    List<Localizacion> resultadoBusqueda = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
        panelCliente.setServidorPorDefecto(new OpenWeather());
        server = panelCliente.getServidorPorDefecto();

    }

    //E1 Valido. El usuario introduce unas coordenadas validas y que cooresponden a una ciudad.
    @Test
    public void ciudadBuscadaDisponible(){
        resultadoBusqueda = server.buscarPorCoordenadas("-0.0576","39.9929");
        assertFalse(resultadoBusqueda.size()==0 );
    }


    //E2 Invalido. El usuario introduce unas coordenadas invalidas.
    @Test(expected = IllegalArgumentException.class)
    public void ciudadBuscadaNoDisponible(){
        resultadoBusqueda = server.buscarPorCoordenadas("-0.0576800","910.03568"); //Las longityudes y latitudes validas, Son validas entre -90º y 90º
        assertFalse(resultadoBusqueda.size()!=0 );
    }



}
