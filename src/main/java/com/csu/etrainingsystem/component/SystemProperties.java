package com.csu.etrainingsystem.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemProperties {
    /*
     * 如果保留{}输出结果为{com.csu-engineer-train-front.system.name}
     * @Value的三种写法:"#{}";"${}";""
     */
    @Value("com.csu-engineer-train-front.system.name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
