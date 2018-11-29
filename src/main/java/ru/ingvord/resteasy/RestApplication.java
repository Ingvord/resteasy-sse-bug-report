package ru.ingvord.resteasy;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 11/29/18
 */
@ApplicationPath("/")
public class RestApplication extends Application {

    private Sse sse;
    private SseBroadcaster broadcaster;

    @Context
    public void setSse(Sse sse){
        this.sse = sse;
        this.broadcaster = sse.newBroadcaster();
        this.broadcaster.onClose(sseEventSink -> {
            System.out.println(sseEventSink.toString());
        });

        CompletableFuture.runAsync(() -> {
            while(!Thread.currentThread().isInterrupted()){
                OutboundSseEvent event = sse.newEvent("event");

                broadcaster.broadcast(event);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    @Override
    public Set<Object> getSingletons() {
        return Collections.singleton(new SseResource(broadcaster));
    }
}
