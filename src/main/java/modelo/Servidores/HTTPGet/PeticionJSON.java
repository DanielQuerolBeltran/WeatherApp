package modelo.Servidores.HTTPGet;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class PeticionJSON implements IHttp {

    @Override
    public JSONObject doHttpGet(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(get);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String retSrc = EntityUtils.toString(entity);
               // retSrc = retSrc.replace('[',' ');
               // retSrc = retSrc.replace(']',' ');
                JSONParser parser = new JSONParser();
                JSONObject result = (JSONObject) parser.parse(retSrc);

                return result;
            }

        }
        catch (IOException ioe) {   ioe.printStackTrace(); }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
