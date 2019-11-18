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
        user.Insertar("Admin","Admin",true);
        Ventana ventana =new Ventana();
        ventana.setVisible(true);
    }
}
