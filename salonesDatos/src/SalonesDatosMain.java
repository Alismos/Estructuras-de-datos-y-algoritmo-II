/**
* @Authors: Andrés Darío Chaves Pérez and Duvan Andrés Ramirez Saedra
* Last update: 5:23am 25/11/2019
*
* */
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class SalonesDatosMain{

    public static void main(String [] args) {
        System.out.println("Ordenamiento de clases con el mayor coeficiente: ");
        Organize();
    }

    /**
     * Method that is called from the main and will have all the structure of the project within the calling of other methods
     */
    private static void Organize(){

        /* Matrix of ints that contains the distances between buildings*/
        int[][] distances = new int[40][40];
        /* Array of ArrayLisy<Group> that contains all the classes of the day*/
        ArrayList<Group>[] Days = new ArrayList[6];
        /* HashMap that as key: the concatenation of the string subject and the string group and value: an ArrayList<String> that has all the Student from a class*/
        HashMap<String, ArrayList<Integer>> sgStudent = new HashMap<>();
        /* HashMAp that as key: a string that means the Id of the student and value: as the ArrayList of Strings that contains al the classes form a student*/
        HashMap<Integer, ArrayList<String>> studentSG = new HashMap<>();
        /* HashMap that contains all the classes of an specific time of the day*/
        HashMap<String, ArrayList<Group>> diHoMin = new HashMap<>();
        /* HashMap that contains all the students and the information of the kind of access they need*/
        HashMap<Integer, Integer> access = new HashMap<>();
        /*HashMap that contains all the classrooms*/
        HashMap<String, Aulas> aulas = new HashMap<>();

        organizarMatriz("DistanciasBloques.csv", distances);
        Days = leerPa(diHoMin, Days, "pa20192.csv");
        matReader(sgStudent,"mat20192.csv", studentSG);
        leerAulas(aulas, "aulas.csv");
        studentsReader(access, "estudiantes.csv");
        leerDias(diHoMin, Days, sgStudent, studentSG, aulas, distances);

    }

    /**
     * Method in charge of travel along the Array Days and the ArrayList of objects of type Group so it can provide the right data to the method getCode()
     * @param diHoMin HashMap of key: day, starting hour and finishing hour of a class, value: ArrayList of objects Group
     * @param Days Array of ArraList of Group
     * @param sgStudent HashMap of key: concatenated combination of string subject and group, value: ArrayList of Integer that represents the students of a class
     * @param studentSG HashMap of key: Id of student, value: ArrayList of Strings that means the classes that the student has
     * @param aulas HashMap of key: id of the classroom, value: objects of type Aula with the information
     * @param distances Matrix of distances between buildings
     */
    private static void leerDias(HashMap<String, ArrayList<Group>> diHoMin, ArrayList<Group>[] Days, HashMap<String, ArrayList<Integer>> sgStudent,
                                 HashMap<Integer, ArrayList<String>> studentSG, HashMap<String, Aulas> aulas, int[][] distances){

        //Records the days of the week
        for (int i = 0; i < Days.length; i++){
            // Records the groups of every day
            for (int j = 0; j < Days[i].size(); j++){
                // Makes sure that the key contains something inside so it can do the code inside
                if (sgStudent.containsKey(Days[i].get(j).getSg())) {
                    getCoe(Days[i], studentSG, diHoMin.get(Days[i].get(j).getDay() + "," + Days[i].get(j).getSh() + "," + Days[i].get(j).getFh()), Days[i].get(j).getFh(), Days[i].get(j).getCr(), distances, sgStudent, aulas);
                }
            }
        }
    }

    /**
     * Method that will be in charge of analyzing
     * @param classes ArrayList of objects type Group that are all the classes from a Day
     * @param studentSG HashMap of key: Id of student, value: ArrayList of Strings that means the classes that the student has
     * @param Grupos an ArrayList of objects Group which are all the classes that happened at the same time
     * @param FH the finishing hour of the class that the student is in at the moment
     * @param cR the classroom
     * @param distances Matrix of distances between buildings
     * @param sgStudent HashMap of key: concatenated combination of string subject and group, value: ArrayList of Integer that represents the students of a class
     * @param aulas HashMap of all the classrooms with their information
     */
    private static void getCoe(ArrayList<Group> classes, HashMap<Integer, ArrayList<String>> studentSG, ArrayList<Group> Grupos, int FH, String cR, int[][] distances, HashMap<String, ArrayList<Integer>> sgStudent, HashMap<String, Aulas> aulas) {
        // variable that contains the highest element and it use to search distances
        int top = Integer.MIN_VALUE;
        // Variable that contains the group that needs to be change, because has the highest top
        Group MaximoGr = new Group();

        //Travels along the ArrayList of the classes that happened at the same time
        for(int i  = 0; i < Grupos.size(); i++){
            // The varible contains the sum of all the distances between one building to another of each student if the have an adjoining class
            int sum = 0;
            // Verifies that the HashMap with the key contains something
            if(sgStudent.containsKey(Grupos.get(i).getSg())) {
                //Obtains the sum of the distances between buildings for a group
                for (int j = 0; j < sgStudent.get(Grupos.get(i).getSg()).size(); j++) {
                    sum = sum + getNextClass(classes, sgStudent.get(Grupos.get(i).getSg()).get(j), FH, cR, studentSG, distances);
                }
                // Sees if the sum of the distances is bigger than the actual, if it is, changes the value of the group that needs to be move
                if(sum > top){
                    MaximoGr.setSh(Grupos.get(i).getSh());
                    MaximoGr.setFh(Grupos.get(i).getFh());
                    MaximoGr.setSg(Grupos.get(i).getSg());
                    MaximoGr.setCr(Grupos.get(i).getCr());
                    MaximoGr.setCoe(sum);
                    top = sum;
                }
            }
        }
        // Obtaining key to search on the HashMap
        String[] llaves = new String[aulas.size()];
        // Iterator for cycle
        int j = 0;
        // Fills up the Array of keys of the HashMap
        for (String key : aulas.keySet()) {
            llaves[j] = key;
            j++;
        }

        // Contains the lowest sum of distances between buildings of a group
        int min = MaximoGr.getCoe();
        // String that determines the change of the schedule
        String change = "";
        // Checks if min is greater than zero
        if(min > 0 ) {
            // Travels the array of the keys of the HashMap
            for (int i = 0; i < llaves.length; i++) {
                boolean isIn = false; // Variable that determines if there is a key in an specific hour
                int distance = 0; // Distance between buildings
                // Travels all the ArrayList Group that are all the classes that are at the same time
                for (int k = 0; k < Grupos.size(); k++) {
                    // Ask if the key is already use, if it is the variable isIn turns true which means that the class is in using an can not be use for change
                    if (llaves[i] == Grupos.get(k).getCr()) {
                        isIn = true;
                    }
                }
                // Ask if the variable have not change
                if (isIn == false) {
                    // Checks if there is something inside the value of a key
                    if (aulas.containsKey(MaximoGr.getCr())) {
                        //Checks if the classroom that is free has easy acces for everyone
                        if (aulas.get(llaves[i]).getAccess() == 1 ) {
                            // calls the calculateDistance to calculate the a new distance between a possible change of actual classroom to the future ones
                            distance = calculateDistance(distances, MaximoGr.getCr(), llaves[i]);
                        }
                    }
                }
                // IF the distance obtained is lower than the actual the classroom change is made
                if (distance < min) {
                    change = llaves[i];
                    min = distance;
                }
            }
            System.out.println("Materia-Grupo: " + MaximoGr.getSg() + " Cambio a aula: " + change + " ");
            System.out.println();
            return;
        }
        System.out.println("Materia-Grupo: " + MaximoGr.getSg() + " no necesita ser optimizada, pues no posee estudiantes con clases adyacentes ");
        System.out.println();

    }

    /**
     * Method in charge of looking if a Student has a class next to another and if that is true searchs for the distance between the buildings of the classes
     * @param classes ArrayList of objects of type Group which are the classes of the Day
     * @param student the id of the student
     * @param FH the finishing hour of the class that the student is in at the moment
     * @param aClassroom the id of the actual classroom that the student is in
     * @param studentSG HashMap with key: id of the student and with value: ArrayList of strings wich are the classes that the student has
     * @param distances Matrix of distances between buildings
     * @return returns a distance in the variable d
     */
    private static int getNextClass(ArrayList<Group> classes, int student, int FH, String aClassroom,
                                    HashMap<Integer, ArrayList<String>> studentSG, int[][] distances) {
        // Temporal ArrayList which will contain the classes of one student
        ArrayList<String> c = studentSG.get(student);
        // Variable that contains the distance between buildings
        int d = 0;
        /* Sees if the ArrayList is longer than just one position, in case that does not enter in it, means that the student only have one class and will
           not have a next one*/
        if(c.size()>1) {
            /* Travels the c ArrayList, which are the classes of a student*/
            for (int i = 0; i < c.size(); i++) {
                /* Travels the ArrayList classes which are the classes of a day*/
                for (int j = 0; j < classes.size(); j++) {
                    /* Checks if the class in the classes arraylist is also in the arraylist of classes of the student and sees if the final hour of the actual class
                    * of the student is the same as the starting hour of the next class of the student*/
                    if (FH == classes.get(j).getSh() && c.contains(classes.get(j).getSg())) {
                        // d varibale will get the value that the calculateDistance method will return, which is the distance between the two buildings of the classes
                        d = calculateDistance(distances, aClassroom, classes.get(j).getCr());
                        // Need to change the value of i so we can break the two cycles
                        i = c.size();
                        break;
                    }
                }
            }
        }
        return d;
    }

    /**
     * Method that calculates the distances between two buildings
     * @param distances matrix of ints of 40x40 positions
     * @param aClassroom the classroom that the student is at the moment
     * @param fClassroom the classroom that the student will move in the future for the next class
     * @return a value int, meaning a distance
     */
    private static int calculateDistance(int[][] distances, String aClassroom, String fClassroom){

        String s1, s2; // Strings that will contain the number of the building
        int b1, b2; // Ints that will have the number of the building and will be use to search into the matrix
        int d = 0; // varible that will contain the distance between two buildings

        /* Each if sees the length of the strings because depending on them, the number of the building can be catch. If the length of the string is four means
        * that the building number is in the first position of the string, but if the length is five the number of the building is the substring of the first
        * and second position of the string.
        * Inside each if the process is the same, it first puts the number of the building into a string, then we parse it into an int so we can put them into
        * another variable and then we search in the matrix distances the distance between those buildings*/

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
        return d; // returns the value of d
    }

    /**
     * In charge of readig the distances between buildings
     * @param file name of the file with the distances
     * @param distances matrix of 40x40 positions, each position means a building of the file
     */
    private static void organizarMatriz(String file, int[][] distances){
        BufferedReader bufferLectura = null;
        try {
            // Opens the .csv so it can be read
            bufferLectura = new BufferedReader(new FileReader(file));
            // Reads the first line of the file
            String line = bufferLectura.readLine();
            // While that executes while the new line is not null, which means the end of the file
            while (line != null) {
                // Separates the read line with the previously define separator
                String[] campos = line.split(",");
                // Puts the "distances" between buildings inside a matrix(each position of the matrix represents a building) so it can be a simetric matrix
                distances[Integer.parseInt(campos[0])][Integer.parseInt(campos[1])] = Integer.parseInt(campos[2]);
                distances[Integer.parseInt(campos[1])][Integer.parseInt(campos[0])] = Integer.parseInt(campos[2]);
                line = bufferLectura.readLine(); // Reads the next line and repeats the process
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
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

    /**
     * Method in charge of reading the academic program file
     * @param matGrup HashMap that has as key: Day, Starting hour and Finishing hour and as value an ArrayLisy of objects Group with the information of a class
     * @param Days Days Array of ArrayList of objects Group
     * @param file file name of the file to be read
     * @return returns the Days Array
     */
    private static ArrayList<Group>[] leerPa (HashMap<String, ArrayList<Group>> matGrup, ArrayList<Group>[] Days, String file) {

        /* Adds in position of the array a new ArrayList of Groups*/
        for (int i = 0; i < Days.length; i++){
            Days[i] = new ArrayList<Group>();
        }

        BufferedReader bufferPa = null;
        try {
            bufferPa = new BufferedReader(new FileReader(file));
            String line = bufferPa.readLine();
            // While that executes while the new line is not null, which means the end of the file
            while (line != null) {
                String[] data;
                // Separates the read line with the previously define separator
                data = line.split(",");
                /* Checks if the length of the array obtain by the split is of length 7, which means that we have all the data we need of the file and
                 * the line doesnt have errors with the commas */
                if (data.length == 7) {
                    // Checks if the class has a classroom defined or if the class was programed on Sunday, in which case is not necessary to get them
                    if (data[6].equals("00000") || data[3].equals("domingo")) {
                    } else {

                        // Separates the read line with the previously define separator so we can convert the hours to an Int
                        String[] hi = data[4].split(":");
                        String[] hf = data[5].split(":");


                        if(matGrup.containsKey(data[3]+","+Integer.parseInt(hi[0]+hi[1])+","+Integer.parseInt(hf[0]+hf[1]))){
                            Group tempGr = new Group(data[0] + "," + data[1], data[6]);
                            matGrup.get(data[3]+","+Integer.parseInt(hi[0]+hi[1])+","+Integer.parseInt(hf[0]+hf[1])).add(tempGr);
                        }
                        else {
                            ArrayList<Group> temp = new ArrayList<>();
                            Group tempGr = new Group(data[0] + "," + data[1], data[6]);
                            temp.add(tempGr);
                            matGrup.put(data[3]+","+Integer.parseInt(hi[0]+hi[1])+","+Integer.parseInt(hf[0]+hf[1]), temp);
                        }
                        /* Checks which day the class is and the adds the group to the arraylist of groups inside the array called Days and adds it*/
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

    /**
     * Method in charge of reading the file aulas.csv
     * @param aulas HashMap that has as key an String (the id of the classroom) and as value an object of Type Aula, which have the rest of the information of a classroom
     * @param file name of the file to be read
     */
    private static void leerAulas (HashMap<String, Aulas> aulas, String file) {

        BufferedReader studentsMa = null;

        try {
            studentsMa = new BufferedReader(new FileReader(file));
            // Will split the line by the symbol defined
            String line = studentsMa.readLine();
            // While that executes while the new line is not null, which means the end of the file
            while (line != null) {
                // Will split the line by the symbol defined
                String[] data = line.split(",");
                // Checks if the lenth of the array containing the splitted string is long enough, which means it has al the information
                if(data.length == 4){
                    /* There are some values that appear in the secong position of the splitted string and we dont knoe what do they mean, so we are changing them
                    * thinking that it means: not estimated capacity*/
                    if(data[2].equals("N/A")){
                        data[2] = "1000";
                    }
                    /* Creates a new Aulas object and the adds it to the HashMap aulas*/
                    Aulas classroom = new Aulas(data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3]));
                    aulas.put(data[0], classroom);
                }
                line = studentsMa.readLine(); // Reads the next line and repeats the process
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

    /**
     * Method in charge of treading the "estudiantes.csv" file
     * @param access HashMap that contains as key the id of a student and as value a 1 or a 0, meaning if the student needs special accessibility
     * @param file the file that will be read
     */
    private static void studentsReader (HashMap<Integer, Integer> access, String file) {

        BufferedReader students = null;

        try {
            students = new BufferedReader(new FileReader(file));
            String line = students.readLine();
            // While that executes while the new line is not null, which means the end of the file
            while (line != null) {
                // Will split the line by the symbol defined
                String [] data = line.split(",");
                // Putting the key and the value into the HashMap
                access.put(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                line = students.readLine(); // Reads the next line and repeats the process
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

    /**
     * Method that reads the mat file and puts all the information in the given HashMaps
     * @param sgS HashMap that will contain as value a list of Strings of all the students from a class and as key the concatenation of the string subject and group
     * @param file the file that will be read
     * @param sSG HashMap that will have as key the id of a student and as value an ArrayList of Strings that contains all the classes from a student
     */
    private static void matReader (HashMap<String, ArrayList<Integer>> sgS, String file, HashMap<Integer, ArrayList<String>> sSG) {

        BufferedReader mats = null;

        try {
            mats = new BufferedReader(new FileReader(file));
            String line = mats.readLine();
            // While that executes while the new line is not null, which means the end of the file
            while (line != null) {
                String [] data = line.split(",");
                /*Adding to the HashMap sgS, key: String concatenated (subject and group) value: ArrayList of Integer(Student), but first make sure that
                 the key exist an has something inside, if not creates the key and then adds the value
                 */
                if(sgS.containsKey(data[1]+ "," + data[2])){
                    sgS.get(data[1]+","+data[2]).add(Integer.parseInt(data[0]));
                }else{
                    ArrayList<Integer> sts = new ArrayList<>();
                    sts.add(Integer.parseInt(data[0]));
                    sgS.put(data[1]+ "," + data[2], sts);
                }
                /*Adding to the HashMap sSG, key: Student and value: ArrayList of Strings concatenated(subject and group),
                 * makes sure that the key exist and has something in it, if not creates the key and adds the value
                 * */
                if(sSG.containsKey(Integer.parseInt(data[0]))){
                    sSG.get(Integer.parseInt(data[0])).add(data[1]+","+data[2]);
                }else{
                    ArrayList<String> sts = new ArrayList<>();
                    sts.add(data[1]+","+data[2]);
                    sSG.put(Integer.parseInt(data[0]), sts);
                }
                line = mats.readLine(); // Reads the next line and repeats the process
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