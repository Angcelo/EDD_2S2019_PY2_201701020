/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author angel
 */
public class Archivos {
    public NodoArchivo Raiz;
    public boolean nivel=false;
    public Archivos(){
        Raiz=null; 
    }
    
    public void insertar1(String nombre,String extension,String contenido,String fecha) throws IOException{
        NodoArchivo nuevo=new NodoArchivo(nombre,extension,contenido,fecha);
        if (this.Raiz==null) {
            this.Raiz=nuevo;
        }
        else{
            if (this.Raiz.nombre.compareTo(nombre)>0) {
                if (this.Raiz.izq==null) {
                    this.Raiz.izq=nuevo;
                    this.Raiz.equilibrio--;
                }else{
                    NodoArchivo col=insertar2(nuevo,this.Raiz.izq);
                    if (this.nivel) {
                        this.Raiz.equilibrio--;
                    }
                    if (col != null) {
                        this.Raiz.izq=col;
                    }
                    if (this.Raiz.equilibrio<-1 && this.Raiz.izq.equilibrio<0) {                        
                        this.Raiz=ESI(this.Raiz);
                        this.nivel=false;
                    }
                    else if(this.Raiz.equilibrio<-1){
                        this.Raiz=EDI(this.Raiz);
                        this.nivel=false;
                    }
                }
            }
            else if(this.Raiz.nombre.compareTo(nombre)<0){
                if (this.Raiz.der==null) {
                    this.Raiz.der=nuevo;
                    this.Raiz.equilibrio++;
                }else{
                    NodoArchivo col=insertar2(nuevo,this.Raiz.der);
                    if (this.nivel) {
                        this.Raiz.equilibrio++;
                    }
                    if (col != null) {
                        this.Raiz.der=col;
                    }
                    if(this.Raiz.equilibrio>1 && this.Raiz.der.equilibrio>0){
                        this.Raiz=ESD(this.Raiz);
                        this.nivel=false;

                    }else if(this.Raiz.equilibrio>1){
                        this.Raiz=EDD(this.Raiz);
                        this.nivel=false;
                    }
                }
            }
            else{
                System.out.println("Palabra ya existente");
            }
        }
        nivel=false;
    }
    
    public NodoArchivo insertar2(NodoArchivo nuevo,NodoArchivo raiz) throws IOException{
        if (raiz.nombre.compareTo(nuevo.nombre)>0) {
            if (raiz.izq==null) {
                raiz.izq=nuevo;
                raiz.equilibrio--;
                if (raiz.der==null) {
                    nivel=true;
                }
            }else{
                NodoArchivo col = insertar2(nuevo,raiz.izq);  
                if (this.nivel) {
                    raiz.equilibrio--;
                }
                if (col != null) {
                    raiz.izq=col;
                    return null;
                }         
                if (raiz.equilibrio<-1 && raiz.izq.equilibrio<0) {
                    this.nivel=false;
                    return ESI(raiz);
                }
                else if(raiz.equilibrio<-1){
                    this.nivel=false;
                    return EDI(raiz);
                }
            }
        }
        else if(raiz.nombre.compareTo(nuevo.nombre)<0){
            if (raiz.der==null) {
                raiz.der=nuevo;
                raiz.equilibrio++;
                if (raiz.izq==null) {
                    nivel=true;
                }
            }else{
                NodoArchivo col=insertar2(nuevo,raiz.der);
                if (nivel) {
                    raiz.equilibrio++;
                }
                if (col!=null) {
                    raiz.der=col;
                    return null;
                }
                if(raiz.equilibrio>1 && raiz.der.equilibrio>0){
                    this.nivel=false;
                    return ESD(raiz);
                }
                else if(raiz.equilibrio>1){
                    this.nivel=false;
                    return EDD(raiz);
                }  
            }
        }
        else{
            System.out.println("Palabra ya existente");
        }
        return null;
    }
    
    public NodoArchivo ESI(NodoArchivo raiz){
        System.out.println("equilibrio simple izquierda");
        NodoArchivo n=raiz;
        NodoArchivo n1=raiz.izq;
        n.izq=n1.der;
        n1.der=n;
        n=n1;
        n.equilibrio=0;
        n.izq.equilibrio=0;
        return n;
    }
    
    public NodoArchivo EDI(NodoArchivo raiz){
        System.out.println("equilibrio doble izquierda");
        NodoArchivo n=raiz;
        NodoArchivo n1=raiz.izq;
        NodoArchivo n2=raiz.izq.der;
        int n2eq=n2.equilibrio;

        n1.der=n2.izq;
        n2.izq=n1;
        n.izq=n2.der;
        n2.der=n;
        n=n2;
        
        if (n2eq==-1) {
            n.der.equilibrio=1;                    
        }else{        
            n.der.equilibrio=0;   
        }
        if (n2eq==1) {        
            n.izq.equilibrio=-1;        
        }else{
            n.izq.equilibrio=0;           
        }
        n.equilibrio=0;                    
               
        return n;
    }
    
