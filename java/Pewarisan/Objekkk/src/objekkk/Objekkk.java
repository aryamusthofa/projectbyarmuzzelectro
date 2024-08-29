/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package objekkk;

/**
 *
 * @author R
 */
public class Objekkk {
    
    static void run(Lampu e) {
        e.hidup();
        e.mati();
    }
    
    static void run(Lilin e) {
        e.hidup();
        e.mati();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Lampu la = new Lampu();
        Lilin li = new Lilin();
        
        run(la);
        run(li);
        
        System.out.println("Created by: Arya Musthofa X PPLG 1");
        // TODO code application logic here
    }
    
}
