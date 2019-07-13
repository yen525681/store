package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.controller.exception.FileEmptyException;
import cn.tedu.store.controller.exception.FileSizeOutOfLimitException;
import cn.tedu.store.controller.exception.FileTypeNotSupportException;
import cn.tedu.store.controller.exception.FileUploadException;
import cn.tedu.store.controller.exception.RequestException;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.AddressNotFoundException;
import cn.tedu.store.service.exception.DeleteException;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.ServiceException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.service.exception.UserNotFoundException;
import cn.tedu.store.util.ResponseResult;

/**
 * 所有控制器的基類
 */
public abstract class BaseController {
	
	public static final Integer SUCCESS = 200;
	
	
	@ExceptionHandler({ServiceException.class,RequestException.class})
	@ResponseBody
	public ResponseResult<Void> handleException(Exception e){
		Integer state = null;
		
		if (e instanceof DuplicateKeyException) {
			//400-違反了Unique約束異常
			state = 400;
		} else if (e instanceof UserNotFoundException) {
			//401-用戶數據不存在
			state = 401;
		} else if (e instanceof PasswordNotMatchException) {
			//402-密碼錯誤
			state = 402;
		} else if (e instanceof AddressNotFoundException) {
			//403-收貨地址數據不存在
			state = 403;
		} else if (e instanceof AccessDeniedException) {
			//404-訪問被拒絕的異常
			state = 404;
		} else if (e instanceof InsertException) {
			//500-插入數據異常
			state = 500;
		} else if (e instanceof UpdateException) {
			//501-更新數據異常
			state = 501;
		} else if (e instanceof DeleteException) {
			//502-更新數據異常
			state = 502;
		} else if(e instanceof FileEmptyException) {
			//600-上傳文件為空異常
			state = 600;
		} else if(e instanceof FileSizeOutOfLimitException) {
			//601-上傳文件大小超出限制異常
			state = 601;
		}else if(e instanceof FileTypeNotSupportException) {
			//602-上傳不支持文件類型異常
			state = 602;
		}else if(e instanceof FileUploadException) {
			//610-文件上傳異常
			state = 610;
		}
		return new ResponseResult<>(state, e);
	}
	
	/**
	 * 從session中獲取uid
	 * @param session HttpSession對象
	 * @return 當前登錄用戶的id
	 */
	protected Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
	}
}