    public NodoArchivo ESD(NodoArchivo raiz){
        System.out.println("equilibrio simple derecha");
        NodoArchivo n=raiz;
        NodoArchivo n1=raiz.der;

        n.der=n1.izq;
        n1.izq=n;                   
        n=n1;           
        n.equilibrio=0;            
        n.izq.equilibrio=0;         
        return n;
    }
    
    public NodoArchivo EDD(NodoArchivo raiz){
        System.out.println("equilibrio doble derecha 2");
        NodoArchivo n=raiz;
        NodoArchivo n1=raiz.der;
        NodoArchivo n2=raiz.der.izq;
        int n2eq=n2.equilibrio;

        n1.izq=n2.der;
        n2.der=n1;
        n.der=n2.izq;
        n2.izq=n;
        n=n2;

        if (n2eq==-1) {
            n.der.equilibrio=-1;            
        }else{
            n.der.equilibrio=0;           
        }
        if (n2eq==1) {
            n.izq.equilibrio=1;            
        }else{
            n.izq.equilibrio=0;            
        }
        n.equilibrio=0;
        return n;
    }
    
    public String ListaArchivos(){
        String archivo="";
        if (this.Raiz!=null) {
            if (this.Raiz.izq!=null) {  
                archivo+=this.listaArchivos2(this.Raiz.izq);
            }
            archivo+=this.Raiz.nombre+"."+this.Raiz.extension+",";
            if (this.Raiz.der!=null) {
                archivo+=this.listaArchivos2(this.Raiz.der);
            }    
        }
        return archivo;
    }
    
    public String listaArchivos2(NodoArchivo actual){
        String archivo="";
        if (actual.izq!=null) {  
            archivo+=this.listaArchivos2(actual.izq);
        }
        archivo+=actual.nombre+"."+actual.extension+",";
        if (actual.der!=null) {
            archivo+=this.listaArchivos2(actual.der);
        }
        return archivo;
    }
    
    public String[] BuscarArchivo(String nombre){
        String[] archivo=new String[4];
        if (this.Raiz.nombre.equals(nombre)) {
            archivo[0]=this.Raiz.nombre;
            archivo[1]=this.Raiz.extension;
            archivo[2]=this.Raiz.contenido;
            archivo[3]=this.Raiz.fecha;
        }else{
            if (this.Raiz.nombre.compareTo(nombre)>0){
                if (this.Raiz.izq!=null) {
                    BuscarArchivo2(nombre,this.Raiz.izq);
                }
            }else if(this.Raiz.nombre.compareTo(nombre)<0){
                if (this.Raiz.der!=null){
                    BuscarArchivo2(nombre,this.Raiz.der);
                }
            }
        }
        return archivo;
    }
    
    public String[] BuscarArchivo2(String nombre,NodoArchivo actual){
        String[] archivo=new String[4];
        if (actual.nombre.equals(nombre)) {
            archivo[0]=actual.nombre;
            archivo[1]=actual.extension;
            archivo[2]=actual.contenido;
            archivo[3]=actual.fecha;
        }else{
            if (actual.nombre.compareTo(nombre)>0){
                if (actual.izq!=null) {
                    archivo=BuscarArchivo2(nombre,actual.izq);
                }
            }else if(actual.nombre.compareTo(nombre)<0){
                if (actual.der!=null){
                    archivo=BuscarArchivo2(nombre,actual.der);
                }
            }
        }
        return archivo;
    }
    
    public void Eliminar(String nombre){
        String nuevonombre=Reemplazo(eliminar,false);
        if (this.nivel) {
            if (nuevonombre.equals("")) {
                NodoArchivo eliminar=BuscaraEliminar(nombre,true);
            }else{
                
            }
        }
        this.nivel=false;
    }
    
    public NodoArchivo BuscaraEliminar(String nombre,boolean equilibrar){
        NodoArchivo Aeliminar=null;
        if (this.Raiz.nombre.equals(nombre)) {
            return this.Raiz;
        }else{
            if (this.Raiz.nombre.compareTo(nombre)>0){
                if (this.Raiz.izq!=null) {
                    Aeliminar=BuscaraEliminar(nombre,this.Raiz.izq,equilibrar);
                }
            }else if(this.Raiz.nombre.compareTo(nombre)<0){
                if (this.Raiz.der!=null){
                    Aeliminar=BuscaraEliminar(nombre,this.Raiz.der,equilibrar);
                }
            }
        }  
        return Aeliminar;
    }
    
    public NodoArchivo BuscaraEliminar(String nombre,NodoArchivo actual,boolean equilibrar){
        NodoArchivo Aeliminar=null;
        if (actual.nombre.equals(nombre)) {
            return actual;
        }else{
            if (actual.nombre.compareTo(nombre)>0){
                if (actual.izq!=null) {
                    Aeliminar=BuscaraEliminar(nombre,actual.izq,equilibrar);
                }
            }else if(actual.nombre.compareTo(nombre)<0){
                if (actual.der!=null){
                    Aeliminar=BuscaraEliminar(nombre,actual.der,equilibrar);
                }
            }
        }  
        return Aeliminar;
    }
    
