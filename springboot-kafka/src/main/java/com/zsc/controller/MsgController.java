package com.zsc.controller;

import com.alibaba.fastjson.JSON;
import com.zsc.dto.ClientMsg;
import com.zsc.service.SendMsgToKafka;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/25 9:29
 */
@Controller
@RequestMapping("/")
public class MsgController {

    @ResponseBody
    @RequestMapping(value = "/msg",method = RequestMethod.POST)
    public String getUniqueId(@RequestBody ClientMsg clientMsg){
        System.out.println(clientMsg.getGroupId());
        String msg = JSON.toJSONString(clientMsg);
        System.out.println(msg);
        SendMsgToKafka.sendMsg(clientMsg);
        return "success";
    }

}
