package ru.otus.protobuf.server;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumberRequest;
import ru.otus.protobuf.generated.NumberResponse;
import ru.otus.protobuf.generated.NumberServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class NumberService extends NumberServiceGrpc.NumberServiceImplBase implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(NumberService.class);
    private final ScheduledExecutorService scheduledExecutor;

    public NumberService() {
        this.scheduledExecutor = Executors.newScheduledThreadPool(10);
    }

    @Override
    public void close() {
        scheduledExecutor.shutdown();
    }

    @Override
    public void provideNumbers(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
        LOG.info("Server received request: {}", request);
        var currentValue = new AtomicLong(request.getFirstValue());

        scheduledExecutor.scheduleAtFixedRate(() -> {
            var value = currentValue.incrementAndGet();
            var response = NumberResponse.newBuilder().setValue(value).build();
            responseObserver.onNext(response);
            if (value == request.getLastValue()) {
                scheduledExecutor.shutdown();
                responseObserver.onCompleted();
                LOG.info("Server end of numbers sequence");
            }
        }, 1, 2, TimeUnit.SECONDS);
    }
}