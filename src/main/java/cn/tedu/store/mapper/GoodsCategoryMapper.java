package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.GoodsCategory;

/**
 * 商品分類數據持久層
 */
public interface GoodsCategoryMapper {
	/**
	 * 根據父級id獲取子級商品分類的數據列表
	 * @param parentId 父級商品分類id
	 * @return 子級商品分類的數據列表
	 */
	List<GoodsCategory> findByParent(Long parentId);
}
