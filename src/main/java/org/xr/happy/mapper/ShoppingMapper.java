package org.xr.happy.mapper;

import org.apache.ibatis.annotations.Param;
import org.xr.happy.basemapper.MyMapper;
import org.xr.happy.model.Shopping;

/**
 * @author Steven
 */
public interface ShoppingMapper extends MyMapper<Shopping> {

    /**
     * 更新库存信息
     *
     * @param shoppingId
     * @param count
     * @return
     */
    int updateShoppingCount(@Param("id") Long shoppingId, Integer count);
}