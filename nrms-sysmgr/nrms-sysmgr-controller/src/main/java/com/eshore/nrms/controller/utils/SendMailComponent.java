package com.eshore.nrms.controller.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.vo.InitData;

@Component
public class SendMailComponent {

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * 发送邮件
	 * 
	 * @param subject:邮件主题
	 * @param contex：邮件内容
	 * @param sendTo：邮件接收者
	 */
	public void send(final String subject, final String contex, final String sendTo) {
		new Thread() {
			List<Param> mailConfig = InitData.paramListTable.get("mail_config");
			Param param = mailConfig.get(0);

			public void run() {
				SimpleMailMessage mail = new SimpleMailMessage();
				if (param != null && param.getParamValue().equals("1")) {
					// 需要邮件发送
					try {
						mail.setTo(sendTo);
						mail.setFrom("hust_hc@sina.com");
						mail.setSubject(subject);
						mail.setText(contex);
						if (mailSender != null) {
							mailSender.send(mail);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}
		}.start();

	}
}
