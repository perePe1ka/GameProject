package ru.vladuss.gamesgateway.utils;

import com.example.gameproto.GameResponse;
import com.google.protobuf.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class ProtoMessConverter implements MessageConverter {
    @Override
    public org.springframework.amqp.core.Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        if (!(object instanceof Message)) {
            throw new MessageConversionException("Объект не является сообщением protobuf");
        }
        Message protobufMessage = (Message) object;
        byte[] bytes = protobufMessage.toByteArray();
        messageProperties.setContentType("application/x-protobuf");
        messageProperties.setContentLength(bytes.length);
        return new org.springframework.amqp.core.Message(bytes, messageProperties);
    }
    @Override
    public Object fromMessage(org.springframework.amqp.core.Message message) throws MessageConversionException {
        try {
            String contentType = message.getMessageProperties().getContentType();
            if (!"application/x-protobuf".equals(contentType)) {
                throw new MessageConversionException("Неподдерживаемый тип контента: " + contentType);
            }
            return GameResponse.parseFrom(message.getBody());
        } catch (Exception e) {
            throw new MessageConversionException("Не удалось преобразовать сообщение Protobuf.", e);
        }
    }
}