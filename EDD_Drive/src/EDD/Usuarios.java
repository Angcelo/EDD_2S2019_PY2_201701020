/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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
public class Usuarios {
    public NodoUsuario usuarios[]=new NodoUsuario[7];
    public int lleno=0;
    
    public boolean Insertar(String usuario,String password) throws NoSuchAlgorithmException{
        if (BuscarUsuario(usuario)!=-1) {
            System.out.println("Usuario existente");
            JOptionPane.showMessageDialog(null, "Usuario existente");
            return false;
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(usuario.getBytes(StandardCharsets.UTF_8));
        int decimal=Arrays.hashCode(encodedhash);
        int id=Math.abs(decimal%usuarios.length);
        boolean continuar=true;
        while(continuar){
            System.out.println(usuario+" "+id);
            if (usuarios[id]==null) {
                byte[] passencode = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                int passcode=Arrays.hashCode(passencode);
                usuarios[id]=new NodoUsuario(usuario,passcode+"");
                lleno++;
                continuar=false;
            }else{
                id=(id*id)+id+1;   
                while (id>=usuarios.length) {
                    id=Math.abs(id-usuario.length());
                }
            }
        }
        continuar=true;
        int iterador=usuarios.length;
        double llenod=lleno;
        double tamaño=usuarios.length;
        double valor=llenod/tamaño;
        if (valor>=0.75) {
            while(continuar){
                iterador++;
                continuar = esPrimo(iterador);
            }
            NodoUsuario temp[]=usuarios;
            usuarios=new NodoUsuario[iterador];
            System.arraycopy(temp, 0, usuarios, 0, temp.length);
            System.out.println("aumento: "+usuarios.length);
        }
        return true;
    }
    
    public boolean esPrimo(int numero){
        int contador = 2;
        boolean primo=false;
        while(contador!=numero){
            if (numero % contador == 0){
                return true;
            }
            contador++;
        }
        return primo;
    }
    
    public void mostrar(){
        for(NodoUsuario m:usuarios){
            if (m!=null) {             
                System.out.print(m.usuario+": ");
                System.out.println(m.password);
                System.out.println("    |");
                System.out.println("    ˇ");
            }
        }
    }
    
    public int BuscarUsuario(String usuario){
        for(int i=0;i<usuarios.length;i++){
            if (usuarios[i]!=null && usuarios[i].usuario.equals(usuario)) {  
                return i;
            }
        }
        return -1;
    }
    
    public String[] Listasusuarios(){
        String[] strusers=new String[this.lleno];
        int iterador=0;
        for (NodoUsuario usuario : usuarios) {
            if (usuario != null) {
                strusers[iterador] = usuario.usuario;
                iterador++;
            }
        }
        return strusers;
    }
    
    public boolean verificar(int indice,String pass) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] passencode = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        int passcode=Arrays.hashCode(passencode);
        if (this.usuarios[indice]!=null) {
            return this.usuarios[indice].password.equals(passcode+"");   
        }
        return false;
    }
    
    public void Graficar() throws IOException{
        int iterador=0;
        File file=new File("usuarios.dot");
        BufferedWriter bw;
        bw=new BufferedWriter(new FileWriter(file));
        String archivo="digraph html { \n";
	archivo+= "abc[shape = none, margin = 0, label = < \n";
	archivo+= "<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\" CELLPADDING=\"4\"> \n";
        archivo+="<TH>\n";
         archivo+="  <TD>Índice</TD>\n";
        archivo+="  <TD>Usuario</TD>\n";
        archivo+="  <TD>Contraseña</TD>\n";
        archivo+="</TH>\n";
        for (NodoUsuario usuario : usuarios) {
            archivo+="<TR>\n";
            archivo+="<TD>"+iterador+"</TD>";
            if (usuario!=null) {
                archivo+="  <TD>"+usuario.usuario+"</TD>\n";
                archivo+="  <TD>"+usuario.password+"</TD>\n";
            }else{
                archivo+="  <TD></TD>\n";
                archivo+="  <TD></TD>\n";
            }
            archivo+="</TR>\n";
            iterador++;
        }
        archivo+="</TABLE>>];";
        archivo+="}";
        bw.write(archivo);
        bw.close();
        try {
            String cmd = "dot -Tjpg usuarios.dot -o usersimg.jpg"; //Comando de apagado en linux
            Runtime.getRuntime().exec(cmd); 
            File imagen=new File("usersimg.jpg");
            JOptionPane.showMessageDialog(null, "Reporte generado");
            Image image = ImageIO.read(imagen);
            Icon icon=new ImageIcon(image);
            JLabel label=new JLabel();
            label.setIcon(icon);
            JScrollPane Scroll=new JScrollPane(label);
            Scroll.setMaximumSize(new Dimension(1400,800));
            JOptionPane.showMessageDialog(null, Scroll);
        } catch (IOException ioe) {
            System.out.println (ioe);
        }
    }
}
