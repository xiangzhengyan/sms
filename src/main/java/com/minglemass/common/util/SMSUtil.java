/**
 * 
 */
package com.minglemass.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * Created by xiangzy on 2017年1月9日
 * 
 */
public class SMSUtil {

	private static String SMS_TYPE = "normal";
	private static String SMS_SIGN_NAME = "智慧污水厂";


	private static TaobaoClient getClient() {

		Properties ps = new Properties();
		try {
			InputStream in = SMSUtil.class.getResourceAsStream("/config.properties");
			ps.load(in);
			String url = ps.getProperty("url");
			String appkey = ps.getProperty("appkey");
			String secret = ps.getProperty("secret");
			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			return client;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 原始接口
	 */
	private static AlibabaAliqinFcSmsNumSendResponse send(String templateCode, String paramString, String phoneNum)
			throws ApiException {
		TaobaoClient client = getClient();
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType(SMS_TYPE);
		req.setSmsFreeSignName(SMS_SIGN_NAME);
		req.setSmsParamString(paramString);
		req.setRecNum(phoneNum);
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		return rsp;

	}

	/**
	 * 方便调用接口
	 */
	public static boolean send(String templateCode, Map<String, String> params, Set<String> phones){
		Gson gson = new Gson();
		String paramString = gson.toJson(params);
		String phoneString = "";
		for (String p : phones) {
			if (!phoneString.equals("")) {
				phoneString += ",";
			}
			phoneString += p;
		}
		AlibabaAliqinFcSmsNumSendResponse req;
		try {
			req = send(templateCode, paramString, phoneString);
			System.out.println(req.getBody());
			return req.isSuccess();
		} catch (ApiException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean send(String templateCode, Map<String, String> params, String phones) {
		Gson gson = new Gson();
		String paramString = gson.toJson(params);
		AlibabaAliqinFcSmsNumSendResponse req;
		try {
			req = send(templateCode, paramString, phones);

			return req.isSuccess();
		} catch (ApiException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 远程控制验证码 您在通过远程${opt}${deviceName}，如果是本人操作，请输入验证码：${code}
	 */
	public static boolean sendRemoteControlCode(String phone,String opt, String deviceName, String code) {
		Map<String, String> params = new HashMap<String, String>();

		params.put("opt", opt);
		params.put("deviceName", deviceName);
		params.put("code", code);
		return send(TCode.REMOTE_CONTROL_CODE, params, phone);
	}

	/**
	 * 用户${userName}正在通过远程对设备${deviceName}进行${opt}操作，若同意此次操作，请登录系统输入验证码：${code}
	 */
	public static boolean sendRemoteControlApproveCode(String phone,String username,String opt, String deviceName, String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", username);
		params.put("deviceName", deviceName);
		params.put("opt", opt);
		params.put("code", code);
		return send(TCode.REMOTE_CONTROL_APPROVE_CODE, params, phone);
	}
	/**
	 * 调用视频监控验证   您在调用视频监控，验证码：${code}
	 */
	public static boolean sendViewVideoCode(String phone,String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		return send(TCode.VIEW_VIDEO_CODE, params, phone);
	}
	/**
	 * 修改手机号验证   用户${username}将更换手机号为${phone}，验证码：${code}
	 */
	public static boolean sendAlterPhoneCode(String phone,String username,String newPhone,String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		params.put("username", username);
		params.put("phone", newPhone);
		return send(TCode.ALTER_PHONE_CODE, params, phone);
	}
	/**
	 * 绑定手机号验证  用户${username}将绑定您的手机号，验证码：${code}
	 */
	public static boolean sendBindingPhoneCode(String phone,String username,String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		params.put("username", username);
		return send(TCode.BINDING_PHONE_CODE, params, phone);
	}
	/**
	 * 重设密码验证   重新设置密码验证码：${code}
	 */
	public static boolean sendAlterPasswordCode(String phone,String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		return send(TCode.ALTER_PASSWORD_CODE, params, phone);
	}
	/**
	 * 登录验证 	登录验证码：${code}
	 */
	public static boolean sendLoginCode(String phone,String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		return send(TCode.LOGIN_CODE, params, phone);
	}
	/**
	 * 事件发生提醒 		污水厂发生紧急事件，污水厂：${plantName}，事件名称：${eventName}，事件编号：${eventId}
	 */
	public static boolean sendEventHappenNote(Set<String> phone,String plantName,String eventName,long eventId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("plantName", plantName);
		params.put("eventName", eventName);
		params.put("eventId", eventId+"");
		return send(TCode.EVENT_HAPPEN_NOTE, params, phone);
	}
	/**
	 * 排班调整提醒	您的排班进行了调整，请登录系统查看
	 */
	public static boolean sendDutyNotificationNote(String phone) {
		Map<String, String> params = new HashMap<String, String>();
		return send(TCode.DUTY_NOTIFICATION_NOTE, params, phone);
	}
	/**
	 * 事件分配提醒		有一个事件需要您处理，事件名称：${eventName}，事件编号：${eventId}
	 */
	public static boolean sendEventAssignNote(String phone,String eventName,long eventId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("eventName", eventName);
		params.put("eventId", eventId+"");
		return send(TCode.EVENT_ASSIGN_NOTE, params, phone);
	}
	/**
	 * 报警提醒    报警信息：${plantName}报警：${alarmMsg}
	 */
	public static boolean sendAlarmNotificationNote(String phone,String plantName,String alarmMsg) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("plantName", plantName);
		params.put("alarmMsg",alarmMsg);
		return send(TCode.ALARM_NOTIFICATION_NOTE, params, phone);
	}

}
