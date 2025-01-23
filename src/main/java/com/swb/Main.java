package com.swb;

import com.swb.ai.GeminiUtils;
import com.swb.netty.server.NettyServerStart;
import com.swb.ai.DeepSeekChatUtils;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        init(args);
        new NettyServerStart().start(Integer.parseInt(args[0]));
    }

    private static void init(String[] args) {
        DeepSeekChatUtils.init(args[1] == null ? "prompt.txt" : args[1], args[2]);
        GeminiUtils.init(args[3]);
    }
}