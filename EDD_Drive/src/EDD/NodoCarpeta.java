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
public class NodoCarpeta {
    public String nombre;
    public String nombreindice;
    public int x,y;   
    public NodoCarpeta sig,ant,sup,inf;
    public Archivos archivos;
    
    public NodoCarpeta(String nombre,int x,int y){
        this.nombre=nombre;
        this.x=x;
        this.y=y;
        archivos=new Archivos();
        this.nombreindice=nombre;
    }
    
    public NodoCarpeta(String nombre,int x,int y,String indice){
        this.nombre=nombre;
        this.x=x;
        this.y=y;
        archivos=new Archivos();
        this.nombreindice=indice;
    }
}
