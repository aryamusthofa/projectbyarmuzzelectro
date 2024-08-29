/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication1armuze;

/**
 *
 * @author R
 */
import java.io.*;
public class JavaApplication1ARMUZE
{
    public static void main (String[] args) throws IOException
    {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader(reader);
        System.out.print("Masukan Kata: ");
        String teks = input.readLine();
        System.out.println (teks);
    }
}
