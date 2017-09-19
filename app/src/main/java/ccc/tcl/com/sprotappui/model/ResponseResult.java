package ccc.tcl.com.sprotappui.model;

public class ResponseResult<T> {
    private String status;
    private String msg;
    private T result;

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

    public T getResult() {
        return result;

    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {

            return "ResponseResult:[status=" + status + ", msg=" + msg
                    + ", result:" + "].";
    }

    public boolean isSuccess(){
        return status != null && "ok".equals(status);
    }
}
