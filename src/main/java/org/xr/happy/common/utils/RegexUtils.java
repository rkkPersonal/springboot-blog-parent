package org.xr.happy.common.utils;

import org.xr.happy.common.enums.RegexEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Steven
 */
public class RegexUtils {


    public static boolean isEmail(String  email){
        return pattern(RegexEnum.EMAIL.getRegex(),email);
    }

    public static boolean isPassword(String  password){
        return pattern(RegexEnum.PASSWORD.getRegex(),password);
    }

    private static boolean pattern(String regex,String email) {
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(email);
        boolean matches = matcher.matches();
        return matches;
    }


}
