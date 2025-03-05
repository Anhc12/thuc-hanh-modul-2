import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StudentManager implements IManager<Student> {
    private List<Student> students;
    private List<ClassInfo> classes;
    private static final String STUDENT_FILE = "data/students.csv";
    private static final String CLASS_FILE = "data/batchs.csv";

    public StudentManager() {
        students = new ArrayList<>();
        classes = new ArrayList<>();
        loadClasses();
        loadStudents();
    }

    private void loadClasses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CLASS_FILE))) {
            String line = reader.readLine(); // Bỏ qua dòng tiêu đề
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    classes.add(new ClassInfo(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi tải danh sách lớp: " + e.getMessage());
        }
    }

    private void loadStudents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line = reader.readLine(); // Bỏ qua dòng tiêu đề
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    try {
                        students.add(new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                    } catch (ParseException e) {
                        System.out.println("Lỗi ngày sinh: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi tải danh sách sinh viên: " + e.getMessage());
        }
    }

    private void saveStudents() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_FILE))) {
            writer.println("id,name,birthDate,gender,phone,classId"); // Ghi lại dòng tiêu đề
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Student sv : students) {
                writer.println(sv.getId() + "," + sv.getName() + "," + sdf.format(sv.getBirthDate()) + "," +
                        sv.getGender() + "," + sv.getPhone() + "," + sv.getClassId());
            }
        } catch (IOException e) {
            System.out.println("Lỗi lưu sinh viên: " + e.getMessage());
        }
    }

    @Override
    public void add(Student sv) {
        students.add(sv);
        saveStudents();
    }

    @Override
    public void delete(String id) {
        Student sv = findById(id);
        if (sv == null) {
            throw new NotFoundStudentException("Sinh viên không tồn tại.");
        }
        students.remove(sv);
        saveStudents();
    }

    @Override
    public List<Student> getAll() {
        return students;
    }

    @Override
    public Student findById(String id) {
        for (Student sv : students) {
            if (sv.getId().equals(id)) {
                return sv;
            }
        }
        return null;
    }

    @Override
    public void search(String keyword) {
        for (Student sv : students) {
            if (sv.getName().toLowerCase().contains(keyword.toLowerCase())) {
                String className = getClassName(sv.getClassId());
                System.out.println(sv + ", Tên lớp: " + className);
            }
        }
    }

    public String getClassName(String classId) {
        for (ClassInfo lop : classes) {
            if (lop.getId().equals(classId)) {
                return lop.getName();
            }
        }
        return "Không xác định";
    }

    public boolean isClassExist(String classId) {
        for (ClassInfo lop : classes) {
            if (lop.getId().equals(classId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPhoneUnique(String phone) {
        for (Student sv : students) {
            if (sv.getPhone().equals(phone)) {
                return false;
            }
        }
        return true;
    }

    public String getNextStudentId() {
        if (students.isEmpty()) {
            return "1";
        }
        int maxId = 0;
        for (Student sv : students) {
            int id = Integer.parseInt(sv.getId());
            if (id > maxId) {
                maxId = id;
            }
        }
        return String.valueOf(maxId + 1);
    }
}