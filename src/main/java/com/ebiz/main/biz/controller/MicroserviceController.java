package com.ebiz.main.biz.controller;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MicroserviceController {


    //服务发现客户端
    @Autowired
    private DiscoveryClient discoveryClient;



    /**
     * 本地服务实例信息
     * */
    @GetMapping("/info")
    public List<String> showInfo(){



        List<String> strings = new ArrayList<>();
        discoveryClient.getServices().forEach(id -> {
            discoveryClient.getInstances(id).forEach(instance -> {
                strings.add("/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
            });
        });
        return strings;
    }


}