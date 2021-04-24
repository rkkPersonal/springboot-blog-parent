package org.xr.happy.service;

import org.xr.happy.common.dto.City;

import java.util.List;

public interface CityService {

   List<City> getAllCities();


   List<City> findCityByCountCode(String countCode);
}
