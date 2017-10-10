package ccc.tcl.com.sprotappui.model;

public class ResponseResult<T> {
    private String status;//成功与否状态标志
    private String msg;//
    private T result;
    private String type;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getResult() {
        return result;

    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {

        return "ResponseResult:[status=" + status + ", msg=" + msg
                + (type == null ? "" : ", type=" + type)
                + ", result:" + "].";
    }

    public boolean isSuccess() {
        return status != null && "ok".equals(status);
    }
}
