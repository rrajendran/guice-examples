package com.capella.aws.s3.bucket.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.capella.aws.s3.bucket.exceptions.ServiceException;
import com.google.common.base.Preconditions;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * @author Ramesh Rajendran
 */
public class AwsSQSServiceImpl implements AwsSQSService {

    @Inject
    private AmazonSQS amazonSQS;

    @Override
    public String createQueue(String queueName) {
        Preconditions.checkNotNull(queueName, "Required queueName");
        final CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
        return amazonSQS.createQueue(createQueueRequest).getQueueUrl();
    }

    @Override
    public List<String> listQueues() {
        ListQueuesResult listQueuesResult = amazonSQS.listQueues();
        return listQueuesResult.getQueueUrls();
    }

    @Override
    public String send(String queueUrl, String message, Map<String,MessageAttributeValue> messageAttributes){
        Preconditions.checkNotNull(queueUrl, "Required queueUrl");
        Preconditions.checkNotNull(message, "Required message");
        SendMessageRequest sendRequest = new SendMessageRequest(queueUrl, message);
        sendRequest.setMessageAttributes(messageAttributes);

        SendMessageResult sendMessageResult = amazonSQS.sendMessage(sendRequest);
        return sendMessageResult.getMessageId();
    }

    @Override
    public List<Message> receive(String queueUrl){
        Preconditions.checkNotNull(queueUrl, "Required queueUrl");
        final ReceiveMessageRequest recieveMessageRequest = new ReceiveMessageRequest(queueUrl);
        return amazonSQS.receiveMessage(recieveMessageRequest).getMessages();
    }

    @Override
    public void deleteQueue(String queueUrl){
        final DeleteQueueRequest deleteQueueRequest = new DeleteQueueRequest(queueUrl) ;
        amazonSQS.deleteQueue(deleteQueueRequest);
    }
}
