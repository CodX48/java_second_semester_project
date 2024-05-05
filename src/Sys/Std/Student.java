package Sys.Std;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Student{
    private String NameOfStudent;
    private static String IdOfStudent = "1000";
    private  ArrayList<String> courses;

    public Student(){}
    
    public Student(String nameOfStudent,ArrayList<String> courses){
        this.NameOfStudent = nameOfStudent;
        this.courses = courses;
        IncreaseId(Objects.requireNonNull(Display_info()));
    }
    public Student(String nameOfStudent){
        this.NameOfStudent = nameOfStudent;
        IncreaseId(Objects.requireNonNull(Display_info()));
    }

    public void AddStudent(boolean add){

        try {
            DataOutputStream Dot = new DataOutputStream(new FileOutputStream("my_data.bin",true));
            Dot.writeUTF(IdOfStudent);
            Dot.writeUTF("_");
            Dot.writeUTF(NameOfStudent);
            if(add){
            Dot.writeUTF("_");
                for (String courses : this.courses){
                    Dot.writeUTF(courses);
                    if(!this.courses.getLast().equals(courses)){
                        Dot.writeUTF(",");
                    }
                }
            }
            Dot.writeUTF("/");
            Dot.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private ArrayList<String> Display_info() {
        ArrayList<String> data_file = new ArrayList<>();
        try {
            DataInputStream IS = new DataInputStream(new FileInputStream("my_data.bin"));
            String x;
            StringBuilder builder = new StringBuilder();
            while (IS.available() > 0) {
                if (!(x=IS.readUTF()).equals("/")) {
                    builder.append(x);
                } else {
                    data_file.add(builder.toString());
                    builder = new StringBuilder();
                }
            }
            return data_file;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data_file;
    }

    private void IncreaseId(ArrayList<String> File_data){
        int[] Ids = new int[File_data.size()];
        int x= 0;
        try{

            while (x< File_data.size()){
               String line = File_data.get(x);
               String[] data = line.split("_");
               Ids[x] = Integer.parseInt(data[0]);
                x++;
            }
            x=0;
            while (x<Ids.length){
                if(Integer.parseInt(IdOfStudent)>=Ids[x]){
                    IdOfStudent = String.valueOf(Ids[x]+1);
                }
                x++;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public String Search_student_from_IdOrName(){
        int x=0;
        Scanner scan = new Scanner(System.in);
        System.out.println("________________Our List Of Student________________");
        while (x < Objects.requireNonNull(Display_info()).size()){
            System.out.println((x+1) + "- " + Objects.requireNonNull(Display_info()).get(x));
            x++;
        }
        System.out.println("________________Please Entre The Id Or Name To Search For Student________________");
        String out = scan.nextLine();
        x=0;
        try {
            int Id = Integer.parseInt(out);
            while (x < Objects.requireNonNull(Display_info()).size()){
                String line = Objects.requireNonNull(Display_info()).get(x);
                String[] info = line.split("_");
                if(Id == Integer.parseInt(info[0])){
                    return line;
                }
                x++;
            }
        }catch (Exception e){
            while (x < Objects.requireNonNull(Display_info()).size()){
                String line = Objects.requireNonNull(Display_info()).get(x);
                String[] info = line.split("_");
                if(Objects.equals(out, info[1])){
                    return line;
                }
                x++;
            }
        }
        return null;
    }

    public void Delete_student_from_IdOrName(){
        int x=0;
        ArrayList<String> List_For_removing = Display_info();
        Scanner scan = new Scanner(System.in);
        System.out.println("________________Our List Of Student________________");
        while (x < Objects.requireNonNull(List_For_removing).size()){
            System.out.println((x+1) + "- " + Objects.requireNonNull(Display_info()).get(x));
            x++;
        }
        try {
            x=0;
            System.out.println("________________Please Entre The Id For Delete Student________________");
            String out = scan.nextLine();
            int Id = Integer.parseInt(out);
            while (x < List_For_removing.size()){
                String line = List_For_removing.get(x);
                String[] info = line.split("_");
                if(Id == Integer.parseInt(info[0])){
                    System.out.println( "Student With [name: "+info[1]+ ", Id: " +info[0]+ "]" + " is out of the System");
                     List_For_removing.remove(x);
                    break;
                }
                x++;
            }

            x=0;
            DataOutputStream Dot = new DataOutputStream(new FileOutputStream("my_data.bin"));
            while (x <  List_For_removing.size()){
                Dot.writeUTF(List_For_removing.get(x));
                Dot.writeUTF("/");
                x++;
            }
            Dot.close();
            x=0;
            System.out.println("________________Our New List Of Student________________");
            while (x < Objects.requireNonNull(List_For_removing).size()){
                System.out.println((x+1) + "- " + Objects.requireNonNull(Display_info()).get(x));
                x++;
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            }
        }

        public void Display_All_Students(){
        ArrayList<String> StdInfo = Display_info();
        int x=0;
        System.out.println("_________________Our Student_________________");
            while (x < StdInfo.size()){
                String[] line = StdInfo.get(x).split("_");
                System.out.println((x+1) + "- " + line[1]);
                x++;
            }

        }

        public void Display_All_StudentCourses(){
        String[] line = Search_student_from_IdOrName().split("_");
        System.out.println("Student Name: " + line[1] + ", Id: " + line[0] + "\nStudent's Courses:");
        String[] Course = line[2].split(",");
        for (String C : Course){
            System.out.println(C);
        }

        }
    }

