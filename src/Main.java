import java.util.Scanner;
import Sys.DataTrans.*;
import Sys.Std.*;
import Sys.course.*;
public class Main {


    public static void main(String[] args) {

        Display_menu();

    }


    public static void Display_menu() {
        Scanner scan = new Scanner(System.in);
        boolean addMore = true;
        String name;

        Student s;
        Course course = new Course();

        while (addMore) {
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

                                System.out.print("Enter the student name: ");
                                name = scan.nextLine().trim();
                                if ( name.matches("[a-zA-Z ]+") && name.contains(" ")) {
                                    System.out.println("___________Courses___________");
                                    for(String sub : course.CoursesList()){
                                        System.out.println(sub);
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
                            if(s.Sys_Empty()){
                                System.out.println(s.Search_student_from_IdOrName());
                            }else{
                                System.out.println("There is no Any Students on the System");
                            }

                            break;
                        case "4":
                            s = new Student();
                            if(s.Sys_Empty()){
                                s.Display_All_StudentsNames();
                                System.out.println("Enter The student's Id: ");
                                String id = scan.nextLine();
                                s.Delete_student_from_Id(id);
                                System.out.println("Deleted Successfully. ");
                            }else{
                                System.out.println("There is no Any Students on the System");
                            }

                            break;
                        case "5":
                            s = new Student();
                            if(s.Sys_Empty()){
                                s.Display_All_StudentsNames();
                            }else{
                                System.out.println("There is no Any Students on the System");
                            }
                            break;
                        case "6":
                            s = new Student();
                            if(s.Sys_Empty()){
                                String[] Sub =  s.Display_All_Student_Courses().split(",");
                                System.out.println("Student's Subject: ");
                                for (String C : Sub){
                                    System.out.println(C);
                                }

                            }else{
                                System.out.println("There is no Any Students on the System");
                            }
                            break;
                        case "7":
                            s = new Student();
                            if(s.Sys_Empty()){
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
                                            Transfer.SendData(s.Search_student_from_IdOrName());
                                            valid = false;
                                            break;
                                        case "2":
                                            Transfer = new Email();
                                            Transfer.SendData(s.Search_student_from_IdOrName());
                                            valid = false;
                                            break;
                                        default:
                                            System.out.println("Please Enter a valid Option. ");
                                    }
                                }

                            }else{
                                System.out.println("There is no Any Students on the System");
                            }
                            break;
                        case "8":
                            s = new Student();
                            if(s.Sys_Empty()){
                                s.UpdateStudent();
                            }else{
                                System.out.println("There is no Any Students on the System");
                            }
                            break;
                        case "9":
                            addMore = false;
                            break;
                    }
                    System.out.println("__________________________________");

                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");

            }
        }

    }

}
