package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.exception.DeleteException;
import cn.tedu.store.service.exception.InsertException;

/**
 * 收貨地址業務層接口
 */
public interface IAddressService {
	/**
	 * 創建新收貨地址
	 * @param username 當前值行人
	 * @param address 收貨地址
	 * @return 受影響的行數
	 * @throws InsertException
	 */
	Address create(String username, Address address) throws InsertException;
	
	/**
	 * 設置默認收貨地址
	 * @param uid 收貨地址歸屬的用戶id
	 * @param id 將要設置為默認收貨地址的數據id
	 */
	void setDefault(Integer uid, Integer id);
	
	/**
	 * 獲取當前用戶收貨地址列表
	 * @param uid 用戶id
	 * @return 收貨地址列表
	 */
	List<Address> getListByUid(Integer uid);
	
	/**
	 * 根據id查詢收貨地址數據
	 * @param id 收貨地址id
	 * @return 匹配的收貨地址數據,如果沒有數據,返回null
	 */
	Address getById(Integer id);
	
	/**
	 * 根據id刪除收貨地址
	 * @param uid 收貨地址歸屬的用戶id
	 * @param id 收貨地址數據的id
	 */
	void delete(Integer uid, Integer id) throws DeleteException;
}
