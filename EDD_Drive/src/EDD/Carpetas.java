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
public class Carpetas {
    public NodoCarpeta inicio;
    public Carpetas(){
        this.inicio=new NodoCarpeta("raiz",0,0);
        this.CrearCol("/");
        this.CrearFil("/");
    }
    
    public NodoCarpeta BuscarCol(String nombre){
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
    
    public NodoCarpeta BuscarFil(String nombre){
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
    
    public NodoCarpeta InsertarCol(NodoCarpeta nuevo,NodoCarpeta cabezaCol){
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
    
    public NodoCarpeta InsertarFil(NodoCarpeta nuevo,NodoCarpeta cabezaCol){
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
    
    public NodoCarpeta CrearCol(String nombre){
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
    
    public NodoCarpeta CrearFil(String nombre){
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
}
