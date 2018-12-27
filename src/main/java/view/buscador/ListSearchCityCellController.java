package view.buscador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import modelo.Localizacion.Localizacion;

import java.io.IOException;

/**
 * Created by iskynet on 03/12/2018.
 */
public class ListSearchCityCellController extends ListCell<Localizacion> {

    @FXML
    private Label nombre;

    @FXML
    private Label info;

    @FXML
    private Pane panel;

    @FXML
    private Button cellButton = new Button("ReaddButtonmove");

    private FXMLLoader mLLoader;


    @Override
    protected void updateItem(Localizacion localizacion, boolean empty) {
        super.updateItem(localizacion, empty);
        if (localizacion != null && !empty) {

            mLLoader = new FXMLLoader(getClass().getResource("ListSearchCityCell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            nombre.setText(localizacion.getNombre());
            info.setText("Lat: "+localizacion.getLat()+" Long: "+localizacion.getLog());


            setText(null);
            setGraphic(panel);
        }
    }






}
