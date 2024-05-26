package Sys.course;
import java.util.ArrayList;
import java.util.Scanner;

public class Course  {
    private String NameOfCourse;
    private String Id;

    public Course (String nameOfCourse,String IdOfCourse){
        this.NameOfCourse = nameOfCourse;
        this.Id = IdOfCourse;
    }

    public Course(){}

    public String getId(){
        return Id;
    }
    public String getNameOfCourse(){
        return NameOfCourse;
    }

    //this is an override method for controlling the way of writing the courses (Course:id)
    @Override
    public String toString(){
        return   NameOfCourse+":"+Id;
    }

    // this arraylist will return a list of courses for Enrolling
    public ArrayList<Course> CoursesList(){
        ArrayList<Course> CoursesList = new ArrayList<>();
        CoursesList.add(new Course("Programming II","1400121004"));
        CoursesList.add(new Course("Calculus II","1400121002"));
        CoursesList.add(new Course("Physics II","1400121006"));
        CoursesList.add(new Course("Discrete Mathematics","1400121008"));
        CoursesList.add(new Course("Linear Algebra","1400121010"));
        CoursesList.add(new Course("German II","5010111012"));
        CoursesList.add(new Course("Atatürk","5010121001"));
        CoursesList.add(new Course("Turkish II","5010121004"));
        return CoursesList;
    }

    //this function for enrolling the subject that the student will take (it will return a list of courses)
    public ArrayList<Course> Enrolled_Courses(){
        ArrayList<Course> Enrolled = new ArrayList<>();
        try {
            Scanner scan = new Scanner(System.in);
            while (true) {
                System.out.print("Enter Subject Id Or exit: ");
                String id = scan.nextLine();

                if (id.equalsIgnoreCase("exit")) {
                    break;
                }
                boolean exist = false;
                //this loop will search from the id to enroll the subjects
                for (Course line : CoursesList()) {
                    if ( line.Id.equals(id)) {
                        for(Course course : Enrolled){
                            if(course.Id.equals(id)){
                                exist = true;
                                break;
                            }
                        }
                        if(exist){
                            System.out.println("this sub is already exist");
                        } else {
                            Enrolled.add(line);
                            System.out.println("Subject " + line + " has been added successfully.");
                            break;
                        }

                    }

                }
            }
            System.out.println("All Subjects have been Added Successfully.");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        return Enrolled;
    }

}