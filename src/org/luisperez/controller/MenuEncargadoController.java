/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.luisperez.dao.Conexion;
import org.luisperez.dto.CargoDTO;
import org.luisperez.model.Encargado;
import org.luisperez.system.Main;

/**
 * FXML Controller class
 *
 * @author LUIIIS
 */
public class MenuEncargadoController implements Initializable {
    Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblEncargados;
    @FXML
    TableColumn colEncargadoId, colNombre, colDescripcion;
    @FXML
    Button btnAgregar, btnEditar, btnEliminar, btnRegresar, btnBuscar;
    @FXML
    TextField tfEncargadoId;
                    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formCargoView(1);
        }else if(event.getSource() == btnEditar){
            CargoDTO.getCargoDTO().setCargo((Encargado)tblEncargados.getSelectionModel().getSelectedItem());
            stage.formCargoView(2);
        }else if(event.getSource() == btnEliminar){
           eliminarCargo(((Encargado)tblEncargados.getSelectionModel().getSelectedItem()).getCargoId()); 
           cargarDatos();
        }else if(event.getSource() == btnBuscar){
            tblEncargados.getItems().clear();
            if(tfEncargadoId.getText().equals("")){
                cargarDatos();
            }
            op = 3;
            cargarDatos();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }  
    
    public void cargarDatos(){
        if(op == 3){
            tblEncargados.getItems().add(buscarCargo());
            op = 0;
        }else{
            tblEncargados.setItems(listarEncargados());
        }
        colEncargadoId.setCellValueFactory(new PropertyValueFactory<Encargado, Integer>("cargoId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Encargado, String>("nombreCargo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Encargado, String>("descripcionCargo"));
    }
    
    public ObservableList<Encargado> listarEncargados(){
        ArrayList<Encargado> encargados = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                encargados.add(new Encargado(cargoId, nombreCargo, descripcionCargo));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        return FXCollections.observableArrayList(encargados);
    }
    
    public void eliminarCargo(int carId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCargos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, carId);
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
            System.out.println(e.getMessage()); 
        } 
    }
}
    public Encargado buscarCargo(){
        Encargado cargo = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCargos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEncargadoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargo = new Encargado(cargoId, nombreCargo, descripcionCargo);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return cargo;
    }
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
