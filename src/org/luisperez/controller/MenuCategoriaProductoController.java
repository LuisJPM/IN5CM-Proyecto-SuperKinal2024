package org.luisperez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class MenuCategoriaProductoController implements Initializable {
    private Main stage;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    @FXML
    private TableView<CategoriaProducto> tblCategoriaProductos;

    @FXML
    private TableColumn<CategoriaProducto, Integer> colCategoriaProductoId;

    @FXML
    private TableColumn<CategoriaProducto, String> colNombre, colDescripcion;

    @FXML
    private Button btnRegresar, btnAgregar, btnEditar, btnEliminar, btnBuscar;

    @FXML
    private TextField tfCategoriaProductoId;

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnAgregar) {
            stage.formCategoriaProductoView(1);
        } else if (event.getSource() == btnEditar) {
            CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto(tblCategoriaProductos.getSelectionModel().getSelectedItem());
            stage.formCategoriaProductoView(2);
        } else if (event.getSource() == btnEliminar) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK) {
                eliminarCategoriaProducto(tblCategoriaProductos.getSelectionModel().getSelectedItem().getCategoriaProductoId());
                cargarDatos();
            }
        } else if (event.getSource() == btnBuscar) {
            tblCategoriaProductos.getItems().clear();
            if (tfCategoriaProductoId.getText().isEmpty()) {
                cargarDatos();
            } else {
                op = 3;
                cargarDatos();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    private void cargarDatos() {
        if (op == 3) {
            tblCategoriaProductos.getItems().add(buscarCategoriaProducto());
            op = 0;
        } else {
            tblCategoriaProductos.setItems(listarCategoriaProductos());
            colCategoriaProductoId.setCellValueFactory(new PropertyValueFactory<>("categoriaProductoId"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionCategoria"));
        }
    }

    private ObservableList<CategoriaProducto> listarCategoriaProductos() {
        ArrayList<CategoriaProducto> categoriaProductos = new ArrayList<>();
        String sql = "call sp_listarCategoriaProductos()";

        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int categoriaProductoId = resultSet.getInt("categoriaProductoId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                categoriaProductos.add(new CategoriaProducto(categoriaProductoId, nombreCategoria, descripcionCategoria));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return FXCollections.observableList(categoriaProductos);
    }

    private void eliminarCategoriaProducto(int capId) {
        String sql = "call sp_eliminarCategoriaProducto(?)";

        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, capId);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private CategoriaProducto buscarCategoriaProducto() {
        CategoriaProducto categoriaProducto = null;
        String sql = "call sp_buscarCategoriaProducto(?)";

        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(tfCategoriaProductoId.getText()));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int categoriaProductoId = resultSet.getInt("categoriaProductoId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                categoriaProducto = new CategoriaProducto(categoriaProductoId, nombreCategoria, descripcionCategoria);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categoriaProducto;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}