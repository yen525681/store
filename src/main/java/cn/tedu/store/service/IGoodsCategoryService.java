package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.GoodsCategory;

/**
 * 商品分類數據業務層接口
 */
public interface IGoodsCategoryService {
	/**
	 * 根據父級id獲取子級商品分類的數據列表
	 * @param parentId 父級商品分類id
	 * @return 子級商品分類的數據列表
	 */
	List<GoodsCategory> getByParent(Long parentId);
}
