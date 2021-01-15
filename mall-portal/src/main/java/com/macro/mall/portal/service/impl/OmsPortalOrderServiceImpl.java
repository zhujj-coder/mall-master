package com.macro.mall.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.Asserts;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.common.service.RedisService;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.portal.component.CancelOrderSender;
import com.macro.mall.portal.dao.PortalOrderDao;
import com.macro.mall.portal.dao.PortalOrderItemDao;
import com.macro.mall.portal.dao.SmsCouponHistoryDao;
import com.macro.mall.portal.domain.*;
import com.macro.mall.portal.service.*;
import lombok.extern.slf4j.Slf4j;
import net.xpyun.platform.opensdk.service.PrintService;
import net.xpyun.platform.opensdk.util.HashSignUtil;
import net.xpyun.platform.opensdk.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 前台订单管理Service
 * Created by macro on 2018/8/30.
 */
@Service
@Slf4j
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private OmsCartItemService cartItemService;
    @Autowired
    private UmsMemberReceiveAddressService memberReceiveAddressService;
    @Autowired
    private UmsMemberCouponService memberCouponService;
    @Autowired
    private UmsIntegrationConsumeSettingMapper integrationConsumeSettingMapper;
    @Autowired
    private PmsSkuStockMapper skuStockMapper;
    @Autowired
    private SmsCouponHistoryDao couponHistoryDao;
    @Autowired
    private OmsOrderMapper orderMapper;
    @Autowired
    private PortalOrderItemDao orderItemDao;
    @Autowired
    private SmsCouponHistoryMapper couponHistoryMapper;
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.orderId}")
    private String REDIS_KEY_ORDER_ID;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Autowired
    private PortalOrderDao portalOrderDao;

    @Autowired
    private OmsOrderSettingMapper orderSettingMapper;
    @Autowired
    private OmsOrderItemMapper orderItemMapper;
    @Autowired
    private CancelOrderSender cancelOrderSender;
    @Autowired
    private UmsPrinterMapper umsPrinterMapper;
    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;
    @Autowired
    private FeiEService feiEService;
    final private String USER ="zjjhot@163.com";
    final private String UserKEY ="d8dc615805a24fbb908f524110be83b5";
    @Override
    public ConfirmOrderResult generateConfirmOrder(List<Long> cartIds, Long adminId) {
        ConfirmOrderResult result = new ConfirmOrderResult();
        //获取购物车信息
        UmsMember currentMember = memberService.getCurrentMember();
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(currentMember.getId(),cartIds,adminId);
        result.setCartPromotionItemList(cartPromotionItemList);
        //获取用户收货地址列表
        List<UmsMemberReceiveAddress> memberReceiveAddressList = memberReceiveAddressService.list();
        result.setMemberReceiveAddressList(memberReceiveAddressList);
        //获取用户可用优惠券列表
//        List<SmsCouponHistoryDetail> couponHistoryDetailList = memberCouponService.listCart(cartPromotionItemList, 1);
//        result.setCouponHistoryDetailList(couponHistoryDetailList);
        //获取用户积分
        result.setMemberIntegration(currentMember.getIntegration());
        //获取积分使用规则


        //计算总金额、活动优惠、应付金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {


            totalAmount = totalAmount.add(cartPromotionItem.getPrice().multiply(new BigDecimal(cartPromotionItem.getQuantity())));
//            不允许优惠
            //            promotionAmount = promotionAmount.add(cartPromotionItem.getReduceAmount().multiply(new BigDecimal(cartPromotionItem.getQuantity())));
        }
        ConfirmOrderResult.CalcAmount calcAmount = calcCartAmount(totalAmount,currentMember.getIntegration(),adminId);
        result.setCalcAmount(calcAmount);
        return result;
    }

    @Override
    public Map<String, Object> generateOrder(OrderParam orderParam, Long adminId) {
        List<OmsOrderItem> orderItemList = new ArrayList<>();
        //获取购物车及优惠信息
        UmsMember currentMember = memberService.getCurrentMember();
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(currentMember.getId(), orderParam.getCartIds(),adminId);
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            //生成下单商品信息
            OmsOrderItem orderItem = new OmsOrderItem();
            orderItem.setProductId(cartPromotionItem.getProductId());
            orderItem.setProductName(cartPromotionItem.getProductName());
            orderItem.setProductPic(cartPromotionItem.getProductPic());
            orderItem.setProductAttr(cartPromotionItem.getProductAttr());
            orderItem.setProductBrand(cartPromotionItem.getProductBrand());
            orderItem.setProductSn(cartPromotionItem.getProductSn());
            orderItem.setProductPrice(cartPromotionItem.getPrice());
            orderItem.setProductQuantity(cartPromotionItem.getQuantity());
            orderItem.setProductSkuId(cartPromotionItem.getProductSkuId());
            orderItem.setProductSkuCode(cartPromotionItem.getProductSkuCode());
            orderItem.setProductCategoryId(cartPromotionItem.getProductCategoryId());
            orderItem.setPromotionAmount(cartPromotionItem.getReduceAmount());
            orderItem.setPromotionName(cartPromotionItem.getPromotionMessage());
            orderItem.setGiftIntegration(cartPromotionItem.getIntegration());
            orderItem.setGiftGrowth(cartPromotionItem.getGrowth());
            orderItemList.add(orderItem);
        }
        //判断购物车中商品是否都有库存
//        if (!hasStock(cartPromotionItemList)) {
//            Asserts.fail("库存不足，无法下单");
//        }
        //判断使用使用了优惠券
//        if (orderParam.getCouponId() == null) {
//            //不用优惠券
//            for (OmsOrderItem orderItem : orderItemList) {
//                orderItem.setCouponAmount(new BigDecimal(0));
//            }
//        } else {
//            //使用优惠券
//            SmsCouponHistoryDetail couponHistoryDetail = getUseCoupon(cartPromotionItemList, orderParam.getCouponId());
//            if (couponHistoryDetail == null) {
//                Asserts.fail("该优惠券不可用");
//            }
//            //对下单商品的优惠券进行处理
//            handleCouponAmount(orderItemList, couponHistoryDetail);
//        }
        for (OmsOrderItem orderItem : orderItemList) {
            orderItem.setCouponAmount(new BigDecimal(0));
        }
        //判断是否使用积分
