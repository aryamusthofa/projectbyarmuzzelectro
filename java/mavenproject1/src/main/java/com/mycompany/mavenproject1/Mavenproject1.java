/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author R
 */
public class Mavenproject1 
{
    static void cetak()
    {
        System.out.println("ini method cetak");
    }
    
    static void cetak(String a)
    {
        System.out.println("A :"+a);
    }
    static void cetak(String nama, int umur)
    {
        System.out.println("Nama Saya :"+nama);
        System.out.println("Umur Tahun Saya :"+umur);
    }
    
    static void cetak(int umur, String nama)
    {
        System.out.println("Nama Teman Saya :"+nama);
        System.out.println("Umur Tahun Teman Saya :"+umur);
    }
    
    public static void main(String [] a)
    
            
            
    {
        cetak();
        cetak("AAA");
        cetak("Musthofa",16);
        cetak(15,"Marsahid");
        
    }
            
            
            
}