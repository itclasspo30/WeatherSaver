package com.weathersaver.service;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.weathersaver.beans.WeatherBox;

@Component
public class AddDataListener {

    @Autowired
    DataBaseService dataBaseService;

    static final Logger logger = LoggerFactory.getLogger(AddDataListener.class);

    @RabbitListener(queues = "myQueue")
    public void process(ArrayList<WeatherBox> newList, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
            throws IOException {
        if (newList != null) {
            boolean result = dataBaseService.addNew(newList);
            if (!result) {
                channel.basicNack(tag, false, true);
            }
        }
    }
}
