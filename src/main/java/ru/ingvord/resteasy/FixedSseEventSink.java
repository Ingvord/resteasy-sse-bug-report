package ru.ingvord.resteasy;

import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseEventSink;
import java.util.Objects;
import java.util.concurrent.CompletionStage;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 11/29/18
 */
public class FixedSseEventSink implements SseEventSink {

    private final SseEventSink sink;

    public FixedSseEventSink(SseEventSink sink) {
        this.sink = sink;
    }

    @Override
    public boolean isClosed() {
        return sink.isClosed();
    }

    @Override
    public CompletionStage<?> send(OutboundSseEvent outboundSseEvent) {
        return sink.send(outboundSseEvent);
    }

    @Override
    public void close() {
        sink.close();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }
}
