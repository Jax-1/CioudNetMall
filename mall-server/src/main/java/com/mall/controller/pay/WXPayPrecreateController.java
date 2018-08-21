package com.mall.controller.pay;
import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream.PutField;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.mall.controller.AbstractController;
import com.mall.entity.goods.Goods;
import com.mall.entity.inventory.Inventory;
import com.mall.entity.login.User;
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderDetails;
import com.mall.entity.payment.PaymentFlow;
import com.mall.entity.payment.PaymentRefund;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.goods.GoodsService;
import com.mall.service.inventory.InventoryService;
import com.mall.service.order.OrderService;
import com.mall.service.payment.PaymentFlowService;
import com.mall.service.payment.PaymentRefundService;
import com.mall.service.sys.CacheService;
import com.mall.util.DateFormatUtil;
import com.mall.util.MapTrunPojoUtil;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

/**
 * 微信支付-扫码支付.
 * <p>
 * detailed description
 *
 * @version 1.0
 * @since 2018/6/18
 */
@Controller
@RequestMapping("/wxpay/precreate")
public class WXPayPrecreateController extends AbstractController{
    @Autowired
    private WXPay wxPay;

    @Autowired
    private WXPayClient wxPayClient;
    @Resource
	private OrderService orderService;
    @Resource
    private PaymentFlowService paymentFlowService;
    @Resource
	private GoodsService goodsService;
    @Resource
    private InventoryService inventoryService;
    @Resource
	private CacheService cacheService;
    @Resource
    private PaymentRefundService paymentRefundService;

    /**
     * 扫码支付 - 统一下单
     * 相当于支付不的电脑网站支付
     *
     * <a href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1">扫码支付API</a>
     */
    @RequestMapping("")
    public void precreate(HttpServletRequest request, HttpServletResponse response,Order order) throws Exception {
       //获取订单信息
    	order=orderService.selectInfo(order);
    	BigDecimal total_amount = order.getTotal_amount().multiply(new BigDecimal(100));
    	//获取商品名称
    	StringBuffer goodsNames=new StringBuffer();
    	for(int i=0;i<order.getOrderDetailsList().size();i++) {
    		goodsNames.append((order.getOrderDetailsList()).get(i).getGoods_name());
    		if(order.getOrderDetailsList().size()>1&&order.getOrderDetailsList().size()!=i+1) {
    			goodsNames.append("-");
    		}
    		Map<String, Integer> map = cacheService.toBePaidGoods(order.getOrderDetailsList().get(i).getGoods_id(),0);
    		logger.info("生成支付二维码，获取正在支付缓存信息，商品ID："+order.getOrderDetailsList().get(i).getGoods_id()+"正支付商品数量："+map);
    		Integer num=map!=null?map.get(order.getOrderDetailsList().get(i).getGoods_id())+order.getOrderDetailsList().get(i).getNum():order.getOrderDetailsList().get(i).getNum();
    		//将待支付商品数量写入缓存
        	Map<String, Integer> bePaidGoods = cacheService.writePaidGoods(order.getOrderDetailsList().get(i).getGoods_id(), num);
        	
        	
    	}
    	
    	//锁库存信息行 END
    	
    	logger.info("支付金额："+total_amount.setScale(0,BigDecimal.ROUND_DOWN).toString()+"分");
    	Map<String, String> reqData = new HashMap<>();
    	//支付流水号，用于微信支付端记录用户订单号。
    	String out_trade_no=String.valueOf(System.nanoTime());
        reqData.put("out_trade_no", out_trade_no);
        reqData.put("trade_type", "NATIVE");
        reqData.put("product_id", "1");
        reqData.put("body", goodsNames.toString());
        // 订单总金额，单位为分
        reqData.put("total_fee", total_amount.setScale(0,BigDecimal.ROUND_DOWN).toString());
        //reqData.put("total_fee", "2");
        // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
        reqData.put("spbill_create_ip", "14.23.150.211");
        // 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        reqData.put("notify_url", "http://www.ywwhcm.com.cn/wxpay/precreate/notify");
        // 自定义参数, 可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        reqData.put("device_info", "WEB");
        // 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
        reqData.put("attach", order.getOrder_number());   //订单号
        
        //将商户订单号存入SESSION中，用于前端异步查询订单状态
        request.getSession().setAttribute("out_trade_no", out_trade_no);  
        /**
         * {
         * code_url=weixin://wxpay/bizpayurl?pr=vvz4xwC,
         * trade_type=NATIVE,
         * return_msg=OK,
         * result_code=SUCCESS,
         * return_code=SUCCESS,
         * prepay_id=wx18111952823301d9fa53ab8e1414642725
         * }
         */
        Map<String, String> responseMap = wxPay.unifiedOrder(reqData);
        logger.info(responseMap.toString());
        String returnCode = responseMap.get("return_code");
        String resultCode = responseMap.get("result_code");
        if (WXPayConstants.SUCCESS.equals(returnCode) && WXPayConstants.SUCCESS.equals(resultCode)) {
            String prepayId = responseMap.get("prepay_id");
            String codeUrl = responseMap.get("code_url");

            BufferedImage image = PayUtil.getQRCodeImge(codeUrl);

            response.setContentType("image/jpeg");
            response.setHeader("Pragma","no-cache");
            response.setHeader("Cache-Control","no-cache");
            response.setIntHeader("Expires",-1);
            ImageIO.write(image, "JPEG", response.getOutputStream());
        }

    }

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/notify")
    public void precreateNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> reqData = wxPayClient.getNotifyParameter(request);

