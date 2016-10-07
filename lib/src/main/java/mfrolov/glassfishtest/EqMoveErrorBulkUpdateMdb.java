package mfrolov.glassfishtest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

@MessageDriven
public class EqMoveErrorBulkUpdateMdb implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(EqMoveErrorBulkUpdateMdb.class);

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            LOG.info(String.format("text: %s, propertyNames: [%s], JMSRedelivered: %s, JMSDeliveryMode: %d, JMSExpiration: %d, JMSTimestamp: %d",
                    text,
                    Joiner.on(",").join(propertyNames(message)),
                    textMessage.getJMSRedelivered(),
                    textMessage.getJMSDeliveryMode(),
                    textMessage.getJMSExpiration(),
                    textMessage.getJMSTimestamp()));

            if (text.equals("catch")) {
                try {
                    throw new RuntimeException(text);
                } catch (RuntimeException e) {
                    LOG.error("RuntimeException: " + e.getMessage());
                }
            } else if (text.startsWith("sleep")) {
                long msec = Long.parseLong(text.substring("sleep".length() + 1));
                try {
                    LOG.info("sleeping for {} msec ...", msec);
                    Thread.sleep(msec);
                    LOG.info("woke up");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (text.equals("kill")) {
                throw new RuntimeException(text);
            }
        } catch (JMSException e) {
            LOG.error("JMSException: " + e.getMessage());
        }
    }

    private List<String> propertyNames(Message message) throws JMSException {
        List<String> result = new ArrayList<>();
        Enumeration propertyNames = message.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = (String) propertyNames.nextElement();
            result.add(propertyName);
        }
        return result;
    }
}
