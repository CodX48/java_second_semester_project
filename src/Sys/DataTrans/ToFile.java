package Sys.DataTrans;

import Sys.Std.Student;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class ToFile extends DataTransfer {

    @Override
    public void SendData(String data){

        if (!Objects.equals(data, "There Is No Any Students In the System.")) {
            String[] line = data.split("_");

            if (line.length >= 3) {

                String[] subject = line[2].split(",");
                StringBuilder Mes = new StringBuilder();
                Mes.append("<html><body><font size=\"3\">");
                Mes.append("<p>Hello, ").append(line[1].toUpperCase()).append("<p/>\n\nThe Is All Your Information :<br>");
                Mes.append("Have a Nice Day ;)");
                Mes.append("<font/><body/><html/>");
                try {
                    PrintWriter file = getPrintWriter(line, subject);
                    file.close();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

                Properties prop = new Properties();
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.starttls.enable", "true");
                prop.put("mail.smtp.host", "smtp.gmail.com");
                prop.put("mail.smtp.port", "587");
                prop.put("mail.smtp.tls.trust", "smtp.gmail.com");

                Session session = Session.getInstance(prop, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("example@gmail.com", "***********");
                    }
                });

                try {

                    String temp;
                    if(line.length >= 4 && !(line[3].equals("EmptyG"))){
                        temp = line[3];
                    }else {
                        Student s = new Student();
                        temp = s.UpdateGmailInformation(data);
                    }

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("example@gmail.com"));
                    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(temp));
                    message.setSubject("Your Subject Of This Semester");

                    MimeBodyPart body = new MimeBodyPart();
                    MimeBodyPart AttachFile = new MimeBodyPart();
                    AttachFile.attachFile("Attached_File.txt");
                    body.setContent(Mes.toString(), "text/html");
                    Multipart parts = new MimeMultipart();
                    parts.addBodyPart(body);
                    parts.addBodyPart(AttachFile);
                    message.setContent(parts);
                    Transport.send(message);
                    System.out.println("Sent Successfully ;) ");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else {
                System.out.println("this Student Does Not Have Any Information To Send. ");
            }


        }else {
            System.out.println("There Is No Any Student On The System. ");
        }
    }

    private static PrintWriter getPrintWriter(String[] line, String[] subject) throws FileNotFoundException {
        PrintWriter file = new PrintWriter("Attached_File.txt");
        file.println("Student Name: " + line[1]);
        file.println("Student Id: " + line[0]);
        file.println("Student Subject: ");
        for (String Sub : subject){
            String[] Sub_info = Sub.split(":");
            file.println(Sub_info[0] + ", Id: " + Sub_info[1]);
        }
        return file;
    }

}
