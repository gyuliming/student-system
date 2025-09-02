import java.io.*;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Comparator;

public class SortedStudent {

    private HashMap<String, Student> students = new HashMap<>();
    private TreeSet<Student> sortedSet;         //중복 없는 집합 + 자동 정렬 상태로 저장
    //해당 경로에 파일 객체 생성
    private File outputFile = new File("C:/Temp/orderByAvg.dat");

    // 1. student.dat 읽기
    public void loadObjectFromFile() {
        File file = new File("C:/Temp/student.dat");

        if (!file.exists()) {
            System.out.println("[오류] student.dat 파일이 존재하지 않습니다.");
            return;
        }

        //예외 발생 시 안전하게 닫히도록 구현
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();

            if (obj instanceof HashMap) {
                students = (HashMap<String, Student>) obj;  //맞으면 형변환 후 저장
                System.out.println("불러온 학생 수: " + students.size());
            } else {
                System.out.println("[오류] 파일 내용이 올바르지 않습니다.");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[오류] 파일 읽기 중 문제 발생");
            e.printStackTrace();
        }
    }

    // 2. TreeSet 생성 및 평균 기준 정렬
    public void createTreeSet() {
                                //학생 데이터가 없는 경우
        if (students == null || students.isEmpty()) {
            System.out.println("[알림] 등록된 학생 정보가 없습니다.");
            return;
        }

        //정렬 기준을 정의하는 Comparator 인터페이스를 람다식으로 호출
//        Comparator<Student> comparator = (s1, s2) -> {
//            //평균 비교 오름차순
//            int cmp = Double.compare(s1.getAverage(), s2.getAverage());
//            return (cmp != 0) ? cmp : s1.getName().compareTo(s2.getName());     //이름기준
//        };

        sortedSet = new TreeSet<>(new StudentComparator());      //TreeSet 객체 생성 시 comparator에 전달
        sortedSet.addAll(students.values());        //Student 객체들을 TreeSet에 추가

        System.out.println("정렬 규칙: 평균 ASC, 평균 동률이면 이름 사전순 ASC");
    }

    // 3. 콘솔에 상위 10명 미리보기
    public void printResult() {
        //정렬 데이터 검증
        if (sortedSet == null || sortedSet.isEmpty()) {
            System.out.println("[알림] 정렬된 데이터가 없습니다.");
            return;
        }

        System.out.println("저장 대상(미리보기 상위 10명):\n");


        int count = 0;
        // 정렬된 학생들을 순서대로 꺼냄
        for (Student s : sortedSet) {
            System.out.printf("- %s (평균 %.2f)\n", s.getName(), s.getAverage());
            if (++count >= 10) break;
        }
    }

    // 4. orderByAvg.dat로 직렬화 저장
    public void outputObject() {
        if (sortedSet == null || sortedSet.isEmpty()) {
            System.out.println("[알림] 저장할 데이터가 없습니다.");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {
            oos.writeObject(sortedSet);
            System.out.println("\n결과 파일: " + outputFile.getAbsolutePath());
            System.out.println("[완료] 정렬된 결과를 파일로 저장했습니다.");
        } catch (IOException e) {
            System.out.println("[오류] 정렬 결과 저장 중 문제 발생");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //생성자 호출 후 객체 불러오기
        SortedStudent sortedStudent = new SortedStudent();
        sortedStudent.loadObjectFromFile();
        sortedStudent.createTreeSet();
        sortedStudent.printResult();
        sortedStudent.outputObject();
    }
}
