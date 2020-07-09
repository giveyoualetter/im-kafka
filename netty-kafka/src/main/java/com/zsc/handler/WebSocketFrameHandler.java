package com.zsc.handler;

import com.zsc.kafka.consumer.KafkaConsumerFactory;
import com.zsc.kafka.task.ConsumeKafkaMessage;
import com.zsc.kafka.utils.KafkaUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/24 13:13
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketFrameHandler.class);
    private KafkaConsumer consumer = null;//tmd这个不要设置为static
    private long userId = -1L;
    private boolean registerflag = false;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled
        ctx.writeAndFlush(new TextWebSocketFrame("已经建立连接"));
        final ChannelHandlerContext context = ctx;
        final Channel channel = ctx.channel();
        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            //logger.info("{} received {}", ctx.channel(), request);
            //ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));
            if(request.startsWith("register:")) {
                userId = Long.parseLong(request.trim().split(":")[1].trim());;
                if (!KafkaUtils.hasTopic(String.valueOf(userId))) {
                    KafkaUtils.createTopic(String.valueOf(userId));
                }
                registerflag = true;
            }
            if(registerflag){
                Map map = KafkaConsumerFactory.getMap();
                if(!map.keySet().contains(String.valueOf(userId))) {
                    schedule(channel);
                }
            }
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
        KafkaConsumerFactory.removeConsumer(String.valueOf(userId));
        System.out.println(cause);
    }
    public void schedule(final Channel channel) throws InterruptedException {
        consumer = KafkaConsumerFactory.getConsumer("localhost:9092", "1", String.valueOf(userId));
        KafkaUtils.getPartitionsForTopic(consumer,String.valueOf(userId));
        System.out.println("当前线程："+Thread.currentThread().getName()+ ";当前WebFrameHandler对象："+this.hashCode()+";当前consumer："+consumer.hashCode());
        while(true){
            synchronized (consumer) {
                Thread.sleep(1000);
                String message = ConsumeKafkaMessage.consume(consumer);
                if (message != "") channel.writeAndFlush(new TextWebSocketFrame(message));
            }
        }
    }
}

