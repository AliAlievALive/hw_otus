package org.otus;

public class ApplicationProperties {
    private static final int SERVER_PORT = 8210;
    private static final String SERVER_HOST = "localhost";

    private ApplicationProperties() {}

    public static String getServerHost() {
        return SERVER_HOST;
    }

    public static int getServerPort() {
        return SERVER_PORT;
    }
}
