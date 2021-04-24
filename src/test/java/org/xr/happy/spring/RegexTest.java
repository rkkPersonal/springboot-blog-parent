package org.xr.happy.spring;

import org.junit.Test;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexTest {

    private static final String REGX = "^\\w{3,}$";
    private static final String REGX2 = ".ar";


    public static void main(String[] args) {

        specifial();
    }

    private static void specifial(){

        String regex= "root(?=[0-9]+)";
        String msg="1234root1231abce";

        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(msg);

        boolean matches = matcher.matches();
        System.out.println(matches);
        while (matcher.find()){
            for (int i = 0; i < matcher.groupCount(); i++) {

                System.out.println(matcher.group(i));
            }
        }

    }

    private static void matches3() {
        String regex = "^[0-9]*$";
        String regex2 = "^(0|[1-9][0-9]*)$";
        System.out.println("请输入：");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        if (s.matches(regex2)) {
            System.out.println("对");
        } else {
            System.out.println("错");
        }

        String str = "";
        String pattern = "^\\w*@[a-zA-Z][.].*$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }


    public static void matches() {
        final String regex = "the";
        final String string = "The fat cat sat on the mat.";
        final String subst = "the";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        // The substituted value will be contained in the result variable
        final String result = matcher.replaceAll(subst);

        System.out.println("Substitution result: " + result);
    }


    public static void matches2() {
        final String regex = "(?i)(t(he))";
        final String string = "The fat cat sat on the mat,He is The.";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        int count = 0;
        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            count++;
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }
        System.out.println(count);

        String ado = string.replaceAll(regex, "ADO");

        System.out.println(ado);

    }

    @Test
    public void firstLetter() {


    }


    @Test
    public void testEmail() {

      /*  String email = "17635841699@163.com";

        String regx = "[a-z0-9A-z]+[@].*[.][a-z]{3,}";

        if (email.matches(regx)) {
            System.out.println("email校验通过");
        } else {
            System.out.println("Email格式不正确！！！");
        }*/

    }
}
