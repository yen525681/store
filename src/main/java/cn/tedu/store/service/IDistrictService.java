package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.District;

/**
 * 省/市/區數據業務層接口
 */
public interface IDistrictService {
	
	/**
	 * 根據父級代號獲取子級的省/市/區的列表
	 * @param parent 父級代號,如果需要獲取省的列表,則父級代號為86
	 * @return 省/市/區的列表
	 */
	List<District> getListByParent(String parent);

	/**
	 * 根據代號獲取省/市/區的詳情
	 * @param code 省/市/區的代號
	 * @return 省/市/區的詳情,如果沒有匹配的數據,則返回null
	 */
	District getByCode(String code);

}
