package sms.demo.controller;

import com.aliyuncs.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sms.demo.service.SendSms;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SmsApiController
 * @Description: TODO
 * @Author 有丶优秀的少年
 * @Date 2020/5/7
 * @Version V1.0
 **/
@RestController
@CrossOrigin
public class SmsApiController {

    @Autowired
    private SendSms sendSms;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/send/{phone}")
    public  String code(@PathVariable("phone") String phone){
        //redis
       String code= redisTemplate.opsForValue().get(phone);
       if (!StringUtils.isEmpty(code)){
           return phone+":"+code+"已存在，还没有过期";
       }
       //生成验证码并存到redis里
       // code=UUID.randomUUID().toString().substring(0,4);
       code=String.format("%04d",new Random().nextInt(9999));
        HashMap<String,Object> param=new HashMap<>();
        param.put("code",code);
        boolean isSend=sendSms.send(phone,"SMS_189523260",param);
        if (isSend){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return phone+":"+code+"发送成功";
        }else {
            return "发送失败";
        }
    }

}
