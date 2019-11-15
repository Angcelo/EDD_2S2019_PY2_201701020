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
public class NodoArchivo {
    public String nombre,extension,contenido,fecha;
    public int pesoizq,pesoder;
    public NodoArchivo izq,der;
    public NodoArchivo(String nombre,String extension,String contenido,String fecha){
        this.nombre=nombre;
        this.extension=extension;
        this.contenido=contenido;
        this.fecha=fecha;
        this.pesoizq=0;
        this.pesoder=0;
        this.izq=null;
        this.der=null;
    }
}
