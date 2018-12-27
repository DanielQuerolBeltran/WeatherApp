package acceptación;

import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.IServidor;
import modelo.Servidores.OpenWeather;
import modelo.Tiempo.EstadoTiempo;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

//Iteracion 1
//Este test consiste en buscar una localización por su nombre, obtener la localización,
//hacer la petición del estado del tiempo y finalmente guardar la localización en la lista de favoritos.

public class TestIteracion1 {
    IServidor server = null;
    PanelCliente panelCliente = null;
    List<Localizacion> resultadoBusqueda = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
        panelCliente.setServidorPorDefecto(new OpenWeather());
    }

    @Test
    public void obtenerCiudadTiempo() throws EstadoTiempoException {
        resultadoBusqueda = panelCliente.buscarCiudadNombre("Madrid");

        assertEquals(resultadoBusqueda.size(), 1 );

        Localizacion localizacion = resultadoBusqueda.get(0);
        assertEquals(localizacion.getNombre(), "Madrid" );

        EstadoTiempo tiempoActual = panelCliente.getTiempoActual(localizacion);

        assertFalse(tiempoActual==null );

        assertEquals(tiempoActual.getTemperatura(),21,20); //Suponemos que la temperatura estará entre 1 y 41 grados C
        assertEquals(tiempoActual.getPresion(),1000,200); //La presion estará entre 800 y 1200 hPa.
        assertEquals(tiempoActual.getVelocidadViento(),50,50); //La velociadad del viento entre 0 y 100 km/h.
        assertEquals(tiempoActual.getNubosidad(),50,50); //La nubosidad estará entre 0% y 100%.

        assertFalse(panelCliente.addLocalizacionFavoritos(localizacion)==false);
    }
}
