package com.happydinner.base;


/**
 * @author Administrator
 *
 */
public class Constant {
    /**
     * 客户端类型
     */
	public final static String CLIENT_TYPE = "TELEANDROID";
	/**网络类型--所有网络*/
	public final static int NETWORK_TYPE_ALL = 1;
	/**网络类型--wifi*/
	public final static int NETWORK_TYPE_ONLY_WIFI = 0;
	
	// 检查首次使用引导界面所需的版本号
	public final static int FIRST_USE_GUIDE_VERSION_CODE = 59;
	/** 控制某些提示的显示的id号 */
	public static final int CONTROL_PROMPT_ALBUMBACK_ID = 60;
	
	/** 返回数据成功 */
	public static final int SUCCESS = 1;
	/** 返回数据失败 */
	public static final int FAILURE = -1;
	/** 网络可用 */
	public static final int NETWORKENABLE = -2;
	/** 网络不可用 */
	public static final int NETWORKDISABLED = -4;

	// 文件列表接口参数
	/** 同步根文件夹 */
	public static final long ROOTFOLDER = 0;
	public static final int SAFEBOX_ROOT_ID = -10;
	public static final long FILE_ROOTFOLDER = -11;
	public static final long PIC_ROOTFOLDER = -12;
	public static final long VIDEO_ROOTFOLDER = -13;
	public static final long MUSIC_ROOTFOLDER = -14;
	public static final long DOCUMENT_ROOTFOLDER = -15;
	public static final long APP_ROOTFOLDER = -16;
	// 通讯录根目录
	public static final long CONTACT_FOLDERID = -20;

	/** 获取单层 文件列表 */
	public static final int FLAG_RECURSIVE_NO = 0;
	/** 递归获取文件列表 */
	public static final int FLAG_RECURSIVE_YES = 1;

	/** 文件媒体类型--不限 */
	public static final int MEDIA_TYPE_ALL = 0;
	/** 文件媒体类型--图片 */
	public static final int MEDIA_TYPE_PIC = 1;
	/** 文件媒体类型--音乐 */
	public static final int MEDIA_TYPE_MUSIC = 2;
	/** 文件媒体类型--视频 */
	public static final int MEDIA_TYPE_VIDEO = 3;
	/** 文件媒体类型--文档 */
	public static final int MEDIA_TYPE_DOCUMENT = 4;

	/** 文件类型---不限 */
	public static final int TYPE_FILE_AND_FOLDER = 0;
	/** 文件类型---文件 */
	public static final int TYPE_ONLY_FILE = 1;
	/** 文件类型---文件夹 */
	public static final int TYPE_ONLY_FOLDER = 2;

	/** 文件缩略图大小--不获取 */
	public static final int THUMB_TYPE_NONE = 0;
	/** 文件缩略图大小--小(80max) */
	public static final int THUMB_TYPE_SMALL = 1;
	/** 文件缩略图大小--中(160max) */
	public static final int THUMB_TYPE_MIDLLE = 2;
	/** 文件缩略图大小--大(320max) */
	public static final int THUMB_TYPE_LARGE = 4;
	/** 文件缩略图大小--超大(600max) */
	public static final int THUMB_TYPE_XLARGE = 8;

	/** 获取媒体文件属性 */
	public static final int MEDIA_ATTR_YES = 1;
	/** 不获取媒体文件属性 */
	public static final int MEDIA_ATTR_NO = 0;

	/** 音乐文件播放模式 */
	public static final int ONE_REPLAY = 1;
	public static final int ALL_PLAY = 2;
	public static final int RANDOM_PLAY = 3;
	
