package co.yiiu.pybbs.plugin;

import co.yiiu.pybbs.service.ISystemConfigService;
import co.yiiu.pybbs.util.FileUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Component
@Aspect
public class FileUploadPlugin {

    private final Logger log = LoggerFactory.getLogger(FileUploadPlugin.class);
    @Resource
    private ISystemConfigService systemConfigService;
    @Resource
    private FileUtil fileUtil;

    @Around("co.yiiu.pybbs.hook.FileUtilHook.upload()")
    public Object upload(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String cloudStoragePlatform = systemConfigService.selectAllConfig().get("cloud_storage_platform");
        log.info("fileUtilHook.upload: {}", cloudStoragePlatform);
        Object[] args = proceedingJoinPoint.getArgs();
        if (StringUtils.isEmpty(cloudStoragePlatform) || UploadPlatForm.LOCAL.name().equals(cloudStoragePlatform)) {
            return proceedingJoinPoint.proceed(args);
        } else if (UploadPlatForm.QINIU.name().equals(cloudStoragePlatform)) {
            return fileUtil.qiniuUpload((MultipartFile) args[0], (String) args[1], (String) args[2]);
        } else if (UploadPlatForm.OSS.name().equals(cloudStoragePlatform)) {
            return fileUtil.ossUpload((MultipartFile) args[0], (String) args[1], (String) args[2]);
        }
        return null;
    }

    enum UploadPlatForm {
        LOCAL, QINIU, OSS
    }
}
