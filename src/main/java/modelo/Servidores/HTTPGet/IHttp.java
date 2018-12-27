package modelo.Servidores.HTTPGet;

import org.json.simple.JSONObject;

/**
 * Created by iskynet on 13/12/2018.
 */

public interface IHttp {

    /**
     * Este método se usa para hacer la llamada a la API y encapsular la respuesta en un objeto JSON.
     * @param url URL de petición para la API
     * @return JSONOBject Todos tipos de peticiones (JSON, HTML, XML) se parsearan a objeto JSON.
     */
    public JSONObject doHttpGet(String url);
}
