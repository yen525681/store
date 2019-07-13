package cn.tedu.store.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.exception.AddressNotFoundException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.vo.CartVO;
import cn.tedu.store.vo.OrderVO;

@Service
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private ICartService cartService;
	
	@Override
	@Transactional
	public Order createOrder(Integer uid, String username, Integer addressId, Integer[] cartIds)
			throws InsertException {
		//創建Date對象
		Date now = new Date();
		//聲明pay變量
		Long pay = 0L;
		//獲取購物車數據信息
		List<CartVO> carts = cartService.getByIds(cartIds);
		//創建List<OrderItem> orderItems
		List<OrderItem> orderItems = new ArrayList<>();
		//遍歷集合
		for (CartVO cartVO : carts) {
			//計算總價pay
			pay += cartVO.getNewPrice()*cartVO.getCount();
			//創建OrderItem
			OrderItem item = new OrderItem();
			//orderItem插入數據
			item.setGoodsId(cartVO.getGid());
			item.setGoodsTitle(cartVO.getTitle());
			item.setGoodsImage(cartVO.getImage());
			item.setGoodsPrice(cartVO.getNewPrice());
			item.setGoodsCount(cartVO.getCount());
			//orderItem插入4個日誌
			item.setCreatedUser(username);
			item.setCreatedTime(now);
			item.setModifiedUser(username);
			item.setModifiedTime(now);
			//將item放入集合
			orderItems.add(item);
		}
		//創建Order對象
		Order order = new Order();
		//order插入數據
		order.setUid(uid);
		order.setPay(pay);
		order.setStatus(0);
		order.setOrderTime(now);
		//通過addressService.getById()得到收貨地址數據
		Address address = addressService.getById(addressId);
		//判斷是否查詢到address數據
		if(address == null) {
			throw new AddressNotFoundException("創建訂單失敗!收貨地址有誤,請刷新後再次嘗試!");
		}
		//order插入地址數據
		order.setRecvName(address.getName());
		order.setRecvPhone(address.getPhone());
		order.setRecvDistrict(address.getDistrict());
		order.setRecvAddress(address.getAddress());
		//order插入4個日誌
		order.setCreatedUser(username);
		order.setCreatedTime(now);
		order.setModifiedUser(username);
		order.setModifiedTime(now);
		//插入訂單數據並獲取oid
		insertOrder(order);
		//遍歷orderItems
		for (OrderItem orderItem : orderItems) {
			///orderItem插入oid
			orderItem.setOid(order.getId());
			//插入訂單商品數據
			insertOrderItem(orderItem);
		}
		//返回
		return order;
	}
	
	@Override
	public OrderVO getById(Integer id) {
		return findById(id);
	}

	/**
	 * 插入訂單數據
	 * @param order 訂單數據
	 */
	private void insertOrder(Order order) {
		Integer rows = orderMapper.insertOrder(order);
		if(rows != 1) {
			throw new InsertException("插入訂單數據時發生錯誤!");
		}
	}
	
	/**
	 * 插入訂單商品數據
	 * @param orderItem 訂單商品數據
	 */
	private void insertOrderItem(OrderItem orderItem) {
		Integer rows = orderMapper.insertOrderItem(orderItem);
		if(rows != 1) {
			throw new InsertException("插入訂單商品數據時發生錯誤!");
		}
	}
	
	/**
	 * 根據id查詢訂單詳情
	 * @param id 訂單的id
	 * @return 匹配的訂單詳情,如沒有匹配數據,返回null
	 */
	private OrderVO findById(Integer id) {
		return orderMapper.findById(id);
	}

}
