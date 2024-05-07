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
            for(String sub : CoursesList()){
                System.out.println(sub);
            }
            Scanner scan = new Scanner(System.in);
                while (true) {
                    String id = scan.nextLine();

                        for (String line : CoursesList()) {
                            String[] c_Id_Data = line.split(":");
                                break;
                            }
                        }
                            }
                        }
                }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        return Enrolled;
    }
}
