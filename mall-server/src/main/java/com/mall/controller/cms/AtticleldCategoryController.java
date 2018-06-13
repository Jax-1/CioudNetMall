package com.mall.controller.cms;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AtticleldCategory;
import com.mall.message.ProcessResult;
import com.mall.service.cms.AtticleldCategoryService;
import com.mall.util.DateFormatUtil;
import com.mall.util.UUIDUtil;

/**
 * 文章分类管理
 * @author Jang
 *
 */
@Controller
@RequestMapping("/category")
public class AtticleldCategoryController extends AbstractController{
	@Resource
	private AtticleldCategoryService atticleldCategoryService;
	
	/**
	 * 查询所有分类信息
	 * 如传入参数为空，返回全部分类信息
	 * @return
	 */
	@GetMapping("/all")
	@ResponseBody
	public List<AtticleldCategory> queryAllCategory(String pid){
		return atticleldCategoryService.queryAll(pid);
	}
	/**
	 * 添加分类
	 * @param atticleldCategory
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public ProcessResult<AtticleldCategory> addCategory(AtticleldCategory atticleldCategory ){
		atticleldCategory.setId(UUIDUtil.getUUID());
		atticleldCategory.setCreateat(DateFormatUtil.getDate());
		return atticleldCategoryService.insert(atticleldCategory);
	}
	/**
	 * 修改分类
	 * @param atticleldCategory
	 * @return
	 */
	@PostMapping("/update")
	@ResponseBody
	public ProcessResult<AtticleldCategory> updateCategory(AtticleldCategory atticleldCategory ){
		
		return atticleldCategoryService.update(atticleldCategory);
	}
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public ProcessResult<AtticleldCategory> deleteCategory(String id ){
		
		return atticleldCategoryService.delete(id);
	}

}
