package com.csu.etrainingsystem.util;

import com.csu.etrainingsystem.form.CommonResponseForm;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionPrint {
    public static String get(Exception e){
        e.printStackTrace();
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        e.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        System.out.println(buffer.toString());
        return buffer.toString();
    }
}
