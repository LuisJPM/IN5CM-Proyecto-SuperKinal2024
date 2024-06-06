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
import org.luisperez.dto.ClienteDTO;
import org.luisperez.model.Cliente;
import org.luisperez.system.Main;
import org.luisperez.utils.SuperKinalAlert;
/**
* FXML Controller class
*
* @author LUISSS
*/
public class FormClienteController implements Initializable {
    private Main stage;
    
    private int operation;
    
    @FXML
    private TextField tfClienteId, tfNombre, tfApellido, tfTelefono, tfNit, tfDireccion;
    
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
        stage.menuClienteView();
        ClienteDTO.getClienteDTO().setCliente(null);
    }

    private void saveAction() {
        if (tfNombre.getText().isEmpty() || tfApellido.getText().isEmpty() || tfDireccion.getText().isEmpty()) {
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
            tfNombre.requestFocus();
        } else if (operation == 1) {
            agregarCliente();
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
            stage.menuClienteView();
        } else if (operation == 2) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK) {
                editarCliente();
                ClienteDTO.getClienteDTO().setCliente(null);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                stage.menuClienteView();
            } else {
                stage.menuClienteView();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (ClienteDTO.getClienteDTO().getCliente() != null) {
            cargarDatos(ClienteDTO.getClienteDTO().getCliente());
        }
    }    
    
    public void cargarDatos(Cliente cliente) {
        tfClienteId.setText(String.valueOf(cliente.getClienteId()));
        tfNombre.setText(cliente.getNombre());
        tfApellido.setText(cliente.getApellido());
        tfTelefono.setText(cliente.getTelefono());
        tfNit.setText(cliente.getNit());
        tfDireccion.setText(cliente.getDireccion());
    }
    
    public void agregarCliente() {
        String sql = "call sp_agregarCliente(?, ?, ?, ?, ?)";
        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setString(3, tfTelefono.getText());
            statement.setString(4, tfNit.getText());
            statement.setString(5, tfDireccion.getText());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void editarCliente() {
        String sql = "call sp_editarCliente(?, ?, ?, ?, ?, ?)";
        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareCall(sql)) {
            statement.setInt(1, Integer.parseInt(tfClienteId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfApellido.getText());
            statement.setString(4, tfTelefono.getText());
            statement.setString(5, tfNit.getText());
            statement.setString(6, tfDireccion.getText());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int operation) {
        this.operation = operation;
    }
}