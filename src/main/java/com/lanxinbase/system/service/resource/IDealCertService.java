package com.lanxinbase.system.service.resource;

import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.model.DealCertModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by alan on 2018/7/22.
 */
public interface IDealCertService {

    String makeCert(DealCertModel model) throws IllegalServiceException;

    BufferedImage drawString(BufferedImage image, String str, int x, int y, int fontSize, boolean isBold, Color color) throws IllegalServiceException;

    BufferedImage drawImage(BufferedImage image, Image src, int x, int y) throws IllegalServiceException;

    BufferedImage makeQrcode(String content, Integer loadId) throws IllegalServiceException;

    File http2File(String url, String filename) throws IllegalServiceException;

    String checkPath(String path) throws IllegalServiceException;

    BufferedImage http2File2(String dealCover);
}
