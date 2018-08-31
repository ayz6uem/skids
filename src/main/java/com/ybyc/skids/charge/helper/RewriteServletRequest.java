package com.ybyc.skids.charge.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

@Slf4j
public class RewriteServletRequest extends HttpServletRequestWrapper {

    HttpServletRequest httpServletRequest;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


    public RewriteServletRequest(HttpServletRequest httpServletRequest) throws IOException {
        super(httpServletRequest);
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return new DelegatingServletInputStream(byteArrayInputStream);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public ServletInputStream getOriginInputStream() throws IOException {
        return httpServletRequest.getInputStream();
    }

    public OutputStream getOutputStream(){
        return byteArrayOutputStream;
    }


}
