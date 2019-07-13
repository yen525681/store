package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.AddressNotFoundException;
import cn.tedu.store.service.exception.DeleteException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.UpdateException;

@Service
public class AddressServiceImpl implements IAddressService{
	@Autowired 
	private AddressMapper addressMapper;
	@Autowired
	private IDistrictService districtService;

	@Override
	public Address create(String username, Address address) throws InsertException {
		//通過address.getUid()得到用戶id,並以此查詢該用戶的收貨地址數量
		Integer count = getCountByUid(address.getUid());
		//判斷數量是否為0
//		if(count == 0) {
//			//是:當前用戶首次創建地址,則設置該地址為默認
//			address.setIsDefault(1);
//		}else {
//			//否:當前用戶非首次創建地址,則設置該地址非默認
//			address.setIsDefault(0);
//		}
		//以上判斷可以三目表達替換
		address.setIsDefault(count == 0 ? 1 : 0);
		//處理district:根據省/市/區的代號獲取District的值
		String district = getDistrict(address.getProvince(),address.getCity(),address.getArea());
		address.setDistrict(district);
		//封裝日誌
		Date now = new Date();
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setCreatedTime(now);
		//執行創建新地址
		addnew(address);
		return address;
	}
	
	@Override
	@Transactional
	public void setDefault(Integer uid, Integer id) {
		//根據id查詢收貨地址數據
		Address data = findById(id);
		//判斷數據是否為null
		if(data == null) {
			throw new AddressNotFoundException("設置默認地址失敗!嘗試設置的地址不存在!");
		}
		//判斷查詢到的數據中的uid與參數uid是否一致
		if(data.getUid() != uid) {
			throw new AccessDeniedException("設置默認地址失敗!訪問數據權限驗證不通過!");
		}	
		//將當前用戶的所有收貨地址設置為非默認
		updateNonDefault(uid);
		//將指定id的收貨地址設置為默認
		updateDefault(id);
	}
	
	@Override
	public List<Address> getListByUid(Integer uid) {
		return findByUid(uid);
	}
	
	@Override
	public Address getById(Integer id) {
		return findById(id);
	}
	
	@Override
	@Transactional
	public void delete(Integer uid, Integer id) throws DeleteException {
		//根據id查詢收貨地址數據:findById(id)
		Address data = findById(id);
		//檢查數據是否為null
		if(data == null) {
			//是:拋出AddressNotFoundException
			throw new AddressNotFoundException("刪除收貨地址失敗!嘗試刪除的數據不存在!");
		}
		//檢查數據歸屬是否有誤
		if(data.getUid() != uid) {
			//是:拋出AccessDeniedException
			throw new AccessDeniedException("刪除收貨地址失敗!訪問驗證權限不通過!");
		}
		//執行刪除
		deleteById(id);
		//檢查還有沒有收貨地址數據:getCountByUid(uid)
		if(getCountByUid(uid) > 0) {
			//是:判斷剛才判斷的是否是默認收貨地址
			if(data.getIsDefault() == 1) {
				//是:獲取最後修改的收貨地址:findLastModified(uid)
				Integer lastModifiedId = findLastModified(uid).getId();
				//將最後修改的收貨地址設置為默認收貨地址
				setDefault(uid, lastModifiedId);
			}	
		}	
	}
	
	/**
	 * 插入新的用戶地址數據
	 * @param address 用戶地址數據
	 */
	private void addnew(Address address) {
		Integer rows = addressMapper.addnew(address);
		if (rows != 1) {
			throw new InsertException("增加用戶地址時出現未知錯誤!請稍後重試!");
		}
	}
	
	/**
	 * 將當前用戶的收貨地址全部設置為非默認
	 * @param uid 用戶id
	 * @return 受影響的行數
	 */
	private void updateNonDefault(Integer uid) {
		Integer rows = addressMapper.updateNonDefault(uid);
		if (rows < 1) {
			throw new UpdateException("修改默認收貨地址時出現未知錯誤!請稍後重試!");
		}
	}

	/**
	 * 將指定id的收貨地址設置為默認
	 * @param id 數據id
	 * @return 受影響的行數
	 */
	private void updateDefault(Integer id) {
		Integer rows = addressMapper.updateDefault(id);
		if (rows != 1) {
			throw new UpdateException("修改默認收貨地址時出現未知錯誤!請稍後重試!!");
		}
	}
	
	/**
	 * 根據用戶id獲取用戶地址的數量
	 * @param uid 用戶id
	 * @return 用戶的地址數量,如沒有數據,返回0
	 */
	private Integer getCountByUid(Integer uid) {
		return addressMapper.getCountByUid(uid);
	}
	
	/**
	 * 獲取當前用戶的收貨地址列表
	 * @param uid 用戶id
	 * @return 收貨地址
	 */
	private List<Address> findByUid(Integer uid) {
		return addressMapper.findByUid(uid);
	}


	/**
	 * 根據id查詢收貨地址數據
	 * @param id 收貨地址id
	 * @return 匹配的收貨地址數據,如果沒有數據,返回null
	 */
	private Address findById(Integer id) {
		return addressMapper.findById(id);
	}
	
	/**
	 * 查找當前用戶最後修改的收貨地址信息
	 * @param uid 用戶的id
	 * @return 匹配的數據,如果沒有數據,返回null
	 */
	private Address findLastModified(Integer uid) {
		return addressMapper.findLastModified(uid);
	}
	
	/**
	 * 根據id刪除收貨地址數據
	 * @param id 收貨地址數據的id
	 */
	private void deleteById(Integer id) {
		Integer rows = addressMapper.deleteById(id);
		if(rows != 1) {
			throw new DeleteException("刪除收貨地址時出現未知錯誤!");
		}
	}
	
	/**
	 * 根據省/市/區的代號獲取名稱
	 * @param province 省的代號
	 * @param city 市的代號
	 * @param area 區的代號
	 * @return 省市區的名稱
	 */
	private String getDistrict(String province,String city,String area) {
		String provinceName = null;
		String cityName = null;
		String areaName = null;
		
		District p = districtService.getByCode(province);
		District c = districtService.getByCode(city);
		District a = districtService.getByCode(area);
		
		
		if(p != null) {
			provinceName = p.getName();
		}
		
		if(c != null) {
			cityName = c.getName();
		}
		
		if(a != null) {
			areaName = a.getName();
		}
		
		return provinceName + cityName + areaName;
	}

	
}
