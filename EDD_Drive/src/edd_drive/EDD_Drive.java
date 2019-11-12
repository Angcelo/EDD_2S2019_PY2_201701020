/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edd_drive;

import EDD.Usuarios;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author angel
 */
public class EDD_Drive {

    static Usuarios user=new Usuarios();
    static int usuarioactual;
    public static void main(String[] args) throws NoSuchAlgorithmException{
        user.Insertar("Admin","Admin");
        Ventana ventana =new Ventana();
        ventana.setVisible(true);
    }
}
