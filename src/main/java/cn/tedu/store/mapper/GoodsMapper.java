package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Goods;

/**
 * 商品數據持久層
 */
public interface GoodsMapper {
	/**
	 * 根據商品分類查詢商品列表
	 * @param categoryId 商品分類的id
	 * @param offset 跳過的數量
	 * @param count 獲取數據的最大數量
	 * @return 商品列表
	 */
	List<Goods> findByCategory(@Param("categoryId") Long categoryId,
					@Param("offset") Integer offset,@Param("count") Integer count);
	
	/**
	 * 根據id查詢商品資訊
	 * @param id 商品id
	 * @return 商品資訊,如沒有匹配資訊,返回null
	 */
	Goods findByid(Long id);
	
	/**
	 * 根據優先級獲取商品數據列表
	 * @param count 獲取商品的數量
	 * @return 優先級最高的商品數據列表
	 */
	List<Goods> findByPriority(Integer count);
}
