package org.luisperez.dto;
import org.luisperez.model.Cliente;
/**
* FXML Controller class
*
* @author LUISSS
*/
public class ClienteDTO {
    private static ClienteDTO instance;
    private Cliente cliente;
    
    private ClienteDTO(){
    
    }
    
    public static ClienteDTO getClienteDTO(){
        if(instance == null){
            instance = new ClienteDTO();
        }
        return instance;
    }
   

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
}
