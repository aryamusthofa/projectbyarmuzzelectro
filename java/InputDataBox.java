import java.io.*;
public class InputDataBox {

  
    public static void main(String[] args) throws IOException
    {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader(reader);
        System.out.print("Masukan Kata: created by AryaMust07, Hikmahh23, Diah15");
        String teks = input.readLine();
        System.out.println (teks);
    }
    
}




/*
import java.io.*;
public class InputOutput 
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

*/