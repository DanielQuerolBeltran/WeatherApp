package view;/**
 * Created by iskynet on 29/11/2018.
 */

import com.sun.glass.ui.CommonDialogs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Servidores.OpenWeather;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    private SplitPane rootLayout;
    private Stage primaryStage;

    private RootLayoutControler controladorRoot;

    private PanelCliente panelCliente;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

      //  FicheroJSON ficheroJSON = FicheroJSON.getInstance();
      //  ListaLocalizaciones.addAll(ficheroJSON.cargarLocalizacionesFavoritas());

        panelCliente = PanelCliente.getInstance();
        panelCliente.setServidorPorDefecto(new OpenWeather());
        this.primaryStage = primaryStage;
        initLayout();


    }

    public void initLayout() {
        try {
            /* Load root layout from fxml file. */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RootLayout.fxml"));
            rootLayout = (SplitPane) loader.load();

            controladorRoot =  loader.getController();
            controladorRoot.setMainApp(this);


            //initLoyoutCiudad(ListaLocalizaciones, false);
           /* if(panelCliente.getListaLocalizaciones().size()==0){ //Si no hay ninguna localizacion creamos una por defecto.
                initLoyoutCiudad(new Localizacion("Castellon", "-0.0576","39.9929"), false);
            }else{
                initLoyoutCiudad(panelCliente.getListaLocalizaciones().get(0), false);
            }*/


            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(MainApp.class.getResource("StyleMain.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void initLoyoutCiudad(Localizacion localizacion, boolean cargaLocal) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("InfoLocLayout.fxml"));
        AnchorPane contactoOverview = null;
        try {
            contactoOverview = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InfoLocLayoutControler controlador =  loader.getController();
        controlador.setMainApp(this);
        controlador.setLocalizacion(localizacion, cargaLocal);

        AnchorPane anchorCentral = (AnchorPane) rootLayout.getItems().get(1);
        anchorCentral.getChildren().setAll(contactoOverview);

    }


    protected void refrescarListaCiudades(){
        controladorRoot.refleshListaCiudades();
    }

    public File abrirVentanaFicheros( FileChooser.ExtensionFilter extension){

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Resource File");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File userDirectory = new File("src");
        fileChooser.setInitialDirectory(userDirectory);

        fileChooser.setSelectedExtensionFilter(extension);

        File selectedFile = directoryChooser.showDialog(primaryStage);

        String fichero = selectedFile.toString();

        return selectedFile;
    }


}
