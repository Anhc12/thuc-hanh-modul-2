public class ClassInfo {
    private String id;
    private String name;
    private String teacherId;

    public ClassInfo(String id, String name, String teacherId) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getTeacherId() { return teacherId; }
}