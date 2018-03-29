package com.ebiz.main.biz.controller;

import com.ebiz.java.util.http.ResponseMessage;
import com.ebiz.java.util.http.Result;
import com.ebiz.main.biz.annotation.RouteRefresh;
import com.ebiz.main.biz.entity.ZuulRouteVO;
import com.ebiz.main.biz.service.RouteService;
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
 * Created by Administrator on 2018/3/9.
 */
@RestController
@RequestMapping("/route")
@Api(description = "|RouteController|动态路由配置")
public class RouteController {

    @Autowired
    private RouteService routeService;

    //增
    @ApiOperation(value = "|RouteController|动态路由增加")
    @PostMapping("/create")
    @RouteRefresh
    public ResponseMessage create(@RequestBody ZuulRouteVO zuulRouteVO) {
        routeService.create(zuulRouteVO);
        return Result.success();
    }

    //删
    @ApiOperation(value = "|RouteController|动态路由删除")
    @GetMapping("/delete")
    @RouteRefresh
    public ResponseMessage delete(@RequestParam String id) {
        routeService.delete(id);
        return Result.success();
    }

    //改
    @ApiOperation(value = "|RouteController|动态路由修改")
    @PostMapping("/update")
    @RouteRefresh
    public ResponseMessage update(@RequestBody ZuulRouteVO zuulRouteVO) {
        routeService.update(zuulRouteVO);
        return Result.success();
    }

    //查
    @ApiOperation(value = "|RouteController|动态路由查询")
    @GetMapping("/query")
    public ResponseMessage<List<ZuulRouteVO>> query() {
        return Result.success(routeService.query());

    }
    //根据Id查
    @ApiOperation(value = "|RouteController|动态路由按ID查询")
    @GetMapping("/queryById")
    public ResponseMessage<ZuulRouteVO> queryById(@RequestParam String id) {
        return Result.success(routeService.query(id));
    }

}