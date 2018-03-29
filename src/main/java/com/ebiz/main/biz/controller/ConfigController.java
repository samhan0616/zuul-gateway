package com.ebiz.main.biz.controller;

import com.ebiz.java.util.http.ResponseMessage;
import com.ebiz.java.util.http.Result;
import com.ebiz.main.biz.annotation.ConfigRefresh;
import com.ebiz.main.biz.entity.ZuulConfigVO;
import com.ebiz.main.biz.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */
@RestController
@RequestMapping("/config")
@Api(description = "|ConfigController|网关配置")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    //增加
    @ApiOperation(value = "|ConfigController|网关配置增加")
    @PostMapping("/create")
    @ConfigRefresh
    public ResponseMessage create(@RequestBody ZuulConfigVO zuulConfigVO) {
        configService.create(zuulConfigVO);
        return Result.success();
    }

    //状态修改
    @ApiOperation(value = "|ConfigController|网关配置修改")
    @PostMapping("/update")
    @ConfigRefresh
    public ResponseMessage update(@RequestBody ZuulConfigVO zuulConfigVO) {
        configService.update(zuulConfigVO);
        return Result.success();
    }



    //查
    @ApiOperation(value = "|ConfigController|网关配置查询")
    @GetMapping("/query")
    public ResponseMessage<List<ZuulConfigVO>> query() {
        return Result.success(configService.query());

    }
    //根据serviceId查
    @ApiOperation(value = "|ConfigController|网关配置按名称查询")
    @GetMapping("/queryByName")
    public ResponseMessage<ZuulConfigVO> queryByName(@RequestParam String name) {
        return Result.success(configService.queryByName(name));
    }
}