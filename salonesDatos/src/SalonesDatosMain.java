import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;

public class SalonesDatosMain{
    public static void main(String [] args) {
        leer_archivo("DistanciasBloques.csv");
    }

    public static void leer_archivo(String archivo){
        BufferedReader bufferLectura = null;
        try {
            int[][] distances = new int[39][39];
            // Abrir el .csv en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader(archivo));

            // Leer una linea del archivo
            String linea = bufferLectura.readLine();
            while (linea != null) {
                // Separar la linea leída con el separador definido previamente
                String[] campos = linea.split(",");
                //Poner en la matriz "distances" la distancia entre dos bloques, los cuales son representados
                // con filas y columnas
                distances[Integer.parseInt(campos[0])][Integer.parseInt(campos[1])] = Integer.parseInt(campos[2]);
                distances[Integer.parseInt(campos[1])][Integer.parseInt(campos[0])] = Integer.parseInt(campos[2]);
                System.out.println(campos[0]+" "+campos[1] +" "+ campos[2]);
                // Volver a leer otra línea del fichero
                linea = bufferLectura.readLine();
            }
            for(int i = 0; i < 39; i++){
                for(int j = 0; j < 39; j++){
                    System.out.print(distances[i][j]+ " ");
                }
                System.out.println();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            // Cierro el buffer de lectura
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
