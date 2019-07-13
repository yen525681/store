package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Address;

/**
 * 處理用戶地址持久層
 */
public interface AddressMapper {
	/**
	 * 插入新的用戶地址數據
	 * @param address 用戶地址數據
	 * @return 受影響的行數
	 */
	Integer addnew(Address address);
	
	/**
	 * 將當前用戶的收貨地址全部設置為非默認
	 * @param uid 用戶id
	 * @return 受影響的行數
	 */
	Integer updateNonDefault(Integer uid);

	/**
	 * 將指定id的收貨地址設置為默認
	 * @param id 數據id
	 * @return 受影響的行數
	 */
	Integer updateDefault(Integer id);
	
	/**
	 * 根據用戶id獲取用戶地址的數量
	 * @param uid 用戶id
	 * @return 用戶的地址數量,如沒有數據,返回0
	 */
	Integer getCountByUid(Integer uid);

	/**
	 * 獲取當前用戶的收貨地址列表
	 * @param uid 用戶id
	 * @return 收貨地址
	 */
	List<Address> findByUid(Integer uid);
	
	/**
	 * 根據id查詢收貨地址數據
	 * @param id 收貨地址id
	 * @return 匹配的收貨地址數據,如果沒有數據,返回null
	 */
	Address findById(Integer id);
	
	/**
	 * 查找當前用戶最後修改的收貨地址信息
	 * @param uid 用戶的id
	 * @return 匹配的數據,如果沒有數據,返回null
	 */
	Address findLastModified(Integer uid);
	
	/**
	 * 根據id刪除收貨地址數據
	 * @param id 收貨地址數據的id
	 * @return 受影響的行數
	 */
	Integer deleteById(Integer id);

}
