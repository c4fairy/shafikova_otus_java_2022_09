package ru.otus.protobuf.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumberResponse;

public class ClientStreamObserver implements StreamObserver<NumberResponse> {
    private static final Logger LOG = LoggerFactory.getLogger(ClientStreamObserver.class);
    private long lastValue;

    @Override
    public synchronized void onNext(NumberResponse response) {
        LOG.info("Client received server value: {}", response);
        this.lastValue = response.getValue();
    }

    @Override
    public void onError(Throwable t) {
        LOG.error("Client error occurred", t);
    }

    @Override
    public void onCompleted() {
        LOG.info("Client ended up request");
    }

    public synchronized long getLastValueAndReset() {
        var lastValuePrev = this.lastValue;
        this.lastValue = 0;
        return lastValuePrev;
    }
}
