package Sys.Std;
import Sys.course.Course;
import java.io.*;
import java.util.*;

public class Student{
    private String NameOfStudent;
    private  String IdOfStudent = "1000";
    private ArrayList<Course> courses = new ArrayList<>();
    private static final ArrayList<Student> ListOfStudents = new ArrayList<>(Display_info());

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
    //for getting id
    public String getIdOfStudent(){
        return IdOfStudent;
    }
    //override method for toString
    @Override
    public String toString(){
        return IdOfStudent + "_" + NameOfStudent + "_" + courses;
    }

    //this function for an id
    private void IncreaseId() {
        ArrayList<String> Ids = new ArrayList<>();
        int x = 0;

        try {
            for(Student s : ListOfStudents){
                Ids.add(s.IdOfStudent); // there I will take the id of the list of the students
            }

            // here I will check if next student id is existed on id list or not
            // to make sure the every student will take a unique id
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

    //throw this method I will just take a student as a parameter and then i will add it to the list of students
    //the Information of the Student will be added throw the constructor (name , list of courses) and the id will be automatic
    public void AddStudent(Student student) {

        ListOfStudents.add(student);
    }
    //here my program takes the list of student and add them to the file
    public void Save(){
        try{
            FileOutputStream f = new FileOutputStream("my_data.bin");
            for(Student student : ListOfStudents){

                f.write(student.IdOfStudent.getBytes()); // here I am writing the id

                WriteInBigEndianNotation(f,student.NameOfStudent.length());  // here is the length of student name
                f.write(student.NameOfStudent.getBytes());  // here is the name of the student

                if(!student.courses.isEmpty()){ // here my program checks if this student has enrolled on any courses or not throw a method from ArrayList<>();
                    WriteInBigEndianNotation(f,getTotalCharactersOfCourses(student.courses)); // if so I will write the length of Courses

                    for(Course c : student.courses) { // here I will access them one by one and write them
                        f.write(c.toString().getBytes());
                        if (!c.equals(student.courses.getLast())){
                            f.write(":".getBytes()); //here I check if it is the last course, so I will not add ":"
                        }
                    }
                } else {
                    WriteInBigEndianNotation(f,0); // if the student has not enrolled on any courses,
                    // so I will "0" as a length
                }
            }
            f.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // here I am taking the number of courses character with including the ":"
    private int getTotalCharactersOfCourses(ArrayList<Course> std_course) {
        int total = 0;
        for (Course str : std_course) {
            total += str.toString().length();
        }
        return total + (std_course.size())-1;
    }

    // this method is used to write in big endian notation
    private static void WriteInBigEndianNotation(OutputStream f, int value) throws Exception{
        f.write(value >> 8 & 0xFF);
        f.write(value & 0xFF);
    }
    //this static method is used to read and return a list all the students from the file
    private static ArrayList<Student> Display_info() {
        ArrayList<Student> list = new ArrayList<>();
        try {
            FileInputStream IS = new FileInputStream("my_data.bin");
            while (IS.available() > 0){
                Student student = new Student(); // object of a student data type to be added to the list
                //Id
                byte[] IdBytes = new byte[4]; // my program give every student a id of 4 digits
                IS.read(IdBytes);
                student.IdOfStudent = new String(IdBytes);
                //name
                byte[] NameBytes = new byte[ReadSizeOfNextString(IS)]; // here I wrote a method to be able to read the (big endian bytes)
                IS.read(NameBytes);
                student.NameOfStudent = new String(NameBytes);

                    int CourseSize = ReadSizeOfNextString(IS); // after the student name I will read the next 2 bytes with is the courses length
                    if(CourseSize != 0){ // if it is = 0 so that means this student did not enroll any courses
                        byte[] courseByte = new byte[CourseSize];
                        IS.read(courseByte);

                        String[] courses = (new String(courseByte)).split(":"); // here I will parse the courses that is Separated with ":"
                        for (int x =0 ; x < courses.length ; x+=2){
                            // then over here I was able to take the course name and id and add them to the courses list
                            student.courses.add(new Course(courses[x],courses[x+1]));
                        }
                    }
                    list.add(student); // when I am done with a student I will add him or here to the list
            }
            IS.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list; // then I will return it
    }

    // here is the method of reading the big endian notation
    private static int ReadSizeOfNextString(InputStream f)throws Exception{
        int byte3 = f.read();
        int byte4 = f.read();
        return (byte3 << 8) | (byte4);
    }

    //this method will return a Student
    public Student searchStudentFromIdOrName() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("________________Our List Of Student________________");

        for (Student student : ListOfStudents) {
            System.out.println(student.NameOfStudent + " : " + student.IdOfStudent); // here i print the students from the list
        }

        System.out.println("________________Please Enter The Id Or Full Name________________");
        String out = scanner.nextLine();
        boolean exist = false;

        // here my program checks if this student that I am searching for does exist on the system or not;
        for (Student student : ListOfStudents) {
            if (student.IdOfStudent.equals(out) || student.NameOfStudent.equals(out)) {
                exist = true;
                break;
            }
        }

        if (exist) {
            //here my program will try to parse the output "if it is parsable that means it is an id
            // if it is not parsable that means it is name of student and it will be caught
            try {
                int id = Integer.parseInt(out);
                for (Student student : ListOfStudents) {
                    if (id == Integer.parseInt(student.IdOfStudent)) {
                        return student;
                    }

                }

            } catch (Exception e) {
                for (Student student : ListOfStudents) {
                    if (Objects.equals(out, student.NameOfStudent)) {
                        return student;
                    }

                }
            }
        } else {
           // in case the student does not exist on the system
            throw  new Exception("This Student Does Not Exist In The System.");
        }
        return null;
    }
    public void Delete_student_from_Id(String Id) {
                for (Student student : ListOfStudents) {
                    if (Id.equals(student.IdOfStudent)) {
                        ListOfStudents.remove(student);
                        break;
                    }
                }
    }
    public void Display_All_StudentsNames() {
            System.out.println("_________________Our Students_________________");
            for (Student student : ListOfStudents) {
                System.out.println(student.NameOfStudent);
            }
    }
    public String Display_All_Student_Courses() throws Exception{
        Student student = searchStudentFromIdOrName(); // I am using my search function to take a student who I need.
            if(student.courses.isEmpty()){
                throw new Exception("this Student does not have any Enrolled Subject.") ;
            }else {
                return student.courses.toString();
            }
    }

    // all the Update method is used to update a student courses
    public void UpdateStudent() throws Exception{
            ArrayList<Course> Sub = new ArrayList<>();
            Student studentToUpdate = searchStudentFromIdOrName(); // I am using my search function to take a student who I need.

                if(!studentToUpdate.courses.isEmpty()){ //that means he enrolled subjects
                    // over here I display the student name and his courses to show all the information about him

                    System.out.println(studentToUpdate.NameOfStudent +"'s Subject:");
                    for(Course course : studentToUpdate.courses){
                        // sub is the student courses that he is already has
                        Sub.add(new Course(course.getNameOfCourse(),course.getId()));
                        System.out.println(course.getNameOfCourse()+ ":" + course.getId());
                    }
                    System.out.println("__________________________________");
                    boolean update = true;
                    Scanner scanner = new Scanner(System.in);

                    //SO THE IDEA IN MY UPDATE STUDENT METHOD IS THAT AFTER I SEARCH FOR A STUDENT I WILL
                    //REMOVE HIM FROM THE LIST THEN I WILL REWRITE HIM AGAIN TO THE LIST WITH THE CHANGES

                    while (update){
                        //here I am asking for the features that my program has
                        System.out.println("1-Add Subject.");
                        System.out.println("2-Remove a Subject.");
                        System.out.println("3-Remove All Subjects.");
                        System.out.println("4-Exit.");
                        System.out.print("Enter: ");
                        String x = scanner.nextLine();
                        switch (x){
                            case "1":
                                Delete_student_from_Id(studentToUpdate.IdOfStudent); // delete the student
                                Sub.addAll(add_new_Sub(Sub)); // make the changes
                                studentToUpdate.courses = Sub; // add the changes
                                AddStudent(studentToUpdate); // rewrite him using AddStudent method
                                break;
                            case "2":
                                Delete_student_from_Id(studentToUpdate.IdOfStudent); // delete the student
                                Scanner scan = new Scanner(System.in);
                                System.out.print("Please Enter the Subject_Id For Delete From The List Above: ");
                                String C_ID = scan.nextLine();
                                // make the changes
                                for (Course OneC : Sub){
                                    if(OneC.getId().equals(C_ID)){
                                        Sub.remove(OneC);
                                        break;
                                    }
                                }
                                System.out.println("The Subject Successfully Removed.");
                                studentToUpdate.courses = Sub; // add the changes
                                AddStudent(studentToUpdate); // rewrite him using AddStudent method
                                break;
                            case "3":
                                Delete_student_from_Id(studentToUpdate.IdOfStudent); // delete the student
                                studentToUpdate.courses.clear(); // make the changes
                                System.out.println("All subject Have been Removed Successfully.");
                                AddStudent(studentToUpdate); // rewrite him using AddStudent method
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
                    Delete_student_from_Id(studentToUpdate.IdOfStudent); // delete the student
                    Sub.addAll(add_new_Sub(Sub)); // make the changes
                    studentToUpdate.courses = Sub; // add the changes
                    AddStudent(studentToUpdate); // rewrite him using AddStudent method
                    System.out.println("Information Updated Successfully ;)");
                }
    }

    //this function is made to add more courses add return them
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
    //this method is made in case we do not have any student on the system
    public void Sys_Empty()throws Exception{
            if(ListOfStudents.isEmpty()){
                throw new Exception("There is no Any Students on the System");
            }
    }


}