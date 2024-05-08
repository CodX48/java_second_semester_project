package Sys.DataTrans;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Email extends DataTransfer{

    @Override
    public void SendData(String data){

        String[] line = data.split("_");
        String[] subject = line[2].split(",");
        StringBuilder Mes = new StringBuilder();
        Mes.append("hello, ").append(line[1]).append("\nThose are your Subject For this semester: \n");
        for (String s: subject){
            Mes.append(s).append("\n");
        }


        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.tls.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Example@gmail.com","************");
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Example@gmail.com"));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("Example@gmail.com"));
            message.setSubject("Your Subject Of This Semester");
            MimeBodyPart body = new MimeBodyPart();
            body.setContent(Mes.toString(),"text/html; charset=utf-8");
            Multipart parts = new MimeMultipart();
            parts.addBodyPart(body);
            message.setContent(parts);
            Transport.send(message);
            System.out.println("Sent Successfully ;) ");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }
}
