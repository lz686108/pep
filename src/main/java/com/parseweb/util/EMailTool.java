package com.parseweb.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

/**
 *  
 * 
 * @author ben
 *
 */
public class EMailTool {

	private static String EMAIL_ACCOUNT = "m18943272155";

	private static String EMAIL_PASSWORD = "fssteel100360";

	private static String SMTP_SERVER = "smtp.163.com";

	private static String SENDER = "m18943272155@163.com";

	private static String SENDER_NAME = "抚顺新钢铁";

	private List<String> receivers = new ArrayList<>();

	public EMailTool() {
		
	}
	
	public static EMailTool newInstance() {
		return new EMailTool();
	}

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}
	
	public void addReceiver(String receiver) {
		receivers.add(receiver);
	}

	public void sendQAReport(String filePath, String fileName, String title) throws EmailException {
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(filePath);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(fileName);
		attachment.setName(fileName);

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(SMTP_SERVER);
		email.setAuthentication(EMAIL_ACCOUNT, EMAIL_PASSWORD);
		for (String receiver : receivers) {
			email.addTo(receiver, receiver);
		}
		email.setFrom(SENDER, SENDER_NAME);
		email.setSubject(title);
		email.setMsg(title);
		
		email.attach(attachment);
		email.send();
	}

	public static void main(String[] args) throws EmailException {
		EMailTool email = EMailTool.newInstance();
		email.addReceiver("wuwenbin@ejianlong.com");
		email.sendQAReport("testdata/report.xls", "report.xls", "Send QA Report from Timer");		
	}

}
