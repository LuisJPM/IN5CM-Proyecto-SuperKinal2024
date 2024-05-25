package org.luisperez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.luisperez.dao.Conexion;
import org.luisperez.dto.CargoDTO;
import org.luisperez.model.Cargo;
import org.luisperez.system.Main;
import org.luisperez.utils.SuperKinalAlert;

/**
* FXML Controller class
*
* @author LUISSS
*/
public class FormCargoController implements Initializable {
    
    private Main stage;
    private int operation;

    @FXML
    private TextField tfCargoId, tfNombre;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private Button btnGuardar, btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            cancelAction();
        } else if(event.getSource() == btnGuardar){
            saveAction();
        }
    }

    private void cancelAction() {
        stage.menuCargoView();
        CargoDTO.getCargoDTO().setCargo(null);
    }

    private void saveAction() {
        if(tfNombre.getText().isEmpty() || taDescripcion.getText().isEmpty()) {
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
            tfNombre.requestFocus();
        } else if (operation == 1) {
            agregarCargo();
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
            stage.menuCargoView();
        } else if (operation == 2) {
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK) {
                editarCargo();
                CargoDTO.getCargoDTO().setCargo(null);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                stage.menuCargoView();
            } else {
                stage.menuCargoView();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(CargoDTO.getCargoDTO().getCargo() != null) {
            cargarDatos(CargoDTO.getCargoDTO().getCargo());
        }
    }

    public void cargarDatos(Cargo cargo){
        tfCargoId.setText(String.valueOf(cargo.getCargoId()));
        tfNombre.setText(cargo.getNombreCargo());
        taDescripcion.setText(cargo.getDescripcionCargo());
    }

    public void agregarCargo(){
        String sql = "call sp_agregarCargo(?, ?)";
        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, tfNombre.getText());
            statement.setString(2, taDescripcion.getText());
            statement.execute();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void editarCargo(){
        String sql = "call sp_editarCargo(?, ?, ?)";
        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareCall(sql)) {
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, taDescripcion.getText());
            statement.execute();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
}