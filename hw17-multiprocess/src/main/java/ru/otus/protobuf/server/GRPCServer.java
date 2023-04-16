package ru.otus.protobuf.server;


import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GRPCServer {
    private static final Logger LOG = LoggerFactory.getLogger(GrpcServer.class);

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var numberEndpoint = new NumberService();

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(numberEndpoint).build();
        server.start();
        LOG.info("Server waiting for client connections...");
        server.awaitTermination();
    }
}