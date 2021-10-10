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

    private String shoppingName;

    private Long shoppingId;

    private String shopping;


}
