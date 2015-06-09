package cn.jfinalbbs.utils;

/**
 * Created by liuyang on 15/4/4.
 */
public class Result {

    private String code;
    private String description;
    private Object detail;

    private boolean success;
    private String msg;
    private String file_path;

    public Result() {
    }

    public Result(String code, String description, Object detail) {
        this.code = code;
        this.description = description;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
