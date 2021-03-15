package com.example.boot.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

        // Let Spring handle the error first, we will modify later :)
        Map<String, Object> errorAttributes = this.getErrorAttributes(webRequest, false);
        Throwable exception = (Throwable) webRequest.getAttribute(DefaultErrorAttributes.class.getName() + ".ERROR", 0);
        // format & update timestamp
        Object timestamp = errorAttributes.get("timestamp");
        if (timestamp == null) {
            errorAttributes.put("timestamp", dateFormat.format(new Date()));
        } else {
            errorAttributes.put("timestamp", dateFormat.format((Date) timestamp));
        }

        if (exception != null) {
            errorAttributes.put("detailMessage", exception.getMessage());
        }

        return errorAttributes;

    }
}
