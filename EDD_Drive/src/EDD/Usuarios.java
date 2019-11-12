/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 * @author angel
 */
public class Usuarios {
    public NodoUsuario usuarios[]=new NodoUsuario[7];
    public int lleno=0;
    
    public void Insertar(String usuario,String password) throws NoSuchAlgorithmException{
        if (BuscarUsuario(usuario)!=-1) {
            System.out.println("Usuario existente");
            return;
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
                id=(id*id)+1;   
                while (id>=usuarios.length) {
                    id=id-usuario.length();
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
    
    public boolean verificar(int indice,String pass) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] passencode = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        int passcode=Arrays.hashCode(passencode);
        if (this.usuarios[indice]!=null) {
            return this.usuarios[indice].password.equals(passcode+"");   
        }
        return false;
    }
}
