package modelo.Localizacion;


import modelo.Tiempo.EstadoTiempo;



public class Localizacion {

    private String KeyOpenWeather, KeyAccuWeather;
    private String nombre, lat, log;
    private EstadoTiempo tiempoActual;

    public Localizacion(String nombre, String lat, String log) {
        this.nombre = nombre;
        this.lat = lat;
        this.log = log;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Localizacion(String nombre) {
        this.nombre = nombre;
    }

    public String getKeyOpenWeather() {

        return KeyOpenWeather;
    }

    public void setKeyOpenWeather(String id){
        this.KeyAccuWeather = id;
    }


    public String getLat() {
        return lat;
    }

    public String getLog() {

        return log;
    }

    public void setTiempoActual(EstadoTiempo estadoTiempo) {
        this.tiempoActual = estadoTiempo;
    }

    public EstadoTiempo getTiempoActual(){
        return tiempoActual;
    }

    public String getNombre() {
        return nombre;
    }
}
