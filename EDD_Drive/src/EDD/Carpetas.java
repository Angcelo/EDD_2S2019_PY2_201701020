/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
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
    
    private NodoCarpeta BuscarCol(String nombreindice){
        NodoCarpeta temp=this.inicio;
        while(temp!=null){
            if (temp.nombreindice.equals(nombreindice)) {
                System.out.println("col: "+temp.nombre+"( "+temp.x+","+temp.y+")");
                return temp;
            }
            temp=temp.sig;
        }
        System.out.println("no existe col");
        return null;
    }
    
    private NodoCarpeta BuscarFil(String nombreindice){
        NodoCarpeta temp=this.inicio;
        while(temp!=null){
            if (temp.nombreindice.equals(nombreindice)) {
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
    
    public boolean interseccion(NodoCarpeta fila, String valor){
        NodoCarpeta temp=fila;
        while(temp!=null){
            if (temp.nombreindice.equals(valor)) {
                return true;
            }
            temp=temp.sig;
        }
        return false;
    }
    
    public boolean insertar(String principal,String secundaria){
        NodoCarpeta col=BuscarCol(principal);
        NodoCarpeta fil=BuscarFil(secundaria);  
        if (col!=null && fil!=null) {
            int iterador=1;
            if (interseccion(fil,principal+"/"+secundaria)) {
                System.out.print("ya existe carpeta");
                return false;
            }
            while(fil!=null){
                System.out.println(secundaria+(iterador-1));
                if (interseccion(fil,principal+"/"+secundaria+(iterador-1))) {
                    System.out.print("ya existe carpeta");
                    return false;
                }
                System.out.println(secundaria+"-"+iterador);
                fil=BuscarFil(secundaria+"-"+iterador);
                iterador++;
            }
            fil=CrearFil(secundaria);
            NodoCarpeta col2 =CrearCol(secundaria);
            iterador--;
            fil.nombreindice=secundaria+"-"+iterador;
            col2.nombreindice=secundaria+"-"+iterador;
        }else if (col!=null && fil==null){
            fil=CrearFil(secundaria);
            CrearCol(secundaria);
        }else if (col==null) {
            System.out.print("Error no existe carpeta en la que se encuentra");
        }
        NodoCarpeta nuevo=new NodoCarpeta((principal+"/"+secundaria),col.x,fil.y,col.nombre+"/"+fil.nombre);
        nuevo.nombreindice=principal+"/"+fil.nombreindice;
        nuevo=InsertarCol(nuevo,fil);
        nuevo=InsertarFil(nuevo,col);
        System.out.println(nuevo.nombre+"("+nuevo.x+","+nuevo.y+")");
        return true;
    }
    
    public String ListarCarpetas(String inicio){
        NodoCarpeta padre=this.BuscarCol(inicio);
        String carpetas="";
        if (padre.sup!=null) {
            padre=padre.sup;
            while(padre!=null){
                String numero=padre.nombreindice.replace(padre.nombre, "");
                numero=numero.replace("-","");
                if (numero.equals("")) {
                    numero="0";
                }
                System.out.println("numero completo:" +numero);
                carpetas+=padre.nombre+"#"+numero+"#,";
                padre=padre.sup;
            }
        }
        System.out.println("esta es la lista de carpetas: "+carpetas);
        return carpetas;
    }
    
    public void InsertarArchivo(String inicio,String nombre,String extension,String contenido) throws IOException{
        NodoCarpeta padre=this.BuscarCol(inicio);
        Calendar calendario = Calendar.getInstance();
        int año=calendario.get(Calendar.YEAR);
        int mes=calendario.get(Calendar.MONTH)+1;
        int dia=calendario.get(Calendar.DATE);
        int hora =calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);
        String tiempo=dia+"/"+mes+"/"+año+" "+hora + ":" + minutos + ":" + segundos;
        padre.archivos.insertar1(nombre,extension,contenido,tiempo);
    }
    
    public String ListarArchivos(String inicio){
        NodoCarpeta padre=this.BuscarCol(inicio);
        return padre.archivos.ListaArchivos();
    }    
    
    public String[] contenidoarchivo(String inicio,String nombreextension){
        NodoCarpeta padre=this.BuscarCol(inicio);
        String[] nomext=nombreextension.split("\\.");
        String nombrearchivo=nomext[0];
        for (int i = 1; i < nomext.length-1; i++) {
            nombrearchivo+="."+nomext[i];
        }
        String[] archivo=padre.archivos.BuscarArchivo(nombrearchivo,nomext[nomext.length-1]);
        System.out.println("Carpeta encontrada: "+Arrays.toString(archivo));
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
    
    public void modArchivo(String carpeta,String nombre,String nombre_nuevo,String contenido) throws IOException{
        NodoCarpeta padre=this.BuscarCol(carpeta);
        String nombreold2[]=nombre.split("\\.");
        String nombrearchivoold=nombreold2[0];
        for (int i = 1; i < nombreold2.length-1; i++) {
            nombrearchivoold+=nombreold2[i];
        }
        String nombrenew2[]=nombre_nuevo.split("\\.");
        String nombrearchivonew=nombrenew2[0];
        for (int i = 1; i < nombrenew2.length-1; i++) {
            nombrearchivonew+=nombrenew2[i];
        }
       if (nombrearchivoold.equals(nombrearchivonew) && nombreold2[nombreold2.length-1].equals(nombrenew2[nombrenew2.length-1])) {
            System.out.println("Modificar con mismo nombre");
            padre.archivos.ModContenidoArchivo(nombrearchivoold,nombreold2[nombreold2.length-1],contenido);
        }else{
            System.out.println("modificar con nombre distinto");
            padre.archivos.BuscaraEliminar(nombrearchivoold,nombreold2[nombreold2.length-1]);
            Calendar calendario = Calendar.getInstance();
            int año=calendario.get(Calendar.YEAR);
            int mes=calendario.get(Calendar.MONTH)+1;
            int dia=calendario.get(Calendar.DATE);
            int hora =calendario.get(Calendar.HOUR_OF_DAY);
            int minutos = calendario.get(Calendar.MINUTE);
            int segundos = calendario.get(Calendar.SECOND);
            String tiempo=dia+"/"+mes+"/"+año+" "+hora + ":" + minutos + ":" + segundos;
            padre.archivos.insertar1(nombrearchivonew, nombrenew2[nombrenew2.length-1], contenido, tiempo);
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
    
    public void eliminararchivo(String carpeta,String nombre){
        NodoCarpeta padre=this.BuscarCol(carpeta);
        String[] nombre2=nombre.split("\\.");
        String nombreeliminar=nombre2[0];
        for (int i = 1; i < nombre2.length-1; i++) {
            nombreeliminar+=nombre2[i];
        }
        padre.archivos.BuscaraEliminar(nombreeliminar,nombre2[nombre2.length-1]);
    }
    
    public void Graficar() throws IOException{
        NodoCarpeta tempx=this.inicio.sig;
        NodoCarpeta tempy;
        File file=new File("Carpetas.dot");
        BufferedWriter bw;
        bw=new BufferedWriter(new FileWriter(file));
        String archivo;
        archivo="digraph pila{ \n";
        archivo+="node [shape=\"record\"]; \n";
        archivo+="inicio [label = \"{"+inicio.nombre+"|("+inicio.x+","+inicio.y+")}\"];\n";
        while(tempx!=null){
            tempy=tempx;
            while(tempy!=null){
                archivo+=tempy.nombreindice+"[label = \"{"+tempy.nombre+"|("+tempy.x+","+tempy.y+")}\"];\n";
                tempy=tempy.sup;
            }
            tempx=tempx.sig;
        }
        archivo+="}";
        bw.write(archivo);
        bw.close();
    }
    
    public File GraficarArchivos(String carpeta) throws IOException{
        NodoCarpeta padre=this.BuscarCol(carpeta);
        return padre.archivos.graficar();
    }
}
