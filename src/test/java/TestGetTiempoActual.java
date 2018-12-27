import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.IServidor;
import modelo.Servidores.OpenWeather;
import modelo.Tiempo.EstadoTiempo;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;




//Iteracción 1.
//R1 - HU1 Como usuario quiero poder ver el estado actual del tiempo en una localización.

public class TestGetTiempoActual{
    IServidor server = null;
    PanelCliente panelCliente = null;
    Localizacion castellon = null;

    @Before
    public void init(){
        panelCliente = PanelCliente.getInstance();
        panelCliente.setServidorPorDefecto(new OpenWeather());
        server = panelCliente.getServidorPorDefecto();
        castellon = new Localizacion("Castellon", "-0.0576","39.9929");
    }

    //E1 Valido. El usuario quiere ver la información del tiempo en una ciudad y esta información está disponible.
    @Test
    public void tiempoActualDisponible() throws EstadoTiempoException {

        EstadoTiempo tiempoActual = panelCliente.getTiempoActual(castellon);

        assertFalse(tiempoActual==null );
        assertEquals(tiempoActual.getTemperatura(),21,20); //Suponemos que la temperatura estará entre 1 y 41 grados C
        assertEquals(tiempoActual.getPresion(),1000,200); //La presion estará entre 800 y 1200 hPa.
        assertEquals(tiempoActual.getVelocidadViento(),50,50); //La velociadad del viento entre 0 y 100 km/h.
        assertEquals(tiempoActual.getNubosidad(),50,50); //La nubosidad estará entre 0% y 100%.

    }

    //E2 Invalido. La información del tiempo para la ciudad elegida no está disponible.
    @Test(expected = EstadoTiempoException.class)
    public void tiempoActualNoDisponible() throws EstadoTiempoException {
        castellon = new Localizacion("Castellon"); //Localizacion sin coordenadas. Debe fallar ya que no se puede hacer la peticion a la API.
        EstadoTiempo tiempoActual = panelCliente.getTiempoActual(castellon);
    }

}
