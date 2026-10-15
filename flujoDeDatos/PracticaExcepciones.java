package flujoDeDatos;

import java.io.*;

public class PracticaExcepciones {
    public static void main(String[] args) {
        BufferedReader bf=null;
        int suma;
        try {
             bf= new BufferedReader(new FileReader("datos.txt"));
            String linea= null;
            linea = bf.readLine();
             suma=0;
            while(linea !=null){
                try {
                    int numero = Integer.parseInt(linea);
                    suma = suma + numero;
                }catch (NumberFormatException e){
                    System.out.println("valor no valido "+ e.getMessage());
                }
                    linea = bf.readLine();
            }
            System.out.println("la suma total es "+ suma);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                bf.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("datos.txt", true));
            bw.write("\nSuma total = " + suma);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
