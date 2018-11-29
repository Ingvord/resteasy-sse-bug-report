package ru.ingvord.resteasy;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.awt.*;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 11/29/18
 */
@Path("/")
public class SseResource {
    private final SseBroadcaster broadcaster;

    public SseResource(SseBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void get(@Context SseEventSink sink){
        this.broadcaster.register(sink);
    }
}