        /**
         * {
         * transaction_id=4200000138201806180751222945,
         * nonce_str=aaaf3fe4d3aa44d8b245bc6c97bda7a8,
         * bank_type=CFT,
         * openid=xxx,
         * sign=821A5F42F5E180ED9EF3743499FBCF13,
         * fee_type=CNY,
         * mch_id=xxx,
         * cash_fee=1,
         * out_trade_no=186873223426017,
         * appid=xxx,
         * total_fee=1,
         * trade_type=NATIVE,
         * result_code=SUCCESS,
         * time_end=20180618131247,
         * is_subscribe=N,
         * return_code=SUCCESS
         * }
         */
        logger.info(reqData.toString());
        logger.info("获取支付回调！支付订单号："+reqData.get("out_trade_no")+"支付结果："+reqData.get("return_code"));
        // 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
        boolean signatureValid = wxPay.isPayResultNotifySignatureValid(reqData);
        if (signatureValid) {
            /**
             * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
             * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，
             * 判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
             * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
             */
        	//支付成功
    		//校验订单状态
    		Order order =new Order();
    		order.setOrder_number(reqData.get("attach"));
    		logger.info("微信支付回调！，商户订单号："+reqData.get("attach"));
    		order=orderService.selectInfo(order);
        	//判断支付状态
        	if("SUCCESS".equals(reqData.get("result_code"))&&Validate.notNull(order)) {
        		
        		
        		logger.info("order pay status:"+order.getPay_state().toString()+",chick:"+"1".equals(order.getPay_state().toString()));
        		//订单状态待支付
        		if("1".equals(order.getPay_state().toString())) {
        			//修改库存信息
        			for(OrderDetails orderDetails:order.getOrderDetailsList()) {
        				Goods goods =new Goods();
        				goods.setGoods_id(orderDetails.getGoods_id());
        				goods=goodsService.selectInfo(goods);
        				Inventory inventory=goods.getGoodsInfo().getInventory();
        				//inventory.setAmount(inventory.getAmount()-orderDetails.getNum());
        				//修改待出库数量为：待出库数量+订单数量
        				inventory.setStay_amount(inventory.getStay_amount()+orderDetails.getNum());
        				inventoryService.updateInventory(inventory);
        				//清除待支付商品信息缓存
        				cacheService.clearPaidGoods(goods.getGoods_id());
        			}
        			
        			//修改订单状态
        			 byte state = Byte.parseByte("2");
        			order.setPay_state(state);
        			order.setPayment_seq(reqData.get("transaction_id"));
        			orderService.updateByPrimaryKeySelective(order);
        			
        			
        		}
        		
        	}
        	Object entity = MapTrunPojoUtil.map2Object(reqData, PaymentFlow.class);
        	//支付时间
        	try {
        		((PaymentFlow)entity).setCreate_time(DateFormatUtil.getDate());
            	((PaymentFlow)entity).setAttach(reqData.get("attach"));
            	((PaymentFlow)entity).setTime_end(reqData.get("time_end"));
            	((PaymentFlow)entity).setTotal_fee(Integer.valueOf(reqData.get("total_fee")));
        	}catch (Exception e) {
        		logger.error("类型转换异常！"+e.getMessage()+e.toString());
        		e.printStackTrace();
			}
        	
			//记录支付流水
			paymentFlowService.insertSelective((PaymentFlow)entity );
            Map<String, String> responseMap = new HashMap<>(2);
            responseMap.put("return_code", "SUCCESS");
            responseMap.put("return_msg", "OK");
            String responseXml = WXPayUtil.mapToXml(responseMap);

            response.setContentType("text/xml");
            response.getWriter().write(responseXml);
            response.flushBuffer();
        }
    }
    /**
     * 查询订单支付情况
     * @param request
     * @param response
     * @param transaction_id 
     * @param out_trade_no
     * @return
     * @throws Exception
     */
    @ResponseBody
	@RequestMapping("/orderQuery")
	public Map<String, String> orderQuery(HttpServletRequest request, HttpServletResponse response,String transaction_id,String out_trade_no) throws Exception {
    	Map<String, String> reqData = new HashMap<>();
    	if(Validate.notNull(transaction_id)) {
    		logger.info("查询微信支付状态！transaction_id="+transaction_id);
    		reqData.put("transaction_id", transaction_id);
    	}else if(Validate.notNull(out_trade_no)){
    		logger.info("查询微信支付状态！out_trade_no="+out_trade_no);
    		reqData.put("out_trade_no", out_trade_no);
    	}else {
    		out_trade_no =(String) request.getSession().getAttribute("out_trade_no");
    		if(!Validate.notNull(out_trade_no)) {
    			return null;
    		}
    		logger.info("查询微信支付状态！out_trade_no="+out_trade_no);
    		reqData.put("out_trade_no", out_trade_no);
    	}
    	
    	Map<String, String> orderQuery = wxPay.orderQuery(reqData);
    	return orderQuery;
		
	}
    /**
     * 延续待支付商品数量缓存信息
     * 如超过10分钟，取消支付
     * @return
     */
    @SuppressWarnings("rawtypes")
	@ResponseBody
	@GetMapping("/cachelive")
    public ProcessResult cacheLive(Order order){
    	ProcessResult res=new ProcessResult();
    	//获取订单信息
    	order=orderService.selectInfo(order);
    	for(OrderDetails orderDetails:order.getOrderDetailsList()) {
    		Map<String, Integer> map = cacheService.toBePaidGoods(orderDetails.getGoods_id(),0);
    		//缓存已失效
    		if(map.get(orderDetails.getGoods_id())==0) {
    			return res;
    		}
    	}
    	
		return ProcessResult.success(res);
    	
    }
	@RequestMapping("/refund")
    @ResponseBody
    public Map<String, String> toRefund(HttpServletRequest request, HttpServletResponse response,PaymentRefund paymentRefund) {
    	Map<String, String> reqData = new HashMap<>();
    	reqData.put("transaction_id", paymentRefund.getTransaction_id());
    	reqData.put("out_refund_no", paymentRefund.getRefund_number());
    	logger.info("订单付款金额："+paymentRefund.getTotal_amount());
    	BigDecimal decimal = paymentRefund.getTotal_amount().multiply(new BigDecimal(100));
    	reqData.put("total_fee", decimal.setScale(0,BigDecimal.ROUND_DOWN).toString());  //订单金额
    	logger.info("订单退款金额："+paymentRefund.getRefund_fee());
    	BigDecimal refund_fee = paymentRefund.getRefund_fee().multiply(new BigDecimal(100));
    	reqData.put("refund_fee", refund_fee.setScale(0,BigDecimal.ROUND_DOWN).toString());
    	logger.info("退款订单号："+paymentRefund.getOrder_number()+",退款金额："+refund_fee.setScale(0,BigDecimal.ROUND_DOWN).toString()+"分");
    	
//    	reqData.put("notify_url", "http://www.ywwhcm.com.cn/wxpay/precreate/notify");//回调URL
//    	Map<String, String> refund =new HashMap<>();
//    	try {
//			refund = wxPay.refund(reqData);
//			//保存退款信息
//			paymentRefund.setCreate_time(DateFormatUtil.getDate());
//			paymentRefund.setAction_admin(SessionUtil.getAdminUser(request).getAdmin_name());
//			paymentRefundService.insertSelective(paymentRefund);
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	logger.info("退款请求结果："+refund.toString());
    	
    	Map<String, String> fillRequestData=new HashMap<>();
    	Map<String, String> processResponseXml=new HashMap<>();
    	try {
			fillRequestData = wxPay.fillRequestData(reqData);
			String mch=fillRequestData.get("mch_id");
			logger.info("商户号："+mch);
			Map<String, String> cache = cacheService.getCache(SystemCode.PATH);
			String path = cache.get(SystemCode.WXSSL);
			String doRefund = ClientCustomSSL.doRefund(WXPayConstants.REFUND_URL,  WXPayUtil.mapToXml(fillRequestData),mch,path);
			
			processResponseXml = wxPay.processResponseXml(doRefund);
			logger.info("退款返回结果："+processResponseXml);
			if("SUCCESS".equals(processResponseXml.get("return_code"))) {
				paymentRefund.setReturn_code(processResponseXml.get("return_code"));
				paymentRefund.setReturn_msg(processResponseXml.get("return_msg"));
				paymentRefund.setResult_code(processResponseXml.get("result_code"));
				paymentRefund.setErr_code(processResponseXml.get("err_code"));
				paymentRefund.setErr_code_des(processResponseXml.get("err_code_des"));
				//请求成功
				if("SUCCESS".equals(processResponseXml.get("result_code"))) {
					//退款成功
					//修改订单状态
					Order order =new Order();
					order.setOrder_number(paymentRefund.getOrder_number());
					byte state = Byte.parseByte("3");
					order.setPay_state(state);
					orderService.updateOrderStatus(order);
					paymentRefund.setState(Byte.parseByte("1"));
					
				}
				paymentRefund.setCreate_time(DateFormatUtil.getDate());
				paymentRefund.setAction_admin(SessionUtil.getAdminUser(request).getAdmin_name());
				//保存退款信息
				paymentRefundService.insertSelective(paymentRefund);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return processResponseXml;
    }
    

}