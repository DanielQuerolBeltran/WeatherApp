package view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import modelo.Excepciones.EstadoTiempoException;
import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Persistencia.PersistenciaJSON;
import modelo.Tiempo.EstadoTiempo;
import modelo.Tiempo.Prevision;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by iskynet on 29/11/2018.
 */
public class InfoLocLayoutControler {

    @FXML
    private Label ciudad, tempActual,pais, humedad, presion, viento, nuvosidad, descripcion, labelPrevision;

    @FXML
    private Button unidades, botonGuardar;

    @FXML
    private ImageView imgFavorita;
    private Image iconoFavorito;

    @FXML
    private ProgressIndicator loader;

    @FXML
    private Pane panelPrevisiones;

    @FXML
    ListView<EstadoTiempo> listPrevisiones;
    ObservableList<EstadoTiempo> observablePrevisiones;


    private Localizacion l1;
    private boolean iconoActivado;
    private EstadoTiempo tiempoActual;
    private PanelCliente panelCliente;
    private boolean estadoCargadoLocal;

    private MainApp main;


    public void setMainApp(MainApp main) {
        this.main = main;
    }


    public InfoLocLayoutControler(){
    }

    @FXML
    private void initialize() {
        ;
    }

    private void initPrevision() {

        Prevision prevision = null;
        try {
             prevision = panelCliente.getPrevision(l1);
        } catch (EstadoTiempoException e) {
            e.printStackTrace();
        }

        if(prevision!=null){
            panelPrevisiones.setVisible(true);
            observablePrevisiones = FXCollections.observableArrayList ();
            for (EstadoTiempo estadoTiempo : prevision.getPrevision()){
                observablePrevisiones.add(estadoTiempo);
            }
            listPrevisiones.setItems(observablePrevisiones);
            listPrevisiones.setCellFactory(listViewPrevisiones -> new ListPrevisionesCellController());
        }else{
            panelPrevisiones.setVisible(false);
        }

    }

    public void setLocalizacion(Localizacion localizacion, boolean cargaLocal) {
        l1 = localizacion;
        panelCliente = PanelCliente.getInstance();

        loader.setVisible(true);

        if(panelCliente.containsCiudad(l1)){
            iconoActivado = true;
        }else{
            iconoActivado = false;
        }

        ciudad.setText(l1.getNombre());
        pais.setText("");



        /*Thread threadTiempoActual = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        threadTiempoActual.start();*/

        initTiempoActual();

        Thread threadPrevision = new Thread(new Runnable() {
            @Override
            public void run() {
                initPrevision();
            }
        });

        if(!cargaLocal){
            try {
                setIconoFavorito(iconoActivado);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imgFavorita.setVisible(true);
            //initPrevision();
            threadPrevision.start();
            panelPrevisiones.setVisible(true);
            botonGuardar.setVisible(true);
            labelPrevision.setVisible(true);
        }else{
            panelPrevisiones.setVisible(false);
            imgFavorita.setVisible(false);
            botonGuardar.setVisible(false);
            labelPrevision.setVisible(false);
        }
        loader.setVisible(false);
    }



    private void initTiempoActual() {

        try {
            tiempoActual = panelCliente.getTiempoActual(l1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(tiempoActual!=null){
            String temperatura = String.valueOf(tiempoActual.getTemperatura());
            temperatura = temperatura.substring(0,temperatura.lastIndexOf(".") + 2);
            tempActual.setText(temperatura);
            unidades.setText(tiempoActual.getUnidadesTemperatura()+"º");
            humedad.setText(tiempoActual.getNubosidad()+"%");
            presion.setText(String.valueOf(tiempoActual.getPresion())+" hpa");
            viento.setText(String.valueOf(tiempoActual.getVelocidadViento())+" km/h");
            nuvosidad.setText(String.valueOf(tiempoActual.getNubosidad())+" %");
        }

    }

    private void setIconoFavorito(boolean activado) throws FileNotFoundException{
        FileInputStream inputstream = null;
        try {
            if(activado){
                inputstream = new FileInputStream("star2.png");
            }else{
                inputstream = new FileInputStream("star1.png");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        iconoFavorito = new Image(inputstream);
        imgFavorita.setImage(iconoFavorito);
    }

    @FXML
    private void presFavorite(){

       if(iconoActivado){ //Se deactiva el icono, se debe borrar de favoritos.
            iconoActivado = false;
            panelCliente.removeLocalizacionFavoritos(l1);
        }else{ //Se activa el icono, se añade a la lista de favoritos.
            iconoActivado = true;

            panelCliente.addLocalizacionFavoritos(l1)
;        }

        try {
            setIconoFavorito(iconoActivado);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       // FicheroJSON ficheroJSON = FicheroJSON.getInstance();
       // ficheroJSON.guardarLocalizacionesFavoritas(ListaLocalizaciones.getLocalizaciones());
        main.refrescarListaCiudades();
    }

    @FXML
    private void presUnidades(){
        if(tiempoActual.getUnidadesTemperatura()=='C'){
            unidades.setText("Fº");
            tiempoActual.cambiarCelsiusToFahrenheit();
        }else{
            unidades.setText("Cº");
            tiempoActual.cambiarFahrenheitToCelsius();
        }
        String temperatura = String.valueOf(tiempoActual.getTemperatura());
        temperatura = temperatura.substring(0,temperatura.lastIndexOf(".") + 2);
        tempActual.setText(temperatura+"º");
    }

    @FXML
    private void guardarTiempoActual() throws IOException {
        PersistenciaJSON ficheroJSON = new PersistenciaJSON();
        boolean resultado = ficheroJSON.guardarTiempoActual(l1,"src/"+l1.getNombre()+"-"+tiempoActual.getFecha()+".json");
        if(resultado){
            //Guardado correctamente.
        }else{
            //Problema guardando.
        }
    }


}
