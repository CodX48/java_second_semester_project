import java.awt.*;
import java.util.Scanner;
import Sys.DataTrans.*;
import Sys.Std.*;
import Sys.course.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Student s = new Student();
        DataTransfer Email;
        Email = new Email();
        //Display_menu();
        Email.SendData(s.Search_student_from_IdOrName());
    }
    public static void Display_menu() {
        boolean addMore = true;
        Scanner scan = new Scanner(System.in);
        String name;
        Course course = new Course();
        Student s;

        while (addMore) {
            try {
                System.out.println("1-AddStudent");
                System.out.println("2-AddStudent And enroll Subjects");
                System.out.println("3-Search for student With ID/Name");
                System.out.println("4-Delete Student");
                System.out.println("5-Display_All_Students");
                System.out.println("6-Display_All_StudentCourses");
                System.out.println("7-Exit.");
                System.out.print("Enter: ");
                int x = scan.nextInt();
                scan.nextLine();
                if (x >= 1 && x <= 7) {
                    switch (x) {
                        case 1:
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
                        case 2:
                            while (true) {

                                System.out.print("Enter the student name: ");
                                name = scan.nextLine().trim();
                                if ( name.matches("[a-zA-Z ]+") && name.contains(" ")) {
                                    s = new Student(name, course.Enrolled_Courses());
                                    s.AddStudent(true);


                                    break;
                                }
                                else {
                                    System.out.println("Please enter a valid name (Full Name, only characters ).");
                                }
                            }
                            break;
                        case 3:
                            s = new Student();
                            System.out.println(s.Search_student_from_IdOrName());
                            break;
                        case 4:
                            s = new Student();
                            s.Delete_student_from_IdOrName();
                            break;
                        case 5:
                            s = new Student();
                            s.Display_All_StudentsNames();
                            break;
                        case 6:
                            s = new Student();
                            s.Display_All_Student_Courses();
                            break;
                        case 7:
                            addMore = false;
                            break;
                    }
                    System.out.println("__________________________________");
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 7.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                scan.next();
            }
        }
        scan.close();

    }
}
