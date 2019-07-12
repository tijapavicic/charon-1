package com.github.martingaston.application.http;

public class Router {
    private Request request;

    public Router(Request request) {
        this.request = request;
    }

    public Response respond() {
        Response response;
        Headers headers = new Headers();
        Body body;

        headers.add("Connection", "close");

        switch (request.method()) {
            case GET:
                headers.add("Content-Length", 0);
                body = Body.from("");
                response = new Response(Status.OK, headers, body);
                break;
            case POST:
                headers.add("Content-Length", request.bodyContentLength());
                body = Body.from(request.body().toString());
                response = new Response(Status.OK, headers, body);
                break;
            case HEAD:
                headers.add("Content-Length", request.bodyContentLength());
                body = Body.from("");
                response = new Response(Status.OK, headers, body);
                break;
            default:
                headers.add("Allow", "GET");
                body = Body.from("");
                response = new Response(Status.METHOD_NOT_ALLOWED, headers, body);
                break;
        }

        return response;
    }
}