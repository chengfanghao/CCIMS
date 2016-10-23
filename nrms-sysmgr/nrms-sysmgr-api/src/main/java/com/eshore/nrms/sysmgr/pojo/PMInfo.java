package com.eshore.nrms.sysmgr.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lile
 * @date 2016年8月1日
 * 物理服务器信息
 *
 */
@Entity
@Table(name="b_server_info")
public class PMInfo {
	
	private String id;			//主键
	private String ipSegmentId;	//服务器IP段ID
	private String ipSegment;	//服务器IP段
	private String ipNum;		//服务器IP
	private String fullIp;		//全IP
	private int cpu;			//CPU
	private int memory;			//内存
	private int disk;			//硬盘
	private int cpuUsed;		//已使用CPU
	private int memoryUsed;		//已使用内存
	private int diskUsed;		//已使用硬盘
	private String consoleUrl;	//控制台地址
	private String consoleLogin;//控制台账号
	private String consolePwd;	//控制台密码
	private String serverLogin; //系统账号
	private String serverPwd;	//系统密码
	private int status;			//状态
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "ip_segment_id")
	public String getIpSegmentId() {
		return ipSegmentId;
	}
	public void setIpSegmentId(String ipSegmentId) {
		this.ipSegmentId = ipSegmentId;
	}
	@Column(name = "ip_segment")
	public String getIpSegment() {
		return ipSegment;
	}
	public void setIpSegment(String ipSegment) {
		this.ipSegment = ipSegment;
	}
	@Column(name = "ip_num")
	public String getIpNum() {
		return ipNum;
	}
	public void setIpNum(String ipNum) {
		this.ipNum = ipNum;
	}
	@Column(name = "full_ip")
	public String getFullIp() {
		return fullIp;
	}
	public void setFullIp(String fullIp) {
		this.fullIp = fullIp;
	}
	@Column(name = "cpu")
	public int getCpu() {
		return cpu;
	}
	public void setCpu(int cpu) {
		this.cpu = cpu;
	}
	@Column(name = "memory")
	public int getMemory() {
		return memory;
	}
	public void setMemory(int memory) {
		this.memory = memory;
	}
	@Column(name = "disk")
	public int getDisk() {
		return disk;
	}
	public void setDisk(int disk) {
		this.disk = disk;
	}
	@Column(name = "cpu_used")
	public int getCpuUsed() {
		return cpuUsed;
	}
	public void setCpuUsed(int cpuUsed) {
		this.cpuUsed = cpuUsed;
	}
	@Column(name = "memory_used")
	public int getMemoryUsed() {
		return memoryUsed;
	}
	public void setMemoryUsed(int memoryUsed) {
		this.memoryUsed = memoryUsed;
	}
	@Column(name = "disk_used")
	public int getDiskUsed() {
		return diskUsed;
	}
	public void setDiskUsed(int diskUsed) {
		this.diskUsed = diskUsed;
	}
	@Column(name = "console_url")
	public String getConsoleUrl() {
		return consoleUrl;
	}
	public void setConsoleUrl(String consoleUrl) {
		this.consoleUrl = consoleUrl;
	}
	@Column(name = "console_login")
	public String getConsoleLogin() {
		return consoleLogin;
	}
	public void setConsoleLogin(String consoleLogin) {
		this.consoleLogin = consoleLogin;
	}
	@Column(name = "console_pwd")
	public String getConsolePwd() {
		return consolePwd;
	}
	public void setConsolePwd(String consolePwd) {
		this.consolePwd = consolePwd;
	}
	@Column(name = "server_login")
	public String getServerLogin() {
		return serverLogin;
	}
	public void setServerLogin(String serverLogin) {
		this.serverLogin = serverLogin;
	}
	@Column(name = "server_pwd")
	public String getServerPwd() {
		return serverPwd;
	}
	public void setServerPwd(String serverPwd) {
		this.serverPwd = serverPwd;
	}
	@Column(name = "status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
