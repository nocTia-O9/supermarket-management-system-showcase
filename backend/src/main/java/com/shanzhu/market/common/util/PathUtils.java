package com.shanzhu.market.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PathUtils {
    public static String getClassLoadRootPath() {
        String path = "";
        try {
            String prePath = URLDecoder.decode(PathUtils.class.getClassLoader().getResource("").getPath(),"utf-8").replace("/target/classes", "");
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase().startsWith("mac")) {
                // 苹果
                path = prePath.substring(0, prePath.length() - 1);
            } else if (osName.toLowerCase().startsWith("windows")) {
                // windows
                path = prePath.substring(1, prePath.length() - 1);
            } else if(osName.toLowerCase().startsWith("linux") || osName.toLowerCase().startsWith("unix")) {
                // unix or linux
                path = prePath.substring(0, prePath.length() - 1);
            } else {
                path = prePath.substring(1, prePath.length() - 1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String upload(MultipartFile multipartFile) {
        String res = null;  // 返回网络路径
        try {
            String staticDir = PathUtils.getClassLoadRootPath() + "/src/main/resources/static/files/";
            // 如果结果目录不存在，则创建目录
            File resDirFile = new File(staticDir);
            if(!resDirFile.exists()) {
                boolean flag = resDirFile.mkdirs();
                if(!flag) throw new RuntimeException("创建结果目录失败");
            }
            // 获取MultipartFile的字节数组
            byte[] fileBytes = multipartFile.getBytes();

            // 创建文件对象

            // 加个时间戳防止重名
            String newFileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            res = "/files/" + newFileName;
            // 写文件
            File file = new File(staticDir + newFileName);

            // 将字节数组写入文件
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileBytes);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
