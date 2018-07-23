package co.yiiu.module.log.pojo;

public class LogWithBLOBs extends Log {
    private String afterContent;

    private String beforeContent;

    public String getAfterContent() {
        return afterContent;
    }

    public void setAfterContent(String afterContent) {
        this.afterContent = afterContent == null ? null : afterContent.trim();
    }

    public String getBeforeContent() {
        return beforeContent;
    }

    public void setBeforeContent(String beforeContent) {
        this.beforeContent = beforeContent == null ? null : beforeContent.trim();
    }
}