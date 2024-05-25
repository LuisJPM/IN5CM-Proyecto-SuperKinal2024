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
import javafx.scene.control.TextField;
import org.luisperez.dao.Conexion;
import org.luisperez.dto.DistribuidorDTO;
import org.luisperez.model.Distribuidor;
import org.luisperez.system.Main;
import org.luisperez.utils.SuperKinalAlert;
/**
* FXML Controller class
*
* @author LUISSS
*/
public class FormDistribuidorController implements Initializable {
    private Main stage;
    private int operation;

    @FXML
    private TextField tfDistribuidorId, tfNombre, tfTelefono, tfNit, tfDireccion, tfWeb;

    @FXML
    private Button btnGuardar, btnCancelar;

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnCancelar) {
            cancelAction();
        } else if (event.getSource() == btnGuardar) {
            saveAction();
        }
    }

    private void cancelAction() {
        stage.menuDistribuidorView();
        DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
    }

    private void saveAction() {
        if (fieldsAreValid()) {
            if (operation == 1) {
                agregarDistribuidor();
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                stage.menuDistribuidorView();
            } else if (operation == 2) {
                if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK) {
                    editarDistribuidor();
                    DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                    stage.menuDistribuidorView();
                } else {
                    stage.menuDistribuidorView();
                }
            }
        } else {
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
            tfNombre.requestFocus();
        }
    }

    private boolean fieldsAreValid() {
        return !tfNombre.getText().isEmpty() &&
               !tfTelefono.getText().isEmpty() &&
               !tfNit.getText().isEmpty() &&
               !tfDireccion.getText().isEmpty() &&
               !tfWeb.getText().isEmpty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (DistribuidorDTO.getDistribuidorDTO().getDistribuidor() != null) {
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidor());
        }
    }

    private void cargarDatos(Distribuidor distribuidor) {
        tfDistribuidorId.setText(Integer.toString(distribuidor.getDistribuidorId()));
        tfNombre.setText(distribuidor.getNombreDistribuidor());
        tfTelefono.setText(distribuidor.getTelefonoDistribuidor());
        tfNit.setText(distribuidor.getNitDistribuidor());
        tfDireccion.setText(distribuidor.getDireccionDistribuidor());
        tfWeb.setText(distribuidor.getWeb());
    }

    private void agregarDistribuidor() {
        String sql = "call sp_agregarDistribuidor(?, ?, ?, ?, ?)";
        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfTelefono.getText());
            statement.setString(3, tfNit.getText());
            statement.setString(4, tfDireccion.getText());
            statement.setString(5, tfWeb.getText());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void editarDistribuidor() {
        String sql = "call sp_editarDistribuidor(?, ?, ?, ?, ?, ?)";
        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareCall(sql)) {
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfTelefono.getText());
            statement.setString(4, tfNit.getText());
            statement.setString(5, tfDireccion.getText());
            statement.setString(6, tfWeb.getText());
            statement.execute();
        } catch (SQLException e) {
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