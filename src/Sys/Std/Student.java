package Sys.Std;
import Sys.course.Course;
import java.io.*;
import java.util.*;

public class Student{
    private String NameOfStudent;
    private  String IdOfStudent = "1000";
    private ArrayList<String> courses;

    public Student() {}

    public Student(String nameOfStudent, ArrayList<String> courses) {
        this.NameOfStudent = nameOfStudent;
        this.courses = courses;
        IncreaseId();
    }
    public Student(String nameOfStudent) {
        this.NameOfStudent = nameOfStudent;
        IncreaseId();
    }
    private int getTotalCharacters() {
        int total = 0;
        for (String str : courses) {
            total += str.length();
        }
        return total;
    }
    public void AddStudent(boolean add) {
        try {
            FileOutputStream Dot = new FileOutputStream("my_data.bin",true);
            //Id
            Dot.write(IdOfStudent.getBytes());
            //StudentNameLength
            WriteInBigEndianNotation(Dot,NameOfStudent.length());
            Dot.write(NameOfStudent.getBytes());
                if (add) {
                    WriteInBigEndianNotation(Dot,getTotalCharacters() + (courses.size())-1);
                    for(String s : courses) {
                        Dot.write(s.getBytes());
                        if (!s.equals(courses.getLast())) Dot.write(":".getBytes());
                    }
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

    public ArrayList<String> Display_info() {
        ArrayList<String> data_file = new ArrayList<>();
        try {
            FileInputStream IS = new FileInputStream("my_data.bin");
            while (IS.available() > 0){
                StringBuilder BuildStudent = new StringBuilder();
                //Id
                byte[] IdBytes = new byte[4];
                IS.read(IdBytes);
                String id = new String(IdBytes);
                //name
                byte[] NameBytes = new byte[ReadSizeOfNextString(IS)];
                IS.read(NameBytes);
                String name = new String(NameBytes);
                BuildStudent.append(id).append("_").append(name);
                //Courses
                //NumOfCourses
                    BuildStudent.append("_");
                    byte[] courseByte = new byte[ReadSizeOfNextString(IS)];
                    IS.read(courseByte);
                    BuildStudent.append(new String(courseByte));
                data_file.add(BuildStudent.toString());
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
        return (byte3 << 8) | (byte4 );
    }
    private void IncreaseId() {
        ArrayList<String> Ids = new ArrayList<>();
        int x = 0;
        try {
            while (x < Display_info().size()) {
                String line = Display_info().get(x);
                String[] data = line.split("_");
                Ids.add(data[0]);
                x++;
            }
            x = 0;
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
    public String searchStudentFromIdOrName() throws Exception {
        int x = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("________________Our List Of Student________________");
        for (String student : Display_info()) {
            String[] nameId = student.split("_");
            System.out.println(nameId[0] + " : " + nameId[1]);
        }
        System.out.println("________________Please Enter The Id Or Full Name________________");
        String out = scanner.nextLine();
        boolean exist = false;
        for (String ex : Display_info()) {
            String[] data = ex.split("_");
            if (data[0].equals(out) || data[1].equals(out)) {
                exist = true;
                break;
            }
        }

        if (exist) {
            try {
                int id = Integer.parseInt(out);
                while (x < Display_info().size()) {
                    String line = Display_info().get(x);
                    String[] info = line.split("_");
                    if (id == Integer.parseInt(info[0])) {
                       // scan.close(); // Close scanner here
                        return line;
                    }
                    x++;
                }
            } catch (Exception e) {
                while (x < Display_info().size()) {
                    String line = Display_info().get(x);
                    String[] info = line.split("_");
                    if (Objects.equals(out, info[1])) {
                       // scan.close(); // Close scanner here
                        return line;
                    }
                    x++;
                }
            }
        } else {
           // scan.close(); // Close scanner here
            throw  new Exception("This Student Does Not Exist In The System.");
        }
        //scan.close(); // Close scanner here
        return "";
    }
    public void Delete_student_from_Id(String Id) {
        int x = 0;
        ArrayList<String> NewList = Display_info();
            try {
                while (x < NewList.size()) {
                    String line = NewList.get(x);
                    String[] info = line.split("_");
                    if (Id.equals(info[0])) {
                        NewList.remove(x);
                        break;
                    }
                    x++;
                }
                x = 0;
                FileOutputStream Dot = new FileOutputStream("my_data.bin");
                while (x < NewList.size()) {
                    String[] line = NewList.get(x).split("_");
                    Dot.write(line[0].getBytes());
                    WriteInBigEndianNotation(Dot,line[1].length());
                    Dot.write(line[1].getBytes());
                    if(line.length>2){
                        WriteInBigEndianNotation(Dot,line[2].length());
                        Dot.write(line[2].getBytes());
                    }
                    x++;
                }
                Dot.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
    public void Display_All_StudentsNames() {
            ArrayList<String> StdInfo = Display_info();
            int x = 0;
            System.out.println("_________________Our Students_________________");
            while (x < StdInfo.size()) {
                String[] line = StdInfo.get(x).split("_");
                System.out.println(line[1]);
                x++;
            }
    }
    public String Display_All_Student_Courses() throws Exception{
            String[] line = searchStudentFromIdOrName().split("_");
            if(line.length <= 2){
                return "this Student does not have any Enrolled Subject.";
            }else {
                return line[2];
            }
    }
    public void UpdateStudent() throws Exception{
            ArrayList<String> Sub = new ArrayList<>();
            String line = searchStudentFromIdOrName();
                String[] Data = line.split("_");
                if(Data.length == 3){ //that means he enrolled subjects
                    String[] Subjects = Data[2].split(":");
                    String [] name = Data[1].split(" ");
                    System.out.println(name[0] +"'s Subject:");
                    for(int x = 0 ; x < Subjects.length ; x+=2){
                        Sub.add(Subjects[x] + ":" + Subjects[x+1]);
                        System.out.println(Subjects[x] + ":" + Subjects[x+1]);
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
                                Delete_student_from_Id(Data[0]);
                                Sub.addAll(add_new_Sub(Sub));
                                this.NameOfStudent = Data[1];
                                this.IdOfStudent = Data[0];
                                this.courses = Sub;
                                AddStudent(!courses.isEmpty());
                                break;
                            case "2":
                                Delete_student_from_Id(Data[0]);
                                Scanner scan = new Scanner(System.in);
                                System.out.print("Please Enter the Subject_Id For Delete From The List Above: ");
                                String C_ID = scan.nextLine();
                                for (String OneC : Sub){
                                    String[] parse_Sub = OneC.split(":");
                                    if(parse_Sub[1].equals(C_ID)){
                                        Sub.remove(OneC);
                                        break;
                                    }
                                }
                                System.out.println("The Subject Successfully Removed.");
                                this.NameOfStudent = Data[1];
                                this.IdOfStudent = Data[0];
                                this.courses = Sub;
                                AddStudent(!courses.isEmpty());
                                break;
                            case "3":
                                Sub.clear();
                                Delete_student_from_Id(Data[0]);
                                this.NameOfStudent = Data[1];
                                this.IdOfStudent = Data[0];
                                AddStudent(!Sub.isEmpty());
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
                    Delete_student_from_Id(Data[0]);
                    Sub.addAll(add_new_Sub(Sub));
                    this.NameOfStudent = Data[1];
                    this.IdOfStudent = Data[0];
                    this.courses = Sub;
                    AddStudent(!courses.isEmpty());
                    System.out.println("Information Updated Successfully ;)");
                }
    }
    private ArrayList<String> add_new_Sub(ArrayList<String> Student_Subjects){
        Course course = new Course();
        System.out.println("Subjects Could Be Enrolled: ");

        for (String sub : course.CoursesList()){
            if(!(Student_Subjects).contains(sub)){
                System.out.println(sub);
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