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
import org.luisperez.dto.CargoDTO;
import org.luisperez.model.Cargo;
import org.luisperez.system.Main;
import org.luisperez.utils.SuperKinalAlert;
/**
* FXML Controller class
*
* @author LUISSS
*/
public class MenuCargoController implements Initializable {
    private Main stage;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    @FXML
    private TableView<Cargo> tblCargos;

    @FXML
    private TableColumn<Cargo, Integer> colCargoId;

    @FXML
    private TableColumn<Cargo, String> colNombre, colDescripcion;

    @FXML
    private Button btnRegresar, btnAgregar, btnEditar, btnEliminar, btnBuscar;

    @FXML
    private TextField tfCargoId;

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnAgregar) {
            stage.formCargoView(1);
        } else if (event.getSource() == btnEditar) {
            CargoDTO.getCargoDTO().setCargo(tblCargos.getSelectionModel().getSelectedItem());
            stage.formCargoView(2);
        } else if (event.getSource() == btnEliminar) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK) {
                eliminarCargo(tblCargos.getSelectionModel().getSelectedItem().getCargoId());
                cargarDatos();
            }
        } else if (event.getSource() == btnBuscar) {
            tblCargos.getItems().clear();
            if (tfCargoId.getText().isEmpty()) {
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
            tblCargos.getItems().add(buscarCargo());
            op = 0;
        } else {
            tblCargos.setItems(listarCargos());
            colCargoId.setCellValueFactory(new PropertyValueFactory<>("cargoId"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCargo"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionCargo"));
        }
    }

    private ObservableList<Cargo> listarCargos() {
        ArrayList<Cargo> cargos = new ArrayList<>();
        String sql = "call sp_listarCargos()";

        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                cargos.add(new Cargo(cargoId, nombreCargo, descripcionCargo));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return FXCollections.observableList(cargos);
    }

    private void eliminarCargo(int carId) {
        String sql = "call sp_eliminarCargo(?)";

        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, carId);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Cargo buscarCargo() {
        Cargo cargo = null;
        String sql = "call sp_buscarCargo(?)";

        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                cargo = new Cargo(cargoId, nombreCargo, descripcionCargo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cargo;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}