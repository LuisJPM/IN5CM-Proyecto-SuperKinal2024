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
import org.luisperez.dto.ClienteDTO;
import org.luisperez.model.Cliente;
import org.luisperez.system.Main;
import org.luisperez.utils.SuperKinalAlert;

public class MenuClienteController implements Initializable {
    private Main stage;
    private int op;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TableColumn<Cliente, Integer> colClienteId;
    @FXML
    private TableColumn<Cliente, String> colNombre, colApellido, colTelefono, colNit, colDireccion;

    @FXML
    private Button btnRegresar, btnAgregar, btnEditar, btnEliminar, btnBuscar;

    @FXML
    private TextField tfClienteId;

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnAgregar) {
            stage.formClienteView(1);
        } else if (event.getSource() == btnEditar) {
            ClienteDTO.getClienteDTO().setCliente(tblClientes.getSelectionModel().getSelectedItem());
            stage.formClienteView(2);
        } else if (event.getSource() == btnEliminar) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK) {
                eliminarCliente(tblClientes.getSelectionModel().getSelectedItem().getClienteId());
                cargarDatos();
            }
        } else if (event.getSource() == btnBuscar) {
            tblClientes.getItems().clear();
            if (tfClienteId.getText().isEmpty()) {
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
            tblClientes.getItems().add(buscarCliente());
            op = 0;
        } else {
            tblClientes.setItems(listarClientes());
            colClienteId.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            colNit.setCellValueFactory(new PropertyValueFactory<>("nit"));
            colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        }
    }

    private ObservableList<Cliente> listarClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "call sp_ListarClientes()";

        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String nit = resultSet.getString("nit");
                String direccion = resultSet.getString("direccion");

                clientes.add(new Cliente(clienteId, nombre, apellido, telefono, nit, direccion));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return FXCollections.observableList(clientes);
    }

    private void eliminarCliente(int cliId) {
        String sql = "call sp_eliminarCliente(?)";

        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, cliId);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Cliente buscarCliente() {
        Cliente cliente = null;
        String sql = "call sp_buscarCliente(?)";

        try (Connection conexion = Conexion.getInstance().obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(tfClienteId.getText()));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int clienteId = resultSet.getInt("clienteId");
                    String nombre = resultSet.getString("nombre");
                    String apellido = resultSet.getString("apellido");
                    String telefono = resultSet.getString("telefono");
                    String nit = resultSet.getString("nit");
                    String direccion = resultSet.getString("direccion");

                    cliente = new Cliente(clienteId, nombre, apellido, telefono, nit, direccion);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cliente;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}