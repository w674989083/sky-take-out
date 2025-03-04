package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
@RequiredArgsConstructor
public class ShopController {

    private final RedisTemplate redisTemplate;

    private final static String SHOP_STATUS = "SHOP_STATUS";

    @GetMapping("/status")
    public Result getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        return Result.success(status);
    }


}
