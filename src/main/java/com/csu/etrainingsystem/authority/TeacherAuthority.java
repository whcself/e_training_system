package com.csu.etrainingsystem.authority;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * 为了方便模糊查询，所以设成String
 */
public class TeacherAuthority {
    public static final String MATERIAL_NULL = "0";
    public static final String MATERIAL_REGISTER = "1";
    public static final String MATERIAL_BUY = "2";
    public static final String OVERWORK_NULL = "0";
    public static final String OVERWORK_MANAGE = "1";
    public static final String ALL = "%";

}
