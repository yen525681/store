package cn.tedu.store.util;

import java.io.Serializable;

/**
 * 服務器端向客戶端響應結果的類型
 * @param <E> 服務器端向客戶端響應數據的類型
 */
public class ResponseResult<E> implements Serializable{

	private static final long serialVersionUID = -1626793180717240861L;
	
	private Integer state;
	private String message;
	private E data;
	
	
	public ResponseResult() {
		super();
	}
	
	public ResponseResult(Integer state) {
		super();
		setState(state);
	}

	public ResponseResult(Integer state, String message) {
		this(state);
		setMessage(message);
	}
	
	public ResponseResult(Integer state, Exception e) {
		this(state,e.getMessage());
	}

	public ResponseResult(Integer state, E data) {
		this(state);
		setData(data);
	}

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "ResponseResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}

	
}
