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
	 int[][] distances = new int[40][40];
	 ArrayList<Group> academicP= new ArrayList<>();
	 HashMap<String, ArrayList<String>> studentsM= new HashMap<>();
	 HashMap<Integer, Integer> access = new HashMap<>();
	 HashMap<String, Aulas> aulas = new HashMap<>();
	 organizarMatriz("DistanciasBloques.csv", distances);
	 leerPa(academicP, "pa20192.csv");
	 leerAulas(aulas, "aulas.csv");
	 studentsReader(access, "estudiantes.csv");
	 matReader(studentsM,"mat20192.csv");
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

    private static void leerPa (ArrayList<Group> academicP, String file) {

        BufferedReader bufferPa = null;

        try {
            bufferPa = new BufferedReader(new FileReader(file));
            String line = bufferPa.readLine();
            while (line != null) {
                String[] data;
                data = line.split(",");
                //Mira si la clase tiene salon o si es un domingo, en caso de que se cumpla ignora estas clases
                if(data.length == 7) {
                    if(!(data[6].equals("00000")) || !(data[3].equals("domingo"))) {
                            String[] hi = data[4].split(":");
                            String[] hf = data[5].split(":");
                            Group group = new Group(data[0] + "," + data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(hi[0]+hi[1])
                                    , Integer.parseInt(hf[0]+hf[1]), data[6]);
                            academicP.add(group);
                        }
                    }
                }
                //Pasa a la siguiente linea
                line = bufferPa.readLine();
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
    private static void leerAulas (HashMap<String, Aulas> aulas, String file) {

        BufferedReader studentsMa = null;

        try {
            studentsMa = new BufferedReader(new FileReader(file));
            String line = studentsMa.readLine();
            while (line != null) {
                String[] data = line.split(",");
                if(data.length == 4){
                    if(data[2].equals("N/A")){
                        data[2] = "1000";
                    }
                    Aulas classroom = new Aulas(data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3]));
                    aulas.put(data[0], classroom);
                }
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

     private static void studentsReader (HashMap<Integer, Integer> access, String file) {

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
    private static void matReader (HashMap<String, ArrayList<String>> studentsM, String file) {

        BufferedReader mats = null;

        try {
            mats = new BufferedReader(new FileReader(file));
            String line = mats.readLine();
            while (line != null) {
                String [] data = line.split(",");
                if(studentsM.containsKey(data[1]+ ", " + data[2])){
                    studentsM.get(data[1]+","+data[2]).add(data[0]);
                }else{
                    ArrayList<String> sts = new ArrayList<>();
                    sts.add(data[0]);
                    studentsM.put(data[1]+ ", " + data[2], sts);
                }
                line = mats.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (mats != null) {
                try {
                    mats.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
