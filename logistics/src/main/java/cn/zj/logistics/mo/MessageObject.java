package cn.zj.logistics.mo;

public class MessageObject {
	private Integer code; //0 失败，1成功
	private String message; //
	
	
	public static MessageObject createMo(Integer code,String message) {
		return new MessageObject(code, message);
	}
	
	public MessageObject(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "MessageObject [code=" + code + ", message=" + message + "]";
	}
	
}
