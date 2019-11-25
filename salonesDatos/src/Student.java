public class Student {
    private int id; // Means the number that represents each student
    private String subject; // The subject of the class
    private String group; // The group the student is in seeing the class

    // Constructors of the object Student
    public Student(int id, String subject, String group) {
        this.id = id;
        this.subject = subject;
        this.group = group;
    }

    // Methods that return the information you are asking for
    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getGroup() {
        return group;
    }

    // Methods that change the values of the attributes of the object
    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) { this.subject = subject; }

    public void setGroup(String group) {
        this.group = group;
    }
}