//        if (orderParam.getUseIntegration() == null||orderParam.getUseIntegration().equals(0)) {
//            //不使用积分
//            for (OmsOrderItem orderItem : orderItemList) {
//                orderItem.setIntegrationAmount(new BigDecimal(0));
//            }
//        } else {
//            //使用积分
//            BigDecimal totalAmount = calcTotalAmount(orderItemList);
//            BigDecimal integrationAmount = getUseIntegrationAmount(orderParam.getUseIntegration(), totalAmount, currentMember, orderParam.getCouponId() != null);
//            if (integrationAmount.compareTo(new BigDecimal(0)) == 0) {
//                Asserts.fail("积分不可用");
//            } else {
//                //可用情况下分摊到可用商品中
//                for (OmsOrderItem orderItem : orderItemList) {
//                    BigDecimal perAmount = orderItem.getProductPrice().divide(totalAmount, 3, RoundingMode.HALF_EVEN).multiply(integrationAmount);
//                    orderItem.setIntegrationAmount(perAmount);
//                }
//            }
//        }
        //计算order_item的实付金额
//        handleRealAmount(orderItemList);
        //进行库存锁定
//        lockStock(cartPromotionItemList);
        //根据商品合计、运费、活动优惠、优惠券、积分计算应付金额  add 备注
        OmsOrder order = new OmsOrder();
        order.setDiscountAmount(new BigDecimal(0));
        order.setTotalAmount(calcTotalAmount(orderItemList));
        order.setFreightAmount(new BigDecimal(0));
        order.setPromotionAmount(calcPromotionAmount(orderItemList));
        order.setPromotionInfo(getOrderPromotionInfo(orderItemList));
        order.setNote(orderParam.getMessage());
//        if (orderParam.getCouponId() == null) {
//            order.setCouponAmount(new BigDecimal(0));
//        } else {
//            order.setCouponId(orderParam.getCouponId());
//            order.setCouponAmount(calcCouponAmount(orderItemList));
//        }

//        if (orderParam.getUseIntegration() == null) {
//            order.setIntegration(0);
//            order.setIntegrationAmount(new BigDecimal(0));
//        } else {
//            order.setIntegration(orderParam.getUseIntegration());
//            order.setIntegrationAmount(calcIntegrationAmount(orderItemList));
//        }
//        计算积分金额
        //获取积分使用规则
        ConfirmOrderResult.CalcAmount calcAmount = calcCartAmount(order.getTotalAmount(), currentMember.getIntegration(), adminId);
//        order.setPayAmount(calcPayAmount(order));
        order.setPayAmount(calcAmount.getPayAmount());
        //转化为订单信息并插入数据库
        order.setMemberId(currentMember.getId());
        order.setCreateTime(new Date());
        order.setMemberUsername(currentMember.getUsername());
        //支付方式：0->未支付；1->支付宝；2->微信
        order.setPayType(orderParam.getPayType());
        //订单来源：0->PC订单；1->app订单
        order.setSourceType(1);
        //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        order.setStatus(0);
        //订单类型：0->正常订单；1->秒杀订单
        order.setOrderType(0);
        //收货人信息：姓名、电话、邮编、地址
        if(orderParam.getMemberReceiveAddressId()!=null&&orderParam.getMemberReceiveAddressId()>0){
            UmsMemberReceiveAddress address = memberReceiveAddressService.getItem(orderParam.getMemberReceiveAddressId());
            order.setReceiverName(address.getName());
            order.setReceiverPhone(address.getPhoneNumber());
            order.setReceiverPostCode(address.getPostCode());
            order.setReceiverProvince(address.getProvince());
            order.setReceiverCity(address.getCity());
            order.setReceiverRegion(address.getRegion());
            order.setReceiverDetailAddress(address.getDetailAddress());
        }else{
            order.setReceiverDetailAddress(orderParam.getDetailAddress());
            order.setReceiverName(orderParam.getDetailAddress());
            order.setReceiverPhone("");
        }

        //0->未确认；1->已确认
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);
        //计算赠送积分
//        order.setIntegration(calcGifIntegration(orderItemList));
        order.setIntegration(order.getPayAmount().intValue());
        //计算赠送成长值
//        order.setGrowth(calcGiftGrowth(orderItemList));
        //生成订单号
        order.setOrderSn(generateOrderSn(order));
        //设置自动收货天数
        List<OmsOrderSetting> orderSettings = orderSettingMapper.selectByExample(new OmsOrderSettingExample());
        if(CollUtil.isNotEmpty(orderSettings)){
            order.setAutoConfirmDay(orderSettings.get(0).getConfirmOvertime());
        }
        // 设置商户ID
        order.setAdminId(adminId);
        // TODO: 2018/9/3 bill_*,delivery_*
        //插入order表和order_item表
        //如使用积分需要扣除积分
        if (calcAmount.isAllowUseIntegrationAmount()) {
            order.setUseIntegration(calcAmount.getUseIntegration());
            order.setIntegrationAmount(calcAmount.getIntegrationAmount());
            memberService.updateIntegration(currentMember.getId(), currentMember.getIntegration() - order.getUseIntegration());
        }
        orderMapper.insert(order);
        for (OmsOrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
        }
        orderItemDao.insertList(orderItemList);
        //如使用优惠券更新优惠券使用状态
