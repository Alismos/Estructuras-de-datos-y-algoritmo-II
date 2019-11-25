public class Group {
    private String sg; // SubjectGroup, combination of concatenated Strings that make an unique identification
    private int professor; // Id of the professor that is going to give the class
    private  String day; // Day that the class is given
    private int sh; // starting hour of the class
    private int fh; // finishing hour of the class
    private String cr; // class room = aula
    private int coe; // Int that helps us to know which is the coefficient of the distances that the students of the class have to walk, by making a mathematic operation

    // Constructors of the object Group
    public Group(String sg, int professor,String day, int sh, int fh, String cr) {
        this.sg = sg;
        this.professor = professor;
        this.day = day;
        this.sh = sh;
        this.fh = fh;
        this.cr = cr;
    }
    public Group(String sg, String cr) {
        this.sg = sg;
        this.cr = cr;
    }
    public Group() {

    }

    // Methods that return the information you are asking for

    public String getSg() {
        return sg;
    }

    public int getProfessor() {
        return professor;
    }

    public String getDay() {
        return day;
    }

    public int getSh() {
        return sh;
    }

    public int getFh() {
        return fh;
    }

    public String getCr() { return cr; }

    public int getCoe() { return coe; }

    // Methods that change the values of the attributes of the object
    public void setSg(String sg) {
        this.sg = sg;
    }

    public void setProfessor(int professor) {
        this.professor = professor;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setSh(int sh) {
        this.sh = sh;
    }

    public void setFh(int fh) {
        this.fh = fh;
    }

    public void setCr(String cr) { this.cr = cr; }

    public void setCoe(int coe) { this.coe = coe; }

}