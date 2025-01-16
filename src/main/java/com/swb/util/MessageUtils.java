package com.swb.util;

import java.util.HashMap;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class MessageUtils {


    public static String createGroupMessage(Long groupId, String message, String active) {
        HashMap<String, Object> msg = new HashMap<>();
        HashMap<String, Object> param = new HashMap<>();
        msg.put("action", active);
        param.put("message", message);
        param.put("group_id", groupId);
        msg.put("params", param);
        return JsonUtils.toJson(msg);
    }
}
