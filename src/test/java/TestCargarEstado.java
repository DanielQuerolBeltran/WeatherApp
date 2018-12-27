import modelo.Localizacion.Localizacion;
import modelo.Persistencia.IPersistencia;
import modelo.Persistencia.PersistenciaJSON;
import modelo.Tiempo.EstadoTiempo;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertFalse;

/**
 * Created by iskynet on 13/12/2018.
 */

//Iteracción 1.
//Como usuario quiero poder recuperar el estado del tiempo a partir de un fichero de texto.
public class TestCargarEstado {

    Localizacion madrid;
    EstadoTiempo estadoTiempo;
    IPersistencia persistencia;

    @Before
    public void init(){
        persistencia = new PersistenciaJSON();
    }

    //E1 Valido. El usuario es capaz de recuperar los datos almacenados y ver la información.
    @Test
    public void cargarEstadoActualValido() throws IOException {
        String direccion = "EstadoTiempoMadrid-13Dic2018.json"; //El fichero existe y está bien formado. Se comprueba antes del test.
        Localizacion loc = persistencia.cargarEstadoActual(direccion);
        estadoTiempo = loc.getTiempoActual();
        assertFalse(estadoTiempo==null );
    }

    @Test(expected = IOException.class)
    public void cargarEstadoActualInvalido() throws IOException {
        String direccion = "EstadoTiempoErroneo.json"; //El fichero existe y pero no está bien formado.
        Localizacion loc = persistencia.cargarEstadoActual(direccion);
    }
}
