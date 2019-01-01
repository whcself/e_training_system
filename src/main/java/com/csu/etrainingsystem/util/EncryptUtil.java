package com.csu.etrainingsystem.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class EncryptUtil {

    public static String encrypt(String pwd ) {
        SimpleHash hash = new SimpleHash ("md5", pwd, "e-training-system", 3);
         pwd = hash.toString ();
         return pwd;
    }
}
