package com.lee.shop.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DefaultServlet extends HttpServlet {

    private String webRoot;

    @Override
    public void init() {
        webRoot = getServletContext().getRealPath("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        File file = new File(webRoot + req.getRequestURI());
        if (file.exists()) {
            Files.copy(file.toPath(), resp.getOutputStream());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}