	public static final String CONTACT_BACKUP_CLIENT_EXTRAS_FILE_NAME = "contacts_client.ext";
	public static final String CONTACT_BACKUP_SERVER_EXTRAS_FILE_NAME = "contacts_server.ext";
	public static final String CONTACT_BACKUP_CLIENT_TEMP_FILE_NAME = "contacts_client.tmp";
	public static final String CONTACT_BACKUP_SERVER_TEMP_FILE_NAME = "contacts_server.tmp";
	public static final String CONTACT_BACKUP_CLIENT_FINAL_FILE_NAME = "contacts_client.vcf";
	public static final String CONTACT_BACKUP_SERVER_FINAL_FILE_NAME = "contacts_server.vcf";
	public static final String CONTACT_BACKUP_CLIENT_BAK_FILE_NAME = "contacts_client.bak";
	public static final String CONTACT_BACKUP_SERVER_BAK_FILE_NAME = "contacts_server.bak";
	/*
	 * BMP、GIF、JPG、JPEG、PSD、PNG、PCX、TIF MP3、WAV、WMA、MIDI、MP3PRO、ASF、FLAC、AAC、VQF
	 * MP4、AVI、MOV、3GP、WMV、FLV、RMVB、RM、MKV、MPEG、MPG、SWF、TS、RA、RAM、NAVI、DAT、MST
	 * DOC、DOCX、XLS、XLSX、PDF、PPT、PPTX、TXT、CHM、UMD、EPUB
	 */
	public static final String[] PIC_ARRAY = { "BMP", "GIF", "JPG", "JPEG",
			"PSD", "PNG", "PCX", "TIF" };
	public static final String[] MUSIC_ARRAY = { "MP3", "WAV", "WMA", "MIDI",
			"MP3PRO", "ASF", "FLAC", "AAC", "VQF" };
	public static final String[] VIDEO_ARRAY = { "MP4", "AVI", "MOV", "3GP",
			"WMV", "FLV", "RMVB", "RM", "MKV", "MPEG", "MPG", "SWF", "TS",
			"RA", "RAM", "NAVI", "DAT", "MST" };
	public static final String[] DOCUMENT_ARRAY = { "DOC", "DOCX", "XLS",
			"XLSX", "PDF", "PPT", "PPTX", "TXT", "CHM", "UMD", "EPUB" };
	/**预览支持的文件类型*/
	public static final String[] DOCUMENT_PREVIEW_ARRAY = { "DOC", "DOCX", "PPT",
		"PPTX", "XLS", "XLSX", "TXT"};

	/** 0 移动 ；1 复制 */
	public static final int MOVE = 0;
	public static final int COPY = 1;
	// 添加分享联系人返回结果码
	public static final int REQUEST_CODE_PICK_PHONE = 1523;
	// 添加分享邮箱返回结果码
	public static final int REQUEST_CODE_PICK_EMAIL = 1524;
	// 上传文件返回结果码
	public static final int UPLOAD_CODE = 1525;
	// 选择相册备份目录返回结果码
	public static final int SELECT_BACKUPPATH_CODE = 1526;
	/**获取目录的请求码*/
    public static final int REQUEST_CODE_PICK_FOLDER = 1527;
    
    /**拍照上传的请求吗*/
    public static final int REQUEST_CODE_CAMERA_UPLOAD = 1528;
    
    /**群空间动态页面跳转到其他页面的请求码*/
    public static final int REQUEST_CODE_GROUP_DYNAMIC = 1529;
    
    /**云盘页面新建群空间的请求吗*/
    public static final int REQUEST_CODE_CREATE_GROUP_SPACE = 1530;

	public static final int BLOG_BIND = 1001;

	/** 新功能没有使用过 */
	public static boolean NEW_FUNCTION_NOT_USED = false;
	/** 新功能已经使用过 */
	public static boolean NEW_FUNCTION_HAS_USED = true;
	
	public static final String NEW_FUNCTION_KEY_APPRECOMMEND = "new_functon_apprecommend";
	/**页面跳转时，intent来自哪个类的key*/
	public static final String INTENT_FROM_KEY = "intent_from";
	
	/**按文件名排序*/
	public static final int FILE_SORT_BY_NAME = 1;
	/**按文件大小排序*/
	public static final int FILE_SORT_BY_SIZE = 2;
	/**按文件修改时间排序*/
	public static final int FILE_SORT_BY_TIME = 3;
	/**正序排列*/
	public static final int FILE_SORT_BY_ASC = 1;
	/**逆序排列*/
	public static final int FILE_SORT_BY_DESC = -1;

	/**用户名、密码登录*/
	public static final int LOGIN_CHANNEL_PASSWORD = 45;
	/**天翼套件登录(旧版)*/
	public static final int LOGIN_CHANNEL_CTACCOUNT = 46;
	/**imsi登录*/
	public static final int LOGIN_CHANNEL_IMSI = 47;
	/**myid登录*/
	public static final int LOGIN_CHANNEL_MYID = 48;
	/**天翼套件登录(新版，对接综合平台)*/
	public static final int LOGIN_CHANNEL_189CTACCOUNT = 49;
	/**第三方应用跳转目标的key*/
	public static final String EXTRA_KEY_TARGET = "target";
	/**第三方应用跳转目标的key*/
	public static final String EXTRA_KEY_TARGET_IMAGE = "image";
	/**第三方应用跳转目标的key*/
	public static final String EXTRA_KEY_TARGET_VIDEO = "video";
	/**第三方应用跳转目标的key*/
	public static final String EXTRA_KEY_TARGET_MUSIC = "music";
	/**第三方应用intent携带的参数title*/
	public static final String EXTRA_KEY_TITLE = "title";
	/**常量result*/
	public static final String EXTRA_KEY_RESULT = "result";
	/**消息中心推送的消息标示*/
	public static final String EXTRA_PUSH_MSG = "push_message";
	
