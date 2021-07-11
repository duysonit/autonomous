/*
 * Liquid Pay
 */
package autonomous.automation.web.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailChecker {

    public  static final String MAIL_BOX = "inbox";
    public  static final String MAIL_SMS_FOLDER = "sms";
    private static final String WRONG_PATTERN = "<WRONG>.*";
    private static final String OR_DELIM = "\\|";
    private static Message expectedMessage;
    private static String messageContent;

    private static final String HOST = "imap.gmail.com";
    private static final String PROTOCOL = "imaps";

    private LinkedList<File> attachments;


    public static Message getExpectedMessage() {
        return expectedMessage;
    }

    /**
     * Get all attachments of expected message
     * @return
     * @throws IOException
     * @throws MessagingException
     */
    public LinkedList<File> getAttachments() throws IOException, MessagingException {
        return attachments;
    }

    public Message getMessage(String email, String password) throws MessagingException {
        Message message;
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", PROTOCOL);
        Session session = Session.getInstance(properties, null);
        Store store = session.getStore();
        store.connect(HOST, email, password);
        Folder inbox = store.getFolder(MAIL_BOX);
        inbox.open(Folder.READ_ONLY);

        int numberOfMess = inbox.getMessageCount();
        System.out.println("Num of mess: " + numberOfMess);

        message = inbox.getMessage(1);

        return message;
    }


    public void checkMail(String email, String password, String folder, String from, String subject, String body, int timeoutInMinute) throws Exception {
        try {
            Boolean havingExpectedEmail = false;
            for (int i = 0; i <= timeoutInMinute * 12; i++) {
                havingExpectedEmail = verifyEmail(email, password, folder, from, subject, body);
                if (havingExpectedEmail) {
                    return;
                }
                Thread.sleep(5 * 1000);
            }
            if (!havingExpectedEmail) {
                throw new Exception("Cannot find matching email");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }


    private Boolean verifyEmail(String email, String password, String folder, String from, String subject, String body) throws Exception {
        Date currentDate = new Date();
        //create properties field
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", PROTOCOL);
        Session session = Session.getInstance(properties, null);
        Store store = session.getStore();
        store.connect(HOST, email, password);
        Folder inbox = store.getFolder(folder);
        inbox.open(Folder.READ_ONLY);

        int numberOfMess = inbox.getMessageCount();
        System.out.println("=========================================");
        System.out.println(new Date( ));
        System.out.println("Checking email in "+folder+" mail box");
        System.out.println("Number of email: "+ numberOfMess);

        for (int i = numberOfMess; i > 0; i--) {
            Message msg = inbox.getMessage(i);
            Date sentDate = msg.getSentDate();
            if ((compareDate(currentDate, sentDate)) < 24) {
                String result = checkEmail(msg, from, subject, body);
                if (!result.matches(WRONG_PATTERN)) {
                    System.out.println("Having expected email!");
                    expectedMessage = msg;
                    messageContent = getContent(msg);
//                    attachments = getAttachedFiles(msg);
                    inbox.close(false);
                    store.close();
                    return true;
                }
            } else {
                break;
            }
        }
        inbox.close(false);
        store.close();
        return false;
    }

    private static long compareDate(Date date1, Date date2) {
        long date1L = date1.getTime();
        long date2L = date2.getTime();
        long distance = date1L - date2L;
        return distance / 1000 / 60 / 60;
    }

    private String checkEmail(Message msg, String from, String subject, String content) throws MessagingException, IOException {
        String info = "";
        //Check "from"
        if (!from.equalsIgnoreCase("")) {
            String msgFrom = (msg.getFrom())[0].toString();
            if (!msgFrom.contains(from)) {
                info = "<WRONG>Wrong from";
                return info;
            }
        }
        //Check "subject"
        if (!subject.equalsIgnoreCase("")) {
            String[] subjectParts = subject.split(OR_DELIM);
            String msgSubject = msg.getSubject();
            for (String subjectPart : subjectParts) {
                if (!msgSubject.matches(".*" + subjectPart + ".*")) {
                    info = "<WRONG>Wrong subject";
                    return info;
                }
            }
        }

        String msgContent = StringEscapeUtils.unescapeHtml3(getContent(msg));

        //Check content
        if (!content.equalsIgnoreCase("") && !msgContent.equalsIgnoreCase("")) {
            String[] contentParts = content.split(OR_DELIM);
            for (String contentPart : contentParts) {
                contentPart = StringUtils.normalizeSpace(contentPart);
                msgContent = StringUtils.normalizeSpace(msgContent);

                if (!msgContent.matches(".*" + contentPart + ".*")) {
                    info = "<WRONG>Wrong content";
                    return info;
                }
            }
        }
        return info;
    }


    public String getMessageInfo(String regex, int groupId) throws IOException, MessagingException {
        String msgContent = messageContent;
        msgContent = msgContent.trim();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msgContent);
        matcher.matches();
        matcher.find();
        return matcher.group(groupId);
    }

    public void cleanInbox(String email, String password, String folder) throws Exception {
        //create properties field
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", PROTOCOL);
        Session session = Session.getInstance(properties, null);
        Store store = session.getStore();
        store.connect(HOST, email, password);
        Folder inbox = store.getFolder(folder);
        inbox.open(Folder.READ_WRITE);

        int numberOfMess = inbox.getMessageCount();

        for (int i = numberOfMess; i > 0; i--) {
            Message msg = inbox.getMessage(i);
            msg.setFlag(Flags.Flag.DELETED, true);
        }

        inbox.close(false);
        store.close();
    }

    public void verifyNoEmailInInbox(String email, String password, String folder, String from, String subject, String body, int timeOutInMinute) {
        try {
            Boolean havingExpectedEmail = false;
            for (int i = 0; i <= timeOutInMinute * 2; i++) {
                havingExpectedEmail = verifyEmail(email, password, folder, from, subject, body);
                if (havingExpectedEmail) {
                    break;
                }
                Thread.sleep(30 * 1000);
            }
            if (!havingExpectedEmail) {
                //TODO
            } else {
                //TODO
            }
        } catch (Exception e) {
            //TODO
        }
    }

    public String getContent(Message msg) throws IOException, MessagingException {
        String msgContent = "";
        if (msg.getContent() instanceof Multipart) {
            Object mp = ((Multipart) msg.getContent()).getBodyPart(0).getContent();
            if( mp instanceof MimeMultipart){
                msgContent = ((Multipart) ((Multipart) msg.getContent()).getBodyPart(0).getContent()).getBodyPart(0).getContent().toString();
            }else {
                msgContent = ((Multipart) msg.getContent()).getBodyPart(0).getContent().toString();
            }
        } else {
            msgContent = (String) msg.getContent();
        }

        return msgContent;
    }

    private LinkedList<File> getAttachedFiles(Message message) throws IOException, MessagingException {
        String tempPath = System.getProperty("java.io.tmpdir");
        LinkedList<File> attachments = new LinkedList<>();

        if(message==null){
            return attachments;
        }

        Multipart multipart = (Multipart) message.getContent();

        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if(!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) &&
                    !StringUtils.isNotBlank(bodyPart.getFileName())) {
                continue; // dealing with attachments only
            }
            InputStream is = bodyPart.getInputStream();
            File theDir = new File(tempPath);
            if (!theDir.exists()) {
                theDir.mkdir();
            }

            File f = new File(tempPath + bodyPart.getFileName());
            FileOutputStream fos = new FileOutputStream(f);
            byte[] buf = new byte[4096];
            int bytesRead;
            while((bytesRead = is.read(buf))!=-1) {
                fos.write(buf, 0, bytesRead);
            }
            fos.close();
            attachments.add(f);
        }

        return attachments;

    }
}
