package com.lanxinbase.service.impl;

import com.lanxinbase.constant.Constant;
import com.lanxinbase.model.common.UploadModel;
import com.lanxinbase.service.resource.IUploadService;
import com.lanxinbase.system.basic.CompactService;
import com.lanxinbase.system.callback.IFilePutCallback;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.utils.DateUtils;
import com.lanxinbase.system.utils.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by alan.luo on 2017/9/21.
 */
@Service
public class UploadServiceImpl extends CompactService implements IUploadService {


    public UploadServiceImpl() {
        super(UploadServiceImpl.class);
    }

    /**
     * 保存上传文件
     *
     * @param file     文件对象
     * @param index    索引号（适用于多张图片同时上传使用）
     * @param callBack 成功或失败回调
     * @return
     * @throws IllegalServiceException
     */
    @Override
    public UploadModel doSave(MultipartFile file, int index, IFilePutCallback callBack) throws IllegalServiceException {

        if (!this.isLegalFile(file.getOriginalFilename())) {
            callBack.error(0, "文件格式不正确");
            return null;
        }
        if (file.getSize() > (Constant.uploadMaxSize * 1024)) {
            callBack.error(0, "文件尺寸过大");
            return null;
        }

        //如果目录不存在，则创建
        String path = getDirectoryPath() + getFileName() + index + getExt(file.getOriginalFilename(), null);


        UploadModel model = new UploadModel();

        model.setContentType(file.getContentType());
        model.setOriginalFilename(file.getOriginalFilename());
        model.setName(file.getName());
        model.setSize(file.getSize());
        model.setUrl(Constant.DOMAIN_IMG + path.replace(Constant.uploadDirectory + "/", ""));

        try {
            File f = new File(path + 1);

            file.transferTo(f);

            File newFile = new File(path);
            if(model.getName().toLowerCase().contains("jpg")){
                ImageUtils.optimize(f, newFile, Constant.uploadImageOptimize);
            }else{
                ImageUtils.resize(f, newFile, 1024, -1);
            }
            model.setAbsolutePath(newFile.getAbsolutePath().replace("\\", "/"));

            callBack.success(1, "保存成功!", model);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.error(0, e.getMessage());
        }

        return model;
    }

    @Override
    public UploadModel doSave(String base64File, IFilePutCallback callBack) throws IllegalServiceException {

        String[] arr = base64File.split(",");
        if (arr.length != 2) {
            callBack.error(0, "文件信息错误");
            return null;
        }

        if (base64File.length() > (Constant.uploadMaxSize * 1024)) {
            callBack.error(0, "文件尺寸过大");
            return null;
        }

        String fileType = arr[0].substring(arr[0].indexOf(":") + 1, arr[0].indexOf(";"));
        String fileName = getFileName() + getExt(null, fileType);

        if (!isLegalFile(fileName)) {
            callBack.error(0, "文件格式不正确");
            return null;
        }

        UploadModel model = new UploadModel();
        String path = getDirectoryPath() + fileName;

        try {

            BASE64Decoder decoder = new BASE64Decoder();

            File outFile = new File(path + 1);

            OutputStream ro = new FileOutputStream(outFile);
            //byte[] b = decoder.decodeBuffer(base64File);
            byte[] b = decoder.decodeBuffer(arr[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            ro.write(b);
            ro.flush();
            ro.close();

            if(fileName.toLowerCase().contains("jpg")){
                ImageUtils.optimize(outFile.getAbsolutePath(), path + 2, Constant.uploadImageOptimize);
                ImageUtils.resize(path + 2, path, 1024, -1);
            }else{
                ImageUtils.resize(path + 1, path, 1024, -1);
            }
            File file = new File(path);

            model.setContentType(fileType);
            model.setName(fileName);
            model.setSize(Long.valueOf(base64File.length()));
            model.setUrl(Constant.DOMAIN_IMG + path.replace(Constant.uploadDirectory + "/", ""));
            model.setAbsolutePath(file.getAbsolutePath().replace("\\", "/"));
            callBack.success(1, "保存成功!", model);

        } catch (Exception e) {
            e.printStackTrace();
            callBack.error(0, e.getMessage());
        }
        return model;

    }

    @Override
    public String getDirectoryPath() throws IllegalServiceException {
        String path = Constant.uploadDirectory;

        if (!path.substring(path.length() - 1).equals("/")) {
            path += "/";
        }
        path += "images/" + DateUtils.getFullDateQ(null) + "/";

        return getFolder(path);
    }

    @Override
    public String getFileName() throws IllegalServiceException {
        return String.valueOf(DateUtils.getTime());
    }

    @Override
    public String getExt(String fileName, String fileType) throws IllegalServiceException {

        String ext = ".jpg";
        if (fileType != null) {
            switch (fileType) {
                case "image/png":
                    ext = ".png";
                    break;
                case "image/bmp":
                    ext = ".bmp";
                    break;
                case "image/gif":
                    ext = ".gif";
                    break;
            }

        } else {
            ext = fileName.substring(fileName.lastIndexOf("."));
        }

        return ext;
    }

    @Override
    public boolean isLegalFile(String fileName) throws IllegalServiceException {
        Iterator<String> type = Arrays.asList(Constant.uploadAllowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * if the directory is exists,when created it.
     *
     * @param path
     */
    private String getFolder(String path) {
        String[] paths = path.split("/");
        File file = null;
        String tmp = "";
        for (String str : paths) {
            tmp += str + "/";
            file = new File(tmp);
            if (!file.exists()) {
                file.mkdir();
            }
        }
        return path;
    }
}
