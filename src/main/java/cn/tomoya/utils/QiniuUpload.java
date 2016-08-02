package cn.tomoya.utils;

import com.jfinal.kit.LogKit;
import com.jfinal.kit.PropKit;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class QiniuUpload {

    static {
        PropKit.use("config.properties");
    }

    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = PropKit.get("qiniu.access_key");
    String SECRET_KEY = PropKit.get("qiniu.secret_key");
    //要上传的空间
    String bucketname = PropKit.get("qiniu.bucketname");
    //上传到七牛后保存的文件名
    String key = DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss") + StrUtil.randomString(6);

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(){
        return auth.uploadToken(bucketname);
    }

    public Map upload(String filePath) throws IOException {
        try {
            //创建上传对象
            UploadManager uploadManager = new UploadManager();
            //调用put方法上传
            Response res = uploadManager.put(filePath, key, getUpToken());
            //打印返回的信息
            Map map = StrUtil.parseToMap(res.bodyString());
            return map;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            LogKit.error(r.toString());
            try {
                //响应的文本信息
                LogKit.info(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
            return null;
        }
    }

    public static void main(String args[]) throws IOException{
        new QiniuUpload().upload("");
    }
}
