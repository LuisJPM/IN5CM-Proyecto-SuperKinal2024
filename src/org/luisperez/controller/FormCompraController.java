package org.luisperez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.luisperez.dao.Conexion;
import org.luisperez.dto.CompraDTO;
import org.luisperez.model.Compra;
import org.luisperez.system.Main;
import org.luisperez.utils.SuperKinalAlert;
/**
* FXML Controller class
*
* @author LUISSS
*/
public class FormCompraController implements Initializable {
    private Main stage;
    private int operation;

    @FXML
    private TextField tfCompraId, tfFecha, tfTotal;

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
        stage.menuCompraView();
        CompraDTO.getCompraDTO().setCompra(null);
    }

    private void saveAction() {
        if (tfFecha.getText().isEmpty() || tfTotal.getText().isEmpty()) {
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
            tfFecha.requestFocus();
        } else if (operation == 1) {
            agregarCompra();
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
            stage.menuCompraView();
        } else if (operation == 2) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK) {
                editarCompra();
                CompraDTO.getCompraDTO().setCompra(null);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                stage.menuCompraView();
            } else {
                stage.menuCompraView();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (CompraDTO.getCompraDTO().getCompra() != null) {
            cargarDatos(CompraDTO.getCompraDTO().getCompra());
        }
    }

    private void cargarDatos(Compra compra) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaCompraStr = dateFormat.format(compra.getFechaCompra());
        tfCompraId.setText(String.valueOf(compra.getCompraId()));
        tfFecha.setText(fechaCompraStr);
        tfTotal.setText(String.valueOf(compra.getTotalCompra()));
    }

    private void agregarCompra() {
        String sql = "call sp_agregarCompra(?, ?)";
        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, tfFecha.getText());
            statement.setString(2, tfTotal.getText());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void editarCompra() {
        String sql = "call sp_editarCompra(?, ?, ?)";
        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareCall(sql)) {
            statement.setInt(1, Integer.parseInt(tfCompraId.getText()));
            statement.setString(2, tfFecha.getText());
            statement.setString(3, tfTotal.getText());
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