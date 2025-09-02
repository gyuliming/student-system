import lombok.Getter;

import java.io.Serializable;
import java.util.List;
@Getter

public class Student implements Serializable {

    //2. 필요한 필드(변수) 선언
    private String name;
    private List<Integer> record;
    private int score;
    private int total;
    private double average;
    private String grade;
    //클래스 버전을 수동으로 관리하는 번호로 자바가 체크
    private static final long serialVersionUID = 1L;    //객체를 파일에  저장했다가 나중에 불러올 때 클래수 구조가 바뀌었는지 자바가 자동으로 체크

    //이름과 점수 리스트를 받도록 생성자를 정의하여 데이터를 받고 calculate()메서드 호출
    public Student(String name, List<Integer> record) {
        this.name = name;
        this.record = record;
        calculate();
    }

    //성적 계산 calculate() 메서드 구현
    public void calculate() {
        int sum = 0;
        for (int score : record) {
            sum += score;
        }
        total = sum;


        average = (double) sum / record.size();

        if (average >= 90) {
            grade = "A";
        } else if (average >= 80) {
            grade = "B";
        } else if (average >= 70) {
            grade = "C";
        } else if (average >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
    }


    //toString() 메서드로 재정의하여 보기 좋게 출력
    public String toString() {
        return "이름 : " + name + ", 총점 : " + total + ", 평균 : " + average + ", 학점 : " + grade;
    }
}
