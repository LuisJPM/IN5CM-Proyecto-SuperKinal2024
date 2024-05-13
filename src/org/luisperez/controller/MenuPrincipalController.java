


/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.luisperez.controller;
 
 
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.luisperez.system.Main;


/**
 *
 * @author informatica
 */

public class MenuPrincipalController implements Initializable {
        private Main stage;
        @FXML
        MenuItem btnClientes, btnTicketSoporte, btnEncargados, btnCategoriaProducto;
        
        @FXML
        public void handleButtonAction(ActionEvent event) throws Exception{
            if(event.getSource()== btnClientes) {
                stage.menuClienteView();
            }else if(event.getSource() == btnTicketSoporte){
                stage.menuTicketSoporteView();
            }else if(event.getSource() == btnEncargados){
                stage.menuEncargadoView();
            }else if(event.getSource() == btnCategoriaProducto){
                stage.menuCategoriaProductoView();
            }
            
        }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
    }
    
}
