public class Aulas {

    private String type; // Means the type of classRoom the class is
    private int capacity; // Means the capacity of people that the room can have
    private int access; // Means if the classroom has an easy access for disable people

    // Constructor of the object
    public Aulas(String type, int capacity, int access) {
        this.type = type;
        this.capacity = capacity;
        this.access = access;
    }

    // Methods that return the information you are asking for
    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAccess() {
        return access;
    }

    // Methods that change the values of the attributes of the object
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
