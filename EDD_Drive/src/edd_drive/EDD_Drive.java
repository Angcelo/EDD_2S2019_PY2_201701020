/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edd_drive;

import EDD.Usuarios;
import EDD.Bitacora;
import EDD.Archivos;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author angel
 */
public class EDD_Drive {

    static Usuarios user=new Usuarios();
    static int usuarioactual;
    static Bitacora bitacora=new Bitacora();
    
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException{
        Archivos arch=new Archivos();
        arch.insertar1("mama", "txt", "hola", "0");
        arch.insertar1("papa", "txt", "hola", "0");
        arch.insertar1("lila", "txt", "hola", "0");
        arch.insertar1("kilo", "txt", "hola", "0");        
        arch.insertar1("jaula", "txt", "hola", "0");
        arch.BuscaraEliminar("mama");
        arch.graficar();
        /*user.Insertar("Admin","Admin");
        Ventana ventana =new Ventana();
        ventana.setVisible(true);*/
    }
}
