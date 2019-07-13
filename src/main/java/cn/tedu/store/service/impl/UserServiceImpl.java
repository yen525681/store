package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.service.exception.UserNotFoundException;

@Service
public class UserServiceImpl implements IUserService{
	@Autowired
	private UserMapper userMapper;

	@Override
	public User reg(User user) throws DuplicateKeyException, InsertException {
		//根據嘗試註冊的用戶名查詢用戶數據
		User data = findByUsername(user.getUsername());
		//根據查詢到的數據是否為null值
		if(data == null) {
			//是:用戶名不存在,允許註冊.補充非用戶提交的數據,處理密碼加密
			//補充數據
			//是否已經刪除:否
			user.setIsDelete(0);
			//四項日誌
			Date now = new Date();
			user.setCreatedUser(user.getUsername());
			user.setCreatedTime(now);
			user.setModifiedUser(user.getUsername());
			user.setModifiedTime(now);
			
			//處理密碼加密
			// 加密-1:獲取隨機的UUID作為鹽值
			String salt = UUID.randomUUID().toString();
			// 加密-2:獲取用戶提交的原始密碼
			String srcPassword = user.getPassword();
			// 加密-3:基於原始密碼和鹽值進行加密，獲取通過MD5加密的密碼
			String md5Password = getMd5Password(srcPassword, salt);
			// 加密-4:將加密後的密碼封裝在user對象中
			user.setPassword(md5Password);
			// 加密-5:將鹽值封裝在user對象中
			user.setSalt(salt);
			//執行註冊
			addnew(user);
			//返回註冊對象
			return user;
		}else {
			//否:用戶名已被占用,拋出DuplicateKeyException異常
			throw new DuplicateKeyException("註冊失敗！嘗試註冊的用戶名(" + user.getUsername() + ")已經被占用!");
		}
	}
	
