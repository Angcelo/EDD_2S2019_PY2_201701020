/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.io.IOException;
import java.util.Calendar;

/**
 *
 * @author angel
 */
public class Carpetas {
    public NodoCarpeta inicio;
    public Carpetas(){
        this.inicio=new NodoCarpeta("raiz",0,0);
        this.CrearCol("/");
        this.CrearFil("/");
    }
    
    private NodoCarpeta BuscarCol(String nombre){
        NodoCarpeta temp=this.inicio;
        while(temp!=null){
            if (temp.nombre.equals(nombre)) {
                System.out.println("col: "+temp.nombre+"( "+temp.x+","+temp.y+")");
                return temp;
            }
            temp=temp.sig;
        }
        System.out.println("no existe col");
        return null;
    }
    
    private NodoCarpeta BuscarFil(String nombre){
        NodoCarpeta temp=this.inicio;
        while(temp!=null){
            if (temp.nombre.equals(nombre)) {
                System.out.println("fil: "+temp.nombre+"( "+temp.x+","+temp.y+")");
                return temp;
            }
            temp=temp.sup;
        }
        System.out.println("no existe fil");
        return null;
    }
    
    private NodoCarpeta InsertarCol(NodoCarpeta nuevo,NodoCarpeta cabezaCol){
        NodoCarpeta temp=cabezaCol;
        boolean bandera=false;
        while(true){
            if (temp.x>nuevo.x) {
                bandera=true;
                break;
            }
            else if(temp.sig!=null){
                temp=temp.sig;
            }
            else{
                break;
            }
        }
        if (bandera) {
            nuevo.sig=temp;
            temp.ant.sig=nuevo;
            nuevo.ant=temp.ant;
            temp.ant=nuevo;
        }else{
            temp.sig=nuevo;
            nuevo.ant=temp;
        }
        return nuevo;
    }
    
    private NodoCarpeta InsertarFil(NodoCarpeta nuevo,NodoCarpeta cabezaCol){
        NodoCarpeta temp=cabezaCol;
        boolean bandera=false;
        while(true){
            if (temp.y>nuevo.y) {
                bandera=true;
                break;
            }
            else if(temp.sup!=null){
                temp=temp.sup;
            }
            else{
                break;
            }
        }
        if (bandera) {
            nuevo.sup=temp;
            temp.inf.sup=nuevo;
            nuevo.inf=temp.inf;
            temp.inf=nuevo;
        }else{
            temp.sup=nuevo;
            nuevo.inf=temp;
        }
        return nuevo;
    }
    
    private NodoCarpeta CrearCol(String nombre){
        NodoCarpeta temp=this.inicio;
        while(temp.sig!=null){
            temp=temp.sig;
        }
        NodoCarpeta nuevo=new NodoCarpeta(nombre,temp.x+1,0);
        temp.sig=nuevo;
        nuevo.ant=temp;
        System.out.println("creada col: "+nuevo.nombre+"("+nuevo.x+","+nuevo.y+")");
        return nuevo;
    }
    
    private NodoCarpeta CrearFil(String nombre){
        NodoCarpeta temp=this.inicio;
        while(temp.sup!=null){
            temp=temp.sup;
        }
        NodoCarpeta nuevo=new NodoCarpeta(nombre,0,temp.y+1);
        temp.sup=nuevo;
        nuevo.inf=temp;
        System.out.println("creada fil: "+nuevo.nombre+"("+nuevo.x+","+nuevo.y+")");
        return nuevo;
    }
    
    public void insertar(String principal,String secundaria){
        NodoCarpeta col=BuscarCol(principal);
        NodoCarpeta fil=BuscarFil(secundaria);  
        
        if (col!=null && fil!=null) {
            System.out.print("ya existe carpeta");
            return;
        }
        if (col!=null && fil==null){
            fil=CrearFil(secundaria);
            CrearCol(secundaria);
        }
        if (col==null) {
            System.out.print("Error no existe carpeta en la que se encuentra");
        }
        NodoCarpeta nuevo=new NodoCarpeta((principal+"/"+secundaria),col.x,fil.y);
        nuevo=InsertarCol(nuevo,fil);
        nuevo=InsertarFil(nuevo,col);
        System.out.println(nuevo.nombre+"("+nuevo.x+","+nuevo.y+")");
    }
    
    public String BuscarArchivos(String inicio){
        NodoCarpeta padre=this.BuscarCol(inicio);
        String carpetas="";
        if (padre.sup!=null) {
            padre=padre.sup;
            while(padre!=null){
                carpetas+=padre.nombre+",";
                padre=padre.sup;
            }
        }
        System.out.println("esta es la lista de carpetas: "+carpetas);
        return carpetas;
    }
    
    public void InsertarArchivo(String inicio,String nombre,String extension,String contenido) throws IOException{
        NodoCarpeta padre=this.BuscarCol(inicio);
        Calendar calendario = Calendar.getInstance();
        int hora =calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);
        String tiempo=hora + ":" + minutos + ":" + segundos;
        padre.archivos.insertar1(nombre,extension,contenido,tiempo);
    }
    
    public String SearchArchivo(String inicio){
        NodoCarpeta padre=this.BuscarCol(inicio);
        return padre.archivos.ListaArchivos();
    }    
    
    public String[] contenidoarchivo(String inicio,String nombreextension){
        NodoCarpeta padre=this.BuscarCol(inicio);
        String[] nomext=nombreextension.split("\\.");
        String[] archivo=padre.archivos.BuscarArchivo(nomext[0]);
        return archivo;
    }
    
    public void modificarcarpeta(String inicio,String nuevo){
        NodoCarpeta col=this.BuscarCol(inicio);
        NodoCarpeta fil=this.BuscarFil(inicio);
        col.nombre=nuevo;
        fil.nombre=nuevo;
        col=col.sup;
        while(col!=null){
            col.nombre=col.nombre.replace(inicio, nuevo);
            col=col.sup;
        }
        fil=fil.sig;
        while(fil!=null){
            fil.nombre=fil.nombre.replace(inicio, nuevo);
            fil=fil.sig;
        }
    }
    
    public void eliminar(String inicio){
        NodoCarpeta col=this.BuscarCol(inicio);
        NodoCarpeta fil=this.BuscarFil(inicio); 
        NodoCarpeta temcol=col.sup;
        NodoCarpeta temfil=fil.sig;
        while(temcol!=null){
            if (temcol.sig!=null) {
                temcol.sig.ant=temcol.ant;
            }
            temcol.ant.sig=temcol.sig;
            temcol=temcol.sup;
        }
        while(temfil!=null){
            if (temfil.sup!=null) {
                temfil.sup.inf=temfil.inf;
            }
            temfil.inf.sup=temfil.sup;
            temfil=temfil.sig;
        }
        if (col.sig!=null) {
            col.sig.ant=col.ant;
        }
        col.ant.sig=col.sig;
        if (fil.sup!=null) {
            fil.sup.inf=fil.inf;
        }
        fil.inf.sup=fil.sup;
    }
}
