/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author angel
 */
public class Archivos {
    public NodoArchivo Raiz;
    private boolean eliminar=false;
    public Archivos(){
        Raiz=null; 
    }
    
    public void insertar1(String nombre,String extension,String contenido,String fecha) throws IOException{
        NodoArchivo nuevo=new NodoArchivo(nombre,extension,contenido,fecha);
        if (this.Raiz==null) {
            this.Raiz=nuevo;
        }
        else{
            if ((this.Raiz.nombre+"."+this.Raiz.extension).compareTo(nombre+"."+extension)>0) {
                if (this.Raiz.izq==null) {
                    this.Raiz.izq=nuevo;
                    this.Raiz.pesoizq++;
                }else{
                    int eant=this.Raiz.izq.pesoder-this.Raiz.izq.pesoizq;
                    NodoArchivo temp=this.insertar2(nuevo,this.Raiz.izq);
                    int enuevo=this.Raiz.izq.pesoder-this.Raiz.izq.pesoizq;
                    if (temp!=null) {
                        this.Raiz.izq=temp;
                    }
                    if (eant!=enuevo && enuevo!=0){
                        this.Raiz.pesoizq++;
                    }
                    if ((this.Raiz.pesoder-this.Raiz.pesoizq)<-1 && (this.Raiz.izq.pesoder-this.Raiz.izq.pesoizq)<0) {                        
                        this.Raiz=ESI(this.Raiz);
                    }
                    else if((this.Raiz.pesoder-this.Raiz.pesoizq)<-1){
                        this.Raiz=EDI(this.Raiz);
                    }
                }
            }
            else if((this.Raiz.nombre+"."+this.Raiz.extension).compareTo(nombre+"."+extension)<0){
                if (this.Raiz.der==null) {
                    this.Raiz.der=nuevo;
                    this.Raiz.pesoder++;
                }else{    
                    int eant=this.Raiz.der.pesoder-this.Raiz.der.pesoizq;
                    NodoArchivo temp=this.insertar2(nuevo,this.Raiz.der);
                    int enuevo=this.Raiz.der.pesoder-this.Raiz.der.pesoizq;
                    if (temp!=null) {
                        this.Raiz.der=temp;
                    }
                    if (eant!=enuevo && enuevo!=0){
                        this.Raiz.pesoder++;
                    }
                    if ((this.Raiz.pesoder-this.Raiz.pesoizq)>1 && (this.Raiz.der.pesoder-this.Raiz.der.pesoizq)>0) {                        
                        this.Raiz=ESD(this.Raiz);
                    }
                    else if((this.Raiz.pesoder-this.Raiz.pesoizq)>1){
                        this.Raiz=EDD(this.Raiz);
                    }
                }
            }
            else{
                System.out.println("Palabra ya existente");
                JOptionPane.showConfirmDialog(null, "Desea Sobreescribir", "Archivo existente", JOptionPane.YES_NO_OPTION);
            }
        }
    }
    
