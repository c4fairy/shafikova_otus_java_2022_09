package ru.otus.protobuf.client;

import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumberRequest;
import ru.otus.protobuf.generated.NumberServiceGrpc;


public class GRPCClient {
    private static final Logger LOG = LoggerFactory.getLogger(GrpcClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int REPEAT_LIMIT = 50;

    private long currentValue;

    public static void main(String[] args) {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var asyncStub = NumberServiceGrpc.newStub(channel);
        var numberRequest = generateRequest(1, 30);

        var clientStreamObserver = new ClientStreamObserver();
        var client = new GrpcClient();
        asyncStub.provideNumbers(numberRequest, clientStreamObserver);

        for (int i = 0; i < REPEAT_LIMIT; i++) {
            sleep();
            client.currentValue = client.getNextValue(clientStreamObserver);
            LOG.info("Client current value: {}", client.getCurrentValue());
        }
        channel.shutdown();
    }

    private long getNextValue(ClientStreamObserver clientStreamObserver) {
        currentValue = currentValue + clientStreamObserver.getLastValueAndReset() + 1;
        return currentValue;
    }

    private long getCurrentValue() {
        return this.currentValue;
    }

    private static NumberRequest generateRequest(int firstValue, int lastValue) {
        return NumberRequest.newBuilder()
                .setFirstValue(firstValue)
                .setLastValue(lastValue)
                .build();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}