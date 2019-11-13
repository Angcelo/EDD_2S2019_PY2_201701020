/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

/**
 *
 * @author angel
 */
public class NodoBitacora {
    public String fecha;
    public String hora;
    public String operacion;
    public String usuario;
    public NodoBitacora sig;
    
    public NodoBitacora(String fecha,String hora,String operacion,String usuario){
        this.fecha=fecha;
        this.hora=hora;
        this.operacion=operacion;
        this.usuario=usuario;
        this.sig=null;
    }
}
