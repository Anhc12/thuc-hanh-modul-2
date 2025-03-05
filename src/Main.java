import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {
    private static StudentManager manager = new StudentManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: addStudent(); break;
                case 2: deleteStudent(); break;
                case 3: viewStudents(); break;
                case 4: searchStudent(); break;
                case 5:
                    System.out.println("Thoát chương trình.");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Quản lý sinh viên ---");
        System.out.println("1. Thêm mới sinh viên");
        System.out.println("2. Xóa sinh viên");
        System.out.println("3. Xem danh sách sinh viên");
        System.out.println("4. Tìm kiếm sinh viên");
        System.out.println("5. Thoát");
        System.out.print("Chọn chức năng: ");
    }

    private static void addStudent() {
        String id = manager.getNextStudentId();

        String name = inputWithValidation("Tên sinh viên (4-50 ký tự): ",
                s -> s.length() >= 4 && s.length() <= 50);

        String birthDate = inputWithValidation("Ngày sinh (dd/MM/yyyy): ",
                s -> {
                    try {
                        new SimpleDateFormat("dd/MM/yyyy").parse(s);
                        return true;
                    } catch (ParseException e) {
                        return false;
                    }
                });

        String gender = inputWithValidation("Giới tính: ", s -> !s.isEmpty());

        String phone = inputWithValidation("Số điện thoại (10 số, bắt đầu 090/091): ",
                s -> s.matches("^(090|091)\\d{7}$") && manager.isPhoneUnique(s));

        String classId = inputWithValidation("Mã lớp học: ", manager::isClassExist);

        try {
            Student sv = new Student(id, name, birthDate, gender, phone, classId);
            manager.add(sv);
            System.out.println("Thêm sinh viên thành công.");
        } catch (ParseException e) {
            System.out.println("Lỗi định dạng ngày sinh.");
        }
    }

    private static String inputWithValidation(String prompt, Predicate<String> validator) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (validator.test(input)) {
                return input;
            }
            System.out.println("Dữ liệu không hợp lệ. Vui lòng nhập lại.");
        }
    }

    private static void deleteStudent() {
        System.out.print("Nhập ID sinh viên cần xóa: ");
        String id = scanner.nextLine();
        try {
            Student sv = manager.findById(id);
            if (sv != null) {
                System.out.print("Bạn có chắc muốn xóa? (Yes/No): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("Yes")) {
                    manager.delete(id);
                    System.out.println("Đã xóa sinh viên.");
                } else {
                    System.out.println("Hủy xóa.");
                }
            } else {
                throw new NotFoundStudentException("Sinh viên không tồn tại.");
            }
        } catch (NotFoundStudentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewStudents() {
        List<Student> students = manager.getAll();
        if (students.isEmpty()) {
            System.out.println("Danh sách sinh viên trống.");
        } else {
            for (Student sv : students) {
                String className = manager.getClassName(sv.getClassId());
                System.out.println(sv + ", Tên lớp: " + className);
            }
        }
    }

    private static void searchStudent() {
        System.out.print("Nhập từ khóa tìm kiếm: ");
        String keyword = scanner.nextLine();
        manager.search(keyword);
    }
}