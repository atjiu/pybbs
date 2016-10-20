package cn.tomoya.util;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class MarkdownUtil {

//    static {
//        PropKit.use("config.properties");
//    }

//    public static String marked(String content) {
//        try {
//            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
//            ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension("js");
//            String markedPath = PropKit.get("markedjs.path");
//            scriptEngine.eval(
//                    new FileReader(
//                            StrUtil.isBlank(
//                                    PropKit.get("markedjs.path")) ?
//                                    PathKit.getWebRootPath() + "/static/js/marked.js" :
//                                    markedPath
//                    )
//            );
//            Invocable invocable = (Invocable) scriptEngine;
//            return (String) invocable.invokeFunction("marked", content);
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    private final static PegDownProcessor md = new PegDownProcessor(Extensions.ALL_OPTIONALS | Extensions.ALL_WITH_OPTIONALS);

    public static String pegDown(String content) {
        return md.markdownToHtml(content == null ? "" : content);
    }

}
