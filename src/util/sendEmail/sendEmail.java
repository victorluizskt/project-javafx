package util.sendEmail;

import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendEmail {

    static int value;

    public static int getValue() {
        return value;
    }

    public static boolean sendGmail(String destination) {
        boolean retorno = false;
        //Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session s = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication("victorluizcefet@gmail.com",
                                "familias2!");//email e senha usuário
                    }
                });

        //compose message
        try {
            Random aleatory = new Random();
            value = Math.abs(value);
            value = aleatory.nextInt() * 100;
            if(value <= 99){
                value = value * 10;
            }
            MimeMessage message = new MimeMessage(s);
            message.setFrom(new InternetAddress(destination));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("victorluizcefet@gmail.com"));

            message.setSubject("Recover Itooks password");
            message.setContent("Hello, to recover your password enter this code " + value +
                            " in the password recovery box.",
                    "text/html; charset=utf-8");

            //send message
            Transport.send(message);

            retorno = true;

        } catch (MessagingException e) {
            retorno = false;
            e.printStackTrace();
        }
        return retorno;
    }
}
