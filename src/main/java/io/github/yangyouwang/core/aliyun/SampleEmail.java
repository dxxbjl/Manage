package io.github.yangyouwang.core.aliyun;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import io.github.yangyouwang.core.exception.CrudException;
import io.github.yangyouwang.core.properties.MailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangyouwang
 * @title: SampleEmail
 * @projectName crud
 * @description: 简单发送邮件工具类
 * @date 2020/7/6下午11:21
 */
@Component
@Slf4j
public class SampleEmail {

    private final IAcsClient iAcsClient;
    private final MailProperties mailProperties;
    @Autowired
    public SampleEmail(IAcsClient iAcsClient, MailProperties mailProperties) {
        this.iAcsClient = iAcsClient;
        this.mailProperties = mailProperties;
    }


    /**
     * 发送验证码
     *
     * @param toAddress 邮件地址
     * @param title     标题
     * @param body      内容
     * @return SingleSendMailResponse
     */
    public SingleSendMailResponse sample(final String toAddress, final String title, final String body) {
        final SingleSendMailRequest request = buildRequest(toAddress, title, body);
        try {
            //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            SingleSendMailResponse httpResponse = iAcsClient.getAcsResponse(request);
            return httpResponse;
        } catch (ClientException e) {
            //捕获错误异常码
            log.error("ErrCode :{}", e.getErrCode());
            log.error("StackTrace: ", e);
            throw new CrudException(e.getMessage());
        }
    }

    private SingleSendMailRequest buildRequest(String toAddress, String title, String body) {
        SingleSendMailRequest request = new SingleSendMailRequest();
        //request.setVersion(“2017-06-22”);// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
        request.setAccountName(mailProperties.getAccountName());
        request.setFromAlias(mailProperties.getSendPersonName());
        request.setAddressType(1);
        request.setTagName(mailProperties.getTagName());
        request.setReplyToAddress(true);
        request.setToAddress(toAddress);
        //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
        //request.setToAddress(“邮箱1,邮箱2”);
        request.setSubject(title);
        //如果采用byte[].toString的方式的话请确保最终转换成utf-8的格式再放入htmlbody和textbody，若编码不一致则会被当成垃圾邮件。
        //注意：文本邮件的大小限制为3M，过大的文本会导致连接超时或413错误
        request.setHtmlBody(body);
        //SDK 采用的是http协议的发信方式, 默认是GET方法，有一定的长度限制。
        //若textBody、htmlBody或content的大小不确定，建议采用POST方式提交，避免出现uri is not valid异常
        request.setMethod(MethodType.POST);
        return request;
    }
}