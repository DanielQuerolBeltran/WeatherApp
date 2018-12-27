import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.IServidor;
import modelo.Servidores.OpenWeather;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

//Iteracion 3
//R1-HU2a - Como usuario quiero poder elegir una ciudad introduciendo un código postal.
public class TestBuscarCiudadCP {
    IServidor server = null;
    PanelCliente panelCliente = null;
    List<Localizacion> resultadoBusqueda = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
        panelCliente.setServidorPorDefecto(new OpenWeather());
        server = panelCliente.getServidorPorDefecto();

    }

    //El valido El usuario introduce un CP correcto y elservidor
    @Test
    public void ciudadBuscadaCPcorrect(){
        resultadoBusqueda = panelCliente.buscarCiudadCP("12005");
        assertFalse(resultadoBusqueda.size()==0 );
    }

    //El invalido El usuario intenta buscar por una cadena que no se corresponde con un CP
    @Test(expected = IllegalArgumentException.class)
    public void ciudadBuscadaCPinvalido(){
        resultadoBusqueda = panelCliente.buscarCiudadCP("123X23"); //"123X23" no es un CP valido
        assertFalse(resultadoBusqueda.size()!=0 );
    }

}
