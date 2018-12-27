package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import modelo.Localizacion.Localizacion;

import java.io.IOException;

/**
 * Created by iskynet on 03/12/2018.
 */
public class ListFavoriteCityCellController extends ListCell<Localizacion> {


    @FXML
    private Label nombre;

    private FXMLLoader mLLoader;

    @FXML
    private Pane panel;

    @Override
    protected void updateItem(Localizacion localizacion, boolean empty) {
        super.updateItem(localizacion, empty);
        if (localizacion != null && !empty) {

            mLLoader = new FXMLLoader(getClass().getResource("ListFavoriteCityCell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            nombre.setText(localizacion.getNombre());

            setText(null);
            setGraphic(panel);
        }
    }

}
