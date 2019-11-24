public class Student {
    private int id;
    private String subject;
    private String group;

    public Student(int id, String subject, String group) {
        this.id = id;
        this.subject = subject;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getGroup() {
        return group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) { this.subject = subject; }

    public void setGroup(String group) {
        this.group = group;
    }
}
