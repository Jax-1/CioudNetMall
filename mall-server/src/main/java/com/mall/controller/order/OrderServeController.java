package com.mall.controller.order;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mall.controller.AbstractController;
import com.mall.dao.sequence.Sequence;
import com.mall.dao.sequence.SequenceMapper;
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderServe;
import com.mall.entity.user.User;
import com.mall.message.ProcessResult;
import com.mall.service.order.OrderServeService;
import com.mall.service.order.OrderService;
import com.mall.service.sys.CacheService;
import com.mall.util.DateFormatUtil;
import com.mall.util.ProcessOrderUtil;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

/**
 * 订单售后服务类
 * @author Jang
 *
 */
@Controller 
@RequestMapping("/mall/order/")
public class OrderServeController extends AbstractController{
	@Resource
	private OrderServeService orderServeService;
	@Resource
	private OrderService orderService;
	@Resource
	private CacheService cacheService;
	@Resource
	private SequenceMapper sequenceMapper;
	
	/**
	 * 保存订单售后申请单
	 * @param model
	 * @param request
	 * @param orderServe
	 * @return
	 */
	@RequestMapping("/serve/save")
	public String toUserManager(Model model,HttpServletRequest request,OrderServe orderServe,RedirectAttributes attr) {
		logger.info("保存订单售后服务单，订单号："+orderServe.getOrder_number());
		Order order =new Order();
		order.setOrder_number(orderServe.getOrder_number());
		order=orderService.selectInfo(order);  //获取订单详情
		User user = SessionUtil.getUser(request);  //获取当前登录用户
		orderServe.setCreate_at(user.getUser_name());
		orderServe.setCreate_time(DateFormatUtil.getDate());
		orderServe.setLogistics_state(order.getLogistics_state());
		orderServe.setOrder_dtate(order.getDel_state());
		orderServe.setState( Byte.parseByte("0"));  //初始化状态为待审核状态
		orderServe.setTotal_amount(order.getTotal_amount());
		
		Sequence sequence = sequenceMapper.currval(orderServe.getOrder_number());
		String ServiceNumber="";
		if(Validate.notNull(sequence)) {
			int nextval = sequenceMapper.nextval(orderServe.getOrder_number());
			ServiceNumber=ProcessOrderUtil.processOrderServiceNumber(nextval, orderServe.getOrder_number());
		}else {
			logger.info("创建新序列"+orderServe.getOrder_number());
			Sequence seq=new Sequence();
			seq.setSeq_name(orderServe.getOrder_number());
			seq.setCurrent_val(1); //初始值1
			sequenceMapper.insertSelective(seq);
			ServiceNumber=ProcessOrderUtil.processOrderServiceNumber(1, orderServe.getOrder_number());
		}
		orderServe.setService_number(ServiceNumber);//设置售后服务单号
		
		logger.info("订单售后服务单状态："+orderServe.getService_number());
		
		try {
			orderServeService.insertSelective(orderServe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//调转到售后单详情界面
		attr.addFlashAttribute("service_number", orderServe.getService_number());
		return "redirect:/mall/user/service/details?service_number="+orderServe.getService_number();
	}
	/**
	 * 通过售后服务申请单号，更改状态
	 * @param orderServe
	 * @return
	 */
	@PostMapping("/serve/chengestate")
	@ResponseBody
	public ProcessResult<OrderServe> chengeStatus(OrderServe orderServe){
		logger.info("修改订单售后服务单状态！单据号："+orderServe.getService_number());
		ProcessResult<OrderServe> res=new ProcessResult<OrderServe>();
		int chengeStatus = orderServeService.chengeStatus(orderServe);
		if(chengeStatus>0) {
			res=ProcessResult.success(res);
		}
		return res;
		
	}

}
