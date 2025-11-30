package com.codewithProject.employee.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.Filter;
import org.slf4j.MDC;
import java.io.IOException;
import java.util.UUID;

public class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestId = UUID.randomUUID().toString();
        try {
            MDC.put("requestId", requestId);
            chain.doFilter(request, response);
        } finally {
            MDC.remove("requestId");
        }
    }
}
