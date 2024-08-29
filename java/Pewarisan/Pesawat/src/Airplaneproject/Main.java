/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Airplaneproject;

/**
 *
 * @author R
 */

public class Main {

    public static void main(String [] args) {
        Orang jawa = new Orang();
        Dewasa kerja = new Dewasa();
        laki la = new laki();
        puan pe = new puan();
        anakkecil ant = new anakkecil();
        introduction myintro = new introduction();
        
        jawa.sapa();
        kerja.sapa();
        
        jawa.bicara();
        kerja.bicara();
        
        //jawa.tipe(); kalo dikasih ini program bakal error
        kerja.tipe();
        
        la.l();
        
        pe.perempuan();
        
        ant.ant();
        
        myintro.intro();
    }
}

