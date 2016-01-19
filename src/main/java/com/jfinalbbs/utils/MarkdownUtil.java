package com.jfinalbbs.utils;

import com.jfinal.kit.PathKit;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class MarkdownUtil {

    public static String marked(String content) {
        try {
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension("js");
            scriptEngine.eval(new FileReader(PathKit.getWebRootPath() + "/static/js/marked.js"));
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
