package com.gp_scheduling;

import com.database.*;

import java.awt.print.Book;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class LogicFunctions {

    DB db;

    public LogicFunctions(DB db) {
        this.db = db;
    }

    public static int slot_length = 15 * 60 * 1000;
    public static Timestamp getEndTime(Timestamp start_time) {
        return new Timestamp(start_time.getTime()+slot_length);
    }

    public boolean bookAppt(Appt appt) {

        /** Role :
         *  - Checks no clashing bookings                           x
         *  - Removes a booking request row                         x
         *  - Removes booking request timeslots from other rows     x
         *  - Deletes any now empty booking requests                x
         *      - notifies those users                              x
        */

        if (noClashingAppts(appt)) {
            db.adjustRequestsTable(appt); // Implements lower 3
            return appt.save(db);
        } else {
            return false;
        }
    }

    public boolean requestAppt(BookingRequest request) {
        return request.save(this.db);
    }

    public boolean rescheduleAppt(Appt initAppt, Timestamp newTime,Timestamp newEnd) {

        boolean success = this.bookAppt(new Appt(initAppt.getPatient_id(),initAppt.getGp_id(),newTime,newEnd,
                initAppt.getSubject(),initAppt.getAppt_details(),false));
        if (success) {
            this.cancelAppt(initAppt);
        } else {
            return false;
        }
        return true;
    }

    public boolean cancelAppt(Appt appt) {
        if (appt.cancel(db)) {
            alertWaiters(appt);
            return true;
        } else {
            return false;
        }
    }

    public boolean noClashingAppts(Appt appt) {
        // Returns true if there is no appointment in the com.database with a matching gp and start time;
        return db.getAppt(appt.getStart_time(),appt.getGp_id()).getGp_id() == -1;
    }

    public boolean alertWaiters(Appt appt) {
        List<Appt> templates = db.getWaitList(appt.getStart_time(),appt.getGp_id());
        if (templates == null) {
            return true;
        }
        for (Appt template : templates) {
            notify(db.getPatient(template.getPatient_id()),template);
        }
        return true;
    }

    private void notify(Patient patient, Appt template) {
        // TODO
        System.out.println("Notified Patient "+Integer.toString(patient.getPatient_id()));
    }

    public Timestamp getTimeStamp(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("\"yyyy/MM/dd HH:mm\"");
        try {
            return new Timestamp(sdf.parse(dateTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean sendEmail(String to, String subject, String msg) {
        String host = "smtp.gmail.com";
        String port = "587";
        String user = "drp26.bookings@gmail.com";
        String password = "rwortzktwxwigedu";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        Session session = Session.getDefaultInstance(props,    
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {  
                    return new PasswordAuthentication(user,password);  
                }  
            });
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);
            System.out.println("Message sent succesfully");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }   
}
