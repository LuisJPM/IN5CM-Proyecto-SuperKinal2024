package org.luisperez.model;
/**
* FXML Controller class
*
* @author LUISSS
*/
public class Encargado {
    private int encargadoId;
    
    public Encargado() {
    }

    public Encargado(int encargadoId) {
        this.encargadoId = encargadoId;
    }

    public Encargado(int cargoId, String nombreCargo, String descripcionCargo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(int encargadoId) {
        this.encargadoId = encargadoId;
    }

    @Override
    public String toString() {
        return "Id: " + encargadoId;
    }

    public int getCargoId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
