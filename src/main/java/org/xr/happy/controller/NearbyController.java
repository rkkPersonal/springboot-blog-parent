package org.xr.happy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xr.happy.common.constant.RedisKey;
import org.xr.happy.common.dto.Result;

@RestController
public class NearbyController {


    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public Result addGeo(Double longitude, Double latitude, String userId) {

        Point point = new Point(longitude, latitude);
        redisTemplate.opsForGeo().add(RedisKey.GEO_KEY, new RedisGeoCommands.GeoLocation(userId, point));
        return Result.ok();
    }


    @GetMapping
    public Result nearby(Double longitude, Double latitude,
                         @RequestParam(defaultValue = "3000") Double range,
                         @RequestParam(defaultValue = "5") Long count) {

        Point point = new Point(longitude, latitude);
        near(point, range, count);

        return Result.ok();
    }

    /**
     * 附近的人
     *
     * @param point 用户自己的位置
     * @param range
     */
    public GeoResults<RedisGeoCommands.GeoLocation> near(Point point, Double range, Long count) {
        // 半径 3000米
        Distance distance = new Distance(range, RedisGeoCommands.DistanceUnit.METERS);
        Circle circle = new Circle(point, distance);
        // 附近5个人 -- 业务需求
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().limit(count);
        GeoResults<RedisGeoCommands.GeoLocation> geoResults = redisTemplate.opsForGeo().radius(RedisKey.GEO_KEY, circle, geoRadiusCommandArgs);
        return geoResults;
    }
}
