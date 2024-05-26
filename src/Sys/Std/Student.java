package Sys.Std;
import Sys.course.Course;
import java.io.*;
import java.util.*;

public class Student{
    private String NameOfStudent;
    private  String IdOfStudent = "1000";
    private ArrayList<Course> courses = new ArrayList<>() ;


    public Student() {}
    //this constructor for add a student and his courses
    public Student(String nameOfStudent, ArrayList<Course> courses) {
        this.NameOfStudent = nameOfStudent;
        this.courses = courses;
        IncreaseId();
    }
    //this constructor for add a student without courses
    public Student(String nameOfStudent) {
        this.NameOfStudent = nameOfStudent;
        IncreaseId();
    }

    public String getIdOfStudent(){
        return IdOfStudent;
    }
    @Override
    public String toString(){
        return IdOfStudent + "_" + NameOfStudent + "_" + courses;
    }
    private int getTotalCharactersOfCourses() {
        int total = 0;
        for (Course str : courses) {
            total += str.toString().length();
        }
        return total + (courses.size())-1;
    }
    public void AddStudent(boolean add) {
        try {
            FileOutputStream Dot = new FileOutputStream("my_data.bin",true);
            //Id
            Dot.write(IdOfStudent.getBytes());
            //StudentNameLength
            WriteInBigEndianNotation(Dot,NameOfStudent.length());
            //Student name
            Dot.write(NameOfStudent.getBytes());
            if (add) {
                WriteInBigEndianNotation(Dot,getTotalCharactersOfCourses()); //courses length
                    for(Course c : courses) {
                        Dot.write(c.toString().getBytes());
                        if (!c.equals(courses.getLast())) Dot.write(":".getBytes()); //here I check if it is the last course, so I will not add ":"
                    }
                }else {
                WriteInBigEndianNotation(Dot,0);
            }
            Dot.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void WriteInBigEndianNotation(OutputStream f, int value) throws Exception{
        f.write(value >> 8 & 0xFF);
        f.write(value & 0xFF);
    }

    public ArrayList<Student> Display_info() {
        ArrayList<Student> data_file = new ArrayList<>();
        try {
            FileInputStream IS = new FileInputStream("my_data.bin");
            while (IS.available() > 0){
                Student student = new Student();
                //Id
                byte[] IdBytes = new byte[4];
                IS.read(IdBytes);
                student.IdOfStudent = new String(IdBytes);
                //name
                byte[] NameBytes = new byte[ReadSizeOfNextString(IS)];
                IS.read(NameBytes);
                student.NameOfStudent = new String(NameBytes);

                    int CourseSize = ReadSizeOfNextString(IS);
                    if(CourseSize != 0){
                        byte[] courseByte = new byte[CourseSize];
                        IS.read(courseByte);
                        String[] courses = (new String(courseByte)).split(":");
                        for (int x =0 ; x < courses.length ; x+=2){
                            student.courses.add(new Course(courses[x],courses[x+1]));
                        }
                    }
                data_file.add(student);
            }
            IS.close();
            return data_file;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data_file;
    }
    private static int ReadSizeOfNextString(InputStream f)throws Exception{
        int byte3 = f.read();
        int byte4 = f.read();
        return (byte3 << 8) | (byte4);
    }
    private void IncreaseId() {
        ArrayList<String> Ids = new ArrayList<>();
        int x = 0;
        try {
            for(Student s : Display_info()){
                Ids.add(s.IdOfStudent);
            }

            while (x < Ids.size()) {
                 int tempId = (Integer.parseInt(IdOfStudent) + x+1);
                if(!(Ids.contains(String.valueOf(tempId)))){
                    IdOfStudent = String.valueOf(tempId);
                    break;
                }
                x++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public Student searchStudentFromIdOrName() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("________________Our List Of Student________________");
        for (Student student : Display_info()) {

            System.out.println(student.NameOfStudent + " : " + student.IdOfStudent);
        }
        System.out.println("________________Please Enter The Id Or Full Name________________");
        String out = scanner.nextLine();
        boolean exist = false;
        for (Student student : Display_info()) {
            if (student.IdOfStudent.equals(out) || student.NameOfStudent.equals(out)) {
                exist = true;
                break;
            }
        }

        if (exist) {
            try {
                int id = Integer.parseInt(out);
                for (Student student : Display_info()) {
                    if (id == Integer.parseInt(student.IdOfStudent)) {
                       // scan.close(); // Close scanner here
                        return student;
                    }

                }
            } catch (Exception e) {
                for (Student student : Display_info()) {
                    if (Objects.equals(out, student.NameOfStudent)) {
                       // scan.close(); // Close scanner here
                        return student;
                    }

                }
            }
        } else {
           // scan.close(); // Close scanner here
            throw  new Exception("This Student Does Not Exist In The System.");
        }
        //scan.close(); // Close scanner here
        return null;
    }
    public void Delete_student_from_Id(String Id) {

        ArrayList<Student> NewList = Display_info();
            try {
                for (Student student : NewList) {
                    if (Id.equals(student.IdOfStudent)) {
                        NewList.remove(student);
                        break;
                    }

                }

                FileOutputStream Dot = new FileOutputStream("my_data.bin");
                for (Student student : NewList) {
                    Dot.write(student.IdOfStudent.getBytes());
                    WriteInBigEndianNotation(Dot,student.NameOfStudent.length());
                    Dot.write(student.NameOfStudent.getBytes());
                    this.courses = student.courses;
                    WriteInBigEndianNotation(Dot,getTotalCharactersOfCourses()); //courses length
                    if(student.courses != null){
                        for(Course c : courses) {
                            Dot.write(c.toString().getBytes());
                            if (!c.equals(courses.getLast())) Dot.write(":".getBytes()); //here I check if it is the last course, so I will not add ":"
                        }
                    }

                }
                Dot.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
    public void Display_All_StudentsNames() {
            ArrayList<Student> StdInfo = Display_info();
            System.out.println("_________________Our Students_________________");
            for (Student student : StdInfo) {
                System.out.println(student.NameOfStudent);
            }
    }
    public String Display_All_Student_Courses() throws Exception{
        Student student = searchStudentFromIdOrName();
            if(student.courses == null){
                return "this Student does not have any Enrolled Subject.";
            }else {
                return student.courses.toString();
            }
    }

    public void UpdateStudent() throws Exception{
            ArrayList<Course> Sub = new ArrayList<>();
            Student student = searchStudentFromIdOrName();
                if(!student.courses.isEmpty()){ //that means he enrolled subjects

                    System.out.println(student.NameOfStudent +"'s Subject:");
                    for(Course course : student.courses){
                        Sub.add(new Course(course.getNameOfCourse(),course.getId()));
                        System.out.println(course.getNameOfCourse()+ ":" + course.getId());
                    }
                    System.out.println("__________________________________");
                    boolean update = true;
                    Scanner scanner = new Scanner(System.in);
                    while (update){
                        System.out.println("1-Add Subject.");
                        System.out.println("2-Remove a Subject.");
                        System.out.println("3-Remove All Subjects.");
                        System.out.println("4-Exit.");
                        System.out.print("Enter: ");
                        String x = scanner.nextLine();
                        switch (x){
                            case "1":
                                Delete_student_from_Id(student.IdOfStudent);
                                Sub.addAll(add_new_Sub(Sub));
                                this.NameOfStudent = student.NameOfStudent;
                                this.IdOfStudent = student.IdOfStudent;
                                this.courses = Sub;
                                AddStudent(!courses.isEmpty());
                                break;
                            case "2":
                                Delete_student_from_Id(student.IdOfStudent);
                                Scanner scan = new Scanner(System.in);
                                System.out.print("Please Enter the Subject_Id For Delete From The List Above: ");
                                String C_ID = scan.nextLine();
                                for (Course OneC : Sub){
                                    if(OneC.getId().equals(C_ID)){
                                        Sub.remove(OneC);
                                        break;
                                    }
                                }
                                System.out.println("The Subject Successfully Removed.");
                                this.NameOfStudent = student.NameOfStudent;
                                this.IdOfStudent = student.IdOfStudent;
                                this.courses = Sub;
                                AddStudent(!courses.isEmpty());
                                break;
                            case "3":
                                Sub.clear();
                                Delete_student_from_Id(student.IdOfStudent);
                                this.NameOfStudent = student.NameOfStudent;
                                this.IdOfStudent = student.IdOfStudent;
                                AddStudent(!courses.isEmpty());
                                System.out.println("All subject Have been Removed Successfully.");
                                break;
                            case "4":
                                update = false;
                                break;
                            default:
                                System.out.println("Enter a valid Number (1 : 4 ). ");
                        }
                        System.out.println("Information Updated Successfully ;)");
                    }
                }else {
                    System.out.println("Please Enroll Some Subjects First.");
                    System.out.println("__________________________________");
                    Delete_student_from_Id(student.IdOfStudent);
                    Sub.addAll(add_new_Sub(Sub));
                    this.NameOfStudent = student.NameOfStudent;
                    this.IdOfStudent = student.IdOfStudent;
                    this.courses = Sub;
                    AddStudent(!courses.isEmpty());
                    System.out.println("Information Updated Successfully ;)");
                }
    }
    private ArrayList<Course> add_new_Sub(ArrayList<Course> Student_Subjects){

        Course course = new Course();
        System.out.println("Subjects Could Be Enrolled: ");
        if(!Student_Subjects.isEmpty()){
            for (Course sub : course.CoursesList()){
                for(Course c : Student_Subjects){
                    if(!Objects.equals(sub.getId(), c.getId())){
                        System.out.println(sub);
                    }

                }
            }
        } else {
            for(Course c : course.CoursesList()){
                System.out.println(c);
            }
        }

        return course.Enrolled_Courses();
    }
    public void Sys_Empty()throws Exception{
            if(!Display_info().isEmpty()){
                Display_info();
            }else {
                throw new Exception("There is no Any Students on the System");
            }
    }


}