    public NodoArchivo insertar2(NodoArchivo nuevo,NodoArchivo raiz) throws IOException{
        if ((raiz.nombre+"."+raiz.extension).compareTo(nuevo.nombre+"."+nuevo.extension)>0) {
            if (raiz.izq==null) {
                raiz.izq=nuevo;
                raiz.pesoizq++;
            }else{
                int eant=raiz.izq.pesoder-raiz.izq.pesoizq;
                NodoArchivo temp=insertar2(nuevo,raiz.izq);  
                int enuevo=raiz.izq.pesoder-raiz.izq.pesoizq;
                if (temp!=null) {
                    raiz.izq=temp;
                }
                if (eant!=enuevo && enuevo!=0){
                        raiz.pesoizq++;
                }
                if ((raiz.pesoder-raiz.pesoizq)<-1 && (raiz.izq.pesoder-raiz.izq.pesoizq)<0) {                        
                    raiz=ESI(raiz);
                    return raiz;
                }
                else if((raiz.pesoder-raiz.pesoizq)<-1){
                    raiz=EDI(raiz);
                    return raiz;
                }
            }
        }
        else if((raiz.nombre+"."+raiz.extension).compareTo(nuevo.nombre+"."+nuevo.extension)<0){
            if (raiz.der==null) {
                raiz.der=nuevo;
                raiz.pesoder++;
            }else{
                int eant=raiz.der.pesoder-raiz.der.pesoizq;
                NodoArchivo temp=this.insertar2(nuevo,raiz.der);
                int enuevo=raiz.der.pesoder-raiz.der.pesoizq;
                if (temp!=null) {
                    raiz.der=temp;
                }
                if (eant!=enuevo && enuevo!=0){
                    raiz.pesoder++;
                }
                if ((raiz.pesoder-raiz.pesoizq)>1 && (raiz.der.pesoder-raiz.der.pesoizq)>0) {                        
                    raiz=ESD(raiz);
                    return raiz;
                }
                else if((raiz.pesoder-raiz.pesoizq)>1){
                    raiz=EDD(raiz);
                    return raiz;
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
        int ponder=n.pesoder;
        int ponizq=n.pesoizq-2;
        int pon1der=n1.pesoder+1;
        int pon1izq=n1.pesoizq;
        
        n.izq=n1.der;
        n1.der=n;
        n=n1;
        
        n.pesoder=pon1der;
        n.pesoizq=pon1izq;
        n.izq.pesoder=ponder;
        n.izq.pesoizq=ponizq;
        
        return n;
    }
    
    public NodoArchivo EDI(NodoArchivo raiz){
        System.out.println("equilibrio doble izquierda");
        NodoArchivo n=raiz;
        NodoArchivo n1=raiz.izq;
        NodoArchivo n2=raiz.izq.der;
        int equilibrio=n2.pesoder-n2.pesoizq;
        int ponder=0,ponizq=0,pon1der=0,pon1izq=0,pon2der=0,pon2izq=0;
        switch (equilibrio) {
            case 1:
                ponizq=n.pesoizq-2;
                pon1der=n1.pesoder-2;
                pon2der=n2.pesoder+1;
                pon2izq=n2.pesoizq+2;
                break;
            case -1:
                ponizq=n.pesoizq-3;
                pon1der=n1.pesoder-1;
                pon2der=n2.pesoder+2;
                pon2izq=n2.pesoizq+1;
                break;
            case 0:
                ponizq=n.pesoizq-2;
                pon1der=n1.pesoder-1;
                pon2der=n2.pesoder+1;
                pon2izq=n2.pesoizq+1;
                break;
            default:
                break;
        }
        ponder=n.pesoder;
        pon1izq=n1.pesoizq;
        
        n1.der=n2.izq;
        n2.izq=n1;
        n.izq=n2.der;
        n2.der=n;
        n=n2;     
        
        n.pesoder=pon2der;
        n.pesoizq=pon2izq;
        n.izq.pesoder=pon1der;
        n.izq.pesoizq=pon1izq;
        n.der.pesoder=ponder;
        n.der.pesoizq=ponizq;
        return n;
    }
    
    public NodoArchivo ESD(NodoArchivo raiz){
        System.out.println("equilibrio simple derecha");
        NodoArchivo n=raiz;
        NodoArchivo n1=raiz.der;
        int ponder=n.pesoder-2;
        int ponizq=n.pesoizq;
        int pon1der=n1.pesoder;
        int pon1izq=n1.pesoizq+1;
        
        n.der=n1.izq;
        n1.izq=n;                   
        n=n1;       
        
        n.pesoder=pon1der;
        n.pesoizq=pon1izq;
        n.izq.pesoder=ponder;
        n.izq.pesoizq=ponizq;
        return n;
    }
    
    public NodoArchivo EDD(NodoArchivo raiz){
        System.out.println("equilibrio doble derecha 2");
        NodoArchivo n=raiz;
        NodoArchivo n1=raiz.der;
        NodoArchivo n2=raiz.der.izq;
        int equilibrio=n2.pesoder-n2.pesoizq;
        int ponder=0,ponizq=0,pon1der=0,pon1izq=0,pon2der=0,pon2izq=0;
        switch (equilibrio) {
            case -1:
                ponder=n.pesoder-2;
                pon1izq=n1.pesoizq-2;
                pon2der=n2.pesoder+2;
                pon2izq=n2.pesoizq+1;
                break;
            case 1:
                ponder=n.pesoder-3;
                pon1izq=n1.pesoizq-1;
                pon2der=n2.pesoder+1;
                pon2izq=n2.pesoizq+2;
                break;
            case 0:
                ponder=n.pesoder-2;
                pon1izq=n1.pesoizq-1;
                pon2der=n2.pesoder+1;
                pon2izq=n2.pesoizq+1;
                break;
            default:
                break;
        }
        ponizq=n.pesoizq;
        pon1der=n1.pesoder;
        
        
        n1.izq=n2.der;
        n2.der=n1;
        n.der=n2.izq;
        n2.izq=n;
        n=n2;
        
        n.pesoder=pon2der;
        n.pesoizq=pon2izq;
        n.der.pesoder=pon1der;
        n.der.pesoizq=pon1izq;
        n.izq.pesoder=ponder;
        n.izq.pesoizq=ponizq;
        
        return n;
    }
    
    public NodoArchivo Archivo(NodoArchivo actual,String nombre,String extension){
        NodoArchivo retornar=null;
        if (actual.nombre.equals(nombre) && actual.extension.equals(extension)) {
            System.out.println("Archivo encotrado");
            retornar=actual;
        }else{
            if ((actual.nombre+"."+actual.extension).compareTo(nombre+"."+extension)>0 && actual.izq!=null){
                retornar = Archivo(actual.izq,nombre,extension);
            }else if((actual.nombre+"."+actual.extension).compareTo(nombre+"."+extension)<0 && actual.der!=null){
                retornar = Archivo(actual.der,nombre,extension);
            }
        }
        return retornar;
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
    
    public String[] BuscarArchivo(String nombre,String extension){
        String[] archivo=new String[4];
        if (this.Raiz.nombre.equals(nombre) && this.Raiz.extension.equals(extension)) {
            System.out.println("Encontrado");
            archivo[0]=this.Raiz.nombre;
            archivo[1]=this.Raiz.extension;
            archivo[2]=this.Raiz.contenido;
            archivo[3]=this.Raiz.fecha;
        }else{
            if ((this.Raiz.nombre+"."+this.Raiz.extension).compareTo(nombre+"."+extension)>0){
                if (this.Raiz.izq!=null) {
                    archivo=BuscarArchivo2(nombre,extension,this.Raiz.izq);
                }
            }else if((this.Raiz.nombre+"."+this.Raiz.extension).compareTo(nombre+"."+extension)<0){
                if (this.Raiz.der!=null){
                    archivo=BuscarArchivo2(nombre,extension,this.Raiz.der);
                }
            }else{
                System.out.println("Fallo al encontrarlo");
            }
        }
        return archivo;
    }
    
    public String[] BuscarArchivo2(String nombre,String extension,NodoArchivo actual){
        String[] archivo=new String[4];
        if (actual.nombre.equals(nombre) && actual.extension.equals(extension)) {
            System.out.println("Encontrado");
            archivo[0]=actual.nombre;
            archivo[1]=actual.extension;
            archivo[2]=actual.contenido;
            archivo[3]=actual.fecha;
        }else{
            if ((actual.nombre+"."+actual.extension).compareTo(nombre+"."+extension)>0){
                if (actual.izq!=null) {
                    archivo=BuscarArchivo2(nombre,extension,actual.izq);
                }
            }else if((actual.nombre+"."+actual.extension).compareTo(nombre+"."+extension)<0){
                if (actual.der!=null){
                    archivo=BuscarArchivo2(nombre,extension,actual.der);
                }
            }else{
                System.out.println("Fallo al encontrarlo");
            }
        }
        return archivo;
    }
    
    public void ModContenidoArchivo(String nombre,String extension,String contenidonuevo){
        NodoArchivo modificar=Archivo(this.Raiz,nombre,extension);
        try{
            modificar.contenido=contenidonuevo;
            modificar.extension=extension;
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void BuscaraEliminar(String nombre,String extension){
        if (this.Raiz.nombre.equals(nombre) && this.Raiz.extension.equals(extension)) {
            System.out.println("Encontrado");
            if (this.Raiz.der==null && this.Raiz.izq==null) {
                System.out.println("Caso 0");
                this.Raiz=null;
            }else if (this.Raiz.izq!=null && this.Raiz.der==null && this.Raiz.izq.der==null && this.Raiz.izq.izq==null) {
                System.out.println("Caso 1");
                this.Raiz=this.Raiz.izq;
            }else if (this.Raiz.der!=null && this.Raiz.izq==null && this.Raiz.der.der==null && this.Raiz.der.izq==null) {
                System.out.println("Caso 2");
                this.Raiz=this.Raiz.der;
            }else if(this.Raiz.izq!=null && this.Raiz.izq.der==null && this.Raiz.izq.izq!=null){
                System.out.println("Caso 3");
                this.Raiz.izq.der=this.Raiz.der;
                this.Raiz.izq.pesoder=this.Raiz.pesoder;
                this.Raiz=this.Raiz.izq;
            }else if(this.Raiz.der!=null && this.Raiz.der.izq==null && this.Raiz.der.der!=null){
                System.out.println("Caso 4");
                this.Raiz.der.izq=this.Raiz.izq;
                this.Raiz.der.pesoizq=this.Raiz.pesoizq;
                this.Raiz=this.Raiz.der;
            }else{
                NodoArchivo Reemplazo=null;
                if(this.Raiz.izq!=null && Reemplazo==null){
                    Reemplazo=Reemplazoizq(this.Raiz.izq);
                    if (Reemplazo!=null) {
                        System.out.println("Caso 5");
                        this.Raiz.nombre=Reemplazo.nombre;
                        this.Raiz.extension=Reemplazo.extension;
                        this.Raiz.contenido=Reemplazo.contenido;
                        this.Raiz.fecha=Reemplazo.fecha;
                    }
                }
                if (this.Raiz.der!=null && Reemplazo==null){
                    Reemplazo=Reemplazoder(this.Raiz.der);
                    if (Reemplazo!=null) {
                        System.out.println("Caso 6");
                        this.Raiz.nombre=Reemplazo.nombre;
                        this.Raiz.extension=Reemplazo.extension;
                        this.Raiz.contenido=Reemplazo.contenido;
                        this.Raiz.fecha=Reemplazo.fecha;
                    }
                }
            }
        }else{
            if ((this.Raiz.nombre+"."+this.Raiz.extension).compareTo(nombre+"."+extension)>0){
                if (this.Raiz.izq!=null) {
                    int eant=this.Raiz.izq.pesoder-this.Raiz.izq.pesoizq;
                    NodoArchivo Nodo=BuscaraEliminar(nombre,extension,this.Raiz.izq);
                    int enuevo=this.Raiz.izq.pesoder-this.Raiz.izq.pesoizq;
                    if (this.eliminar) {
                        this.Raiz.izq=Nodo;
                        this.Raiz.pesoizq--;
                        this.eliminar=false;
                    }
                    if (eant!=enuevo && enuevo==0) {
                            this.Raiz.pesoizq--;
                    }                    
                }
            }else if((this.Raiz.nombre+"."+this.Raiz.extension).compareTo(nombre+"."+extension)<0){
                if (this.Raiz.der!=null){
                    int eant=this.Raiz.der.pesoder-this.Raiz.der.pesoizq;
                    NodoArchivo Nodo=BuscaraEliminar(nombre,extension,this.Raiz.der);
                    int enuevo=this.Raiz.der.pesoder-this.Raiz.der.pesoizq;
                    if (this.eliminar) {
                        this.Raiz.der=Nodo;
                        this.Raiz.pesoder--;
                        this.eliminar=false;
                    }else{
                        if (eant!=enuevo && enuevo==0) {
                            this.Raiz.pesoder--;
                        }
                    }
                }
            }
        }  
        this.eliminar=false;
    }
    
    public NodoArchivo BuscaraEliminar(String nombre,String extension,NodoArchivo actual){
        NodoArchivo Nodo=null;
        if (actual.nombre.equals(nombre) && actual.extension.equals(extension)) {
            System.out.println("Encontrado");
            this.eliminar=true;
            if (actual.der==null && actual.izq==null) {
                System.out.println("Caso 0");
                actual=null;
            }else if (actual.izq!=null && actual.der==null && actual.izq.der==null && actual.izq.izq==null) {
                System.out.println("Caso 1");
                actual=actual.izq;
            }else if (actual.der!=null && actual.izq==null && actual.der.der==null && actual.der.izq==null) {
                System.out.println("Caso 2");
                actual=actual.der;
            }else if(actual.izq!=null && actual.izq.der==null){
                System.out.println("Caso 3");
                actual.izq.der=actual.der;
                actual.izq.pesoder=actual.pesoder;
                actual=actual.izq;
            }else if(actual.der.izq==null){
                System.out.println("Caso 4");
                actual.der.izq=actual.izq;
                actual.der.pesoizq=actual.pesoizq;
                actual=actual.der;
            }else{
                NodoArchivo Reemplazo=null;
                if(actual.izq!=null && Reemplazo==null){
                    Reemplazo=Reemplazoizq(actual.izq);
                    if (Reemplazo!=null) {
                        System.out.println("Caso 5");
                        actual.nombre=Reemplazo.nombre;
                        actual.extension=Reemplazo.extension;
                        actual.contenido=Reemplazo.contenido;
                        actual.fecha=Reemplazo.fecha;
                    }
                }
                if (actual.der!=null && Reemplazo==null){
                    Reemplazo=Reemplazoder(actual.der);
                    if (Reemplazo!=null) {
                        System.out.println("Caso 6");
                        actual.nombre=Reemplazo.nombre;
                        actual.extension=Reemplazo.extension;
                        actual.contenido=Reemplazo.contenido;
                        actual.fecha=Reemplazo.fecha;
                    }
                }
            }
            Nodo=actual;
        }else{
            if ((actual.nombre+"."+actual.extension).compareTo(nombre+"."+extension)>0){
                if (actual.izq!=null) {
                    int eant=actual.izq.pesoder-actual.izq.pesoizq;
                    Nodo=BuscaraEliminar(nombre,extension,actual.izq);
                    int enuevo=actual.izq.pesoder-actual.izq.pesoizq;
                    if (this.eliminar) {
                        actual.izq=Nodo;
                        actual.pesoizq--;
                        this.eliminar=false;
                    }else{
                        if (eant!=enuevo && enuevo==0) {
                            actual.pesoizq--;
                        }
                    }
                }
            }else if((actual.nombre+"."+actual.extension).compareTo(nombre+"."+extension)<0){
                if (actual.der!=null){
                    int eant=actual.der.pesoder-actual.der.pesoizq;
                    Nodo=BuscaraEliminar(nombre,extension,actual.der);
                    int enuevo=actual.der.pesoder-actual.der.pesoizq;
                    if (this.eliminar) {
                        actual.der=Nodo;
                        actual.pesoder--;
                        this.eliminar=false;
                    }else{
                        if (eant!=enuevo && enuevo==0) {
                            actual.pesoder--;
                        }
                    }
                }
            }
        }  
        return Nodo;
    }
    
    public NodoArchivo Reemplazoder(NodoArchivo actual){
        NodoArchivo Aeliminar;
        if (actual.der!=null) {
            Aeliminar=Reemplazoizq(actual.der);
            if(Aeliminar!=null){
                return Aeliminar;
            }
        }
        if (actual.izq==null) {
            return null;
        }else{
            if (actual.izq.der==null && actual.izq.izq==null) {
                Aeliminar=actual.izq;
                actual.izq=null;
            }else{
                Aeliminar=Reemplazoder(actual.izq);
            }
        }
        return Aeliminar;
    }
    
    public NodoArchivo Reemplazoizq(NodoArchivo actual){
        NodoArchivo Aeliminar;
        if (actual.izq!=null) {
            Aeliminar=Reemplazoizq(actual.izq);
            if(Aeliminar!=null){
                return Aeliminar;
            }
        }
        if (actual.der==null) {
            return null;
        }else{
            if (actual.der.der==null && actual.der.izq==null) {
                Aeliminar=actual.der;
                actual.der=null;
            }else{
                Aeliminar=Reemplazoizq(actual.der);
            }
        }
        return Aeliminar;
    }
    
    public File graficar() throws IOException{
        File file=new File("arbol.dot");
        BufferedWriter bw;
        bw=new BufferedWriter(new FileWriter(file));
        String archivo;
        archivo="digraph pila{ \n";
        archivo+="node [shape=\"record\"]; \n";
        archivo+=this.Raiz.nombre+this.Raiz.extension+" [ label = \"{"
                +this.Raiz.nombre+"."+this.Raiz.extension+"|"
                +this.Raiz.contenido+"|"
                +this.Raiz.fecha+"|"
                +"izq: "+(this.Raiz.pesoizq+"der: "+this.Raiz.pesoder)
                +"}\"];\n";
        if (this.Raiz.izq!=null) {
            archivo+=this.Raiz.nombre+this.Raiz.extension+"->"+this.Raiz.izq.nombre+this.Raiz.izq.extension+"\n";    
            archivo+=graficar1(this.Raiz.izq);
        }
        if (this.Raiz.der!=null) {
            archivo+=this.Raiz.nombre+this.Raiz.extension+"->"+this.Raiz.der.nombre+this.Raiz.der.extension+"\n";
            archivo+=graficar1(this.Raiz.der);
        }
        archivo+="}";
        bw.write(archivo);
        bw.close();
        try {
            String cmd = "dot -Tjpg arbol.dot -o imagena.jpg"; //Comando de apagado en linux
            Runtime.getRuntime().exec(cmd); 
        } catch (IOException ioe) {
            System.out.println (ioe);
        }
        File imagen=new File("imagena.jpg");
        JOptionPane.showMessageDialog(null, "Reporte generado");
        Image image = ImageIO.read(imagen);
        Icon icon=new ImageIcon(image);
        JLabel label=new JLabel();
        label.setIcon(icon);
        JScrollPane Scroll=new JScrollPane(label);
        Scroll.setMaximumSize(new Dimension(1400,800));
        JOptionPane.showMessageDialog(null, Scroll);
        return imagen;
    }
    
    public String graficar1(NodoArchivo actual){
        String archivo="";
        archivo+=actual.nombre+actual.extension+" [ label = \"{"
                +actual.nombre+"."+actual.extension+"|"
                +actual.contenido+"|"
                +actual.fecha+"|"
                +"izq: "+actual.pesoizq+" der: "+actual.pesoder
                +"}\"];\n";
        if (actual.izq!=null) {
            archivo+=actual.nombre+actual.extension+"->"+actual.izq.nombre+actual.izq.extension+"\n";
            archivo+=graficar1(actual.izq);
        }
        if (actual.der!=null) {
            archivo+=actual.nombre+actual.extension+"->"+actual.der.nombre+actual.der.extension+"\n";
            archivo+=graficar1(actual.der);
        }
        return archivo;
    }
}
