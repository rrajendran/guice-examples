package com.capella.aws.s3.bucket.services;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.util.List;
import java.util.Map;

/**
 * @author Ramesh Rajendran
 */
public interface AwsSQSService {

    /**
     * Create queue
     * @param queueName     The queue name
     * @return              Returns the queue url
     */
    String createQueue(String queueName);

    /**
     * List queues
     * @return      Returns queue url
     */
    List<String> listQueues();

    /**
     * Send message
     * @param queueUrl              The queue url
     * @param message               The message
     * @param messageAttributes     The message attributes
     * @return                      Returns the message id
     */
    String send(String queueUrl, String message, Map<String,MessageAttributeValue> messageAttributes);

    /**
     * Receive message
     * @param queueUrl      The queue url
     * @return              Returns the list of messages
     */
    List<Message> receive(String queueUrl);

    /**
     * Delete queue
     * @param queueUrl      The queue url
     */
    void deleteQueue(String queueUrl);
}
