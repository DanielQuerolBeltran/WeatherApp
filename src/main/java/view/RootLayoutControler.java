package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import modelo.Localizacion.Localizacion;
import modelo.PanelCliente;
import modelo.Persistencia.PersistenciaJSON;
import modelo.Util.CoordenadasUtil;
import view.buscador.ListSearchCityCellController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iskynet on 30/11/2018.
 */
public class RootLayoutControler {


    @FXML
    private ComboBox buscador = new ComboBox();

    @FXML
    private Button buscarButton;

    ToggleGroup radioButtons = new ToggleGroup();

    @FXML
    private RadioButton nombreRadio, cpRadio, coordenadasRadio;

    private ObservableList<Localizacion> resultadoBusqueda;

    private ObservableList<Localizacion> localizacionsFavoritas;

    @FXML
    private ListView listaCiudadesFavoritas;

    private MainApp main;

    private PanelCliente panelCliente;

    private Toggle radioButtonSeleccionado;

    public void setMainApp(MainApp main) {
        this.main = main;
    }


    @FXML
    private void initialize() {

        panelCliente = PanelCliente.getInstance();

        inicializarBuscador();
        inicializarListaFavoritas();

        nombreRadio.setToggleGroup(radioButtons);
        coordenadasRadio.setToggleGroup(radioButtons);
        cpRadio.setToggleGroup(radioButtons);

        radioButtons.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {


                if(new_toggle==nombreRadio){
                    buscador.setPromptText("Buscar nombre ...");
                }else{
                    if(new_toggle==coordenadasRadio){
                        buscador.setPromptText("Buscar ... (47.11,19.99)");
                    }else{
                        buscador.setPromptText("Buscar ... (12004)");
                    }
                }

            }
        });

    }

    private void inicializarListaFavoritas(){

        List<Localizacion> listaResultado = panelCliente.getListaLocalizaciones();
        localizacionsFavoritas = FXCollections.observableArrayList(listaResultado);
        listaCiudadesFavoritas.setItems(localizacionsFavoritas);
        listaCiudadesFavoritas.refresh();


        listaCiudadesFavoritas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Localizacion>() {
            @Override
            public void changed(ObservableValue<? extends Localizacion> observable, Localizacion oldValue, Localizacion newValue) {
                main.initLoyoutCiudad(newValue, false);
            }
        });
    }

    public void refleshListaCiudades(){
        localizacionsFavoritas.clear();
        inicializarListaFavoritas();
    }

    private void inicializarBuscador() {
        buscador.setVisibleRowCount(0);

        buscador.setPromptText("Buscar lugar ...");
        buscador.setEditable(true);

        buscador.valueProperty().addListener((obs, oldItem, newItem) -> {
            List<Localizacion> listaResultado = new ArrayList<>();
            if(newItem.getClass()==String.class){
                if(radioButtonSeleccionado==nombreRadio){
                    buscador.setPromptText("Buscar nombre ...");
                }else{
                    if(radioButtonSeleccionado==coordenadasRadio){
                        buscador.setPromptText("Buscar ... (lat,long)");
                    }else{
                        buscador.setPromptText("Buscar ... (12004)");
                    }
                }

                buscador.setItems(FXCollections.observableArrayList(listaResultado));
                buscador.setCellFactory(localizacionListView -> new ListSearchCityCellController());
                buscador.setVisibleRowCount(5);
                buscador.placeholderProperty();
                buscador.show();
                buscador.applyCss();
            }
            if(newItem.getClass()==Localizacion.class){
                Localizacion localizacionSelected = (Localizacion) newItem;
                main.initLoyoutCiudad(localizacionSelected, false);
            }


        });
    }

    @FXML
    private void searchButtonPress() {
        buscarButton.setDisable(true);
        String cadenabusqueda;
        cadenabusqueda = buscador.getEditor().getText();

        if(cadenabusqueda!=null && cadenabusqueda.length()>0){
            List<Localizacion> listaResultado = new ArrayList<>();

            if(nombreRadio == radioButtons.getSelectedToggle()){
                listaResultado= panelCliente.buscarCiudadNombre(cadenabusqueda);
            }else{
                if(cpRadio == radioButtons.getSelectedToggle()){
                    listaResultado = panelCliente.buscarCiudadCP(cadenabusqueda);
                }else{
                    String[] coor = cadenabusqueda.split(" ");
                    if(CoordenadasUtil.validarCoordenadas(coor[0]) && CoordenadasUtil.validarCoordenadas(coor[1])){
                        listaResultado = panelCliente.buscarCiudadCoordenadas(coor[0], coor[1]);
                    }
                }
            }

            buscador.setItems(FXCollections.observableArrayList(listaResultado));
            buscador.setCellFactory(localizacionListView -> new ListSearchCityCellController());
            buscador.setVisibleRowCount(5);
            buscador.placeholderProperty();
            buscador.show();
        }

        buscarButton.setDisable(false);

    }

    @FXML
    private void cargarTiempo() throws IOException {

        FileChooser.ExtensionFilter jsonExtensionFilter =
                new FileChooser.ExtensionFilter(
                        "JSON (.json)", "*.json");

        String dir = main.abrirVentanaFicheros(jsonExtensionFilter).toString();

        PersistenciaJSON ficheroJSON = new PersistenciaJSON();
        Localizacion l1 = ficheroJSON.cargarEstadoActual(dir);
        main.initLoyoutCiudad(l1, true);

    }

}
