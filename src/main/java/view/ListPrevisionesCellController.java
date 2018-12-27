package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import modelo.Tiempo.EstadoTiempo;
import modelo.Tiempo.Prevision;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ListPrevisionesCellController extends ListCell<EstadoTiempo> {

    @FXML
    Label labelFecha, labelPresion, labelViento,labelNuvosidad;

    @FXML
    Label labelTemperatura;


    @FXML
    Pane panel;

    private FXMLLoader mLLoader;


     @Override
     protected void updateItem(EstadoTiempo estadoTiempo, boolean empty){
         super.updateItem(estadoTiempo, empty);

         if (estadoTiempo != null && !empty) {

             mLLoader = new FXMLLoader(getClass().getResource("ListPrevisionesCell.fxml"));
             mLLoader.setController(this);

             try {
                 mLLoader.load();
             } catch (IOException e) {
                 e.printStackTrace();
             }

             SimpleDateFormat formateador = new SimpleDateFormat("EEEE", Locale.forLanguageTag("es-ES"));
             String date = formateador.format(estadoTiempo.getFecha());

             labelFecha.setText(date.substring(0, 1).toUpperCase() + date.substring(1));

             Double temp = estadoTiempo.getTemperatura();
             String temperatura = temp.toString().substring(0,temp.toString().lastIndexOf(".") + 2);;
             labelTemperatura.setText(temperatura+" "+estadoTiempo.getUnidadesTemperatura()+"º");
             labelPresion.setText(estadoTiempo.getPresion()+"hPa");

            labelViento.setText(estadoTiempo.getVelocidadViento()+"km/h");
            labelNuvosidad.setText(estadoTiempo.getNubosidad()+"%");

             panel.getStyleClass().add("list-cellItems");
             setGraphic(panel);
         }


     }

}
