package cn.tedu.store.service;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.vo.OrderVO;

/**
 * 訂單與訂單商品業務層接口
 */
public interface IOrderService {
	/**
	 * 創建訂單
	 * @param uid 當前用戶id
	 * @param username 當前用戶的用戶名
	 * @param addressId 選擇的收貨地址id
	 * @param cartIds 訂單中商品在購物車中的數據id
	 * @return 創建成功的訂單
	 */
	Order createOrder(Integer uid, String username,Integer addressId,Integer[] cartIds) throws InsertException;
	
	/**
	 * 根據id查詢訂單詳情
	 * @param id 訂單的id
	 * @return 匹配的訂單詳情,如沒有匹配數據,返回null
	 */
	OrderVO getById(Integer id);
}