    public String Reemplazo(NodoArchivo actual){
        NodoArchivo Aeliminar=null;
        if (actual.izq==null && actual.der==null) {
            actual=null;
            this.nivel=true;
            return "";
        }else if(actual.izq==null && actual.der!=null){
            if (actual.der.izq==null && actual.der.der==null) {
                actual.nombre=actual.der.nombre;
                actual.extension=actual.der.extension;
                actual.fecha=actual.der.fecha;
                actual.equilibrio=0;
                this.nivel=true;
                actual.der=null;
                return actual.nombre;
            }
        }else if(actual.izq!=null){
            if (actual.izq.izq==null && actual.izq.der==null) {
                actual.nombre=actual.izq.nombre;
                actual.extension=actual.izq.extension;
                actual.fecha=actual.izq.fecha;
                if (actual.der==null) {
                    actual.equilibrio=0;
                    this.nivel=true;
                }else{
                    actual.equilibrio=1;
                }
                actual.izq=null;
                return actual.nombre;
            }
        }
        Aeliminar=Reemplazoizq(actual.izq);  
        if (Aeliminar==null) {
            Aeliminar=Reemplazoder(actual.der);
            if (Aeliminar==null) {
                actual.izq.der=actual.izq;
                actual=actual.izq;
                actual.equilibrio=1;
                this.nivel=true;
            }else{
                actual.nombre=Aeliminar.nombre;
                actual.extension=Aeliminar.extension;
                actual.fecha=Aeliminar.fecha;
                if (nivel) {
                    actual.equilibrio--;
                }
            }
        }else{
            actual.nombre=Aeliminar.nombre;
            actual.extension=Aeliminar.extension;
            actual.fecha=Aeliminar.fecha;
            if (this.nivel) {
                actual.equilibrio++;
            }
        }
        return actual.nombre;
    }
    
    public NodoArchivo Reemplazoder(NodoArchivo actual){
        NodoArchivo Aeliminar;
        if (actual.der!=null) {
            Aeliminar=Reemplazoizq(actual.der);
            if(Aeliminar!=null){
                if(this.nivel){
                    actual.equilibrio--;
                }
                return Aeliminar;
            }
        }
        if (actual.izq==null) {
            return null;
        }else{
            if (actual.izq.der==null && actual.izq.izq==null) {
                if (actual.der==null) {
                    actual.equilibrio=0;
                    this.nivel=true;
                }else{
                    actual.equilibrio=1;
                }
                return actual.izq;
            }else{
                Aeliminar=Reemplazoizq(actual.izq);
            }
        }
        return Aeliminar;
    }
    
    public NodoArchivo Reemplazoizq(NodoArchivo actual){
        NodoArchivo Aeliminar;
        if (actual.izq!=null) {
            Aeliminar=Reemplazoizq(actual.izq);
            if(Aeliminar!=null){
                if(this.nivel){
                    actual.equilibrio++;
                }
                return Aeliminar;
            }
        }
        if (actual.der==null) {
            return null;
        }else{
            if (actual.der.der==null && actual.der.izq==null) {
                if (actual.izq==null) {
                    actual.equilibrio=0;
                    this.nivel=true;
                }else{
                    actual.equilibrio=-1;
                }
                return actual.der;
            }else{
                Aeliminar=Reemplazoizq(actual.der);
            }
        }
        return Aeliminar;
    }
    
    public void graficar() throws IOException{
        File file=new File("arbol.dot");
        BufferedWriter bw;
        bw=new BufferedWriter(new FileWriter(file));
        String archivo;
        archivo="digraph pila{ \n";
        archivo+="node [shape=\"record\"]; \n";
        if (this.Raiz.izq!=null) {
            archivo+=this.Raiz.nombre+"->"+this.Raiz.izq.nombre+"\n";    
            archivo+=graficar1(this.Raiz.izq);
        }
        if (this.Raiz.der!=null) {
            archivo+=this.Raiz.nombre+"->"+this.Raiz.der.nombre+"\n";
            archivo+=graficar1(this.Raiz.der);
        }
        archivo+="}";
        bw.write(archivo);
        bw.close();
        File comando=new File("comando.bat");
        BufferedWriter bw2;
        bw2=new BufferedWriter(new FileWriter(comando));
        bw2.write("dot -Tjpg arbol.dot -o imagena.jpg");
        bw2.newLine();
        bw2.write("pause");
        bw2.close();
        Desktop.getDesktop().open(comando);
        File imagen=new File("imagena.jpg");
        try{
           Desktop.getDesktop().open(imagen);
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public String graficar1(NodoArchivo actual){
        String archivo="";
        if (actual.izq!=null) {
            archivo+=actual.nombre+"->"+actual.izq.nombre+"\n";
            archivo+=graficar1(actual.izq);
        }
        if (actual.der!=null) {
            archivo+=actual.nombre+"->"+actual.der.nombre+"\n";
            archivo+=graficar1(actual.der);
        }
        return archivo;
    }
}
