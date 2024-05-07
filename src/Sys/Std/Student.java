package Sys.Std;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Student {
    private String NameOfStudent;
    private static String IdOfStudent = "1000";
    private ArrayList<String> courses;

    public Student() {}

    public Student(String nameOfStudent, ArrayList<String> courses) {
        this.NameOfStudent = nameOfStudent;
        this.courses = courses;
        IncreaseId(Objects.requireNonNull(Display_info()));
    }
    public Student(String nameOfStudent) {
        this.NameOfStudent = nameOfStudent;
        IncreaseId(Objects.requireNonNull(Display_info()));
    }
    public void AddStudent(boolean add) {

        try {
            DataOutputStream Dot = new DataOutputStream(new FileOutputStream("my_data.bin", true));
            Dot.writeUTF(IdOfStudent);
            Dot.writeUTF("_");
            Dot.writeUTF(NameOfStudent);
            if (add) {
                Dot.writeUTF("_");
                for (String courses : this.courses) {
                    Dot.writeUTF(courses);
                    if (!this.courses.getLast().equals(courses)) {
                        Dot.writeUTF(",");
                    }
                }
            }
            Dot.writeUTF("/");
            Dot.close();
        } catch (Exception e) {
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
                if (!(x = IS.readUTF()).equals("/")) {
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
    private void IncreaseId(ArrayList<String> File_data) {
        int[] Ids = new int[File_data.size()];
        int x = 0;
        try {

            while (x < File_data.size()) {
                String line = File_data.get(x);
                String[] data = line.split("_");
                Ids[x] = Integer.parseInt(data[0]);
                x++;
            }
            x = 0;
            while (x < Ids.length) {
                if (Integer.parseInt(IdOfStudent) >= Ids[x]) {
                    IdOfStudent = String.valueOf(Ids[x] + 1);
                }
                x++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public String Search_student_from_IdOrName() {
        int x = 0;
        Scanner scan = new Scanner(System.in);

        if (!(Display_info().isEmpty())) {

            System.out.println("________________Our List Of Student________________");
            for (String student : Display_info()){
                System.out.println(student);
            }

            System.out.println("________________Please Entre The Id Or Full Name To Search For Student________________");
            String Out = scan.nextLine();

            boolean exist = false;
            for (String ex : Display_info()) {
                String[] data = ex.split("_");
                if (data[0].equals(Out) || data[1].equals(Out)) {
                    exist = true;
                    break;
                }
            }

            if (exist) {
                try {
                    int Id = Integer.parseInt(Out);
                    while (x < Display_info().size()) {
                        String line = Display_info().get(x);
                        String[] info = line.split("_");
                        if (Id == Integer.parseInt(info[0])) {
                            return line;
                        }
                        x++;
                    }
                } catch (Exception e) {
                    while (x < Display_info().size()) {
                        String line = Display_info().get(x);
                        String[] info = line.split("_");
                        if (Objects.equals(Out, info[1])) {
                            return line;
                        }
                        x++;
                    }
                }
            } else {
                return "This Student Does Not Exist In The System.";
            }

        }else {
            return "There Is No Any Students In the System.";
        }
        return "";
    }
    public void Delete_student_from_IdOrName() {
        int x = 0;
        if (!(Display_info().isEmpty())) {

        ArrayList<String> NewList = Display_info();
        Scanner scan = new Scanner(System.in);


            for (String student : Display_info()){
                System.out.println(student);
            }

            try {
                System.out.println("________________Please Entre The Id For Delete Student________________");
                String Id = scan.nextLine();
                while (x < NewList.size()) {
                    String line = NewList.get(x);
                    String[] info = line.split("_");
                    if (Id.equals(info[0])) {
                        System.out.println("Student With [name: " + info[1] + ", Id: " + info[0] + "]" + " is out of the System");
                        NewList.remove(x);
                        break;
                    }
                    x++;
                }

                x = 0;
                DataOutputStream Dot = new DataOutputStream(new FileOutputStream("my_data.bin"));
                while (x < NewList.size()) {
                    Dot.writeUTF(NewList.get(x));
                    Dot.writeUTF("/");
                    x++;
                }
                Dot.close();
                System.out.println("________________Our New List Of Student________________");
                Display_All_StudentsNames();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else {
            System.out.println("there is no any Students in the system to Delete .");
        }
    }
    public void Display_All_StudentsNames() {
        if (!(Display_info().isEmpty())) {

            ArrayList<String> StdInfo = Display_info();
            int x = 0;
            System.out.println("_________________Our Students_________________");
            while (x < StdInfo.size()) {
                String[] line = StdInfo.get(x).split("_");
                System.out.println((x + 1) + "- " + line[1]);
                x++;
            }
        } else {
            System.out.println("there is no any Students in the system.");
        }
    }
    public void Display_All_Student_Courses() {

        if (!(Display_info().isEmpty())) {

            String[] line = Search_student_from_IdOrName().split("_");
            if(line.length <= 2){
                System.out.println("this Student does not have any Enrolled Subject.");
            }else {
                System.out.println("Student Name: " + line[1] + ", Student Id: " + line[0] + "\nStudent's Courses:");
                String[] SubLines = line[2].split(",");
                for (String C : SubLines) {
                    String[] Course = C.split(":");
                    System.out.println("Subject: " + Course[0] + ", With ID: " + Course[1]);
                }
            }

        } else {
            System.out.println("there is no any Students in the system. ");
        }

    }
}

