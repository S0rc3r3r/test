
package com.muso.utils.Gmail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.utils.RegexTools.RegExpTools;
import com.muso.utils.encryption.AESEncrypt;
import com.muso.utils.thread.ThreadHandler;

public class JavaGmailSearchInboxa {

    private static final int MAX_NUMBER_OF_MESSAGES = 20;
    private static final String EMAIL_SUBJECT = "Muso Recovery";
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaGmailSearchInbox.class);

    private String manualLink;

    /**
     * Method searches for MUSO password recovery email. It retrurns TRUE is found
     * and FALSE otherwise
     * 
     * @param username
     *            gmail username
     * @param tokenString
     *            gmail token string
     * @param timeoutSeconds
     *            seconds to wait until email is received
     * @return
     * @throws Exception
     */
    public boolean searchMusoPasswordRecoveryEmail(String username, String tokenString,
            int timeoutSeconds)
            throws Exception {

        // mail server info
        final String host = "imap.gmail.com";
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imap.port", "993");
        boolean emailFound = false;

        // set any other needed mail.imap.* properties here
        Session session = Session.getInstance(props);
        session.setDebug(false);
        Store store = session.getStore("imap");
        store.connect(host, username, AESEncrypt.getInstance().decrypt(tokenString));

        final long startTime = System.currentTimeMillis() / 1000;
        long currentTime = System.currentTimeMillis() / 1000;
        while (currentTime - startTime < timeoutSeconds && !emailFound) {

            final Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);

            LOGGER.info("Accessing test account: '{}'", username);
            LOGGER.info("Total messages = " + inbox.getMessageCount());
            LOGGER.info("New messages = " + inbox.getNewMessageCount());
            LOGGER.info("Unread messages = " + inbox.getUnreadMessageCount());
            LOGGER.info("Deleted messages = " + inbox.getDeletedMessageCount());

            // Get the last messages
            final int end = inbox.getMessageCount();
            int start = (end - MAX_NUMBER_OF_MESSAGES + 1) < 1 ? 1 : end - MAX_NUMBER_OF_MESSAGES
                    + 1;
            final Message messages[] = inbox.getMessages(start, end);

            // Reverse the ordering so that the latest comes out first
            Message messageReverse[] = reverseMessageOrder(messages);
            emailFound = searchForMessage(messageReverse);
            inbox.close(true);

            if (!emailFound) {
                LOGGER.warn("Did not find Muso recovery email. Sleeping for 10 seconds...");
                ThreadHandler.sleep(10000);
                currentTime = System.currentTimeMillis() / 1000;
            }
        }

        store.close();

        return emailFound;
    }

    public String getManualLink() {
        return manualLink;
    }

    /*
     * reverse the order of the messages
     */
    private Message[] reverseMessageOrder(Message[] messages) {
        Message revMessages[] = new Message[messages.length];
        int i = messages.length - 1;
        for (int j = 0; j < messages.length; j++, i--) {
            revMessages[j] = messages[i];

        }

        return revMessages;

    }

    private boolean searchForMessage(Message[] messages) throws MessagingException,
            IOException {
        boolean emailFound = false;

        int i = 0;
        for (Message message : messages) {
            i++;
            LOGGER.info("Found message no #{}", i);
            for (Address address : message.getFrom()) {
                LOGGER.info("FROM: " + address.toString());
            }
            for (Address address : message.getRecipients(RecipientType.TO)) {
                LOGGER.info("To: " + address.toString());
            }
            LOGGER.debug("Subject: " + message.getSubject());

            final String messageSubject = message.getSubject();
            if (messageSubject.equalsIgnoreCase(EMAIL_SUBJECT)) {
                LOGGER.info("Found password recovery email from Muso");
                LOGGER.info("Message: /n" + getMessageContent(message));
                emailFound = true;
                // Searching for password recovery manual link
                final String pattern = "try entering this link manually: (.+?)(http[s]?)://(.*?)\"";
                manualLink = RegExpTools.getCustomGroupAllMatches(
                        getMessageContent(message), pattern, 3).get(0);

                //  manualLink = RegExpTools.regExpExtractor(getMessageContent(message), pattern);

                message.setFlag(Flags.Flag.SEEN, true);
                message.setFlag(Flags.Flag.DELETED, true);
                LOGGER.info("Marked SEEN for message: " + messageSubject);
                LOGGER.info("Marked DELETE for message: " + messageSubject);
                break;
            }
        }
        return emailFound;
    }

    private String getMessageContent(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getMessageContent(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getMessageContent(bp);
                    if (s != null)
                        return s;
                } else {
                    return getMessageContent(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getMessageContent(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }
}