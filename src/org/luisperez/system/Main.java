/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.luisperez.system;
/**
* FXML Controller class
*
* @author LUISSS
*/
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.luisperez.controller.FormCargoController;
import org.luisperez.controller.FormCategoriaProductoController;
import org.luisperez.controller.FormClienteController;
import org.luisperez.controller.FormCompraController;
import org.luisperez.controller.FormDistribuidorController;
import org.luisperez.controller.FormUsuarioController;
import org.luisperez.controller.MenuCargoController;
import org.luisperez.controller.MenuCategoriaProductoController;
import org.luisperez.controller.MenuClienteController;
import org.luisperez.controller.MenuCompraController;
import org.luisperez.controller.MenuDistribuidorController;
import org.luisperez.controller.MenuEmpleadoController;
import org.luisperez.controller.MenuFacturaController;
import org.luisperez.controller.LoginController;
import org.luisperez.controller.MenuPrincipalController;
import org.luisperez.controller.MenuProductoController;
import org.luisperez.controller.MenuPromocionController;
import org.luisperez.controller.MenuTicketSoporteController;

public class Main extends Application {
    private final String URLVIEW = "/org/luisperez/view/";
    private Stage stage;
    private Scene scene;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("SuperKinal 2020066");
        menuLoginView();
        stage.show();
}
    public Initializable switchScene(String fxmlName, int width, int height) throws Exception{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        scene = new Scene((AnchorPane)loader.load(file), width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        resultado = (Initializable)loader.getController();
        return resultado;
    }
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml", 900, 600);
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
            MenuTicketSoporteController menuTicketSoporteView = (MenuTicketSoporteController)switchScene("MenuTicketSoporteView.fxml", 1200, 750);
            menuTicketSoporteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuCargoView(){
        try{
            MenuCargoController menuCargoView = (MenuCargoController)switchScene("MenuCargoView.fxml", 1200, 750);
            menuCargoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formCargoView(int op){
        try{
            FormCargoController formCargoView = (FormCargoController)switchScene("FormCargoView.fxml", 500, 750);
            formCargoView.setOperation(op);
            formCargoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
 
    public void menuCompraView(){
        try{
            MenuCompraController menuCompraView = (MenuCompraController)switchScene("MenuCompraView.fxml", 1200, 750);
            menuCompraView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formCompraView(int op){
        try{
            FormCompraController formCompraView = (FormCompraController)switchScene("FormCompraView.fxml", 500, 750);
            formCompraView.setOp(op);
            formCompraView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void menuDistribuidorView(){
        try{
            MenuDistribuidorController menuDistribuidorView = (MenuDistribuidorController)switchScene("MenuDistribuidorView.fxml", 1200, 750);
            menuDistribuidorView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formDistribuidorView(int op){
        try{
            FormDistribuidorController formDistribuidorView = (FormDistribuidorController)switchScene("FormDistribuidorView.fxml", 500, 750);
            formDistribuidorView.setOperation(op);
            formDistribuidorView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuCategoriaProductoView(){
        try{
            MenuCategoriaProductoController menuCategoriaProductoView = (MenuCategoriaProductoController)switchScene("MenuCategoriaProductoView.fxml", 1200, 750);
            menuCategoriaProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formCategoriaProductoView(int op){
        try{
            FormCategoriaProductoController formCategoriaProductoView = (FormCategoriaProductoController)switchScene("FormCategoriaProductoView.fxml", 500, 750);
            formCategoriaProductoView.setOperation(op);
            formCategoriaProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuEmpleadoView(int op){
        try{
            MenuEmpleadoController menuEmpleadoView = (MenuEmpleadoController)switchScene("MenuEmpleadoView.fxml", 1200, 750);
            menuEmpleadoView.setOperation(op);
            menuEmpleadoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuFacturaView(){
        try{
            MenuFacturaController menuFacturaView = (MenuFacturaController)switchScene("MenuFacturaView.fxml", 1200, 750);
            menuFacturaView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

   
    
    public void menuProductoView(){
        try{
            MenuProductoController menuProductoView = (MenuProductoController)switchScene("MenuProductoView.fxml", 1200, 750);
            menuProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuPromocionView(){
        try{
            MenuPromocionController menuPromocionView = (MenuPromocionController)switchScene("MenuPromocionView.fxml", 1200, 750);
            menuPromocionView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formUsuarioView(){
        try{
            FormUsuarioController formUsuarioView = (FormUsuarioController)switchScene("FormUsuarioView.fxml", 500, 750);
            formUsuarioView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuLoginView(){
        try{
            LoginController MenuLoginView = (LoginController)switchScene("MenuLoginView.fxml", 500, 600);
            MenuLoginView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void menuEncargadoView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}