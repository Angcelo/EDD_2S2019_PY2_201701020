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
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author angel
 */
public class Bitacora {
    public NodoBitacora cima;
    
    public Bitacora(){
        this.cima=null;
    }
    
    public void insertar(String fecha,String hora,String operacion,String usuario){
        if (this.cima==null) {
            this.cima=new NodoBitacora(fecha,hora,operacion,usuario);
        }else{
            NodoBitacora nuevo=new NodoBitacora(fecha,hora,operacion,usuario);
            nuevo.sig=this.cima;
            this.cima=nuevo;
        }
    }
    
    public File GraficarBitacora() throws IOException{
        File eliminar=new File("imgBitacora.jpg");
        eliminar.delete();
        File file=new File("Bitacora.dot");
        BufferedWriter bw;
        bw=new BufferedWriter(new FileWriter(file));
        String archivo;
        archivo="digraph pila{ \n";
        archivo+="rankdir=\"LR\";\n";
        archivo+="node [shape=\"record\"]; \n";
        archivo+="node0 [label=\"";
        NodoBitacora temp=cima;
        while(temp!=null){
            archivo+="|"+temp.operacion+"\\n"+temp.usuario+"\\n"+temp.fecha+" "+temp.hora;
            temp=temp.sig;
        }
        archivo+="\",height=2.5];}";
        bw.write(archivo);
        bw.close();
        File imagen=null;
        try {
            String cmd = "dot -Tjpg Bitacora.dot -o imgBitacora.jpg"; //Comando de apagado en linux
            Runtime.getRuntime().exec(cmd);
            JOptionPane.showMessageDialog(null, "Reporte generado");
            imagen=new File("imgBitacora.jpg");
            Image image = ImageIO.read(imagen);
            Icon icon=new ImageIcon(image);
            JLabel label=new JLabel();
            label.setIcon(icon);
            JScrollPane Scroll=new JScrollPane(label);
            Scroll.setMaximumSize(new Dimension(1400,800));
            JOptionPane.showMessageDialog(null, Scroll);
        } catch (IOException ioe) {
            System.out.println("Error al crear el archivo");
            System.out.println (ioe);
        }
        return imagen;
    }
}
