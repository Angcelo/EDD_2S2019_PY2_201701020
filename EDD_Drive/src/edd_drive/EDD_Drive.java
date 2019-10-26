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
/**
 *
 * @author angel
 */
public class EDD_Drive {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Usuarios user=new Usuarios();
        System.out.println(8%7);
        System.out.println(9%7);
        System.out.println(10%7);
        System.out.println(11%7);
        System.out.println(12%7);
        System.out.println(13%7);
        user.Insertar("Javier", "123");
        user.Insertar("Angel", "123");
        user.Insertar("Marcelo", "678");
        user.Insertar("Marroquin", "abc");
        user.Insertar("Garcia", "cde");
        user.Insertar("Rama", "yui");
        user.mostrar();
    }
}
