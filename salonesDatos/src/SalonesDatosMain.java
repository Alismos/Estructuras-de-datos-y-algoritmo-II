import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;

public class SalonesDatosMain{
    public static void main(String [] args){
        leer_archivo("D:/Alismos/Documentos/GitHub/Estructuras-de-datos-y-algoritmo-II/aulas.csv");
    }

    public HashMap<HashMap<>, >
    public static void leer_archivo(String archivo){
        BufferedReader bufferLectura = null;
        try {
            // Abrir el .csv en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader(archivo));

            // Leer una linea del archivo
            String linea = bufferLectura.readLine();
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                String[] campos = linea.split(",");

                System.out.println(Arrays.toString(campos));

                // Volver a leer otra línea del fichero
                linea = bufferLectura.readLine();
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
