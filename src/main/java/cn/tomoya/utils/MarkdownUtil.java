package cn.tomoya.utils;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://bbs.tomoya.cn
 */
public class MarkdownUtil {

    static {
        PropKit.use("config.properties");
    }

    public static String marked(String content) {
        try {
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension("js");
            String markedPath = PropKit.get("markedjs.path");
            scriptEngine.eval(
                    new FileReader(
                            StrUtil.isBlank(
                                    PropKit.get("markedjs.path")) ?
                                    PathKit.getWebRootPath() + "/static/js/marked.js" :
                                    markedPath
                    )
            );
            Invocable invocable = (Invocable) scriptEngine;
            return (String) invocable.invokeFunction("marked", content);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "";
    }

}
