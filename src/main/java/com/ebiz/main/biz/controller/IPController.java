package com.ebiz.main.biz.controller;

import com.ebiz.java.util.http.ResponseMessage;
import com.ebiz.java.util.http.Result;
import com.ebiz.main.biz.annotation.WhilteListRefresh;
import com.ebiz.main.biz.entity.IPFilterVO;
import com.ebiz.main.biz.service.IPService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/3/12.
 */
@RestController
@RequestMapping("/ip-filter")
@Api(description = "|IPController|白名单配置")
public class IPController {

    @Autowired
    private IPService ipService;

    //增
    @ApiOperation(value = "|IPController|白名单配置增加")
    @PostMapping("/create")
    @WhilteListRefresh
    public ResponseMessage create(@RequestBody IPFilterVO ipFilterVO) {
        ipService.create(ipFilterVO);
        return Result.success();
    }

    //查询
    @ApiOperation(value = "|IPController|白名单配置查询")
    @GetMapping("/query")
    public ResponseMessage<Map<String, Collection<IPFilterVO>>> query() {
        return Result.success(ipService.query());
    }

    //删
    @ApiOperation(value = "|IPController|白名单配置删除")
    @PostMapping("/delete")
    @WhilteListRefresh
    public ResponseMessage delete(@RequestBody IPFilterVO ipFilterVO) {
        ipService.delete(ipFilterVO);
        return Result.success();
    }
}