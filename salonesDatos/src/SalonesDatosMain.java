import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class SalonesDatosMain{
    
    public static void main(String [] args) {
	Organize();
    }


    private static void Organize(){
	 int[][] distances = new int[39][39];
	 HashMap<String, ArrayList<String>> academicP= new HashMap<>();
	 //HashMap<String, Student> studentsM= new HashMap<>();
	 HashMap<Integer, Integer> access = new HashMap<>();
	 ArrayList<Aulas> aulas = new ArrayList<>();
	 organizarMatriz("DistanciasBloques.csv", distances);
	 leerPa(academicP, "pa20192.csv");
	 leerAulas(aulas, "aulas.csv");
	 students(access, "estudiantes.csv");
    }

    private static void organizarMatriz(String file, int[][] distances){
        BufferedReader bufferLectura = null;
        try {
            // Abrir el .csv en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader(file));

            // Leer una line del archivo
            String line = bufferLectura.readLine();
            while (line != null) {
                // Separar la linea leída con el separador definido previamente
                String[] campos = line.split(",");
                //Poner en la matriz "distances" la distancia entre dos bloques, los cuales son representados
                // con filas y columnas
                distances[Integer.parseInt(campos[0])][Integer.parseInt(campos[1])] = Integer.parseInt(campos[2]);
                distances[Integer.parseInt(campos[1])][Integer.parseInt(campos[0])] = Integer.parseInt(campos[2]);
                // Volver a leer otra línea del fichero
                line = bufferLectura.readLine();
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

    private static void leerPa (HashMap<String,ArrayList<String>> academicP, String file) {

        BufferedReader bufferPa = null;

        try {
            bufferPa = new BufferedReader(new FileReader(file));
            String line = bufferPa.readLine();
            while (line != null) {
                String[] data = line.split(",");
                //Mira si la clase tiene salon o si es un domingo, en caso de que se cumpla ignora estas clases
                
                if(data[6].equals("00000") || data[3].equals("domingo")){
                //Mira si la key ya fue creada
                    if(academicP.get(data[3]+ ", " + data[4] + "," +data[5]) == null){
                        // Crea un arraylist y lo coloca en el valor de la nueva key, para despues agragarle el string
                        ArrayList<String> value = new ArrayList<>();
                        academicP.put(data[3]+ ", " + data[4] + "," +data[5], value);
                        academicP.get(data[3]+ ", " + data[4] + "," +data[5]).add(data[0]+", "+data[1]+", "+data[2]+", "+data[6]);
                    }else{
                        //Agrega el string en el arraylist de la key que ya fue creada
                        academicP.get(data[3]+ ", " + data[4] + "," +data[5]).add(data[0]+", "+data[1]+", "+data[2]+", "+data[6]);
                    }
                }
                //Pasa a la siguiente linea
                line = bufferPa.readLine();
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
    private static void leerAulas (ArrayList<Aulas> aulas, String file) {

        BufferedReader studentsMa = null;

        try {
            studentsMa = new BufferedReader(new FileReader(file));
            String line = studentsMa.readLine();
            while (line != null) {
                String[] data = line.split(",");
                Aulas classroom = new Aulas(data[0],data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3]));
		        aulas.add(classroom);
                line = studentsMa.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (studentsMa != null) {
                try {
                    studentsMa.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

     private static void students (HashMap<Integer, Integer> access, String file) {

        BufferedReader students = null;

        try {
            students = new BufferedReader(new FileReader(file));
            String line = students.readLine();
            while (line != null) {
                String [] data = line.split(",");
                access.put(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                line = students.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (students != null) {
                try {
                    students.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
     }
}
