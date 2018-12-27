package modelo.Util;

/**
 * Created by iskynet on 15/12/2018.
 */
public final class CoordenadasUtil {

    /**
     * Este metodo se usa validar las coordenadas. Se pasa de unidad en unidad.
     * @param  grados
     */
    public static boolean validarCoordenadas(String grados) {
        return grados.matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$\n");
    }

}
