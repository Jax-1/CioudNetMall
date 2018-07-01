package com.mall.controller.cms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.Atticleld;
import com.mall.entity.cms.AtticleldCategory;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.cms.FilePath;
import com.mall.message.SystemCode;
import com.mall.service.cms.AtticleldCategoryService;
import com.mall.service.cms.AtticleldService;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.cms.FilePathService;
import com.mall.service.sys.CacheService;
import com.mall.util.PageResult;
import com.mall.util.Validate;


/**
 * CMS文章管理
 * @author Jang
 *
 */
@Controller  
@RequestMapping("/admin/cms")
public class AdminCMSController extends AbstractController{
	@Resource
	private AtticleldCategoryService atticleldCategoryService;
	@Resource
	private AtticleldService atticleldService;
	@Resource
	private CacheService cacheService;
	@Resource
	private FilePathService filePathService;
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	/**
	 * 添加文章
	 * @return
	 */
	@RequestMapping("/add.do")
	public String toIndex(String Pid ,Model model) {
		List<AtticleldCategory> list = atticleldCategoryService.queryAll(Pid);
		model.addAttribute("Category", list);
		/**
		 * CMS列展开
		 */
		Map<String,String > map =new HashMap<String,String>();
		String mainClass = null;
		map.put("ZPJJ", "");
		map.put("ZXZX", "");
		map.put("CTWH", "");
		//01:作品集锦，02：资讯中心，03：传统文化
		switch (Pid) {
		case "ZPJJ":
			map.put("ZPJJ", "start active open");
			mainClass="作品集锦";
			break;
		case "ZXZX":
			map.put("ZXZX", "start active open");
			mainClass="资讯中心";
			break;
		case "CTWH":
			map.put("CTWH", "start active open");
			mainClass="传统文化";
			break;

		default:
			break;
		}
		model.addAttribute("lineopen", map);
		//主分类默认选中,及显示
		model.addAttribute("main", mainClass);
		model.addAttribute("Pid", Pid);
		model.addAttribute("page", "admin/cms/add_cms");
		model.addAttribute("content", "nav-item start active open");
		return "admin/index";
	}
	/**
	 * 保存文章
	 * @param id
	 * @param att
	 * @param editorValue 编辑器值
	 * @param model
	 * @return
	 */
	@PostMapping("/save")
	public String toSave(String Pid ,Atticleld att,String editorValue,String type,Model model,HttpServletRequest request) {
		if(Validate.notNull(att)) {
			if(SystemCode.TYPE_SAVE.equals(type)) {
				//保存操作
				Atticleld initAtt = Atticleld.initAtt(att, request, editorValue);
				logger.info("getRecommended:"+att.getRecommended());
				int insert = 0;
				try {
					insert = atticleldService.insert(initAtt);
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
				List<FilePath> fileList=new ArrayList<FilePath>();
				if(insert>0) {
					FilePath file=new FilePath();
					file.setBelongid(initAtt.getId());
					file.setFileid(initAtt.getViewImg());
					fileList.add(file);
					filePathService.update(fileList);
				}
			}else if(SystemCode.TYPE_UPDATE.equals(type)) {
				//更新操作
				logger.info("更新文章信息：文章名："+att.getTitle()+", ID="+att.getId());
				try {
					atticleldService.updateByPrimaryKeySelective(att);
					List<FilePath> fileList=new ArrayList<FilePath>();
					FilePath file=new FilePath();
					file.setBelongid(att.getId());
					file.setFileid(att.getViewImg());
					logger.info("info:"+att.getId()+","+att.getViewImg());
					fileList.add(file);
					filePathService.update(fileList);
				} catch (Exception e) {
					logger.error("更新文章信息失败：文章名："+att.getTitle()+", ID="+att.getId());
					logger.error(e);
					e.printStackTrace();
				}
				
			}
			
			
		}
		logger.info("PID="+Pid);
		//保存文章，跳转到列表界面
		return "redirect:/admin/cms/list.do?Pid="+Pid;
	}
	/**
	 * 保存作家信息
	 * @param id
	 * @param auth
	 * @param editorValue
	 * @param type
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/saveAuth")
	public String toSaveAuth(String Pid ,AuthorWithBLOBs auth,String editorValue,String type,Model model,HttpServletRequest request) {
		if(Validate.notNull(auth)) {
			List<FilePath> fileList=new ArrayList<FilePath>();
			if(SystemCode.TYPE_SAVE.equals(type)) {
				//保存操作
					//初始化
				AuthorWithBLOBs initAuth = AuthorWithBLOBs.init(auth, request, editorValue);
				
				System.out.println(initAuth.getId());
				
				try {
					authorWithBLOBsService.insert(initAuth);
					FilePath file=new FilePath();
					file.setBelongid(initAuth.getId());
					file.setFileid(initAuth.getViewimg());
					fileList.add(file);
					filePathService.update(fileList);
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
			}else if(SystemCode.TYPE_UPDATE.equals(type)) {
				//更新操作
				logger.info("更新作家信息：姓名："+auth.getAuthorname()+", ID="+auth.getId());
				try {
					authorWithBLOBsService.updateByPrimaryKeySelective(auth);
					FilePath file=new FilePath();
					file.setBelongid(auth.getId());
					file.setFileid(auth.getViewimg());
					fileList.add(file);
					filePathService.update(fileList);
				} catch (Exception e) {
					logger.info("更新作家信息结果：姓名："+auth.getAuthorname()+", ID="+auth.getId()+".失败！");
					logger.error(e);
					e.printStackTrace();
				}
				
			}
			
			
		}
		//保存文章，跳转到列表界面
		return "redirect:/admin/cms/writerlist.do?Pid="+Pid;
	}
	/**
	 * 添加名家
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/addwriter.do")
	public String toAddWriter(String Pid ,Model model) {
		List<AtticleldCategory> list = atticleldCategoryService.queryAll(Pid);
		model.addAttribute("Category", list);
		model.addAttribute("Pid", Pid);
		model.addAttribute("page", "admin/cms/add_mingjia");
		model.addAttribute("content", "nav-item start active open");
		return "admin/index";
	}
	/**
	 * 名家荟萃列表
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/writerlist.do")
	public String toWriterList(String Pid ,AuthorWithBLOBs aut,PageResult<AuthorWithBLOBs> list,Model model) {
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.AUT_PAGE));
		list.setPageSize(pageSize);
		list = authorWithBLOBsService.queryByPageFront(list, aut);
		//作家列表
		model.addAttribute("list", list);
		model.addAttribute("Pid", Pid);
		model.addAttribute("page", "admin/cms/list_mingjia");
		model.addAttribute("content", "nav-item start active open");
		return "admin/index";
	}
	/**
	 * 文章列表
	 * @param str
	 * @return
	 */
	@GetMapping("/list.do")
	public String toCMSList(String Pid ,Model model,PageResult<Atticleld> list,Atticleld att) {
		
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.ATT_PAGE));
		list.setPageSize(pageSize);
		att.setColumns(Pid);
		list = atticleldService.queryByPageFront(list, att);
		model.addAttribute("list", list);
//		Page<Atticleld> page = atticleldService.queryList( Pid,pageNow,pageSize);
//		
//		
//		model.addAttribute("list", page.getResult());
//		//总页数
//		model.addAttribute("pageCount", page.getPages());
//		model.addAttribute("pageNow", pageNow);
		
		
		model.addAttribute("Pid", Pid);
		model.addAttribute("page", "admin/cms/list_cms");
		model.addAttribute("content", "nav-item start active open");
		return "admin/index";
	}
	/**
	 * 分类
	 * @return
	 */
	@RequestMapping("/classify.do")
	public String toClassify(Model model) {
		List<AtticleldCategory> list = atticleldCategoryService.queryAll(null);
		List<AtticleldCategory> mjhc=new ArrayList<AtticleldCategory>();
		List<AtticleldCategory> zpjj=new ArrayList<AtticleldCategory>();
		List<AtticleldCategory> zxzx=new ArrayList<AtticleldCategory>();
		List<AtticleldCategory> ctwh=new ArrayList<AtticleldCategory>();
		for(AtticleldCategory att:list) {
			if(Validate.notNull(att)&&"ZPJJ".equals(att.getParentId())) {
				zpjj.add(att);
			}else if(Validate.notNull(att)&&"ZXZX".equals(att.getParentId())) {
				zxzx.add(att);
			}else if(Validate.notNull(att)&&"CTWH".equals(att.getParentId())) {
				ctwh.add(att);
			}else if(Validate.notNull(att)&&"MJHC".equals(att.getParentId())) {
				mjhc.add(att);
			}
		}
		model.addAttribute("mjhc", mjhc);
		model.addAttribute("zpjj", zpjj);
		model.addAttribute("zxzx", zxzx);
		model.addAttribute("ctwh", ctwh);
		model.addAttribute("page", "admin/cms/classify_cms");
		model.addAttribute("content", "nav-item start active open");
		return "admin/index";
	}
	
	@RequestMapping("/editor.do")
	public String toEditor(Atticleld att ,Model model,String Pid) {
		List<AtticleldCategory> list = atticleldCategoryService.queryAll(Pid);
		model.addAttribute("Category", list);
		/**
		 * CMS列展开
		 */
		Map<String,String > map =new HashMap<String,String>();
		String mainClass = null;
		map.put("ZPJJ", "");
		map.put("ZXZX", "");
		map.put("CTWH", "");
		//01:作品集锦，02：资讯中心，03：传统文化
		switch (Pid) {
		case "ZPJJ":
			map.put("ZPJJ", "start active open");
			mainClass="作品集锦";
			break;
		case "ZXZX":
			map.put("ZXZX", "start active open");
			mainClass="资讯中心";
			break;
		case "CTWH":
			map.put("CTWH", "start active open");
			mainClass="传统文化";
			break;

		default:
			break;
		}
		/**
		 * 初始化文章信息
		 */
		 att = atticleldService.selectInfo(att);
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		//文件服务器路径
		model.addAttribute("fileServicePath", fileUrlPrefix);
		//文章详细信息
		model.addAttribute("att", att);
		model.addAttribute("lineopen", map);
		//主分类默认选中,及显示
		model.addAttribute("main", mainClass);
		model.addAttribute("Pid", Pid);
		model.addAttribute("page", "admin/cms/add_cms");
		model.addAttribute("content", "nav-item start active open");
		return "admin/index";
	}
	/**
	 * 编辑作家信息
	 * @param Pid
	 * @param model
	 * @param auth
	 * @return
	 */
	@RequestMapping("/editorWriter.do")
	public String toEditorWriter(String Pid ,AuthorWithBLOBs auth,Model model) {
		List<AtticleldCategory> list = atticleldCategoryService.queryAll(Pid);
		logger.info("编辑作家信息：id="+auth.getId());
		AuthorWithBLOBs authInfo = authorWithBLOBsService.selectInfo(auth);
		
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		//文件服务器路径
		model.addAttribute("fileServicePath", fileUrlPrefix);
		//作家信息
		model.addAttribute("auth", authInfo);
		model.addAttribute("Category", list);
		model.addAttribute("Pid", Pid);
		model.addAttribute("page", "admin/cms/add_mingjia");
		model.addAttribute("content", "nav-item start active open");
		return "admin/index";
	}
}
