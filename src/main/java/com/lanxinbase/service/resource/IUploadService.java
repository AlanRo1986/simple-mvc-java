package com.lanxinbase.service.resource;

import com.lanxinbase.model.common.UploadModel;
import com.lanxinbase.system.callback.IFilePutCallback;
import com.lanxinbase.system.exception.IllegalServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by alan.luo on 2017/9/21.
 */
public interface IUploadService {

    UploadModel doSave(MultipartFile file, int index, IFilePutCallback callBack) throws IllegalServiceException;

    UploadModel doSave(String base64File, IFilePutCallback callBack) throws IllegalServiceException;

    String getDirectoryPath() throws IllegalServiceException;

    String getFileName() throws IllegalServiceException;

    String getExt(String fileName, String fileType) throws IllegalServiceException;

    boolean isLegalFile(String fileName) throws IllegalServiceException;

}
