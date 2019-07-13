package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.CartNotFoundException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.vo.CartVO;

/**
 * 購物車業務層接口
 */
public interface ICartService {
	/**
	 * 將商品添加到購物車
	 * @param username 當前操作執行人
	 * @param cart  購物車數據
	 * @throws InsertException
	 * @throws UpdateException
	 */
	void addToCart(String username,Cart cart) throws InsertException, UpdateException;
	
	/**
	 * 增加商品的購物車商品的數量
	 * @param id 購物車數據的id
	 * @param uid 當前用戶id
	 * @throws CartNotFoundException
	 * @throws AccessDeniedException
	 * @throws UpdateException
	 */
	void addCount(Integer id, Integer uid)throws CartNotFoundException,AccessDeniedException,UpdateException;
	
	/**
	 * 減少商品的購物車商品的數量
	 * @param id 購物車數據的id
	 * @param uid 當前用戶id
	 * @throws CartNotFoundException
	 * @throws AccessDeniedException
	 * @throws UpdateException
	 */
	void reduceCount(Integer id, Integer uid)throws CartNotFoundException,AccessDeniedException,UpdateException;
	
	/**
	 * 根據用戶id查詢當前用戶的購物車數據列表
	 * @param uid 用戶id
	 * @return 當前用戶的購物車數據列表
	 */
	List<CartVO> getByUid(Integer uid);
	
	/**
	 * 根據若干id查詢匹配的購物車數據的集合
	 * @param ids 若干id
	 * @return 匹配的購物車數據的集合
	 */
	List<CartVO> getByIds(Integer[] ids);
}