//        if (orderParam.getCouponId() != null) {
//            updateCouponStatus(orderParam.getCouponId(), currentMember.getId(), 1);
//        }

        //删除购物车中的下单商品
        deleteCartItemList(cartPromotionItemList, currentMember,adminId);
        //发送延迟消息取消订单
        sendDelayMessageCancelOrder(order.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("orderItemList", orderItemList);
        return result;
    }
    @Override
    public Map<String, Object> generateDirectPayOrder(OrderDirectPayOrderParam orderParam, Long adminId) {
        //获取购物车及优惠信息
        UmsMember currentMember = memberService.getCurrentMember();

        //根据商品合计、运费、活动优惠、优惠券、积分计算应付金额  add 备注
        OmsOrder order = new OmsOrder();
        order.setDiscountAmount(new BigDecimal(0));
        order.setTotalAmount(orderParam.getValue());
        order.setFreightAmount(new BigDecimal(0));
        order.setPromotionAmount(null);
        order.setPromotionInfo(null);
        order.setNote(orderParam.getMessage());
//        计算积分金额
        //获取积分使用规则
        ConfirmOrderResult.CalcAmount calcAmount = calcCartAmount(order.getTotalAmount(), currentMember.getIntegration(), adminId);
//        order.setPayAmount(calcPayAmount(order));
        order.setPayAmount(calcAmount.getPayAmount());
        //转化为订单信息并插入数据库
        order.setMemberId(currentMember.getId());
        order.setCreateTime(new Date());
        order.setMemberUsername(currentMember.getUsername());
        //支付方式：0->未支付；1->支付宝；2->微信
        order.setPayType(orderParam.getPayType());
        //订单来源：0->PC订单；1->app订单
        order.setSourceType(1);
        //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        order.setStatus(0);
        //订单类型：0->正常订单；1->秒杀订单
        order.setOrderType(0);
        //收货人信息：姓名、电话、邮编、地址
//        UmsMemberReceiveAddress address = memberReceiveAddressService.getItem(orderParam.getMemberReceiveAddressId());
//        order.setReceiverName(address.getName());
//        order.setReceiverPhone(address.getPhoneNumber());
//        order.setReceiverPostCode(address.getPostCode());
//        order.setReceiverProvince(address.getProvince());
//        order.setReceiverCity(address.getCity());
//        order.setReceiverRegion(address.getRegion());
//        order.setReceiverDetailAddress(address.getDetailAddress());
        //0->未确认；1->已确认
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);
        //计算赠送积分
//        order.setIntegration(calcGifIntegration(orderItemList));
        order.setIntegration(order.getPayAmount().intValue());
        //计算赠送成长值
//        order.setGrowth(calcGiftGrowth(orderItemList));
        //生成订单号
        order.setOrderSn(generateOrderSn(order));
        //设置自动收货天数
//        List<OmsOrderSetting> orderSettings = orderSettingMapper.selectByExample(new OmsOrderSettingExample());
//        if(CollUtil.isNotEmpty(orderSettings)){
//            order.setAutoConfirmDay(orderSettings.get(0).getConfirmOvertime());
//        }
        // 设置商户ID
        order.setAdminId(adminId);
        // TODO: 2018/9/3 bill_*,delivery_*
        //插入order表和order_item表
        //如使用积分需要扣除积分
        if (calcAmount.isAllowUseIntegrationAmount()) {
            order.setUseIntegration(calcAmount.getUseIntegration());
            order.setIntegrationAmount(calcAmount.getIntegrationAmount());
            memberService.updateIntegration(currentMember.getId(), currentMember.getIntegration() - order.getUseIntegration());
        }
        order.setReceiverName("");
        order.setReceiverPhone("");
        order.setNote("直接买单用户");
        orderMapper.insert(order);
        //如使用优惠券更新优惠券使用状态
