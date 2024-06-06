package org.luisperez.dto;
import org.luisperez.model.Distribuidor;
/**
* FXML Controller class
*
* @author LUISSS
*/
public class DistribuidorDTO {
    private static DistribuidorDTO instance;
    private Distribuidor distribuidor;
    
    private DistribuidorDTO(){
    
    }
    
    public static DistribuidorDTO getDistribuidorDTO(){
        if(instance == null){
            instance = new DistribuidorDTO();
        }
        return instance;
    }

    public Distribuidor getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
    }
}
