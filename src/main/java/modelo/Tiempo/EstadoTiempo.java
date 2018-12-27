package modelo.Tiempo;

import java.util.Date;

public class EstadoTiempo {
    private double presion,temperatura,velocidadViento, nubosidad;
    private Date fecha;
    private Long humedad;
    private char unidadesTemperatura;

    public double getTemperatura() {
        return temperatura;
    }

    public double getPresion() {
        return presion;
    }

    public double getVelocidadViento() {
        return velocidadViento;
    }

    public double getNubosidad() {
        return nubosidad;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public void setPresion(Double presion) {
        this.presion = presion;
    }

    public void setHumedad(Long humedad) {
        this.humedad = humedad;
    }

    public void setNubosidad(Double nubosidad) {
        this.nubosidad = nubosidad;
    }

    public void setVelocidadViento(Double velocidadViento) {
        this.velocidadViento = velocidadViento;
    }

    public Date getFecha() {

        return fecha;
    }

    public double cambiarFahrenheitToCelsius() {
        setTemperatura((getTemperatura()-32)*5/9);
        setUnidadesTemperatura('C');
        return getTemperatura();
    }

    public char getUnidadesTemperatura() {
        return unidadesTemperatura;
    }

    public void setUnidadesTemperatura(char c) {
        this.unidadesTemperatura = c;
    }

    public double  cambiarCelsiusToFahrenheit() {
        setTemperatura(((getTemperatura()*9)/5)+32);
        setUnidadesTemperatura('F');
        return getTemperatura();
    }
}
