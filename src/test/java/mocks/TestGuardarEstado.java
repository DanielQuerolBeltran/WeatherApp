package mocks;

//Iteracción 2.
//Como usuario quiero poder guardar el estado actual del tiempo.
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

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

public class TestGuardarEstado {

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

    //E1 Valido. El usuario puede guardar toda la información.

    @Test
    public void guardarEstadoTiempoValido_mock() throws IOException {

        //Inyectamos la persistencia del sistema de guardado.
        IPersistencia mock = Mockito.mock(PersistenciaJSON.class);
        panelCliente.setIPersistencia(mock);

        when(mock.guardarTiempoActual(anyObject(),anyString())).thenReturn(true);

        String direccion = "EstadoTiempoMadrid-13Dic2018.json";
        IPersistencia persistencia = panelCliente.getIPersistencia();
        assertEquals(persistencia.guardarTiempoActual(madrid, direccion), true);

    }


    //E2 Invalido. El usuario no puede guardar toda la información.
    @Test(expected = IOException.class)
    public void guardarEstatoTiempoInvalido_mock() throws IOException {

        //Inyectamos la persistencia del sistema de guardado.
        IPersistencia mock = Mockito.mock(PersistenciaJSON.class);
        panelCliente.setIPersistencia(mock);

        when(mock.guardarTiempoActual(anyObject(),anyString())).thenThrow(new IOException());

        String direccion = "EstadoTiempoMadrid-13Dic2018.qweqwerqrewqew"; //La extension no es correcta.
        IPersistencia persistencia = panelCliente.getIPersistencia();
        assertEquals(persistencia.guardarTiempoActual(madrid, direccion), true);
    }


}
