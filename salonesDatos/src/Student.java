public class Student {
    private int name;
    private byte haveD;

    public Student(int name, byte haveD) {
        this.name = name;
        this.haveD = haveD;
    }

    public int getName() {
        return name;
    }

    public byte getHaveD() {
        return haveD;
    }

    public void setName(int name) {
        this.name = name;
    }

    public void setHaveD(byte haveD) {
        this.haveD = haveD;
    }
}
