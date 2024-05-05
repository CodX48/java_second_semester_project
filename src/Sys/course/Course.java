package Sys.course;
import java.util.ArrayList;
import java.util.Scanner;

public class Course {
    private String NameOfCourse;
    private String Id;

    public Course (String nameOfCourse,String IdOfCourse){
        this.NameOfCourse = nameOfCourse;
        this.Id = IdOfCourse;
    }

    public Course(){

    }

    @Override
    public String toString(){
        return   NameOfCourse+":"+Id;
    }

    private ArrayList<String> CoursesList(){
        ArrayList<String> CoursesList = new ArrayList<>();
        CoursesList.add(new Course("Programming II","1400121004").toString());
        CoursesList.add(new Course("Calculus II","1400121002").toString());
        CoursesList.add(new Course("Physics II","1400121006").toString());
        CoursesList.add(new Course("Discrete Mathematics","1400121008").toString());
        CoursesList.add(new Course("Linear Algebra","1400121010").toString());
        CoursesList.add(new Course("German II","5010111012").toString());
        CoursesList.add(new Course("Atatürk","5010121001").toString());
        CoursesList.add(new Course("Turkish II","5010121004").toString());
        return CoursesList;
    }

    public ArrayList<String> Enrolled_Courses(){
        ArrayList<String> Enrolled = new ArrayList<>();
        System.out.println("_________________Enroll Courses_________________");
        System.out.println("___________Your Courses___________");
        for(String sub : CoursesList()){
            System.out.println(sub);
        }
        try {
            Scanner scan = new Scanner(System.in);
            while (true) {
                System.out.print("Please Enter The Subject ID: ");
                String id = scan.nextLine();

                boolean found = false;
                for (String line : CoursesList()) {
                    String[] c_Id_Data = line.split(":");
                    if (c_Id_Data.length >= 2 && id.equals(c_Id_Data[1])) {
                        Enrolled.add(line);
                        System.out.println("You added: " + line);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Subject ID not found. Please try again.");
                }
                System.out.println("Do you want to enroll more subjects? [Y/N]");
                String add = scan.nextLine();
                if (add.equalsIgnoreCase("N")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        return Enrolled;
    }
}