	/**
	 * 天翼套件登录——天翼云AppID和AppSecret
	 */
	public final static String CTA_TYY_APP_ID = "cloud";
	public final static String CTA_TYY_APP_SECRET = "g7qP45TVkQ5G6iNbbhaU5nXlAelGcAcs";
	
	
	public final static String APP_ID = "3500000000400107";

	public final static String APP_KEY = "BE0EE1AC3CA3C663720E03E652E8BED7ECD5C3D4F6DD4104";
//	public final static String APP_ID = "3500000000406702";
//	
//	public final static String APP_KEY = "7A823C96B46112E4F29B49C126F3654AF72FD6A9A884FD5E";
	/**根目录id的key*/
	public static final String EXTRA_FILE_ROOT_ID_KEY = "file_manage_root_id";
	/**根目录title的key*/
	public static final String EXTRA_FILE_ROOT_TITLE_KEY = "file_manage_root_title";
	/**群空间key*/
	public static final String EXTRA_GROUP_SPACE_KEY = "groupspace";
	/**群空间id的key*/
	public static final String EXTRA_GROUP_SPACE_ID_KEY = "groupspace_id";
	/**群空间名称的key*/
	public static final String EXTRA_GROUP_SPACE_NAME_KEY = "groupspace_name";
	/**群空间动态（所有、单个）种类的key*/
	public static final String EXTRA_GROUP_SPACE_TYPE_KEY = "groupspace_type";
	
	public static final String EXTRA_DATA_CHANGE = "data_change";
	
	//群空间参数
	/**成员角色--群主*/
    public final static int GROUP_ROLE_CREATER = 1;
    /**成员角色--管理员*/
    public final static int GROUP_ROLE_ADMINISTRATOR= 2;
    /**成员角色--普通成员*/
    public final static int GROUP_ROLE_MEMBER = 3;
	/**成员权限--只读*/
	public final static String GROUP_RIGHTS_READ = "read";
	/**成员权限--编辑*/
	public final static String GROUP_RIGHTS_WRITE = "write";
	/**成员权限--上传*/
	public final static String GROUP_RIGHTS_UPLOAD = "upload";
	/**空间容量限制--无限制*/
	public final static long GROUP_QUOTA_NOLIMIT = 0;
	/**空间容量限制--1G*/
	public final static long GROUP_QUOTA_1G = 1024;
	/**空间容量限制--5G*/
	public final static long GROUP_QUOTA_5G = 5120;
	/**空间容量限制--10G*/
	public final static long GROUP_QUOTA_10G = 10240;
	/**所有群空间动态*/
    public final static int GROUP_SPACE_DYNAMIC_ALL = 2;
    /**单个群空间动态*/
    public final static int GROUP_SPACE_DYNAMIC_SINGLE = 1;
    /**我自己创建的群空间*/
    public final static int GROUP_SPACE_TYPE_MYCREATE = 1;
    /**我加入创建的群空间*/
    public final static int GROUP_SPACE_TYPE_MYJOIN = 2;
    /**自己创建和加入的群空间*/
    public final static int GROUP_SPACE_TYPE_MYCREATE_AND_MYJOIN = 3;
    /**群空间动态加载类型--向上获取*/
    public final static int GROUP_DYNAMIC_LOADTYPE_UP = 0;
    /**群空间动态加载类型--向下获取*/
    public final static int GROUP_DYNAMIC_LOADTYPE_DOWN = 1;
	

	// 微信APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String WX_APP_ID = "wx8090b1c08c5d6616";
    
    // 谷歌广告id
    public static final String GOOGLE_ADMOB_ID = "a1509caefd31993";
    
    public static final String MEITU_PAKAGE = "com.mt.mtxx.mtxx";
    public static final String ACTION_MEIHUA = "com.meitu.intent.action.MEIHUA";
	/**
	 * 大部分界面listview的marginTop，单位dip
	 */
    public static final int LISTVIEW_OFFSET_TOP = 0;
    
