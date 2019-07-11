package com.github.martingaston.application;

import com.github.martingaston.application.transport.MockConnection;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@DisplayName("The App class")
class AppTest {
    @DisplayName("POST /echo_body with body 'some body' returns 200 with body 'some body'")
    @Test
    void simplePostRequest() throws IOException {
        byte[] request = "POST /echo_body HTTP/1.1\r\n\r\nsome body".getBytes();
        byte[] response = "HTTP/1.1 200 OK\r\n\r\nsome body".getBytes();

        var connection = new MockConnection(request);
        var app = new App(connection);
        app.listen();

        assertThat(connection.received()).isEqualTo(response);
    }

    @DisplayName("POST /echo_body with body 'i wanna dance with some body' returns 200 with body 'i wanna dance with some body'")
    @Test
    void simplePostRequestDanceWithSomeBody() throws IOException {
        byte[] request = "POST /echo_body HTTP/1.1\r\n\r\ni wanna dance with some body".getBytes();
        byte[] response = "HTTP/1.1 200 OK\r\n\r\ni wanna dance with some body".getBytes();

        var connection = new MockConnection(request);
        var app = new App(connection);
        app.listen();

        assertThat(connection.received()).isEqualTo(response);
    }

    @DisplayName("Will wait for a client to connect")
    @Test
    void waitsForClient() throws IOException {
        byte[] request = "GET /echo_body HTTP/1.1\r\n\r\nsome body".getBytes();

        var connection = new MockConnection(request);
        var app = new App(connection);
        app.listen();

        assertThat(connection.awaitClientCalledXTimes()).isEqualTo(1);
    }
}