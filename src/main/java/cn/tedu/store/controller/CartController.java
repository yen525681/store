package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.util.ResponseResult;
import cn.tedu.store.vo.CartVO;

@RestController
@RequestMapping("/cart")
public class CartController extends BaseController {
	@Autowired
	private ICartService cartService;
	
	@PostMapping("/add_to_cart")
	public ResponseResult<Void> addToCart(HttpSession session, Cart cart) {
		//從session中獲取username
		String username = session.getAttribute("username").toString();
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//將uid封裝到cart中
		cart.setUid(uid);
		//執行存入購物車
		cartService.addToCart(username, cart);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@RequestMapping("/list")
	public ResponseResult<List<CartVO>> getByUid(HttpSession session) {
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//查詢
		List<CartVO> list = cartService.getByUid(uid);
		//返回
		return new ResponseResult<List<CartVO>>(SUCCESS,list);
	}
	
	@RequestMapping("/add_count")
	public ResponseResult<Void> addCount(Integer id,HttpSession session) {
		//從session中獲取uid獲取uid
		Integer uid = getUidFromSession(session);
		//執行增加數量
		cartService.addCount(id, uid);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@RequestMapping("/reduce_count")
	public ResponseResult<Void> reduceCount(Integer id,HttpSession session) {
		//從session中獲取uid獲取uid
		Integer uid = getUidFromSession(session);
		//執行增加數量
		cartService.reduceCount(id, uid);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@RequestMapping("/get_by_ids")
	public ResponseResult<List<CartVO>> getByIds(@RequestParam("cart_id") Integer[] ids) {
		//查詢
		List<CartVO> list = cartService.getByIds(ids);
		//返回
		return new ResponseResult<List<CartVO>>(SUCCESS,list);
	}

}
