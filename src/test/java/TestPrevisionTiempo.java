import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.IServidor;
import modelo.Servidores.OpenWeather;
import modelo.Tiempo.EstadoTiempo;
import modelo.Tiempo.Prevision;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

//Iteracción 2.
// R2 - HU1 Como usuario quiero poder ver el estado actual del tiempo de una ciudad.
public class TestPrevisionTiempo {

    IServidor server = null;
    PanelCliente panelCliente = null;
    Localizacion barcelona = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
        panelCliente.setServidorPorDefecto(new OpenWeather());
        server = panelCliente.getServidorPorDefecto();

    }

    //E1 Valido. El usuario obtiene la previsión del tiempo para una localizacion.
    @Test
    public void predicionDisponible() throws EstadoTiempoException {
        barcelona = new Localizacion("Barcelona", "41.38","2.18");
        Prevision prevision = panelCliente.getPrevision(barcelona);
        assertEquals(prevision.getPrevision().size(), 5); //La predición tiene el EstadoTiempo de los 5 días.
    }


    //E2 Invalido. El usuario no obtiene los datos de la previsión.
    @Test(expected = EstadoTiempoException.class)
    public void predicionNoDisponible() throws EstadoTiempoException {
        barcelona = new Localizacion("Barcelona"); //No se proporcionan suficientes datos para hacer la peticion
        Prevision prevision = panelCliente.getPrevision(barcelona);
        assertEquals(prevision.getPrevision(), null);
    }
}
