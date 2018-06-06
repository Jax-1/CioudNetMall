package com.mall.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mall.entity.cms.AtticleldCategory;

public class MuneUtile {
	/**
	 * 为分类信息创建目录结构
	 * @param list
	 * @param attic
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map mune(List<AtticleldCategory> list,AtticleldCategory attic) {
		if(!Validate.notNull(list)) {
			return null;
		}
		Map map =new HashMap();
		for(AtticleldCategory att:list) {
			
			if(!Validate.notNull(attic)&&!Validate.notNull(att.getParentId())) {
				
				Map childMap =mune(list,att);
				ArrayList arrayList = new ArrayList();
				arrayList.add(att.getId());
				arrayList.add(att.getLadelname());
				map.put(arrayList, childMap);
			}
			if(Validate.notNull(attic)&&Validate.notNull(attic.getId())&&attic.getId().equals(att.getParentId())) {
				Map childMap =mune(list,att);
				ArrayList arrayList = new ArrayList();
				arrayList.add(att.getId());
				arrayList.add(att.getLadelname());
				map.put(arrayList, childMap);
			}
			
		}
		return map;
	}
}
