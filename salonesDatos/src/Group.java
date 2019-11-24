public class Group {
    private String sg; // SubjectGroup
    private int professor;
    private  String day;
    private int sh; // starting hour
    private int fh; // finishing hour
    private String cr; // class room = aula

    public Group(String sg, int professor,String day, int sh, int fh, String cr) {
        this.sg = sg;
        this.professor = professor;
        this.day = day;
        this.sh = sh;
        this.fh = fh;
        this.cr = cr;
    }

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
}
