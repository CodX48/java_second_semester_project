package Sys.DataTrans;
import Sys.Std.Student;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;

public class Email extends DataTransfer {

    @Override
    public void SendData(String data) {

        if (!Objects.equals(data, "There Is No Any Students In the System.")) {
            String[] line = data.split("_");

            if (line.length >= 3) {

                    String[] subject = line[2].split(",");
                    StringBuilder Mes = new StringBuilder();
                    Mes.append("<html><body><font size=\"3\">");
                    Mes.append("<p>Hello, ").append(line[1].toUpperCase()).append("<p/>\n\nYour Subject For This Semester :<br><br><div>");
                    for (String s : subject) {
                        Mes.append(s);
                        Mes.append("<br>");
                    }
                    Mes.append("<div/><br>");
                    Mes.append("Have a Nice Day ;)");
                    Mes.append("<font/><body/><html/>");

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



                    try {
                        String temp;
                        if(line.length >= 4 && !(line[3].equals("EmptyG"))){
                             temp = line[3];
                        }else {
                            Scanner scanner = new Scanner(System.in);
                            System.out.print("Hello, " + line[1] + "\nPlease Enter your Gmail: ");
                            temp = scanner.nextLine();
                            ArrayList<String> Course = new ArrayList<>(List.of(subject));
                            Student s = new Student(line[1],Course);
                            s.Delete_student_from_Id(line[0]);
                            s.SetId(line[0]);
                            s.SetGmail(temp);
                            s.AddStudent(true);
                        }

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("codx207@gmail.com"));
                        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(temp));
                        message.setSubject("Your Subject Of This Semester");

                        MimeBodyPart body = new MimeBodyPart();
                        body.setContent(Mes.toString(), "text/html");
                        Multipart parts = new MimeMultipart();
                        parts.addBodyPart(body);
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

}
