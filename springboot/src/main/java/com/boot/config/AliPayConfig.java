package com.boot.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//支付宝接口配置类
@Configuration
public class AliPayConfig {
    // 请填写您的AppId，例如：2019091767145019（必填）
    private static final String appID = "2021000119619959";
    //应用私钥，这里修改生成的私钥即可（必填）
    private static final String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCLmr2VcsBNhRXTlCw21ryLgQuGZjVQdIrcUAzV3+Mk+eTaSNQ7Cr8sduhcT/+ncsBRoOeShbrf/NJ0FN72dDYrUMSvm8Hepr7E15FjVVgfBgmyldFZRQWL1KArxlqc/Ffus7Nic2jkM0G9Oj9zhLQbCEjuCEts1XYBYqqMDgHyi6FKqmloerFIS6ZnUU98+APwQ31vX8sTbGNkpdx6QZQ5mQbxVNl+BOLDoCByJ2fmQMyaHcAADKNJqAOGLMraFu900hOtToJO6717WlvtFPSnOXy/DaVENS2GAHUuUIy8lgwADxPRZLnH0cj3Xt3B0l1IAM2qMocp3AZGaqipipT5AgMBAAECggEABW16uXjAqULnWlaEcY1fvzzBKfSHVh1x2Kb0GMyflCxzDVmPhswgKqZk2lHx6py3hXTwC+5PGa5GsGSvVn2mLYDyZLF1lCZ/fD+1U1taFbqyXdMdVI8ubAVURN9tXnUhJhKugsFh7p1fjO/az671IafszfRcmlqiP+OKJe/Ve4yLP0mT/j7dw+KPjH37K4TqVbExIbDgWpdwdn82/LJVCFjSaU9fgRQ9CD3VZwGgJC/6QOGBqX5e52qE/DOdWtC7BWaWrhN+VGPjL+O+a3woMrF17br/E67Pg31sHQoAOqpJhc3tzxCQZqZUww8FnXlEsMQxLhJqTQPun8242PnMkQKBgQDkBdwkyMwjaZRrEuSkDc95btVSp0AAy6rmSuEvWDUTm/VVOvLR1321Zw/D2KTr22ekVTZFlRBUb+tHXhPnRZvOldGA6mZyI2N+ESaCOAPvrdIJZoZd/nzYl8DhkXIDTxRM1rHKPdmgFjY5dgnwxfFFFRE3UzPQ8TbWhcU5DrDRHwKBgQCcu64wjBqo6/H2s+tMCXkiJ9uZApj0ITIcdDl5s2Xy4izcf/MHNARaUwhcHQX8doqLcr47BU5AmuZ9l1vY4wpqmoUv1MXcvZhdlBsafPIbMYBODUiA39qSlvINicSwV/RDxid5TPmsDvjbUlVCiQ18dyre2nOfXwBzS6nKj7ve5wKBgQDE5oqC+SHOMo2HKHlncMWIBr0O8n3CMh0RX/ez+mda14GyZ4Tmzz4uNyDV7W38fSmyLiL/UwkW4M46gnKNcv5oFdcji0WLqiNv6T9nkxdTELYeh/nfkLIeFmzoW8AoO1E8SOidy2rxYjzQwa0zzLna/aYwMBi+hIzmEf2Ml7L34QKBgDYpKPV/xMqw1ilxXRMmhuSFr339hst+GSn6A86JbuZywBvmXx4THJn17YKfSO1ILXIo0ZHlsHfkz1eBEW0ub/AMu4+e0E0Ke3eC8abCnItHHNZcArz+HjLf1H9XJ0/k5PTyqbtiHTkW52oq1mmqoK1tJLRj+uaM1sQA1oi4CKzrAoGARQ7nAEYFUzkTFNQQJL68e/luedsrWZc7/cPlrr9tghRGewSxRAyUawCtzZhC4W7+i6KTupugkECs449YRyzVK+bggC5Pq1TSko7Di6sI0W4Mc+r4lbBwndjeLI5J9xFZcy9v1+vKVfMausUydkqPyeOWWACJziHJ3qGpKr7YlYQ=";
    //支付宝公钥，而非应用公钥（必填）
    private static  final  String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjEMpukvxIrQH5KQIPPHjDs9+PjIsKJKGS8oqmOjhv6gtE2heXAAdAAAGTHDsCvFBLBomQcODqmmzIavW5OPq+jAWR1dAqrsMyX3GFQvetuCpGGljmdeYTtpr3dulCyZR6AadstAordIHJ2ZkLwg+tH13rAVZ0zS0m8Kn4kYaTi5XkIAXib4ATlwlwgfRLc6i7i+Qbekmmp3ehb4/aDyzMXPTazHtzp9cpYJKqaakyy46vWXlWsqIICDwPSPVAovC+JpwsBflc6KODRf37RM1pJDmq6wGsISsR0XvItwvlbDZu6T7QbSZ8e8m6kg6Ihj8ljcuy0sx+cKlDhln1NH3nwIDAQAB";
    //默认即可（必填）
    public static final String charset = "utf-8";
    //默认即可（必填）
    public static final String signType = "RSA2";
    @Bean
    public AlipayClient alipayClient(){
        //沙箱环境使用https://openapi.alipaydev.com/gateway.do，线上环境使用https://openapi.alipay.com/gateway.do
        return new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", appID, privateKey, "json", charset, publicKey, signType);
    }
    /**
     * 验签，是否正确
     */
    public static boolean checkSign(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();
        requestMap.forEach((key, values) -> {
            String strs = "";
            for(String value : values) {
                strs = strs + value;
            }
            System.out.println(key +"===>"+strs);
            paramsMap.put(key, strs);
        });
        System.out.println();
        //调用SDK验证签名
        try {
            return  AlipaySignature.rsaCheckV1(paramsMap, AliPayConfig.publicKey, AliPayConfig.charset, AliPayConfig.signType);
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("*********************验签失败********************");
            return false;
        }

    }

}
