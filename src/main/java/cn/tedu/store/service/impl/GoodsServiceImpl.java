package cn.tedu.store.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.mapper.GoodsMapper;
import cn.tedu.store.service.IGoodsService;

@Service
public class GoodsServiceImpl implements IGoodsService {
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Override
	public List<Goods> getByCategory(Long categoryId, Integer offset, Integer count) {
		return findByCategory(categoryId, offset, count);
	}
	
	@Override
	public Goods getByid(Long id) {
		return findByid(id);
	}
	
	@Override
	public List<Goods> getByPriority(Integer count) {
		return findByPriority(count);
	}

	/**
	 * 根據商品分類查詢商品列表
	 * @param categoryId 商品分類的id
	 * @param offset 跳過的數量
	 * @param count 獲取數據的最大數量
	 * @return 商品列表
	 */
	private List<Goods> findByCategory(Long categoryId,Integer offset,Integer count){
		return goodsMapper.findByCategory(categoryId, offset, count);
	}
	
	/**
	 * 根據id查詢商品資訊
	 * @param id 商品id
	 * @return 商品資訊,如沒有匹配資訊,返回null
	 */
	private Goods findByid(Long id) {
		return goodsMapper.findByid(id);
	}

	/**
	 * 根據優先級獲取商品數據列表
	 * @param count 獲取商品的數量
	 * @return 優先級最高的商品數據列表
	 */
	private List<Goods> findByPriority(Integer count){
		return goodsMapper.findByPriority(count);
	}

}
