package cn.jfinalbbs.common;

import cn.jfinalbbs.utils.Result;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * Created by liuyang on 15/4/15.
 */
public class BaseController extends Controller {

    public void success() {
        success(null);
    }

    public void success(Object object) {
        renderJson(new Result(Constants.ResultCode.SUCCESS, Constants.ResultDesc.SUCCESS, object));
    }

    public void error(String message) {
        renderJson(new Result(Constants.ResultCode.FAILURE, message, null));
    }

    /**
     * 根据cacheName, cacheKey来清除缓存
     * cacheName 必填，cacheKey选填，不填的话为null
     * @param cacheName
     * @param cacheKey
     */
    public void clearCache(String cacheName, Object cacheKey) {
        if (cacheKey == null) {
            CacheKit.removeAll(cacheName);
        } else {
            CacheKit.remove(cacheName, cacheKey);
        }
    }
}
