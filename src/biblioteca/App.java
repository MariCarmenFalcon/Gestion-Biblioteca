package biblioteca;

import java.awt.Image;
import javax.swing.ImageIcon;

public class App {
    
    public static void main(String[] args) {
        
        
        VentPrincipal NF = new VentPrincipal();  
        
        //Imagen hierba        
        ImageIcon icon = (ImageIcon)NF.jLabel2Hierba.getIcon();
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(NF.jLabel2Hierba.getWidth(), NF.jLabel2Hierba.getHeight(), image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImage);        
        NF.jLabel2Hierba.setIcon(newIcon);
        
        //Imagen prestamo-libro
        ImageIcon icon2 = (ImageIcon)NF.imagenPrestLibro1.getIcon();
        Image image2= icon2.getImage();
        Image newImage2= image2.getScaledInstance(NF.imagenPrestLibro1.getWidth(), NF.imagenPrestLibro1.getHeight(), image2.SCALE_SMOOTH);
        ImageIcon newIcon2= new ImageIcon(newImage2);        
        NF.imagenPrestLibro1.setIcon(newIcon2);
        
        //Imagen inicio libros
        ImageIcon icon3 = (ImageIcon)NF.jLabel12.getIcon();
        Image image3 = icon3.getImage();
        Image newImage3 = image3.getScaledInstance(NF.jLabel12.getWidth(), NF.jLabel12.getHeight(), image3.SCALE_SMOOTH);
        ImageIcon newIcon3 = new ImageIcon(newImage3);        
        NF.jLabel12.setIcon(newIcon3);
        
        //Imagen devol libros
        ImageIcon icon4 = (ImageIcon)NF.jLabelFoto_dev1libro.getIcon();
        Image image4 = icon4.getImage();
        Image newImage4 = image4.getScaledInstance(NF.jLabelFoto_dev1libro.getWidth(), NF.jLabelFoto_dev1libro.getHeight(), image4.SCALE_SMOOTH);
        ImageIcon newIcon4 = new ImageIcon(newImage4);        
        NF.jLabelFoto_dev1libro.setIcon(newIcon4);
        
         //Imagen registro usuario
        ImageIcon icon5 = (ImageIcon)NF.jLabelResg_Usuar.getIcon();
        Image image5 = icon5.getImage();
        Image newImage5 = image5.getScaledInstance(NF.jLabelResg_Usuar.getWidth(), NF.jLabelResg_Usuar.getHeight(), image5.SCALE_SMOOTH);
        ImageIcon newIcon5 = new ImageIcon(newImage5);        
        NF.jLabelResg_Usuar.setIcon(newIcon5);
        
        ImageIcon icon6 = (ImageIcon)NF.jLabelLogoBiblio.getIcon();
        Image image6 = icon6.getImage();
        Image newImage6 = image6.getScaledInstance(NF.jLabelLogoBiblio.getWidth(), NF.jLabelLogoBiblio.getHeight(), image6.SCALE_SMOOTH);
        ImageIcon newIcon6 = new ImageIcon(newImage6);        
        NF.jLabelLogoBiblio.setIcon(newIcon6);        
        
        NF.setVisible(true);
        
      
        
       
    }

}
