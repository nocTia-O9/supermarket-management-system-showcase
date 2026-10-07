package com.shanzhu.market.common.util;

/**
 * 文件上传工具
 */
public class UploadUtil {
	//阿里域名
	private static final String ALI_DOMAIN = System.getenv("ALIYUN_OSS_DOMAIN");
	private static final String ENDPOINT = System.getenv("ALIYUN_OSS_ENDPOINT");
	private static final String BUCKET_NAME = System.getenv("ALIYUN_OSS_BUCKET_NAME");

	private static final String ACCESS_KEY_ID = System.getenv("ALIYUN_OSS_ACCESS_KEY_ID");
	private static final String ACCESS_KEY_SECRET = System.getenv("ALIYUN_OSS_ACCESS_KEY_SECRET");
	private static  final String path = "wolf2w-70";
	//MultipartFile 对象
//	public static String uploadAli(MultipartFile file,String path_suffix) throws Exception {
//		//生成文件名称
//		String uuid = UUID.randomUUID().toString();
//		String orgFileName =file.getOriginalFilename();//获取真实文件名称 xxx.jpg
//		String ext= "." + FilenameUtils.getExtension(orgFileName);//获取拓展名字.jpg
//		String fileName =path+path_suffix+"/"+uuid + ext;//xxxxxsfsasa.jpg
//		// 创建OSSClient实例。
//		OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);
//		// 上传文件流。
//		ossClient.putObject(BUCKET_NAME, fileName, file.getInputStream());
//		// 关闭OSSClient。
//		ossClient.shutdown();
//		return ALI_DOMAIN + fileName;
//	}

}
