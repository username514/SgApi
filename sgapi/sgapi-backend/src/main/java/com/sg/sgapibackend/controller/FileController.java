package com.sg.sgapibackend.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.sg.sgapibackend.common.BaseResponse;
import com.sg.sgapibackend.common.ErrorCode;
import com.sg.sgapibackend.common.ResultUtils;
import com.sg.sgapibackend.exception.BusinessException;
import com.sg.sgapibackend.model.enums.FileUploadBizEnum;
import com.sg.sgapibackend.service.FileService;
import com.sg.sgapibackend.service.UserService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import com.sg.sgapibackend.utils.GetAbsolutePathUtils;

import static com.sg.sgapibackend.constant.RedisConstants.CACHE_FILE_SDK_KEY;
import static com.sg.sgapibackend.constant.RedisConstants.CACHE_FILE_SDK_TTL;
import static com.sg.sgapibackend.constant.UserConstant.DEFAULT_XCX_QRCODE_PATH;

/**
 * 文件接口
 *
 * @author WSG
 * 
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Value("${gigot-api.upload.avatarUrlFilePath}")
    private String filePath;

    @Value("${gigot-api.sdk.download}")
    private String sdkPath;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserService userService;


    @Autowired
    private FileService fileService;



    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    //形参名称必须与请求体里的名称保持一
    @PostMapping("/uploadAvatar")
    public BaseResponse<String> uploadAvatar(MultipartFile file) throws IOException {
        //file是一个临时文件，否则本次请求完成后临时文件会被删除
        log.info("上传文件,接收到的前端数据为file:{}", file);
        if (file == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "传输的file为空");
        }
        String fileName = fileService.uploadAvatar(file);
        return ResultUtils.success(fileName);
    }

    /**
     * 文件下载
     *
     * @param name
     */
    @GetMapping("/downloadAvatar")
    public void downloadAvatar(@RequestParam String name, HttpServletResponse resp) throws IOException {
        log.info("下载文件,接收到的前端数据为{}", name);
        String absolutePath = GetAbsolutePathUtils.getAbsolutePathUtil(filePath);
        //输入流，通过输入流读取文件内容
        FileInputStream fileInputStream = new FileInputStream(new File(absolutePath + File.separator + name));

        //输出流，输出文件返回给前端
        ServletOutputStream outputStream = resp.getOutputStream();

        //设置响应时一个image文件
        resp.setContentType("image/jpeg");

        // 读取本地文件
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }

        //关闭资源
        fileInputStream.close();
        outputStream.close();
    }

    /**
     * 文件下载
     */
    @GetMapping("/sdk")
    public void sdk( HttpServletResponse resp, HttpServletRequest res) throws IOException {
        log.info("下载SDK");
        String ipAddress = res.getRemoteAddr();
        String downloadSdkCount = stringRedisTemplate.opsForValue().get(CACHE_FILE_SDK_KEY + ipAddress);
        if (StrUtil.isBlank(downloadSdkCount)) {
            stringRedisTemplate.opsForValue().set(CACHE_FILE_SDK_KEY + ipAddress, "1" ,CACHE_FILE_SDK_TTL, TimeUnit.MINUTES);
        }
        if ("1".equals(downloadSdkCount)) {
            stringRedisTemplate.opsForValue().set(CACHE_FILE_SDK_KEY + ipAddress, "2" ,CACHE_FILE_SDK_TTL, TimeUnit.MINUTES);
        }

        if ("2".equals(downloadSdkCount)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "下载次数过多，请5分钟后再试或访问：https://github.com/PanYW-Git/gigotapi-client-sdk");
        }

        String absolutePath = GetAbsolutePathUtils.getAbsolutePathUtil(sdkPath);
        //输入流，通过输入流读取文件内容
        File sdkFile = new File(absolutePath);
        FileInputStream fileInputStream = new FileInputStream(sdkFile);

        //输出流，输出文件返回给前端
        ServletOutputStream outputStream = resp.getOutputStream();


        resp.setContentType("application/java-archive");
        resp.setHeader("Content-Disposition", "attachment; filename=\""+sdkFile.getName() +"\"");

        // 读取本地文件
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }

        //关闭资源
        fileInputStream.close();
        outputStream.close();
    }

    /**
     * 文件下载
     *
     * @param name
     */
    @GetMapping("/downloadQRCode")
    public void downloadQRCode(@RequestParam String name, HttpServletResponse resp) throws IOException {
        log.info("下载文件,接收到的前端数据为{}", name);
        String absolutePath = GetAbsolutePathUtils.getAbsolutePathUtil(DEFAULT_XCX_QRCODE_PATH);
        //输入流，通过输入流读取文件内容
        FileInputStream fileInputStream = new FileInputStream(new File(absolutePath + File.separator + name));

        //输出流，输出文件返回给前端
        ServletOutputStream outputStream = resp.getOutputStream();

        //设置响应时一个image文件
        resp.setContentType("image/jpeg");

        // 读取本地文件
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }

        //关闭资源
        fileInputStream.close();
        outputStream.close();
    }


    /**
     * 校验文件
     *
     * @param multipartFile
     * @param fileUploadBizEnum 业务类型
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }
}
