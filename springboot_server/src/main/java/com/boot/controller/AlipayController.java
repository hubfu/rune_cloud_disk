package com.boot.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.boot.config.AliPayConfig;
import com.boot.entity.*;
import com.boot.service.AdminService;
import com.boot.service.AliPayService;
import com.boot.utils.UploadFileUtils;

import com.boot.utils.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Controller
@Slf4j
@RequestMapping("/user")
public class AlipayController {
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private WebSocket webSocket;
    @Autowired
    AliPayService aliPayService;
    @Autowired
    AdminService adminService;
    @Value("${alipay.callback.url}")
    String callbackUrl;

    /**
     * 支付宝扫码支付
     * @param payUser
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("/alipay")
    @ResponseBody
    public CommonResult aliPay(@RequestBody PayUser payUser) throws AlipayApiException {
        log.info("payUser"+payUser);
        Combo combo = new Combo();
        combo = aliPayService.selectACombo(payUser.getComboID());
        StatisticsInfo statisticsInfo=new StatisticsInfo();
        statisticsInfo=adminService.getStatisticsInfo();
        if (statisticsInfo.getUsablePatitionSpace()<combo.getAmount()*1024*1024*1024L){
            return new CommonResult(CommonResult.FAIL,"系统繁忙，请稍后再试");
        }

//        常见api对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(callbackUrl+payUser.getUserID()+"/"+payUser.getComboID());
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        String newTradeNo = UploadFileUtils.getFileName();
        model.setOutTradeNo(newTradeNo);
        model.setTotalAmount(combo.getAmount().toString());
        model.setSubject(combo.getName());
        model.setStoreId("符文云盘");
        model.setTimeoutExpress("90m");
        request.setBizModel(model);
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            log.info("调用成功");
            return new CommonResult(CommonResult.SUCCESS,response.getQrCode(),"调用成功");
//            return response.getQrCode();

        } else {
            log.info("调用失败");
            return new CommonResult(CommonResult.FAIL,"系统繁忙，请稍后再试");
        }


    }

    /**
     * 结果回调
     * @param request
     * @param response
     * @param returnPayBean
     * @param userid
     * @param comcoid
     * @throws IOException
     */
    @RequestMapping("/call/{userid}/{comboid}")
    public void call(HttpServletRequest request, HttpServletResponse response, AliReturnPayBean returnPayBean,@PathVariable("userid") String  userid,@PathVariable("comboid") Integer comcoid) throws IOException {
        response.setContentType("type=text/html;charset=UTF-8");
        log.info("支付宝的回调函数被调用");
        if (!AliPayConfig.checkSign(request)) {
            log.info("验签失败");

            return;
        }
        if (returnPayBean == null) {
            log.info("支付宝的returnPay返回为空");

            return;
        }
        log.info("支付宝的returnPay" + returnPayBean.toString());

        if (("TRADE_SUCCESS").equals(returnPayBean.getTrade_status())) {

            log.info("支付宝的支付状态为TRADE_SUCCESS");
//            业务处理逻辑
            Combo combo = new Combo();
            combo = aliPayService.selectACombo(comcoid);
            if (aliPayService.expansionDepot(userid, combo.getCapacity())) {
                Consume consume = new Consume();
                consume.setBuyTime(new Date());
                consume.setUserId(userid);
                consume.setOutTradeNo(returnPayBean.getOut_trade_no());
                consume.setTotalAmount(returnPayBean.getTotal_amount());
                consume.setSubject(combo.getName());
                consume.setStoreId("符文云盘");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(consume.getBuyTime());
                calendar.add(Calendar.MONTH, combo.getTime());
                consume.setExpireDate(calendar.getTime());
                consume.setComboId(combo.getId());
                if (aliPayService.insertAConsume(consume)) {
                    aliPayService.addComboSum(combo);
                    webSocket.sendMessage("true");
                }
            }

            log.info("信息已发送");
        }


    }

    /**
     * 查询所有的套餐
     * @return
     */
    @ResponseBody
    @GetMapping("/combo")
    public CommonResult getAllCombo(){
        log.info("访问成功");
        List<Combo> combos=new ArrayList<>();
        combos=aliPayService.selectAllCombo();
        log.info("list:"+combos+"=====size:"+combos.size());
        List<ComboInfo> comboInfos=new ArrayList<>();
       for (int i=0;i<combos.size();i++){
           ComboInfo comboInfo=new ComboInfo();
           comboInfo.toConvert(combos.get(i));
         comboInfos.add(comboInfo);
       }
       log.info("info:"+comboInfos.toString());
        if (combos.size()>0){
            return new CommonResult(CommonResult.SUCCESS,comboInfos,"查找成功");

        }
        return new CommonResult(CommonResult.SUCCESS,"查找失败");
    }


}