	@Override
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		//根據參數username查詢用戶數據
		User data = findByUsername(username);
		//判斷用戶數據是否為null
		if(data == null) {
			//是:為null,用戶名不存在,拋出UserNotFoundException
			throw new UserNotFoundException("登錄失敗!嘗試登錄的用戶名不存在!");
		}
		//否:非null,根據用戶名找到數據,取出鹽值
		String salt = data.getSalt();
		//對參數password進行加密
		String md5Password = getMd5Password(password, salt);
		//判斷密碼是否匹配
		if(data.getPassword().equals(md5Password)) {
			//是:匹配,密碼正確,判斷是否被刪除
			if(data.getIsDelete() == 1) {
				//是:已被刪除,拋出UserNotFoundException
				throw new UserNotFoundException("登錄失敗!嘗試登錄的用戶已被刪除!");
			}	
			//否:沒被刪除,登錄成功,將查詢到的用戶數據中的鹽值和密碼改為null
			data.setSalt(null);
			data.setPassword(null);
			//返回用戶數據
			return data;
		}else {
			//否:不匹配,密碼錯誤,拋出PasswordNotMatchException
			throw new PasswordNotMatchException("登錄失敗!密碼錯誤!");
		}
	}
	
	@Override
	public void changePassword(Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, UpdateException {
		//根據uid查詢用戶數據
		User data = findById(uid);
		// 判斷查詢結果是否為null
		if(data == null) {
			//是:拋出異常:UserNotFoundException
			throw new UserNotFoundException("修改密碼失敗!嘗試修改的用戶不存在!");
		}
		//判斷查詢結果中的isDelete是否為1
		if(data.getIsDelete() == 1) {
			//是:拋出異常:UserNotFoundException
			throw new UserNotFoundException("修改密碼失敗!嘗試修改的用戶已被刪除!");
		}
		//取出查詢結果中的鹽值
		String salt = data.getSalt();
		//對參數oldPassword執行MD5加密
		String oldMd5Password = getMd5Password(oldPassword, salt);
		//將加密結果與查詢結果中的password對比是否匹配
		if(data.getPassword().equals(oldMd5Password)) {
			//是:原密碼正確,對參數newPassword執行MD5加密
			String newMd5Password = getMd5Password(newPassword, salt);
			//獲取當前時間
			Date now = new Date();
			//更新密碼
			updatePassword(uid, newMd5Password,data.getUsername(),now);
		}else {
			//否:原密碼錯誤,拋出異常:PasswordNotMatchException
			throw new PasswordNotMatchException("修改密碼失敗!原密碼錯誤!");
		}
	}
	
	@Override
	public void changeAvatar(Integer uid, String avatar) throws UserNotFoundException, UpdateException {
		// 根據用戶id查詢用戶數據
		User data = findById(uid);
		// 判斷數據是否為null
		if(data == null) {
			// 是:拋出:UserNotFoundException
			throw new UserNotFoundException("修改個人頭像失敗!嘗試修改的用戶數據不存在!");
		}
		// 判斷is_delete是否為1
		if(data.getIsDelete() == 1) {
			// 是:拋出:UserNotFoundException
			throw new UserNotFoundException("修改個人頭像失敗!嘗試修改的用戶已被刪除!");
		}
		//執行更新頭像
		String modifiedUser = data.getUsername();
		Date modifiedTime = new Date();
		updateAvatar(uid, avatar, modifiedUser, modifiedTime);
	}
	
	@Override
	public void changeInfo(User user) throws UserNotFoundException, UpdateException {
		// 根據用戶id查詢用戶數據
		User data = findById(user.getId());
		// 判斷數據是否為null
		if(data == null) {
			// 是:拋出:UserNotFoundException
			throw new UserNotFoundException("修改個人資料失敗!嘗試修改的用戶數據不存在!");
		}
		// 判斷is_delete是否為1
		if(data.getIsDelete() == 1) {
			// 是:拋出:UserNotFoundException
			throw new UserNotFoundException("修改個人資料失敗!嘗試修改的用戶已被刪除!");
		}
		// 向參數對像中封裝: modified_user、modified_time
		user.setModifiedUser(data.getUsername());
		user.setModifiedTime(new Date());
		// 執行修改：gender,phone,email,modified_user,modified_time
		updateInfo(user);
	}
	
	@Override
	public User getById(Integer id) {
		User data = findById(id);
		data.setPassword(null);
		data.setSalt(null);
		data.setIsDelete(null);
		return data;
	}
	
	/**
	 * 獲取根據MD5加密後的密碼
	 * @param srcPassword 原密碼
	 * @param salt	鹽值
	 * @return 加密後的密碼
	 */
	private String getMd5Password(String srcPassword,String salt){
		String str = salt + srcPassword + salt;
		for (int i = 0; i < 10; i++) {
			str = DigestUtils.md5DigestAsHex(str.getBytes());
		}
		return str;
	}
	
	/**
	 * 插入用戶數據
	 * @param user 用戶數據
	 * @throws InsertException
	 */
	private void addnew(User user) {
		Integer rows = userMapper.addnew(user);
		if(rows != 1) {
			throw new InsertException("增加用戶數據時出現未知錯誤!");
		}
	}
	
	/**
	 * 更新密碼
	 * @param uid 用戶id
	 * @param password 新密碼
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改時間
	 */
	private void updatePassword(Integer uid,String password,String modifiedUser,Date modifiedTime) {
		Integer rows = userMapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("修改密碼失敗!");
		}
	}
	
	/**
	 * 更新頭像
	 * @param uid 用戶id
	 * @param avatar 新頭像路徑
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改時間
	 */
	private void updateAvatar(Integer uid,String avatar,String modifiedUser,Date modifiedTime) {
		Integer rows = userMapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("修改頭像失敗!");
		}
	}
	
	/**
	 * 更新個人資料
	 * @param user 用戶數據
	 */
	private void updateInfo(User user) {
		Integer rows = userMapper.updateInfo(user);
		if(rows != 1) {
			throw new UpdateException("更新用戶數據出現錯誤!");
		}
	}
	
	/**
	 * 根據用戶名查詢用戶數據
	 * @param username 用戶名
	 * @return 匹配的用戶數據,如果沒有匹配的數據,返回null
	 */
	private User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}
	
	/**
	 * 根據id查詢用戶數據
	 * @param id 用戶id
	 * @return 匹配的用戶數據,如果沒有匹配的數據,返回null
	 */
	private User findById(Integer id) {
		return userMapper.findById(id);
	}


}
