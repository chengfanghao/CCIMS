package com.eshore.nrms.vo;

public class Conts {

	public static final String USER_SESSION_KEY = "LOGIN_USER";
	public static final String VALIDATE_CODE_KEY = "VALIDATE_CODE";

	public static final String[] PARAM_TYPE = new String[] { "user_ip", // 用户IP
			"server_ip", // 服务器IP
			"vm_ip", // 虚拟机IP
			"os", // 操作系统
			"site", // 区域
			"ip_require_msg", // IP要求
			"mail_config"	//邮件通知
	};

	public static final String[] PARAM_TYPE_LIST = { "用户ip", "服务器ip", "虚拟机ip", "操作系统", "座位区域",
			"ip要求", "邮件通知"};
	public static final Integer STATE_OK = 1;
	public static final Integer STATE_DELETE = 0;
	
	public static final Integer CAN_AUDIT = 1;
	public static final Integer CAN_NOT_AUDIT = 0;

	public static final String ROLE_EMPLOYEE = "3";
	public static final String ROLE_MANAGER = "2";
	public static final String ROLE_ADMIN = "1";
	
	public static final int ISASSINED = 200; //已部署
	public static final int AUDIT_SUCCESS = 198; //通过审核
	public static final int WAITING_APPLY = 1; //工单未提交状态值
	public static final int TURN_DOWN = -1; //工单未通过状态值


}
