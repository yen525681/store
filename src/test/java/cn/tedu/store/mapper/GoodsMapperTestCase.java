package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.entity.GoodsCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsMapperTestCase {
	@Autowired
	private GoodsMapper mapper;
	
	@Test
	public void findByCategory() {
		Long categoryId = 163L;
		Integer offset = 2;
		Integer count = 10;
		List<Goods> list = mapper.findByCategory(categoryId, offset, count);
		System.err.println("BEGIN");
		for (Goods data : list) {
			System.err.println(data);
		}
		System.err.println("END");
	}
	
	@Test
	public void findByPriority() {
		Integer count = 5;
		List<Goods> list = mapper.findByPriority(count);
		System.err.println("BEGIN");
		for (Goods data : list) {
			System.err.println(data);
		}
		System.err.println("END");
	}
	
	@Test
	public void findByid() {
		Long id = 100000401L;
		Goods goods = mapper.findByid(id);
		System.err.println(goods);
	}
}
