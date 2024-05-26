package Sys.DataTrans;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class ToFile extends DataTransfer {

    @Override
    public void SendData(String data){

        if (!Objects.equals(data, "There Is No Any Students In the System.")) {
            String[] line = data.split("_");

            if (line.length == 3) {

                StringBuilder Mes = new StringBuilder();
                Mes.append("<html><body><font size=\"3\">");
                Mes.append("<p>Hello, ").append(line[1].toUpperCase()).append("<p/>\n\nThe Is All Your Information :<br>");
                Mes.append("Have a Nice Day ;)");
                Mes.append("<font/><body/><html/>");
                try {
                    PrintWriter file = getPrintWriter(line);
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
                        return new PasswordAuthentication("codx207@gmail.com", "sytn xmji bnon pmls");
                    }
                });
                Scanner scanner = new Scanner(System.in);
                System.out.print("Hello, " + line[1] + "\nPlease Enter your Gmail: ");
                String temp = scanner.nextLine();
                try {


                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("codx207@gmail.com"));
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

    private static PrintWriter getPrintWriter(String[] line) throws FileNotFoundException {
        PrintWriter file = new PrintWriter("Attached_File.txt");
        file.println("Student Name: " + line[1]);
        file.println("Student Id: " + line[0]);
        file.println("Student Subject: ");
        file.println(line[2]);
        return file;
    }
}