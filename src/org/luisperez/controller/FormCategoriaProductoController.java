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
import org.luisperez.dto.CategoriaProductoDTO;
import org.luisperez.model.CategoriaProducto;
import org.luisperez.system.Main;
import org.luisperez.utils.SuperKinalAlert;
/**
* FXML Controller class
*
* @author LUISSS
*/
public class FormCategoriaProductoController implements Initializable {
    
    private Main stage;    
    private int operation;

    @FXML
    private TextField tfCategoriaProductoId, tfNombre;
    
    @FXML
    private TextArea taDescripcion;
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
        stage.menuCategoriaProductoView();
        CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto(null);
    }

    private void saveAction() {
        if (tfNombre.getText().isEmpty() || taDescripcion.getText().isEmpty()) {
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
            tfNombre.requestFocus();
        } else if (operation == 1) {
            agregarCategoriaProducto();
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
            stage.menuCategoriaProductoView();
        } else if (operation == 2) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK) {
                editarCategoriaProducto();
                CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto(null);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                stage.menuCategoriaProductoView();
            } else {
                stage.menuCategoriaProductoView();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProducto() != null) {
            cargarDatos(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProducto());
        }
    }

    public void cargarDatos(CategoriaProducto categoriaProducto) {
        tfCategoriaProductoId.setText(String.valueOf(categoriaProducto.getCategoriaProductoId()));
        tfNombre.setText(categoriaProducto.getNombreCategoria());
        taDescripcion.setText(categoriaProducto.getDescripcionCategoria());
    }

    public void agregarCategoriaProducto() {
        String sql = "call sp_agregarCategoriaProducto(?, ?)";
        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, tfNombre.getText());
            statement.setString(2, taDescripcion.getText());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editarCategoriaProducto() {
        String sql = "call sp_editarCategoriaProducto(?, ?, ?)";
        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareCall(sql)) {
            statement.setInt(1, Integer.parseInt(tfCategoriaProductoId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, taDescripcion.getText());
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