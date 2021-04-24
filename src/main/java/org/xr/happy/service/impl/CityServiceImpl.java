package org.xr.happy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xr.happy.common.dto.City;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.model.User;
import org.xr.happy.service.CityService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Steven
 */
@Service
public class CityServiceImpl implements CityService {

    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public List<City> getAllCities() {
        List<City> list = new ArrayList<>();
        Collections.addAll(list, new City("A", 1L, "杭州", "浙江", "中国"),
                new City("B", 2L, "海宁", "浙江", "中国"));
        List<User> users = userMapper.selectAll();
        return list;
    }

    @Override
    public List<City> findCityByCountCode(String countCode) {
        List<City> list = new ArrayList<>();
        Collections.addAll(list, new City("A", 1L, "杭州", "浙江", "中国"),
                new City("B", 2L, "海宁", "浙江", "中国"));
        return list;
    }
}
