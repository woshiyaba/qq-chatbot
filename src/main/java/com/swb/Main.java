package com.swb;

import com.swb.netty.server.NettyServerStart;
import com.swb.ai.DeepSeekChatUtils;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        new NettyServerStart().start(Integer.parseInt(args[0]));
        DeepSeekChatUtils.init(args[1] == null ? "system.txt" : args[1]);
    }
}