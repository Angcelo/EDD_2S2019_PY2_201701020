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
import EDD.Archivos;
import java.awt.Desktop;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
/**
 *
 * @author angel
 */
public class EDD_Drive {

    static Usuarios user=new Usuarios();
    static int usuarioactual;
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        Ventana ventana =new Ventana();
        ventana.setVisible(true);
    }
}
