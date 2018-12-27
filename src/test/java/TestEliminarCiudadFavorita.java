

//Iteracción 3.
//R5 - HU2 Como usuario, quiero poder eliminar una ciudad de mi lista de ciudades favoritas.

import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import org.junit.Before;
import org.junit.Test;
import org.mockito.cglib.core.Local;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import static junit.framework.Assert.assertEquals;

public class TestEliminarCiudadFavorita {

    PanelCliente panelCliente = null;

    @Before
    public void init() {
        panelCliente = PanelCliente.getInstance();
        panelCliente.addLocalizacionFavoritos(new Localizacion("Culla"));
        panelCliente.addLocalizacionFavoritos(new Localizacion("Benassal"));
    }


    //E1 Válido. El usuario selecciona una de las ciudad de los lugares favoritos y seleccionada la opción de eliminar
    // de la lista. El sistema la elimina adecuadamente.
    @Test
    public void eliminarCiudadFavoritaValida(){
        panelCliente.addLocalizacionFavoritos(new Localizacion("Alicante"));
        int size = panelCliente.getListaLocalizaciones().size();
        assertEquals(panelCliente.removeLocalizacionFavoritos(new Localizacion("Alicante")),true);
        assertEquals(size-1,panelCliente.getListaLocalizaciones().size());
    }

    //E2 Inválido. El usuario intenta eliminar una ciudad que no está en la lista de ciudades favoritas.
    public void eliminarCiudadFavoritaInvalida() {
        int size = panelCliente.getListaLocalizaciones().size();
        assertEquals(panelCliente.removeLocalizacionFavoritos(new Localizacion("Alicante")),false);
    }


}