//        if (orderParam.getCouponId() != null) {
//            updateCouponStatus(orderParam.getCouponId(), currentMember.getId(), 1);
//        }

        //发送延迟消息取消订单
        sendDelayMessageCancelOrder(order.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        return result;
    }
    @Override
    public Integer paySuccess(Long orderId, Integer payType) {
        //修改订单支付状态
        OmsOrder order = new OmsOrder();
        order.setId(orderId);
        order.setStatus(1);
        order.setPaymentTime(new Date());
        order.setPayType(payType);
        orderMapper.updateByPrimaryKeySelective(order);

        //恢复所有下单商品的锁定库存，扣减真实库存
//        OmsOrderDetail orderDetail = portalOrderDao.getDetail(orderId);
//        int count = portalOrderDao.updateSkuStock(orderDetail.getOrderItemList());
        // 修改积分
        OmsOrder omsOrder = orderMapper.selectByPrimaryKey(orderId);
        //        修改订单是否打印
        if(omsOrder.getReceiverName()==null||omsOrder.getReceiverName().length()<=0){
//语音播报
            voiceOrder(omsOrder);
        }else{
//            打印订单
            try{
                printOrder(omsOrder);
            }catch (Exception e){
                log.error("!!!!!!!!!!!!!!!!!订单打印异常!!!!!!!!!!!!!!!!!",e);
            }
        }

        UmsMember byOpenId = memberService.getByOpenId(omsOrder.getMemberUsername());
        int intergration = 0;
        int history = 0;
        if(byOpenId==null){
            return 1;
        }else if(byOpenId.getIntegration()==null){
            intergration=omsOrder.getIntegration();
        }else{
            intergration=byOpenId.getIntegration()+omsOrder.getIntegration();
        }
        if(byOpenId.getHistoryIntegration()==null){
            history=omsOrder.getIntegration();
        }else{
            history=byOpenId.getHistoryIntegration()+omsOrder.getIntegration();
        }
        memberService.updateIntegrationAll(byOpenId.getId(),intergration,history);
        return 1;
    }

    private void voiceOrder(OmsOrder omsOrder) {
        UmsPrinterExample example = new UmsPrinterExample();
        example.or().andAdminIdEqualTo(omsOrder.getAdminId());
        List<UmsPrinter> umsPrinters = umsPrinterMapper.selectByExample(example);
        umsPrinters.forEach(item->{
            PrintService printService = new PrintService();
            VoiceRequest restRequest = new VoiceRequest();
            initRequest(restRequest);
            restRequest.setSn(item.getPrinterSn());
            //        打印模式：
//        支付方式：
//        取值范围41~55：
//        支付宝 41、微信 42、云支付 43、银联刷卡 44、银联支付 45、会员卡消费 46、会员卡充值 47、翼支付 48、成功收款 49、嘉联支付 50、壹钱包 51、京东支付 52、快钱支付 53、威支付 54、享钱支付 55
//        仅用于支持金额播报的芯烨云打印机。
            restRequest.setPayType(41);
//        支付与否：
//        取值范围59~61：
//        退款 59 到账 60 消费 61。
//        仅用于支持金额播报的芯烨云打印机。
            restRequest.setPayType(61);
            //        支付金额：
//        最多允许保留2位小数。
//        仅用于支持金额播报的芯烨云打印机。
            restRequest.setMoney(omsOrder.getPayAmount().doubleValue());
            log.info("请求[{}]",restRequest);
            ObjectRestResponse<String> printerResultObjectRestResponse = printService.playVoice(restRequest);
            log.info("返回[{}]",printerResultObjectRestResponse);
            handResponse(printerResultObjectRestResponse);
        });

    }

    private void printOrder(OmsOrder omsOrder) {
        UmsPrinterExample example = new UmsPrinterExample();
        example.or().andAdminIdEqualTo(omsOrder.getAdminId());
        List<UmsPrinter> umsPrinters = umsPrinterMapper.selectByExample(example);
        umsPrinters.forEach(item->{
            if(item.getPrinterFactory()==1){
                xinyePrint(omsOrder, item);
            }else if(item.getPrinterFactory()==2){
                feiEService.printMsg(omsOrder,item);
            }else{
                log.info("打印机[{}]厂商[{}]型有误！",item.getPrinterSn(),item.getPrinterFactory());
            }

        });

    }


    private void xinyePrint(OmsOrder omsOrder, UmsPrinter item) {
        try {
            PrintService printService = new PrintService();
            PrintRequest restRequest = new PrintRequest();
            initRequest(restRequest);
            restRequest.setSn(item.getPrinterSn());
//        打印模式：
//        值为 0 或不指定则会检查打印机是否在线，如果不在线 则不生成打印订单，直接返回设备不在线状态码；如果在线则生成打印订单，并返回打印订单号。
//        值为 1不检查打印机是否在线，直接生成打印订单，并返回打印订单号。如果打印机不在线，订单将缓存在打印队列中，打印机正常在线时会自动打印。
            restRequest.setMode(2);
//        支付方式：
//        取值范围41~55：
//        支付宝 41、微信 42、云支付 43、银联刷卡 44、银联支付 45、会员卡消费 46、会员卡充值 47、翼支付 48、成功收款 49、嘉联支付 50、壹钱包 51、京东支付 52、快钱支付 53、威支付 54、享钱支付 55
//        仅用于支持金额播报的芯烨云打印机。
            restRequest.setPayType(41);
//        支付与否：
//        取值范围59~61：
//        退款 59 到账 60 消费 61。
//        仅用于支持金额播报的芯烨云打印机。
            restRequest.setPayType(61);
//        支付金额：
//        最多允许保留2位小数。
//        仅用于支持金额播报的芯烨云打印机。
            restRequest.setMoney(omsOrder.getPayAmount().doubleValue());
            restRequest.setCopies(item.getCopies());
//               <C><IMG160></IMG>
//<CB>**#8 美团**
//<L><N>--------------------------------
//<CB>--在线支付--
//<HB>芯烨云小票
//<L><N>下单时间:2019年09月06日15时35分
//订单编号:5842160392535156
//**************商品**************
//<C><HB>---1号口袋---
//<L><N>红焖猪手砂锅饭            x1 19
//牛肉                      x1 8
//--------------------------------
//配送费:￥4
//--------------------------------
//<B>小计:￥31
//折扣:￥4
//<L><N>********************************
//<B>订单总价:￥27
//
//<N>香洲花园 5栋6单元1404号
//肖(女士):135-4444-6666
//订单备注：[用餐人数]1人;
//少放辣椒
//<C><HB>**#8 完**
//<L><N>二维码打印测试
//<L><QR>https://www.xpyun.net</QR>
//<R><N>条形码打印测试
//<R><BARCODE>5842160392535156</BARCODE>
            String content ="<C><IMG160></IMG>\n" +
                    "<CB>** 微信支付**\n" +
                    "<L><N>--------------------------------\n" +
                    "<CB>--在线已支付--\n" +
                    "<HB>TITLE\n" +
                    "<L><N>下单时间:CREATE_TIME\n" +
                    "订单编号:ORDER_SN\n" +
                    "**************商品**************\n" +
//                        "<C><HB>---1号口袋---\n" +
                    "<L><N>PRODUCT_ITEM" +
                    "--------------------------------\n" +
//                        "配送费:￥4\n" +
                    "--------------------------------\n" +
                    "<B>小计:￥TOTAL_AMOUNT\n" +
                    "DISCOUNT\n" +
                    "<L><N>********************************\n" +
                    "<B>实付:￥PAY_AMOUNT\n" +
                    "\n" +
                    "<N>RECEIVER_ADDRESS\n" +
                    "RECEIVER_NAME:RECEIVER_MOBILE\n" +
                    "订单备注：REMARK;\n" +
                    "<C><HB>** 完**\n" ;
//                TODO
//                        "<L><N>QR_TITLE\n" +
//                        "<L><QR>QR_URL</QR>\n" ;
//                        "<R><N>条形码打印测试\n" +
//                        "<R><BARCODE>5842160392535156</BARCODE>"
            DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分");
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(omsOrder.getCreateTime().toInstant(), zoneId);
            String replace1 = content.replace("TITLE", item.getTitle())
                    .replace("CREATE_TIME", dateTimeFormatter.format(localDateTime))
                    .replace("ORDER_SN", omsOrder.getOrderSn())
                    .replace("PAY_AMOUNT", omsOrder.getPayAmount().toString())
                    .replace("TOTAL_AMOUNT", omsOrder.getTotalAmount().toString())
                    .replace("RECEIVER_ADDRESS", omsOrder.getReceiverProvince() + omsOrder.getReceiverCity()
                            + omsOrder.getReceiverRegion())
                    .replace("RECEIVER_NAME", omsOrder.getReceiverName())
                    .replace("RECEIVER_MOBILE", omsOrder.getReceiverPhone())
                    .replace("DISCOUNT","积分抵扣￥:-"+(omsOrder.getIntegrationAmount()==null?"0":omsOrder.getIntegrationAmount().toString()))
                    .replace("REMARK",omsOrder.getNote());
            StringBuilder sb = new StringBuilder();
            OmsOrderItemExample exampleItem = new OmsOrderItemExample();
            exampleItem.or().andOrderSnEqualTo(omsOrder.getOrderSn());
            List<OmsOrderItem> omsOrderItems = omsOrderItemMapper.selectByExample(exampleItem);

            omsOrderItems.forEach(item2->{
                sb.append(item2.getProductName())
                        .append("  x")
                        .append(item2.getProductQuantity())
                        .append("  ￥")
                        .append(item2.getProductPrice().toString())
                        .append("\n");
            });
            String replace2 = replace1.replace("PRODUCT_ITEM", sb.toString());
//                判断是否有二维码
            if(item.getPrinterQrUrl()!=null&&item.getPrinterQrUrl().length()>0){
                replace2+="<L><N>"+item.getPrinterQrTitle()+"\n"+
                        "<L><QR>"+item.getPrinterQrUrl()+"</QR>\n";
            }
            restRequest.setContent(replace2);
            log.info("请求[{}]",restRequest);
            ObjectRestResponse<String> printerResultObjectRestResponse = printService.print(restRequest);
            log.info("返回[{}]",printerResultObjectRestResponse);
            handResponse(printerResultObjectRestResponse);
        }catch (Exception e){
            log.error("!!!!!!!!!!!!!!!!!订单打印异常!!!!!!!!!!!!!!!!!",e);
        }
    }

    private void handResponse(ObjectRestResponse printerResultObjectRestResponse) {
        if(printerResultObjectRestResponse.getCode()!=0){
            throw new MyException(ExceptionEnum.UNKNOWN_ERROR.getCode(),printerResultObjectRestResponse.getMsg());
        }
    }
    private void initRequest(RestRequest request) {
        request.setUser(USER);
        request.setTimestamp( String.valueOf(System.currentTimeMillis()/1000));
        request.setSign(HashSignUtil.sign(USER+UserKEY+request.getTimestamp()));
    }
    @Override
    public Integer cancelTimeOutOrder() {
        Integer count=0;
        OmsOrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);
        //查询超时、未支付的订单及订单详情
        List<OmsOrderDetail> timeOutOrders = portalOrderDao.getTimeOutOrders(orderSetting.getNormalOrderOvertime());
        if (CollectionUtils.isEmpty(timeOutOrders)) {
            return count;
        }
        //修改订单状态为交易取消
        List<Long> ids = new ArrayList<>();
        for (OmsOrderDetail timeOutOrder : timeOutOrders) {
            ids.add(timeOutOrder.getId());
        }
        portalOrderDao.updateOrderStatus(ids, 4);
        for (OmsOrderDetail timeOutOrder : timeOutOrders) {
            //解除订单商品库存锁定
            portalOrderDao.releaseSkuStockLock(timeOutOrder.getOrderItemList());
            //修改优惠券使用状态
            updateCouponStatus(timeOutOrder.getCouponId(), timeOutOrder.getMemberId(), 0);
            //返还使用积分
            if (timeOutOrder.getUseIntegration() != null) {
                UmsMember member = memberService.getById(timeOutOrder.getMemberId());
                memberService.updateIntegration(timeOutOrder.getMemberId(), member.getIntegration() + timeOutOrder.getUseIntegration());
            }
        }
        return timeOutOrders.size();
    }

    @Override
    public void cancelOrder(Long orderId) {
        //查询未付款的取消订单
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andIdEqualTo(orderId).andStatusEqualTo(0).andDeleteStatusEqualTo(0);
        List<OmsOrder> cancelOrderList = orderMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(cancelOrderList)) {
            return;
        }
        OmsOrder cancelOrder = cancelOrderList.get(0);
        if (cancelOrder != null) {
            //修改订单状态为取消
            cancelOrder.setStatus(4);
            orderMapper.updateByPrimaryKeySelective(cancelOrder);
            OmsOrderItemExample orderItemExample = new OmsOrderItemExample();
            orderItemExample.createCriteria().andOrderIdEqualTo(orderId);
            List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
            //解除订单商品库存锁定
            if (!CollectionUtils.isEmpty(orderItemList)) {
                portalOrderDao.releaseSkuStockLock(orderItemList);
            }
            //修改优惠券使用状态
            updateCouponStatus(cancelOrder.getCouponId(), cancelOrder.getMemberId(), 0);
            //返还使用积分
            if (cancelOrder.getUseIntegration() != null) {
                UmsMember member = memberService.getById(cancelOrder.getMemberId());
                memberService.updateIntegration(cancelOrder.getMemberId(), member.getIntegration() + cancelOrder.getUseIntegration());
            }
        }
    }
    @Override
    public void cancelOrder(Long orderId, Long adminId) {
        UmsMember umsMember= memberService.getCurrentMember();
        OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
        if(!umsMember.getId().equals(order.getMemberId())){
            Asserts.fail("不能取消他人订单！");
        }
        //查询未付款的取消订单
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andIdEqualTo(orderId).andStatusEqualTo(0).andDeleteStatusEqualTo(0)
            .andAdminIdEqualTo(adminId);
        List<OmsOrder> cancelOrderList = orderMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(cancelOrderList)) {
            return;
        }
        OmsOrder cancelOrder = cancelOrderList.get(0);
        if (cancelOrder != null) {
            //修改订单状态为取消
            cancelOrder.setStatus(4);
            orderMapper.updateByPrimaryKeySelective(cancelOrder);
            OmsOrderItemExample orderItemExample = new OmsOrderItemExample();
            orderItemExample.createCriteria().andOrderIdEqualTo(orderId);
            List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
            //解除订单商品库存锁定
            if (!CollectionUtils.isEmpty(orderItemList)) {
                portalOrderDao.releaseSkuStockLock(orderItemList);
            }
            //修改优惠券使用状态
            updateCouponStatus(cancelOrder.getCouponId(), cancelOrder.getMemberId(), 0);
            //返还使用积分
            if (cancelOrder.getUseIntegration() != null) {
                UmsMember member = memberService.getById(cancelOrder.getMemberId());
                memberService.updateIntegration(cancelOrder.getMemberId(), member.getIntegration() + cancelOrder.getUseIntegration());
            }
        }
    }

    @Override
    public void sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间
        OmsOrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);
        long delayTimes = orderSetting.getNormalOrderOvertime() * 60 * 1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }

    @Override
    public void confirmReceiveOrder(Long orderId) {
        UmsMember member = memberService.getCurrentMember();
        OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
        if(!member.getId().equals(order.getMemberId())){
            Asserts.fail("不能确认他人订单！");
        }
        if(order.getStatus()!=2){
            Asserts.fail("该订单还未发货！");
        }
        order.setStatus(3);
        order.setConfirmStatus(1);
        order.setReceiveTime(new Date());
        orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public CommonPage<OmsOrderDetail> list(Integer status, Integer pageNum, Integer pageSize,Long adminId) {
        if(status==-1){
            status = null;
        }
        UmsMember member = memberService.getCurrentMember();
        PageHelper.startPage(pageNum,pageSize);
        OmsOrderExample orderExample = new OmsOrderExample();
        OmsOrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0)
                .andMemberIdEqualTo(member.getId())
                .andAdminIdEqualTo(adminId);
        if(status!=null&&status!=10){
            criteria.andStatusEqualTo(status);
        }else if(status!=null){
            Integer[] arr ={5,6,7,8};
            criteria.andStatusIn(Arrays.asList(arr));
        }
        orderExample.setOrderByClause("create_time desc");
        List<OmsOrder> orderList = orderMapper.selectByExample(orderExample);
        CommonPage<OmsOrder> orderPage = CommonPage.restPage(orderList);
        //设置分页信息
        CommonPage<OmsOrderDetail> resultPage = new CommonPage<>();
        resultPage.setPageNum(orderPage.getPageNum());
        resultPage.setPageSize(orderPage.getPageSize());
        resultPage.setTotal(orderPage.getTotal());
        resultPage.setTotalPage(orderPage.getTotalPage());
        if(CollUtil.isEmpty(orderList)){
            return resultPage;
        }
        //设置数据信息
        List<Long> orderIds = orderList.stream().map(OmsOrder::getId).collect(Collectors.toList());
        OmsOrderItemExample orderItemExample = new OmsOrderItemExample();
        orderItemExample.createCriteria().andOrderIdIn(orderIds);
        List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
        List<OmsOrderDetail> orderDetailList = new ArrayList<>();
        for (OmsOrder omsOrder : orderList) {
            OmsOrderDetail orderDetail = new OmsOrderDetail();
            BeanUtil.copyProperties(omsOrder,orderDetail);
            List<OmsOrderItem> relatedItemList = orderItemList.stream().filter(item -> item.getOrderId().equals(orderDetail.getId())).collect(Collectors.toList());
            orderDetail.setOrderItemList(relatedItemList);
            orderDetailList.add(orderDetail);
        }
        resultPage.setList(orderDetailList);
        return resultPage;
    }

    @Override
    public OmsOrderDetail detail(Long orderId) {
        OmsOrder omsOrder = orderMapper.selectByPrimaryKey(orderId);
        OmsOrderItemExample example = new OmsOrderItemExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(example);
        OmsOrderDetail orderDetail = new OmsOrderDetail();
        BeanUtil.copyProperties(omsOrder,orderDetail);
        orderDetail.setOrderItemList(orderItemList);
        return orderDetail;
    }

    @Override
    public void deleteOrder(Long orderId) {
        UmsMember member = memberService.getCurrentMember();
        OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
        if(!member.getId().equals(order.getMemberId())){
            Asserts.fail("不能删除他人订单！");
        }
        if(order.getStatus()==3||order.getStatus()==4){
            order.setDeleteStatus(1);
            orderMapper.updateByPrimaryKey(order);
        }else{
            Asserts.fail("只能删除已完成或已关闭的订单！");
        }
    }

    @Override
    public OmsOrder applyReturn(Long orderId) {
        UmsMember member = memberService.getCurrentMember();
        OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
        if(!member.getId().equals(order.getMemberId())){
            Asserts.fail("不能为他人申请退换！");
        }
        if(order.getStatus()==1||order.getStatus()==2||order.getStatus()==3){
//            退货处理中
            order.setStatus(5);
            orderMapper.updateByPrimaryKey(order);
        }else{
            Asserts.fail("只能为已支付的订单申请退换！");
        }
        return null;
    }

    /**
     * 生成18位订单编号:8位日期+2位平台号码+2位支付方式+6位以上自增id
     */
    private String generateOrderSn(OmsOrder order) {
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String key = REDIS_DATABASE+":"+ (REDIS_KEY_ORDER_ID==null?"":REDIS_KEY_ORDER_ID) + date;
        Long increment = redisService.incr(key, 1);
        sb.append(date);
        sb.append(String.format("%02d", order.getSourceType()==null?"":order.getSourceType()));
//        sb.append(String.format("%02d", order.getPayType()==null?"":order.getSourceType()));
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }

    /**
     * 删除下单商品的购物车信息
     */
    private void deleteCartItemList(List<CartPromotionItem> cartPromotionItemList, UmsMember currentMember,Long adminId) {
        List<Long> ids = new ArrayList<>();
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            ids.add(cartPromotionItem.getId());
        }
        cartItemService.delete(currentMember.getId(), ids,adminId);
    }

    /**
     * 计算该订单赠送的成长值
     */
    private Integer calcGiftGrowth(List<OmsOrderItem> orderItemList) {
        Integer sum = 0;
        for (OmsOrderItem orderItem : orderItemList) {
            sum = sum + orderItem.getGiftGrowth() * orderItem.getProductQuantity();
        }
        return sum;
    }

    /**
     * 计算该订单赠送的积分
     */
    private Integer calcGifIntegration(List<OmsOrderItem> orderItemList) {
        int sum = 0;
        for (OmsOrderItem orderItem : orderItemList) {
            sum += orderItem.getGiftIntegration() * orderItem.getProductQuantity();
        }
        return sum;
    }

    /**
     * 将优惠券信息更改为指定状态
     *
     * @param couponId  优惠券id
     * @param memberId  会员id
     * @param useStatus 0->未使用；1->已使用
     */
    private void updateCouponStatus(Long couponId, Long memberId, Integer useStatus) {
        if (couponId == null) return;
        //查询第一张优惠券
        SmsCouponHistoryExample example = new SmsCouponHistoryExample();
        example.createCriteria().andMemberIdEqualTo(memberId)
                .andCouponIdEqualTo(couponId).andUseStatusEqualTo(useStatus == 0 ? 1 : 0);
        List<SmsCouponHistory> couponHistoryList = couponHistoryMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(couponHistoryList)) {
            SmsCouponHistory couponHistory = couponHistoryList.get(0);
            couponHistory.setUseTime(new Date());
            couponHistory.setUseStatus(useStatus);
            couponHistoryMapper.updateByPrimaryKeySelective(couponHistory);
        }
    }

    private void handleRealAmount(List<OmsOrderItem> orderItemList) {
        for (OmsOrderItem orderItem : orderItemList) {
            //原价-促销优惠-优惠券抵扣-积分抵扣
            BigDecimal realAmount = orderItem.getProductPrice()
                    .subtract(orderItem.getPromotionAmount())
                    .subtract(orderItem.getCouponAmount())
                    .subtract(orderItem.getIntegrationAmount());
            orderItem.setRealAmount(realAmount);
        }
    }

    /**
     * 获取订单促销信息
     */
    private String getOrderPromotionInfo(List<OmsOrderItem> orderItemList) {
        StringBuilder sb = new StringBuilder();
        for (OmsOrderItem orderItem : orderItemList) {
            sb.append(orderItem.getPromotionName());
            sb.append(";");
        }
        String result = sb.toString();
        if (result.endsWith(";")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 计算订单应付金额
     */
    private BigDecimal calcPayAmount(OmsOrder order) {
        //总金额+运费-促销优惠-优惠券优惠-积分抵扣
        BigDecimal payAmount = order.getTotalAmount()
                .add(order.getFreightAmount())
                .subtract(order.getPromotionAmount())
                .subtract(order.getCouponAmount())
                .subtract(order.getIntegrationAmount());
        return payAmount;
    }

    /**
     * 计算订单优惠券金额
     */
    private BigDecimal calcIntegrationAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal integrationAmount = new BigDecimal(0);
        for (OmsOrderItem orderItem : orderItemList) {
            if (orderItem.getIntegrationAmount() != null) {
                integrationAmount = integrationAmount.add(orderItem.getIntegrationAmount().multiply(new BigDecimal(orderItem.getProductQuantity())));
            }
        }
        return integrationAmount;
    }

    /**
     * 计算订单优惠券金额
     */
    private BigDecimal calcCouponAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal couponAmount = new BigDecimal(0);
        for (OmsOrderItem orderItem : orderItemList) {
            if (orderItem.getCouponAmount() != null) {
                couponAmount = couponAmount.add(orderItem.getCouponAmount().multiply(new BigDecimal(orderItem.getProductQuantity())));
            }
        }
        return couponAmount;
    }

    /**
     * 计算订单活动优惠
     */
    private BigDecimal calcPromotionAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal promotionAmount = new BigDecimal(0);
        for (OmsOrderItem orderItem : orderItemList) {
            if (orderItem.getPromotionAmount() != null) {
                promotionAmount = promotionAmount.add(orderItem.getPromotionAmount().multiply(new BigDecimal(orderItem.getProductQuantity())));
            }
        }
        return promotionAmount;
    }

    /**
     * 获取可用积分抵扣金额
     *
     * @param useIntegration 使用的积分数量
     * @param totalAmount    订单总金额
     * @param currentMember  使用的用户
     * @param hasCoupon      是否已经使用优惠券
     */
    private BigDecimal getUseIntegrationAmount(Integer useIntegration, BigDecimal totalAmount, UmsMember currentMember, boolean hasCoupon) {
        BigDecimal zeroAmount = new BigDecimal(0);
        //判断用户是否有这么多积分
        if (useIntegration.compareTo(currentMember.getIntegration()) > 0) {
            return zeroAmount;
        }
        //根据积分使用规则判断是否可用
        //是否可与优惠券共用
        UmsIntegrationConsumeSetting integrationConsumeSetting = integrationConsumeSettingMapper.selectByPrimaryKey(1L);
        if (hasCoupon && integrationConsumeSetting.getCouponStatus().equals(0)) {
            //不可与优惠券共用
            return zeroAmount;
        }
        //是否达到最低使用积分门槛
        if (useIntegration.compareTo(integrationConsumeSetting.getUseUnit()) < 0) {
            return zeroAmount;
        }
        //是否超过订单抵用最高百分比
        BigDecimal integrationAmount = new BigDecimal(useIntegration).divide(new BigDecimal(integrationConsumeSetting.getUseUnit()), 2, RoundingMode.HALF_EVEN);
        BigDecimal maxPercent = new BigDecimal(integrationConsumeSetting.getMaxPercentPerOrder()).divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN);
        if (integrationAmount.compareTo(totalAmount.multiply(maxPercent)) > 0) {
            return zeroAmount;
        }
        return integrationAmount;
    }

    /**
     * 对优惠券优惠进行处理
     *
     * @param orderItemList       order_item列表
     * @param couponHistoryDetail 可用优惠券详情
     */
    private void handleCouponAmount(List<OmsOrderItem> orderItemList, SmsCouponHistoryDetail couponHistoryDetail) {
        SmsCoupon coupon = couponHistoryDetail.getCoupon();
        if (coupon.getUseType().equals(0)) {
            //全场通用
            calcPerCouponAmount(orderItemList, coupon);
        } else if (coupon.getUseType().equals(1)) {
            //指定分类
            List<OmsOrderItem> couponOrderItemList = getCouponOrderItemByRelation(couponHistoryDetail, orderItemList, 0);
            calcPerCouponAmount(couponOrderItemList, coupon);
        } else if (coupon.getUseType().equals(2)) {
            //指定商品
            List<OmsOrderItem> couponOrderItemList = getCouponOrderItemByRelation(couponHistoryDetail, orderItemList, 1);
            calcPerCouponAmount(couponOrderItemList, coupon);
        }
    }

    /**
     * 对每个下单商品进行优惠券金额分摊的计算
     *
     * @param orderItemList 可用优惠券的下单商品商品
     */
    private void calcPerCouponAmount(List<OmsOrderItem> orderItemList, SmsCoupon coupon) {
        BigDecimal totalAmount = calcTotalAmount(orderItemList);
        for (OmsOrderItem orderItem : orderItemList) {
            //(商品价格/可用商品总价)*优惠券面额
            BigDecimal couponAmount = orderItem.getProductPrice().divide(totalAmount, 3, RoundingMode.HALF_EVEN).multiply(coupon.getAmount());
            orderItem.setCouponAmount(couponAmount);
        }
    }

    /**
     * 获取与优惠券有关系的下单商品
     *
     * @param couponHistoryDetail 优惠券详情
     * @param orderItemList       下单商品
     * @param type                使用关系类型：0->相关分类；1->指定商品
     */
    private List<OmsOrderItem> getCouponOrderItemByRelation(SmsCouponHistoryDetail couponHistoryDetail, List<OmsOrderItem> orderItemList, int type) {
        List<OmsOrderItem> result = new ArrayList<>();
        if (type == 0) {
            List<Long> categoryIdList = new ArrayList<>();
            for (SmsCouponProductCategoryRelation productCategoryRelation : couponHistoryDetail.getCategoryRelationList()) {
                categoryIdList.add(productCategoryRelation.getProductCategoryId());
            }
            for (OmsOrderItem orderItem : orderItemList) {
                if (categoryIdList.contains(orderItem.getProductCategoryId())) {
                    result.add(orderItem);
                } else {
                    orderItem.setCouponAmount(new BigDecimal(0));
                }
            }
        } else if (type == 1) {
            List<Long> productIdList = new ArrayList<>();
            for (SmsCouponProductRelation productRelation : couponHistoryDetail.getProductRelationList()) {
                productIdList.add(productRelation.getProductId());
            }
            for (OmsOrderItem orderItem : orderItemList) {
                if (productIdList.contains(orderItem.getProductId())) {
                    result.add(orderItem);
                } else {
                    orderItem.setCouponAmount(new BigDecimal(0));
                }
            }
        }
        return result;
    }

    /**
     * 获取该用户可以使用的优惠券
     *
     * @param cartPromotionItemList 购物车优惠列表
     * @param couponId              使用优惠券id
     */
    private SmsCouponHistoryDetail getUseCoupon(List<CartPromotionItem> cartPromotionItemList, Long couponId) {
        List<SmsCouponHistoryDetail> couponHistoryDetailList = memberCouponService.listCart(cartPromotionItemList, 1);
        for (SmsCouponHistoryDetail couponHistoryDetail : couponHistoryDetailList) {
            if (couponHistoryDetail.getCoupon().getId().equals(couponId)) {
                return couponHistoryDetail;
            }
        }
        return null;
    }

    /**
     * 计算总金额
     */
    private BigDecimal calcTotalAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OmsOrderItem item : orderItemList) {
            totalAmount = totalAmount.add(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        return totalAmount;
    }

    /**
     * 锁定下单商品的所有库存
     */
    private void lockStock(List<CartPromotionItem> cartPromotionItemList) {
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            PmsSkuStock skuStock = skuStockMapper.selectByPrimaryKey(cartPromotionItem.getProductSkuId());
            if(skuStock!=null){
                skuStock.setLockStock(skuStock.getLockStock() + cartPromotionItem.getQuantity());
                skuStockMapper.updateByPrimaryKeySelective(skuStock);
            }

        }
    }

    /**
     * 判断下单商generateConfirmOrder品是否都有库存
     */
    private boolean hasStock(List<CartPromotionItem> cartPromotionItemList) {
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            if (cartPromotionItem.getRealStock()!=null&&cartPromotionItem.getRealStock() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算购物车中商品的价格
     */
    @Override
    public ConfirmOrderResult.CalcAmount calcCartAmount(BigDecimal totalAmount, Integer integration, Long adminId) {
        if(integration==null){
            integration=0;
        }
        UmsIntegrationConsumeSettingExample example =new UmsIntegrationConsumeSettingExample();
        example.or().andAdminIdEqualTo(adminId);
        List<UmsIntegrationConsumeSetting> umsIntegrationConsumeSettings = integrationConsumeSettingMapper.selectByExample(example);
        UmsIntegrationConsumeSetting integrationConsumeSetting = umsIntegrationConsumeSettings.get(0);
        ConfirmOrderResult.CalcAmount calcAmount = new ConfirmOrderResult.CalcAmount();
        calcAmount.setFreightAmount(new BigDecimal(0));
//        BigDecimal totalAmount = new BigDecimal("0");
//        BigDecimal promotionAmount = new BigDecimal("0");
        BigDecimal integrationAmout = new BigDecimal("0");

        //        开始计算积分

        if(integrationConsumeSetting.getCouponStatus()!=2){
            calcAmount.setAllowUseIntegrationAmount(true);
//            最大允许使用金额
            BigDecimal maxAmout = totalAmount.divide(new BigDecimal(100)).multiply(new BigDecimal(integrationConsumeSetting.getMaxPercentPerOrder())).setScale(2, BigDecimal.ROUND_DOWN);
//            最大拥有能抵扣金额
            BigDecimal maxHave = new BigDecimal(integration).divide(new BigDecimal(integrationConsumeSetting.getDeductionPerAmount()));
            if(maxHave.compareTo(maxAmout)>0){
                integrationAmout = maxAmout;
                calcAmount.setUseIntegration(integrationAmout.multiply(new BigDecimal(integrationConsumeSetting.getDeductionPerAmount())).setScale(0,BigDecimal.ROUND_UP).intValue());
                calcAmount.setIntegrationAmount(integrationAmout);
            }else{
                calcAmount.setUseIntegration(integration);
                integrationAmout= new BigDecimal(integration).divide(new BigDecimal(integrationConsumeSetting.getDeductionPerAmount())).setScale(2, BigDecimal.ROUND_DOWN);
                calcAmount.setIntegrationAmount(integrationAmout);
            }
        }else {

        }

        calcAmount.setTotalAmount(totalAmount);
//        calcAmount.setPromotionAmount(promotionAmount);
        calcAmount.setPayAmount(totalAmount.subtract(integrationAmout));

        return calcAmount;
    }

}
