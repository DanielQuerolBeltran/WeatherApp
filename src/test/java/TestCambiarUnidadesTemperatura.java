//Iteracción 3.
//R1 - HU3 Como usuario quiero poder cambiar las unidades de temperatura.

import modelo.Tiempo.EstadoTiempo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCambiarUnidadesTemperatura {

    private EstadoTiempo estadoTiempo;
    @Before
    public void init(){
        estadoTiempo = new EstadoTiempo();
    }


    //E1 Válido  El usuario puede cambiar los grados celsius a fahrenheit. Las cambia.
    @Test
    public void cambiarCelsiusAFahrenheitValido(){
        estadoTiempo.setTemperatura(47.12);
        Double temperaturaAntes = estadoTiempo.getTemperatura();
        Double temperaturaCalculada = (temperaturaAntes-32)*5/9;
        estadoTiempo.cambiarFahrenheitToCelsius();

        assertEquals(estadoTiempo.getTemperatura(), temperaturaCalculada, 0.01);
        assertEquals(estadoTiempo.getUnidadesTemperatura(), 'C', 0);
    }

    //E2 Inválido El usuario puede cambiar los grados fahrenheit a celsius. No las cambia
    @Test
    public void cambiarCelsiusAFahrenheitInvalido(){
        estadoTiempo.setTemperatura(8.4);
        Double temperaturaAntes = estadoTiempo.getTemperatura();
        Double temperaturaCalculada = temperaturaAntes*9/5+32;
        estadoTiempo.cambiarCelsiusToFahrenheit();

        assertEquals(estadoTiempo.getTemperatura(), temperaturaCalculada, 0.01);
        assertEquals(estadoTiempo.getUnidadesTemperatura(), 'F', 0);
    }

}
