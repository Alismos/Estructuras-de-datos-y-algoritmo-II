public class Aulas {
    private String id;
    private String type;
    private int capacity;
    private int access;

    public Aulas(String id, String type, int capacity, int access) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.access = access;
    }

    //public Aulas(String datum, String datum1, int capacity, int access) {
    //}

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAccess() {
        return access;
    }

    public void setId(String id) { this.id = id; }

    public void setType(String type) {
        this.type = type;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setAccess(int access) {
        this.access = access;
    }
}
