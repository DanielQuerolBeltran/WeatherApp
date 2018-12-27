package modelo.Tiempo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iskynet on 13/12/2018.
 */
public class Prevision {


    private List<EstadoTiempo> previsiones;

    public Prevision(){
        previsiones = new ArrayList<EstadoTiempo>();
    }

    public void setPrevision(List<EstadoTiempo> listaEstados) {
        previsiones = listaEstados;
    }

    public List<EstadoTiempo> getPrevision() {
        return previsiones;
    }

}
