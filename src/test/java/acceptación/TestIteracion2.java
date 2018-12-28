package acceptación;

import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Persistencia.IPersistencia;
import modelo.Persistencia.PersistenciaJSON;
import modelo.Servidores.IServidor;
import modelo.Servidores.OpenWeather;
import modelo.Tiempo.EstadoTiempo;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


//Iteracion 1
//Este test consiste en buscar guardar una prediccion, recuperarla y leer sus datos
public class TestIteracion2 {

    Localizacion madrid;
    EstadoTiempo estadoTiempo;
    IPersistencia persistencia;

    @Before
    public void init(){
        persistencia = new PersistenciaJSON();
        madrid = new Localizacion("Madrid", "-3.7","40.42");
        estadoTiempo = new EstadoTiempo();
        estadoTiempo.setFecha(new Date());
        estadoTiempo.setHumedad((long) 60.0);
        estadoTiempo.setNubosidad((double) 100);
        estadoTiempo.setTemperatura(15.0);
        estadoTiempo.setPresion(1000.0);
        madrid.setTiempoActual(estadoTiempo);
    }

    @Test
    public void guardarCargarLeerEstado() throws IOException {

        String direccion = "EstadoTiempoMadrid-13Dic2018.json";
        assertEquals(persistencia.guardarTiempoActual(madrid, direccion), true);

        Localizacion loc = persistencia.cargarEstadoActual(direccion);
        estadoTiempo = loc.getTiempoActual();
        assertFalse(estadoTiempo==null );

        assertEquals(estadoTiempo.getTemperatura(),15.0,2);
        assertEquals(estadoTiempo.getVelocidadViento(),0.0,2);
        assertEquals(estadoTiempo.getNubosidad(),100.0,2);
        assertEquals(estadoTiempo.getTemperatura(),15.0,2);

    }



}
