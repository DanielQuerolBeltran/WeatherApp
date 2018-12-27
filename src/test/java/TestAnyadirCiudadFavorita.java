

import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


//Iteraccion 1
//R5 HU1 - Como usuario, quiero poder añadir ciudades a mi lista de ciudades favoritas.

public class TestAnyadirCiudadFavorita {

    PanelCliente panelCliente = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();

    }

    //E1 Valido. El usuario guarda una nueva localización en la lista de favoritos.
    @Test
    public void anyadirCiudadFavoritaDisponible(){
        panelCliente.addLocalizacionFavoritos(new Localizacion("Cati"));
        panelCliente.addLocalizacionFavoritos(new Localizacion("Benassal"));
        Localizacion culla = new Localizacion("Culla","-0.0076","40.9929");
        assertFalse(panelCliente.addLocalizacionFavoritos(culla)==false);
        assertEquals(panelCliente.getListaLocalizaciones().size(),3,0);
    }

    //E2 Invalido. El usuario intenta guardar una localización ya guardada en la lista.
    @Test(expected = IllegalArgumentException.class)
    public void anyadirCiudadFavoritaYaAñadida(){
        panelCliente.addLocalizacionFavoritos(new Localizacion("Cati"));
        panelCliente.addLocalizacionFavoritos(new Localizacion("Benassal"));
        Localizacion culla = new Localizacion("Culla","-0.0076","40.9929");
        panelCliente.addLocalizacionFavoritos(culla); //Introducimos la ciudad.
        assertFalse(panelCliente.addLocalizacionFavoritos(culla)==true); //La volvemos a introducir.
        assertEquals(panelCliente.getListaLocalizaciones().size(),3,0);
    }
}








