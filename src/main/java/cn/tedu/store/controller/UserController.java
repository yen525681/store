package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.exception.FileEmptyException;
import cn.tedu.store.controller.exception.FileSizeOutOfLimitException;
import cn.tedu.store.controller.exception.FileTypeNotSupportException;
import cn.tedu.store.controller.exception.FileUploadException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.ResponseResult;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
	/**
	 * 上傳文件夾名稱
	 */
	private static final String UPLOAD_DIR_NAME = "upload";
	/**
	 * 上傳文件最大值
	 */
	private static final long FILE_MAX_SIZE = 5*1024*1024;
	/**
	 * 允許上傳的文件類型
	 */
	private static final List<String> FILE_CONTENT_TYPE = new ArrayList<>();
	
	/**
	 * 初始化允許上傳的文件類型集合
	 */
	static {
		FILE_CONTENT_TYPE.add("img/jpeg");
		FILE_CONTENT_TYPE.add("img/png");
	}
	
	@Autowired
	private IUserService userService;
	
	
	
	@PostMapping("/reg.do")
	public ResponseResult<Void> handleReg(User user) {
		userService.reg(user);
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@PostMapping("/login.do")
	public ResponseResult<User> handleLogin(@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session) {
		User user = userService.login(username, password);
		//將用戶相關信息放入session
		session.setAttribute("uid", user.getId());
		session.setAttribute("username", user.getUsername());
		return new ResponseResult<>(SUCCESS,user);
	}
	
	@PostMapping("/password.do")
	public ResponseResult<Void> changePassword(@RequestParam("old_password") String oldPassword,@RequestParam("new_password") String newPassword,HttpSession session) {
		//獲取登錄的用戶id
		Integer uid = getUidFromSession(session);
		userService.changePassword(uid, oldPassword, newPassword);
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@RequestMapping("/info.do")
	public ResponseResult<User> getInfo(HttpSession session) {
		Integer id = getUidFromSession(session);
		User user = userService.getById(id);
		return new ResponseResult<User>(SUCCESS, user);
	}
	
	@PostMapping("/change_info.do")
	public ResponseResult<Void> changeInfo(User user, HttpSession session) {
		Integer id = getUidFromSession(session);
		//將id封裝到參數user中,因此參數user為用戶提交,其中不包含id
		user.setId(id);
		userService.changeInfo(user);
		return new ResponseResult<>(SUCCESS);
	}
	
	@PostMapping("/upload.do")
	public ResponseResult<String> handleUpload(HttpSession session,@RequestParam("file") MultipartFile file) {
		//檢查是否存在上傳文件 
		if(file.isEmpty()) {
			//拋出異常:文件不允許為空
			throw new FileEmptyException("上傳失敗!請選擇上傳圖片!");
		}
		//檢查文件大小 
		if(file.getSize() > FILE_MAX_SIZE) {
			//拋出異常:文件大小超出限制
			throw new FileSizeOutOfLimitException("上傳失敗!請確認圖片大小!");
		}
		//檢查文件類型 
		if(!FILE_CONTENT_TYPE.contains(file.getContentType())) {
			//拋出異常:文件類型限制
			throw new FileTypeNotSupportException("上傳失敗!請選擇正確圖片格式!");
		}
		//確定上傳文件夾的路徑 
		String parentPath = session.getServletContext().getRealPath(UPLOAD_DIR_NAME);
		File parent = new File(parentPath);
		if(!parent.exists()) {
			parent.mkdirs();
		}
		//確定文件名
		String originalFileName = file.getOriginalFilename();
		int beginIndex = originalFileName.lastIndexOf(".");
		String suffix = originalFileName.substring(beginIndex);
		String fileName = System.currentTimeMillis() + "" +new Random().nextInt(50000) + suffix;
		//確定文件
		File dest = new File(parent,fileName);
		//執行保存文件
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			//拋出異常:上傳失敗
			throw new FileUploadException("上傳失敗!請稍後再試!");
		} catch (IOException e) {
			//拋出異常:上傳失敗
			throw new FileUploadException("上傳失敗!請稍後再試!");
		}
		//獲取當前用戶的id
		Integer uid = getUidFromSession(session);
		//更新頭像數據
		String avatar = "/" + UPLOAD_DIR_NAME + "/" + fileName;
		userService.changeAvatar(uid, avatar);
		//返回
		ResponseResult<String> rr = new ResponseResult<>();
		rr.setState(SUCCESS);
		rr.setData(avatar);
		return rr;
	}
}
