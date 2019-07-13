package cn.tedu.store.mapper;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.vo.OrderVO;

/**
 * 訂單與訂單商品數據持久層
 */
public interface OrderMapper {
	/**
	 * 插入訂單數據
	 * @param order 訂單數據
	 * @return 受影響行數
	 */
	Integer insertOrder(Order order);
	
	/**
	 * 插入訂單商品數據
	 * @param orderItem 訂單商品數據
	 * @return 受影響行數
	 */
	Integer insertOrderItem(OrderItem orderItem);
	
	/**
	 * 根據id查詢訂單詳情
	 * @param id 訂單的id
	 * @return 匹配的訂單詳情,如沒有匹配數據,返回null
	 */
	OrderVO findById(Integer id);
}
