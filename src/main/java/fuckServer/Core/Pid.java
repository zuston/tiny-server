package fuckServer.Core;

import sun.security.jca.GetInstance;

/**
 * Created by zuston on 17/1/7.
 */
public class Pid {
    public static Long pid = Long.valueOf(0);

    public static Long getPid(){
        return pid++;
    }
}
