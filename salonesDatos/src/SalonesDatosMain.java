import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;

public class SalonesDatosMain{

    public static void main(String [] args) {
        Organize();
    }


    private static void Organize(){
        int[][] distances = new int[40][40];
        ArrayList<Group>[] Days = new ArrayList[6];
        HashMap<String, ArrayList<Integer>> sgStudent = new HashMap<>();
        HashMap<Integer, ArrayList<String>> studentSG = new HashMap<>();
        HashMap<Integer, Integer> access = new HashMap<>();
        HashMap<String, Aulas> aulas = new HashMap<>();
        organizarMatriz("DistanciasBloques.csv", distances);
        Days = leerPa(Days, "pa20192.csv");
        matReader(sgStudent,"mat20192.csv", studentSG);
        leerAulas(aulas, "aulas.csv");
        studentsReader(access, "estudiantes.csv");

        leerDias(Days, sgStudent, studentSG, access, aulas, distances);
    }

    private static void leerDias(ArrayList<Group>[] Days, HashMap<String, ArrayList<Integer>> sgStudent,HashMap<Integer, ArrayList<String>> studentSG,
                                 HashMap<Integer, Integer> access, HashMap<String, Aulas> aulas, int[][] distances){

        int d;
        //Dias de la semana
        for (int i = 0; i < Days.length; i++){
            // Grupos de cada día
            for (int j = 0; j < Days[i].size(); j++){
                //Estudiantes de cada grupo
                if(sgStudent.containsKey(Days[i].get(j).getSg())){
                    for (int k = 0; k < sgStudent.get(Days[i].get(j).getSg()).size(); k++) {
                        d = getNextClass(Days[i], sgStudent.get(Days[i].get(j).getSg()).get(k), Days[i].get(j).getFh()
                                , Days[i].get(j).getCr(), studentSG, distances);
                    }
                }
            }
        }
    }

    private static int getNextClass(ArrayList<Group> classes, int student, int FH, String aClassroom,
                                    HashMap<Integer, ArrayList<String>> studentSG, int[][] distances){
        ArrayList<String> c = studentSG.get(student);

        int d = 0;
        if(c.size()>1) {
            for (int i = 0; i < c.size(); i++) {
                for (int j = 0; j < classes.size(); j++) {
                    if (FH == classes.get(j).getSh() && c.contains(classes.get(j).getSg())) {
                        System.out.println(student);
                        System.out.println(c.get(i) + " CON " + classes.get(j).getSg());
                        d = calculateDistance(distances, aClassroom, classes.get(j).getCr());
                        System.out.println(d);
                        System.out.println("-------------------------------------------");
                        // Need to change the value of i so we can break the two cycles
                        i = c.size();
                        break;
                    }
                }
            }
        }
        return d;
    }

    private static int calculateDistance(int[][] distances, String aClassroom, String fClassroom){

        String s1, s2;
        int b1, b2;
        int d = 0;

        if(aClassroom.length() == 4 && fClassroom.length() == 4){
            s1 = aClassroom.substring(0,1);
            s2 = fClassroom.substring(0,1);
            b1 = Integer.parseInt(s1);
            b2 = Integer.parseInt(s2);
            d = distances[b1][b2];
        } else if(aClassroom.length() == 4 && fClassroom.length() == 5){
            s1 = aClassroom.substring(0,1);
            s2 = fClassroom.substring(0,2);
            b1 = Integer.parseInt(s1);
            b2 = Integer.parseInt(s2);
            d = distances[b1][b2];

        } else if(aClassroom.length() == 5 && fClassroom.length() == 4){
            s1 = aClassroom.substring(0,2);
            s2 = fClassroom.substring(0,1);
            b1 = Integer.parseInt(s1);
            b2 = Integer.parseInt(s2);
            d = distances[b1][b2];

        } else {
            s1 = aClassroom.substring(0,2);
            s2 = fClassroom.substring(0,2);
            b1 = Integer.parseInt(s1);
            b2 = Integer.parseInt(s2);
            d = distances[b1][b2];
        }
        return d;
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

    private static ArrayList<Group>[] leerPa (ArrayList<Group>[] Days, String file) {

        BufferedReader bufferPa = null;
        for (int i = 0; i < Days.length; i++){
            Days[i] = new ArrayList<Group>();
        }

        try {
            bufferPa = new BufferedReader(new FileReader(file));
            String line = bufferPa.readLine();
            while (line != null) {
                String[] data;
                data = line.split(",");
                //Mira si la clase tiene salon o si es un domingo, en caso de que se cumpla ignora estas clases
                if (data.length == 7) {
                    if (data[6].equals("00000") || data[3].equals("domingo")) {
                    } else {
                        String[] hi = data[4].split(":");
                        String[] hf = data[5].split(":");
                        switch(data[3]){
                            case "lunes":
                                Group groups0 = new Group(data[0] + "," + data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(hi[0] + hi[1])
                                        , Integer.parseInt(hf[0] + hf[1]), data[6]);
                                Days[0].add(groups0);
                                break;
                            case "martes":
                                Group groups1 = new Group(data[0] + "," + data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(hi[0] + hi[1])
                                        , Integer.parseInt(hf[0] + hf[1]), data[6]);
                                Days[1].add(groups1);
                                break;
                            case "miércoles":
                                Group groups2 = new Group(data[0] + "," + data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(hi[0] + hi[1])
                                        , Integer.parseInt(hf[0] + hf[1]), data[6]);
                                Days[2].add(groups2);
                                break;
                            case "jueves":
                                Group groups3 = new Group(data[0] + "," + data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(hi[0] + hi[1])
                                        , Integer.parseInt(hf[0] + hf[1]), data[6]);
                                Days[3].add(groups3);
                                break;
                            case "viernes":
                                Group groups4 = new Group(data[0] + "," + data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(hi[0] + hi[1])
                                        , Integer.parseInt(hf[0] + hf[1]), data[6]);
                                Days[4].add(groups4);
                                break;
                            case "sábado":
                                Group groups5 = new Group(data[0] + "," + data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(hi[0] + hi[1])
                                        , Integer.parseInt(hf[0] + hf[1]), data[6]);
                                Days[5].add(groups5);
                                break;
                        }
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
        return Days;
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
    private static void matReader (HashMap<String, ArrayList<Integer>> sgS, String file, HashMap<Integer, ArrayList<String>> sSG) {

        BufferedReader mats = null;

        try {
            mats = new BufferedReader(new FileReader(file));
            String line = mats.readLine();
            while (line != null) {
                String [] data = line.split(",");
                //Adding to the HashMap sgS, key: String concatenated (subject and group) value: ArrayList of Integer(Student)
                if(sgS.containsKey(data[1]+ "," + data[2])){
                    sgS.get(data[1]+","+data[2]).add(Integer.parseInt(data[0]));
                }else{
                    ArrayList<Integer> sts = new ArrayList<>();
                    sts.add(Integer.parseInt(data[0]));
                    sgS.put(data[1]+ "," + data[2], sts);
                }
                //Adding to the HashMap sSG, key: Student and value: ArrayList of Strings concatenated(subject and group)
                if(sSG.containsKey(Integer.parseInt(data[0]))){
                    sSG.get(Integer.parseInt(data[0])).add(data[1]+","+data[2]);
                }else{
                    ArrayList<String> sts = new ArrayList<>();
                    sts.add(data[1]+","+data[2]);
                    sSG.put(Integer.parseInt(data[0]), sts);
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