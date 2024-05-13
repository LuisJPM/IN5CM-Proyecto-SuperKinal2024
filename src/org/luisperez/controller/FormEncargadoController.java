/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.luisperez.dao.Conexion;
import org.luisperez.dto.CargoDTO;
import org.luisperez.model.Encargado;
import org.luisperez.system.Main;

/**
 * FXML Controller class
 *
 * @author LUIIIS
 */
public class FormEncargadoController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
    @FXML
    TextField tfCargoId, tfNombre;
    @FXML
    TextArea taDescripcion;
    @FXML
    Button btnGuardar, btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuEncargadoView();
            CargoDTO.getCargoDTO().setCargo(null);
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                agregarCargo();
                stage.menuEncargadoView();
            }else if(op == 2){
                editarCargo();
                CargoDTO.getCargoDTO().setCargo(null);
                stage.menuEncargadoView();
            }
            
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       if(CargoDTO.getCargoDTO().getCargo() != null){
           cargarDatos(CargoDTO.getCargoDTO().getCargo());  
       }
    }  
    
    public void cargarDatos(Encargado cargo){
        tfCargoId.setText(Integer.toString(cargo.getCargoId()));
        tfNombre.setText(cargo.getNombreCargo());
        taDescripcion.setText(cargo.getDescripcionCargo());
    }    
    
    public void agregarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCargos(?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, taDescripcion.getText());
            statement.execute();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement!=null){
                    statement.close();
                }
                if(conexion !=null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCargos(?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, taDescripcion.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
           try{
               if(statement !=null){
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

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
}
