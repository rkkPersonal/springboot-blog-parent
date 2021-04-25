package org.xr.happy.common.bean;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Steven
 */
@Getter
@Setter
@ToString
public class CsvUser {

    @CsvBindByName(column = "username")
    private String username;
    @CsvBindByName(column = "age")
    private String age;

}
