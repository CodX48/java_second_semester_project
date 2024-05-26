import java.util.Scanner;
import Sys.DataTrans.*;
import Sys.Std.*;
import Sys.course.*;
public class Main {


    public static void main(String[] args){
        Display_menu();
    }

    public static void Display_menu(){
        boolean addMore = true;
        String name;
        Student s;
        Course course;

        while (addMore) {
            Scanner scan = new Scanner(System.in);
            try {
                System.out.println("1-AddStudent");
                System.out.println("2-AddStudent And enroll Subjects");
                System.out.println("3-Search for student With ID/Name");
                System.out.println("4-Delete Student");
                System.out.println("5-Display_All_Students");
                System.out.println("6-Display_All_StudentCourses");
                System.out.println("7-Send Data throw Student Gmail");
                System.out.println("8-Update student information");
                System.out.println("9-Exit.");
                System.out.print("Enter: ");

                if (scan.hasNextLine()) {
                    String x = scan.nextLine();

                    switch (x) {
                        case "1":
                            while (true) {
                                System.out.print("Enter the student name: ");
                                name = scan.nextLine().trim();
                                if (name.matches("[a-zA-Z ]+") && name.contains(" ")) {
                                    s = new Student(name);
                                    s.AddStudent(false);
                                    break;
                                }
                                else {
                                    System.out.println("Please enter a valid name (Full Name, only characters ).");
                                }
                            }

                            break;
                        case "2":
                            while (true) {
                                course = new Course();
                                System.out.print("Enter the student name: ");
                                name = scan.nextLine().trim();
                                if ( name.matches("[a-zA-Z ]+") && name.contains(" ")) {
                                    System.out.println("___________Courses___________");
                                    for(Course sub : course.CoursesList()){
                                        System.out.println(sub.toString());
                                    }
                                    s = new Student(name, course.Enrolled_Courses());
                                    s.AddStudent(true);

                                    break;
                                }
                                else {
                                    System.out.println("Please enter a valid name (Full Name, only characters ).");
                                }
                            }
                            break;
                        case "3":
                            s = new Student();
                           s.Sys_Empty();
                                String Student = s.searchStudentFromIdOrName().toString();
                                    String[] StudentInfo = Student.split("_");
                            try{
                                System.out.println("Student Name: " + StudentInfo[1].toUpperCase() + ", Student Id: " + StudentInfo[0]);
                                System.out.println("\nStudent Subjects: ");
                                System.out.println(StudentInfo[2]);
                            }catch (Exception e){
                                System.out.println("Student Name: " + StudentInfo[1].toUpperCase() + ", Student Id: " + StudentInfo[0]);
                            }

                            break;
                        case "4":
                                s = new Student();
                                s.Sys_Empty();
                                s.Delete_student_from_Id(s.searchStudentFromIdOrName().getIdOfStudent());
                                System.out.println("Deleted Successfully. ");
                            break;
                        case "5":
                                 s = new Student();
                                 s.Sys_Empty();
                                 s.Display_All_StudentsNames();
                            break;
                        case "6":
                            s = new Student();
                           s.Sys_Empty();
                                System.out.println(s.Display_All_Student_Courses());
                            break;
                        case "7":
                               s = new Student();
                               s.Sys_Empty();
                                DataTransfer Transfer;
                                boolean valid = true;
                                while (valid) {
                                    Scanner scanner = new Scanner(System.in);
                                    System.out.println("1-Send With A Text File.");
                                    System.out.println("2-Send All In Email.");
                                    System.out.print("Enter:");
                                    switch (scanner.nextLine()) {
                                        case "1":
                                            Transfer = new ToFile();
                                            Transfer.SendData(s.searchStudentFromIdOrName().toString());
                                            valid = false;
                                            break;
                                        case "2":
                                            Transfer = new Email();
                                            Transfer.SendData(s.searchStudentFromIdOrName().toString());
                                            valid = false;
                                            break;
                                        default:
                                            System.out.println("Please Enter a valid Option. ");
                                    }
                                }
                            break;
                        case "8":
                            s = new Student();
                           s.Sys_Empty();
                                s.UpdateStudent();
                            break;
                        case "9":
                            addMore = false;
                            break;
                    }
                    System.out.println("__________________________________");

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("__________________________________");
            }

        }

    }

}
