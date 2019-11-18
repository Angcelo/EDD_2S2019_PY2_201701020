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
public class NodoUsuario {
    public String usuario;
    public String password;
    public String documentos;
    public Carpetas carpetas;
    public boolean Admin;
    
    public NodoUsuario(String usuario,String password){
        this.usuario=usuario;
        this.password=password;
        carpetas=new Carpetas();
        this.Admin=false;
    }
    
    public NodoUsuario(String usuario,String password,boolean Admin){
        this.usuario=usuario;
        this.password=password;
        carpetas=new Carpetas();
        this.Admin=Admin;
    }
}
