package ru.ingvord.resteasy.test;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;
import java.util.concurrent.CompletableFuture;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 11/29/18
 */
public class TestSse {

    @Test
    public void test() throws Exception {
        Client client1 = ResteasyClientBuilder.newClient();

        Client client2 = ResteasyClientBuilder.newClient();


        WebTarget target1 = client1.target("http://localhost:8080/sse");
        WebTarget target2 = client2.target("http://localhost:8080/sse");

        SseEventSource eventSource1 = SseEventSource.target(target1).build();
        eventSource1.register(inboundSseEvent ->{
                System.out.print("event1:");
                System.out.println(inboundSseEvent.readData());
        });

        eventSource1.open();


        SseEventSource eventSource2 = SseEventSource.target(target2).build();
        eventSource2.register(inboundSseEvent ->{
                System.out.print("event2:");
                System.out.println(inboundSseEvent.readData());
        });


        eventSource2.open();

        Thread.sleep(3000);
        System.out.println("Close second event source");
        eventSource2.close();

        Thread.sleep(3000);
        //eventSource1 stops receive any events
    }
}
