/**
 * 
 */
package sms;

import java.util.HashSet;
import java.util.Set;

import com.minglemass.common.util.SMSUtil;
import com.taobao.api.ApiException;

/**
 * Created by xiangzy on 2017年1月11日
 * 
 */
public class Test {
	public static void main(String[] args) throws ApiException {
//		SMSUtil.sendRemoteControlCode("13348840080", "打开", "1#搅拌机", "1245");
		SMSUtil.sendRemoteControlApproveCode("13348840080", "王晓明", "打开", "1#搅拌机", "1245");
//		SMSUtil.sendAlarmNotificationNote("15182000797", "测试污水厂", "cod超标");
//		SMSUtil.sendEventAssignNote("15182000797", "mimuweb项目重新打入", 1111);
//		SMSUtil.sendDutyNotificationNote("15182000797");
//		Set<String> set = new HashSet<String>();
//		set.add("15182000797");
//		set.add("17086236808");
//		SMSUtil.sendEventHappenNote(set, "测试水厂", "大事不好", 111);
//		SMSUtil.sendLoginCode("15182000797", "1111");
//		SMSUtil.sendAlterPasswordCode("17086236808", "1111");
//		SMSUtil.sendBindingPhoneCode("15182000797", "王大锤", "1111");
//		SMSUtil.sendAlterPhoneCode("15182000797", "王大锤", "17086236808", "1111");
//		SMSUtil.sendViewVideoCode("17086236808", "1111");
	}
}
