package com.sg.sgapibackend.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.sg.sgapibackend.service.InterfaceInfoService;
import com.sg.sgapibackend.service.UserService;
import com.sg.sgapibackend.utils.MailUtils;
import com.sg.sgapibackend.utils.RedissonLockUtil;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class MessageJob {

    @Autowired
    RedissonLockUtil redissonLockUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private InterfaceInfoService interfaceInfoService;

    @Autowired
    MailUtils mailUtils;

    /**
     * 每天晚上四点发送一份运行报告到管理员邮箱
     * 秒、分、时、日、月和星期
     */
    @Scheduled(cron = "00 00 8 * * *")
    public void cleanFile(){
        redissonLockUtil.redissonDistributedLocks("lock:cleanFile", () -> {
            log.info("向管理员发送邮箱====>");
            sendAdminEmail();
            // 替换参数
        });
    }
    
    public void sendAdminEmail(){
        try {
            String content = mailUtils.loadEmailTemplate("static/email/adminMsgEmail.html");
            Long userCount = userService.count();
            Long interfaceInfoCount = interfaceInfoService.getInterfaceInfoTotalInvokesCount();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = now.format(formatter);
            // 替换参数
            content = mailUtils.populateTemplate(content, userCount.toString(),interfaceInfoCount.toString(),formattedTime);
            // 发送邮件
            mailUtils.sendMail("717055919@qq.com", content, "【SgApi开放平台】运行通知");
        } catch (IOException | MessagingException e) {
            throw new RuntimeException("邮箱发送失败", e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
