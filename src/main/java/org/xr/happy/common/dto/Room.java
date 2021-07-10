package org.xr.happy.common.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Steven
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "直播间的所有对象属性")
public class Room {

    @ApiModelProperty(required = true, name = "roomId", value = "12345", dataType = "String", notes = "用来描述房间号")
    private String roomId;

    @ApiModelProperty(required = true, name = "roomId", value = "99", dataType = "String", notes = "汇总房间总数")
    private Integer sum;


}
