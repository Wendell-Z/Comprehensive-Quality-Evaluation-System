package com.nmid.cqes.utils;

import com.nmid.cqes.domain.FormatData;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author
 */
@Component
public class FileUtil {
    @Autowired
    private FormatData formatData;

    /**
     * 上传文件
     *
     * @param multipartFile
     * @param stuId
     * @param targetDirectory
     * @return
     */
    public FormatData uploadFile(MultipartFile multipartFile, int stuId, String prizeName, String targetDirectory) throws IOException {
        String targetFileName = "";
        if (multipartFile != null) {
            // 获取物理路径
            //String targetDirectory = request.getSession().getServletContext()
            //      .getRealPath("/prizeFiles");
            System.out.println(targetDirectory);

            // 上传的文件名
            String tmpFileName = multipartFile.getOriginalFilename();
            int dot = tmpFileName.lastIndexOf('.');
            // 文件后缀名
            String ext = "";
            if ((dot > -1) && (dot < (tmpFileName.length() - 1))) {
                ext = tmpFileName.substring(dot + 1);
            }

            // 其他文件格式不处理,只处理pdf文件
            if ("pdf".equalsIgnoreCase(ext)) {
                // 重命名上传的文件名
                targetFileName = stuId + "-" + prizeName + ".pdf";
                // 保存的新文件
                File target = new File(targetDirectory, targetFileName);
                // 保存文件
                FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),
                        target);
                formatData.set200("http://47.93.231.115/cqes/prizeFiles/" + targetFileName);
                return formatData;
            } else {
                formatData.setStatus("801");
                formatData.setInfo("Illegal Parameter");
                formatData.setData("文件格式错误");
                return formatData;
            }
        } else {
            formatData.setStatus("801");
            formatData.setInfo("Illegal Parameter");
            formatData.setData("请先上传文件！");
            return formatData;
        }
    }


    /**
     * 下载 没用过
     *
     * @param fileName
     * @param request
     * @return
     * @throws IOException
     */
    public ResponseEntity<byte[]> download(String fileName, HttpServletRequest request) throws IOException {

        String realPath = request.getSession().getServletContext()
                .getRealPath("/file/" + fileName);
        //得到文件所在位置

        System.out.println("路径:" + realPath);

        InputStream in = new FileInputStream(new File(realPath));
        //将该文件加入到输入流之中

        byte[] body = null;


        // 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数

        body = new byte[in.available()];


        in.read(body);
        //读入到输入流里面

        fileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
        //防止中文乱码

        HttpHeaders headers = new HttpHeaders();
        //设置响应头

        headers.add("Content-Disposition", "attachment;filename=" + fileName);

        HttpStatus statusCode = HttpStatus.OK;
        //设置响应码

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);

        return response;

    }
}

