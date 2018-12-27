import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.Persistencia.IPersistencia;
import modelo.Persistencia.PersistenciaJSON;
import modelo.Tiempo.EstadoTiempo;
import org.junit.Before;
import org.junit.Test;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

//Iteracción 2.
//Como usuario quiero poder guardar el estado actual del tiempo.
public class TestGuardarEstado {

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

    //E1 Valido. El usuario puede guardar toda la información.
    @Test
    public void guardarEstadoTiempoValido() throws IOException {
        //String direccion = "/src/EstadoTiempoMadrid-13Dic2018.json";
        String direccion = "EstadoTiempoMadrid-13Dic2018.json";
        assertEquals(persistencia.guardarTiempoActual(madrid, direccion), true);
    }


    //E2 Invalido. El usuario no puede guardar toda la información.
    @Test(expected = IOException.class)
    public void guardarEstatoTiempoInvalido() throws IOException {
        String direccion = "EstadoTiempoMadrid-13Dic2018.qweqwerqrewqew"; //La extension no es correcta.
        assertEquals(persistencia.guardarTiempoActual(madrid, direccion), true);
    }
}
