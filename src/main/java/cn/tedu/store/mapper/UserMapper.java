package cn.tedu.store.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

/**
 * 處理用戶數據持久層
 */
public interface UserMapper {
	
	/**
	 * 插入用戶數據
	 * @param user 用戶數據
	 * @return 受影響的行數
	 */
	Integer addnew(User user);
	
	/**
	 * 更新密碼
	 * @param uid 用戶id
	 * @param password 新密碼
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改時間
	 * @return 受影響行數
	 */
	Integer updatePassword(@Param("uid") Integer uid,@Param("password") String password,
	@Param("modifiedUser") String modifiedUser,@Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 更新用戶頭像
	 * @param uid 用戶id
	 * @param avatar 頭像路徑
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改時間
	 * @return 受影響行數
	 */
	Integer updateAvatar(@Param("uid") Integer uid,@Param("avatar") String avatar, 
	@Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 修改用戶資料(不含用戶名、密碼、頭像)
	 * @param user 用戶資料
	 * @return 受影響行數
	 */
	Integer updateInfo(User user);
	
	/**
	 * 根據用戶名查詢用戶數據
	 * @param username 用戶名
	 * @return 匹配的用戶數據,如果沒有匹配的數據,返回null
	 */
	User findByUsername(String username);
	
	/**
	 * 根據id查詢用戶數據
	 * @param id 用戶id
	 * @return 匹配的用戶數據,如果沒有匹配的數據,返回null
	 */
	User findById(Integer id);
	
}
