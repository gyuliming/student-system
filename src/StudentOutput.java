package mission;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class StudentOutput {
    private HashMap<String, Student> students = new HashMap<> ();
    private ArrayList<Student> datas = new ArrayList<> ();

    public void loadObjectFromFile(){
        //파일 읽어오기
        File file = new File("C:/Temp/student.dat");

        if (!file.exists()){
            System.out.println("[오류] 데이터 파일이 존재하지 않습니다.");
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();

            //객체 타입 확인 후 형 변환
            if (obj instanceof HashMap){
                students = (HashMap<String, Student>) obj;

                //calculate 불러온 후 평균, 총점, 학점 재계산
                for (Student student : datas) {
                    student.calculate();
                }

            } else {
                System.out.println("[오류] 파일 내용이 올바르지 않습니다.");
            }
            ois.close();
            fis.close();

        } catch (FileNotFoundException e) {
            System.out.println("[오류] 파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            System.out.println("[오류] 파일 읽기 중 문제 발생 : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[오류] 클래스 정의를 찾을 수 없습니다.");
        }
    }

    public void rearrangeData() {
        if (students == null || students.isEmpty()) {
            System.out.println("[알림] 등록된 학생 정보가 없습니다.");
            return;
        }

        //정렬을 위해 리스트에 담음
        datas = new ArrayList<>(students.values());
        //오름차순 정렬
        datas.sort((s1, s2) -> {
            int cmp = Double.compare(s1.getAverage(), s2.getAverage()); // 음수면 s1이 앞, 양수면 s2가 앞
            if (cmp == 0) return s1.getName().compareTo(s2.getName()); // 평균이 같을 경우 이름으로 오름차순 정렬
            return cmp;
        });
    }

    public void printInfo() {
        if (datas == null || datas.isEmpty()) {
            System.out.println("[알림] 출력할 학생 정보가 없습니다.");
            return;
        }

        System.out.println("[평균 오름차순 성적표]\n");

        int idx = 1;
        for (Student s : datas) {
            System.out.println(idx++ + ") " + s.getName());
            System.out.println("   점수: " + s.getRecord());
            System.out.println("   총점: " + s.getTotal() +
                    ", 평균: " + s.getAverage() +
                    ", 학점: " + s.getGrade() + "\n");
        }
    }

    public static void main(String[] args) {
        StudentOutput output = new StudentOutput(); //생성자 불러오기
        output.loadObjectFromFile();
        output.rearrangeData();
        output.printInfo();
    }
}
