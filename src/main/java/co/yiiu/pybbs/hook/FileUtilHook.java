package co.yiiu.pybbs.hook;

import org.aspectj.lang.annotation.Pointcut;

public class FileUtilHook {

    @Pointcut("execution(public * co.yiiu.pybbs.util.FileUtil.upload(..))")
    public void upload() {
    }

}
