package sms.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Service;
import sms.demo.service.SendSms;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SendSmsImpl
 * @Description: TODO
 * @Author 有丶优秀的少年
 * @Date 2020/5/7
 * @Version V1.0
 **/
@Service
public class SendSmsImpl implements SendSms {
    @Override
    public boolean send(String phoneNumbers, String templateCode, Map<String, Object> code) {
        //连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");
        IAcsClient client = new DefaultAcsClient(profile);
        //构建请求
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");//不要动
        request.setVersion("2017-05-25");//不要动
        request.setAction("SendSms");
        //自定义参数 手机号 验证码 签名 模板
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", "雅丹科技");
        request.putQueryParameter("TemplateCode", templateCode);
        //构建一个验证码
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(code));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
