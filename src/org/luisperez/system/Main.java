/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.luisperez.system;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.InputStream;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.luisperez.controller.FormClienteController;
import org.luisperez.controller.FormEncargadoController;
import org.luisperez.controller.MenuCategoriaProductoController;
import org.luisperez.controller.MenuClienteController;
import org.luisperez.controller.MenuEncargadoController;
import org.luisperez.controller.MenuPrincipalController;
import org.luisperez.controller.MenuTicketSoporteController;

 
/**
*
* @author informatica
*/
public class Main extends Application {
    private final String URLVIEW = "/org/luisperez/view/";
    private Stage stage;
    private Scene scene;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Super Kinal 2020066");
        menuPrincipalView();
        stage.show();
    }
    
  public Initializable switchScene(String fxmlName, int width, int height) throws Exception{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        scene = new Scene((AnchorPane) loader.load(file), width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        
        resultado = (Initializable)loader.getController();
                
        return resultado;
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml", 950, 675);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuClienteView(){
        try{
            MenuClienteController menuClienteView = (MenuClienteController)switchScene("MenuClienteView.fxml", 1200, 750);
            menuClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formClienteView(int op){
        try{
            FormClienteController formClienteView = (FormClienteController)switchScene("FormClienteView.fxml", 500, 750);
            formClienteView.setOp(op);
            formClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuTicketSoporteView(){
        try{
            MenuTicketSoporteController menuTicketSoporteView = (MenuTicketSoporteController)switchScene("MenuTicketSoporteView.fxml", 1200,750);
            menuTicketSoporteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
   }
    }
    public void menuEncargadoView(){
        try{
            MenuEncargadoController menuEncargadoView = (MenuEncargadoController) switchScene("MenuEncargadoView.fxml", 1200, 750);
            menuEncargadoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public void formCargoView(int op){
        try{
           FormEncargadoController formEncargadoView = (FormEncargadoController) switchScene("FormEncargadoView.fxml", 500, 750);
           formEncargadoView.setOp(op);
           formEncargadoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCategoriaProductoView(){
        try{
           MenuCategoriaProductoController menuCategoriaProductoView = (MenuCategoriaProductoController) switchScene("MenuCategoriaProductoView.fxml", 1200, 750);
           menuCategoriaProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);       
    }
    
}
