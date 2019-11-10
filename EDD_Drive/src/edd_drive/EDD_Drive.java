/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edd_drive;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import EDD.Usuarios;
import EDD.Carpetas;
/**
 *
 * @author angel
 */
public class EDD_Drive {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Carpetas hola;
        hola=new Carpetas();
        
        hola.insertar("/", "Documentos");
        hola.insertar("/", "Imagenes");
        hola.insertar("/", "Videos");
        hola.insertar("Documentos", "Publico");
        hola.insertar("Documentos", "Privado");
        hola.insertar("Documentos", "Privado");
    }
}
