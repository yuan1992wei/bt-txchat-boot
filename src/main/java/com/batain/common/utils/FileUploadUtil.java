package com.batain.common.utils;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 附件上传工具类
 */
public class FileUploadUtil {

    public static Map<String, String> MIME_TYPE = new HashMap<String, String>();

    public static final String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb","mp4" };

    static {
        MIME_TYPE.put("txt", "text/plain");
        MIME_TYPE.put("rtf", "application/rtf");
        MIME_TYPE.put("pdf", "application/pdf");
        MIME_TYPE.put("gif", "image/gif");
        MIME_TYPE.put("png", "image/png");
        MIME_TYPE.put("jpg", "image/jpeg");
        MIME_TYPE.put("jpeg", "image/jpeg");
        MIME_TYPE.put("jpe", "image/jpeg");
        MIME_TYPE.put("bmp", "application/x-MS-bmp");
        MIME_TYPE.put("tif", "image/tiff");
        MIME_TYPE.put("tiff", "image/tiff");
        MIME_TYPE.put("doc", "application/msword");
        MIME_TYPE.put("docx", "application/msword");
        MIME_TYPE.put("xls", "application/vnd.ms-excel");
        MIME_TYPE.put("xlsx", "application/vnd.ms-excel");
        MIME_TYPE.put("ppt", "application/vnd.ms-powerpoint");
        MIME_TYPE.put("pptx", "application/vnd.ms-powerpoint");
        MIME_TYPE.put("htm", "text/html");
        MIME_TYPE.put("html", "text/html");
        MIME_TYPE.put("xml", "text/xml");

        MIME_TYPE.put("log", "text/plain");
        MIME_TYPE.put("ini", "text/plain");
        MIME_TYPE.put("jsp", "text/plain");
        MIME_TYPE.put("asp", "text/plain");

        MIME_TYPE.put("mp4", "video/mp4");
        MIME_TYPE.put("mp3", "audio/x-mpeg");
        MIME_TYPE.put("avi", "video/x-msvideo");
    }

    /**
     * 获取mime类型
     *
     * @param fileType
     * @return
     */
    public static String getMIMEType(String fileType) {
        String mime = MIME_TYPE.get(fileType);
        if (mime == null) {
            mime = "application/octet-stream";
        }
        return mime;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名称
     * @return 文件扩展名
     */
    public static String getFileExtName(String fileName) {
        String fileExtName = "";
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            fileExtName = fileName.substring(index + 1);
        }
        return fileExtName;
    }

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException 比如读写文件出错时
     * @throws InvalidExtensionException 文件校验异常
     */
//    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
//            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
//            InvalidExtensionException
//    {
//        int fileNamelength = file.getOriginalFilename().length();
//        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
//        {
//            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
//        }
//
//        assertAllowed(file, allowedExtension);
//
//        String fileName = extractFilename(file);
//
//        File desc = getAbsoluteFile(baseDir, fileName);
//        file.transferTo(desc);
//        String pathFileName = getPathFileName(baseDir, fileName);
//        return pathFileName;
//    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String uploadMedia(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
//            return upload(baseDir, file, MEDIA_EXTENSION);
            //TODO upload
            return null;
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }
}
