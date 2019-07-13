package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.GoodsCategory;
import cn.tedu.store.mapper.GoodsCategoryMapper;
import cn.tedu.store.service.IGoodsCategoryService;

@Service
public class GoodsCategoryServiceImpl implements IGoodsCategoryService{
	@Autowired
	private GoodsCategoryMapper goodsCategoryMapper;
	
	@Override
	public List<GoodsCategory> getByParent(Long parentId) {
		return findByParent(parentId);
	}
	
	/**
	 * 根據父級id獲取子級商品分類的數據列表
	 * @param parentId 父級商品分類id
	 * @return 子級商品分類的數據列表
	 */
	private List<GoodsCategory> findByParent(Long parentId){
		return goodsCategoryMapper.findByParent(parentId);
	}
}
