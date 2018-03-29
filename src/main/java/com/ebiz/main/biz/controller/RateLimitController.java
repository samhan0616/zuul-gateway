package com.ebiz.main.biz.controller;

import com.ebiz.java.util.http.ResponseMessage;
import com.ebiz.java.util.http.Result;
import com.ebiz.main.biz.annotation.RateLimitRefresh;
import com.ebiz.main.biz.entity.RateLimitVO;
import com.ebiz.main.biz.service.RateLimitService;
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
 * Created by Administrator on 2018/3/12.
 */
@RestController
@RequestMapping("/rate-limit")
@Api(description = "|RateLimitController|限流配置")
public class RateLimitController {

    @Autowired
    private RateLimitService rateLimitService;

    //增
    @ApiOperation(value = "|RateLimitController|限流增加")
    @PostMapping("/create")
    @RateLimitRefresh
    public ResponseMessage create(@RequestBody RateLimitVO rateLimitVO) {
        rateLimitService.create(rateLimitVO);
        return Result.success();
    }

    //删
    @ApiOperation(value = "|RateLimitController|限流删除")
    @GetMapping("/delete")
    @RateLimitRefresh
    public ResponseMessage delete(@RequestParam String id) {
        rateLimitService.delete(id);
        return Result.success();
    }

    //改
    @ApiOperation(value = "|RateLimitController|限流修改")
    @PostMapping("/update")
    @RateLimitRefresh
    public ResponseMessage update(@RequestBody RateLimitVO rateLimitVO) {
        rateLimitService.update(rateLimitVO);
        return Result.success();
    }

    //查
    @ApiOperation(value = "|RateLimitController|限流查询")
    @GetMapping("/query")
    public ResponseMessage<List<RateLimitVO>> query() {
        return Result.success(rateLimitService.query());

    }
    //根据Id查
    @ApiOperation(value = "|RateLimitController|限流按ID查询")
    @GetMapping("/queryById")
    public ResponseMessage<RateLimitVO> queryById(@RequestParam String id) {
        return Result.success(rateLimitService.query(id));
    }
}