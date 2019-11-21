import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class SalonesDatosMain{
    
    public static void main(String [] args) {
        leer_archivo("DistanciasBloques.csv");
        Estructura();
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

    public static void Estructura () {
        HashMap<String, String> academicP= new HashMap<String, String>();
        HashMap<String, Student> estudenM= new HashMap<String, Student>();

        BufferedReader bufferPa = null;
        BufferedReader bufferAulas = null;
        BufferedReader bufferStudents = null;

        try {
            bufferPa = new BufferedReader(new FileReader("D:/Alismos/Documentos/GitHub/Estructuras-de-datos-y-algoritmo-II/pa20192.csv"));
            String linea = bufferPa.readLine();
            String [] data = new String[7];
            while (linea != null) {
                data = linea.split(",");
                academicP.put(data[3]+ ", " + data[4] + "," +data[5], data[0] + "," + data [1] + ", " + data[2] +","+data[6]);
                System.out.println(data[3]+ ", " + data[4] + "," +data[5]);
                System.out.println(academicP.get(data[3]+ ", " + data[4] + "," +data[5]));

                linea = bufferPa.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (bufferPa != null) {
                try {
                    bufferPa.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
