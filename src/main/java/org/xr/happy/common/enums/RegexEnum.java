package org.xr.happy.common.enums;

import lombok.Getter;

/**
 * @author Steven
 */

@Getter
public enum RegexEnum {

    EMAIL("","邮箱正则匹配"),
    PASSWORD("\\w{6,12}","密码为数据字母下划线，密码长度为6-12");

    private String regex;

    private String descr;
    RegexEnum() {
    }
    RegexEnum(String regex, String descr) {
        this.regex = regex;
        this.descr = descr;
    }
}
