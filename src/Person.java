import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Person {
    protected String name;
    protected Date birthDate;
    protected String gender;
    protected String phone;

    public Person(String name, String birthDate, String gender, String phone) throws ParseException {
        this.name = name;
        this.birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) throws ParseException {
        this.birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
    }
    public void setGender(String gender) { this.gender = gender; }
    public String getGender() { return gender; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPhone() { return phone; }

    public abstract String getId();
}