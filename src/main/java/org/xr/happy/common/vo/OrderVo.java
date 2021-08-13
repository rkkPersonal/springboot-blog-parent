package org.xr.happy.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xr.happy.common.annotation.NotEmpty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVo {

    private String orderId;

    private String shopping;

    public static void main(String[] args) throws Exception{

        Pattern compile = Pattern.compile("/swagger.*/.*");

        Matcher matcher = compile.matcher("/swagger-ui/index.html");

        boolean matches = matcher.matches();
        System.out.println(matches);
    }
}
