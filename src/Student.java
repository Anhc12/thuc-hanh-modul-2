import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Student extends Person {
    private String id;
    private String classId;

    public Student(String id, String name, String birthDate, String gender, String phone, String classId) throws ParseException {
        super(name, birthDate, gender, phone);
        this.id = id;
        this.classId = classId;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "ID: " + id + ", Tên: " + name + ", Ngày sinh: " + sdf.format(birthDate) +
                ", Giới tính: " + gender + ", Số điện thoại: " + phone + ", Mã lớp: " + classId;
    }
}