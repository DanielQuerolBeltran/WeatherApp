import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.IServidor;
import modelo.Servidores.OpenWeather;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;


//Iteracción 1
//R1 HU2 c. Como usuario quiero poder buscar una ciudad por su nombre.
public class TestBuscarCiudadNombre {
    IServidor server = null;
    PanelCliente panelCliente = null;
    List<Localizacion> resultadoBusqueda = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
        panelCliente.setServidorPorDefecto(new OpenWeather());
        server = panelCliente.getServidorPorDefecto();

    }

    //E1 Valido. El usuario introduce una ciudad y el sistema la tiene disponible.
    @Test
    public void ciudadBuscadaDisponible(){
        resultadoBusqueda = panelCliente.buscarCiudadNombre("Madrid");
        assertFalse(resultadoBusqueda.size()==0 );
    }

    //E2 Invalido. El usuario introduce una ciudad, pero el sistema no tienen ningún resultado asociado con ese nombre.
    @Test
    public void ciudadBuscadaNoDisponible(){
        resultadoBusqueda = server.buscarPorNombre("Maaadrit"); //Suponemos que el servidor no tienen ningun resultado para este nombre.
        assertFalse(resultadoBusqueda.size()!=0 );
    }
}
