package mocks;

import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Persistencia.IPersistencia;
import modelo.Persistencia.PersistenciaJSON;
import modelo.Tiempo.EstadoTiempo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

//Iteracción 1.
//Como usuario quiero poder recuperar el estado del tiempo a partir de un fichero de texto.
public class TestCargarEstado {


    PanelCliente panelCliente = null;
    EstadoTiempo estadoTiempo;
    Localizacion madrid;

    @Before
    public void init() {
        panelCliente = PanelCliente.getInstance();
        madrid = new Localizacion("Madrid", "-3.7","40.42");
        estadoTiempo = new EstadoTiempo();
        estadoTiempo.setFecha(new Date());
        estadoTiempo.setHumedad((long) 60.0);
        estadoTiempo.setNubosidad((double) 100);
        estadoTiempo.setTemperatura(15.0);
        estadoTiempo.setPresion(1000.0);
        madrid.setTiempoActual(estadoTiempo);
    }

    //E1 Valido. El usuario es capaz de recuperar los datos almacenados y ver la información.

    @Test
    public void cargarEstadoTiempoValido_mock() throws IOException {

        //Inyectamos la persistencia del sistema de guardado.
        IPersistencia mock = Mockito.mock(PersistenciaJSON.class);
        panelCliente.setIPersistencia(mock);
        when(mock.cargarEstadoActual(anyString())).thenReturn(madrid);

        String direccion = "EstadoTiempoMadrid-13Dic2018.json"; //El fichero existe y está bien formado. Se comprueba antes del test.
        IPersistencia persistencia = panelCliente.getIPersistencia();
        Localizacion loc = persistencia.cargarEstadoActual(direccion);
        estadoTiempo = loc.getTiempoActual();
        assertFalse(estadoTiempo==null );

    }


    //E2 Invalido. El usuario no puede cargar toda la información.
    @Test(expected = IOException.class)
    public void cargarEstatoTiempoInvalido_mock() throws IOException {

        //Inyectamos la persistencia del sistema de guardado.
        IPersistencia mock = Mockito.mock(PersistenciaJSON.class);
        panelCliente.setIPersistencia(mock);

        when(mock.cargarEstadoActual(anyString())).thenThrow(new IOException());

        String direccion = "EstadoTiempoErroneo.json"; //El fichero existe y pero no está bien formado.
        IPersistencia persistencia = panelCliente.getIPersistencia();
        Localizacion loc = persistencia.cargarEstadoActual(direccion);
    }




}
