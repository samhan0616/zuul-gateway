package com.ebiz.main.biz.controller;

import com.ebiz.java.util.http.ResponseMessage;
import com.ebiz.java.util.http.Result;
import com.ebiz.main.biz.entity.StatisticConditionVO;
import com.ebiz.main.biz.entity.ZuulConfigVO;
import com.ebiz.main.biz.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */
@RestController
@RequestMapping("/statistic")
@Api(description = "|StatisticController|流量统计")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    //查
    @ApiOperation(value = "|StatisticController|流量统计查询")
    @GetMapping("/query")
    public ResponseMessage<List<ZuulConfigVO>> query(StatisticConditionVO statisticConditionVO) {

        return Result.success();
    }

}