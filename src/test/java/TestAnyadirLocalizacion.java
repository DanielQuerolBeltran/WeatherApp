import modelo.PanelCliente;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by iskynet on 15/12/2018.
 */

//Iteración 4
//Como usuario, quiero poder introducir a la mi lista de ciudades favoritas un punto de interés dado por unas coordenadas.
public class TestAnyadirLocalizacion {

    PanelCliente panelCliente = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
    }

    //E1 Valido. El usuario introduce unas coordenadas válidas y un nombre válido para este punto. El sistema guarda el lugar a favoritos.
    @Test
    public void anyadirLocalizacionValida(){
        String nombre = "Mi casa";
        String lat = "0.014642";
        String log = "40.404295";
        int size = panelCliente.getListaLocalizaciones().size();
        assertEquals(panelCliente.addPuntoInteres(nombre, lat, log), true);
        assertEquals(panelCliente.getListaLocalizaciones().size(), size+1);
    }

    //E2 Invalido. El usuario accede a la pantalla de agregar favoritos e introduce las coordenadas válidas pero no introduce ningún nombre para el lugar.
    @Test
    public void anyadirLocalizacionNoValida(){
        String nombre = "";
        String lat = "0.014642";
        String log = "40.404295";
        assertEquals(panelCliente.addPuntoInteres(nombre, lat, log), false);
    }

}