    //注册、修改密码端口号
    public static final String ADDR_TELECOM ="10659189";
    public static final String ADDR_UNICOM = "1065505913666";
    public static final String ADDR_CHINA_MOBILE = "10657109001366";
    
    //成功退出群或者解散群
    public static final int RESULT_CODE_EXIT_GROUP_SPACE_OK = 200;
    /**成功创建群空间*/
    public static final int RESULT_CODE_CREATE_GROUP_SPACE_OK = 201;
    /**活动--分享*/
    public static final String HUODONG_TASK_ID_SHARE = "100000114";
    /**活动--百度首发*/
    public static final String HUODONG_TASK_ID_BAIDU_SF = "100000115";
    /**活动--T级空间*/
    public static final String HUODONG_TASK_ID_T_CAPACITY = "T_CAPACITY_TASK";
    /**活动--分享旅行*/
    public static final String HUODONG_TASK_ID_TRAVEL = "100000712";
    /**客户端向平台获取指定应用访问的AccessToken--一键换机appid*/
    public static final String APP_ID_ONEKEY = "30217";
    /**配置信息缓存文件名称*/
    public static final String CONFIG_CACHE_FILE_NAME = "configs_cloud.obj";
    
    public static final String ACTION_WX_RESULT = "com.cn21.ecloud.ACTION_WX_RESULT";
    public static final String ACTION_YX_RESULT = "com.cn21.ecloud.ACTION_YX_RESULT";
    /**更新头像结果*/
    public static final String ACTION_UPDATE_AVATAR = "ecloud.ACTION_UPDATE_AVATAR_RESULT";
    /**更新群空间列表*/
    public static final String ACTION_UPDATE_GROUP_SPACE = "com.cn21.ecloud.ACTION_UPDATE_GROUP_SPACE";
    /**
     * 推送给系统消息栏的action
     */
    public static final String ACTION_PUSH_MSG_NOTIFICATION = "com.cn21.broadcast.push.msg.notification";
    //notification相关id
    /**
     * notification，天翼云新版本安装提示
     */
    public final static int NOTIFY_ID_UPDATE = 18;
    /**
     * notification，流量宝安装提示
     */
    public final static int NOTIFY_ID_APK_INSTALLED = 19;
    /**
     * notification，VGO安装提示
     */
    public final static int NOTIFY_ID_VGO_INSTALLED = 20;
    
    /**消息中心——分享类消息*/
    public final static int NOTITY_ID_SHARE = 201;
    /**消息中心——群空间类消息*/
    public final static int NOTITY_ID_GROUPSPACE = 202;
    /**消息中心——营销类消息*/
    public final static int NOTITY_ID_MARKET = 203;
    
    /** 主动发起版本更新检查 */
    public final static long VERSION_CHECK_INITIATIVE = 1;
    /** 被动发起版本更新检查 */
    public final static long VERSION_CHECK_UNINITIATIVE = 0;
    /**
     * 状态源--文件管理
     */
    public final static int STATE_FROM_CLOUD = 1;
    /**
     * 状态源--本地文件
     */
    public final static int STATE_FROM_LOCAL_FILE = 2;
    /**
     * 意见反馈的pid
     */
    public final static String FEEDBACK_PID = "27CB506B181C4788BEDFB52859DFB14A";
    /**
     * 流量宝package name
     */
    public final static String FLOWPAY_PKG_NAME = "com.corp21cn.flowpay";
    /**
     * vgo package name
     */
    public final static String VGO_PKG_NAME = "com.cn21.vgo";
    /**
     * wps package name
     */
    public final static String WPS_PKG_NAME = "cn.wps.moffice_eng";
    
    public final static String  ACTION_REPORT_FLOW = "action_report_flow";
    /**
     * 分享类型--公开分享
     */
    public static final short SHARE_TYPE_PUBLIC = 2;
    /**
     * 分享类型--提取码分享
     */
    public static final short SHARE_TYPE_PRIVATE = 1;
    
    /**
     * 推送消息类型--营销
     */
    public static final String PUSH_MSG_TYPE_MARKET = "1";

    /**
     * 推送消息类型--分享
     */
    public static final String PUSH_MSG_TYPE_SHARE = "2";

    /**
     * 推送消息类型--动态
     */
    public static final String PUSH_MSG_TYPE_DYNAMIC = "3";
    
}
