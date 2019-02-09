package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

public interface Servlet {
    public void service(HttpRequest request, HttpResponse response) throws Exception;
}
