import java.io.Serializable;
import java.util.Comparator;

public class StudentComparator implements Comparator<Student>, Serializable {
    @Override
    public int compare(Student s1, Student s2) {
        int result = Double.compare(s1.getAverage(), s2.getAverage());
        if (result == 0) return s1.getName().compareTo(s2.getName());
        return result;
    }
}