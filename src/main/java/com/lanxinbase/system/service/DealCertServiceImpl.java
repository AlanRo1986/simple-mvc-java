package com.lanxinbase.system.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lanxinbase.constant.Constant;
import com.lanxinbase.system.basic.CompactService;
import com.lanxinbase.system.basic.ExceptionError;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.model.DealCertModel;
import com.lanxinbase.system.service.resource.IDealCertService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.*;
import java.util.List;

/**
 * Created by alan on 2018/7/22.
 */

@Service
public class DealCertServiceImpl extends CompactService implements IDealCertService {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT = "jpg";
    private static final String fontType = "simhei";

    private static final String ROOT = Constant.uploadDirectory + "/dealLoad/";

    private static final String PNG_SUCCESS = ROOT + "success.png";
    private static final String PNG_FAIL = ROOT + "fail.png";

    private static final int QRCODE_SIZE = 132;

    private static final int width = 600;
    private static final int height = 854;

    //活动封面
    private static final int dealCoverWidth = 600;
    private static final int dealCoverHeight = 400;
    private static final int dealCoverX = 0;
    private static final int dealCoverY = 0;

    //头像
    private static final int avatarWidth = 132;
    private static final int avatarCoverHeight = 132;
    private static final int avatarCoverX = (dealCoverWidth - avatarWidth) / 2;
    private static final int avatarCoverY = 334;

    //活动标题
    private static final int dealNameX = 50;
    private static final int dealNameY = 544;
    private static final int dealNameFontSize = 24;

    //备注信息
    private static final int remarkX = 50;
    private static final int remarkY = 590;
    private static final int fontSize = 16;

    //报名时间
    private static final int createTimeX = 50;
    private static final int createTimeY = 700;

    //活动时间
    private static final int dealTimesX = 16;
    private static final int dealTimesY = 820;

    //二维码
    private static final int qrcodeX = 458;
    private static final int qrcodeY = 712;

    //印章
    private static final int stataX = 278;
    private static final int stataY = 595;

    public DealCertServiceImpl() {
        super(DealCertServiceImpl.class);
    }

    @Override
    public String makeCert(DealCertModel model) throws IllegalServiceException {
        if (model == null) {
            throw new IllegalServiceException(ExceptionError.error_require_params);
        }

        String dir = ROOT + model.getId() + "/";
        this.checkPath(dir);
        String name = "deal_load_" + model.getId() + ".png";
        if (new File(dir + name).exists()) {
            return Constant.DOMAIN_IMG + "dealLoad/" + model.getId() + "/" + name;
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D dg = image.createGraphics();
        dg.setColor(Color.WHITE);
        dg.fillRect(0, 0, width, height);
        dg.setColor(Color.BLACK);

        try {
            //封面跟头像
            this.drawImage(image, this.resizeImage(this.http2File2(model.getDealCover()), new File(dir + "deal_cover.jpg"), dealCoverWidth, dealCoverHeight, 0), dealCoverX, dealCoverY);
            this.drawImage(image, this.resizeImage(this.http2File2(model.getAvatar()), new File(dir + "avatar.jpg"), avatarWidth, avatarCoverHeight, 4), avatarCoverX, avatarCoverY);

            //印章
            this.drawImage(image, this.makeQrcode(model.getDealQrCode(), model.getId()), qrcodeX, qrcodeY);
            File file = null;
            if (model.isSuccess()) {
                file = new File(PNG_SUCCESS);
            } else {
                file = new File(PNG_FAIL);
            }

            this.drawImage(image, ImageIO.read(file), stataX, stataY);

            //写字
            int nicknameX = (width - model.getNickname().length() * 20) / 2;

            this.drawString(image, model.getNickname(), nicknameX, 490, 20, true, new Color(47, 47, 47));
            this.drawString(image, model.getDealName(), dealNameX, dealNameY, dealNameFontSize, true, new Color(47, 47, 47));
            this.drawString(image, model.getRemark(), remarkX, remarkY, fontSize, false, new Color(155, 155, 155));
            this.drawString(image, model.getLoadCreateTime(), createTimeX, createTimeY, fontSize, false, new Color(155, 155, 155));
            this.drawString(image, model.getLoadType(), createTimeX, createTimeY + 36, fontSize, false, new Color(155, 155, 155));
            this.drawString(image, model.getLoadMoney(), createTimeX, createTimeY + 72, fontSize, false, new Color(255, 0, 0));
            this.drawString(image, model.getDealTimes(), dealTimesX, dealTimesY, 14, false, new Color(155, 155, 155));

            //导出
            FileOutputStream out = new FileOutputStream(new File(dir + name));
            ImageIO.write(image, "png", out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Constant.DOMAIN_IMG + "dealLoad/" + model.getId() + "/" + name;
    }

    /**
     * 在图片上画字
     *
     * @param image    需要画字的图片
     * @param str      字符串
     * @param x        x坐标
     * @param y        y坐标
     * @param fontSize 字号
     * @param isBold   是否粗体
     * @param c        字体颜色
     * @return 返回原图片
     * @throws IllegalServiceException
     */
    @Override
    public BufferedImage drawString(BufferedImage image, String str, int x, int y, int fontSize, boolean isBold, Color c) throws IllegalServiceException {
        int style = Font.PLAIN;
        if (isBold) {
            style += Font.BOLD;
        }
        Font font = new Font(fontType, style, fontSize);

        Graphics2D g2 = image.createGraphics();
        g2.setColor(c);
        g2.setFont(font);

        /* 消除java.awt.Font字体的锯齿 */
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AttributedString ats = new AttributedString(str);
        ats.addAttribute(TextAttribute.FONT, font, 0, str.length());
        AttributedCharacterIterator iter = ats.getIterator();

        if (fontSize >= 20 && str.length() > 20) {
            str = str.substring(0, 20) + "...";
        }

        List<String> stringList = new ArrayList<>();
        if (str.length() <= 30 || fontSize == 14) {
            stringList.add(str);
        } else {
            if (str.length() >= 60) {
                str = str.substring(0, 57) + "...";
            }

            int len = (int) Math.ceil((float) str.length() / 30.f);
            //活动时间：2018-07-30 08:00:00 至 2018-08-02 20:00:00
            for (int i = 0; i < len; i++) {
                if (i * 30 + 30 > str.length()) {
                    stringList.add(str.substring(i * 30));
                } else {
                    stringList.add(str.substring(i * 30, 30));
                }
            }
        }

        for (int i = 0; i < stringList.size(); i++) {
            ats = new AttributedString(stringList.get(i));
            ats.addAttribute(TextAttribute.FONT, font, 0, stringList.get(i).length());
            iter = ats.getIterator();

            g2.drawString(iter, x, y);

            //换行的高度
            y += fontSize + 16;
        }

        g2.dispose();
        return image;

    }

    @Override
    public BufferedImage drawImage(BufferedImage image, Image src, int x, int y) throws IllegalServiceException {
        Graphics2D g2 = image.createGraphics();
        g2.drawImage(src, x, y, src.getWidth(null), src.getHeight(null), null);
        return image;
    }

    @Override
    public BufferedImage makeQrcode(String content, Integer loadId) throws IllegalServiceException {
        String path = ROOT + loadId + "/";
        this.checkPath(path);

        BitMatrix bitMatrix = null;
        try {
            File file = new File(path + "qrcode_" + loadId + "." + FORMAT);
            if (file.exists() && file.isFile() && file.length() > 0) {
                return ImageIO.read(file);
            }

            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
            hints.put(EncodeHintType.MARGIN, 1);

            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            ImageIO.write(image, FORMAT, new File(path + "qrcode_" + loadId + "." + FORMAT));
            return image;
        } catch (WriterException e) {
            e.printStackTrace();
            throw new IllegalServiceException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalServiceException(e.getMessage());
        }
    }

    /**
     * 从网络中下载图片
     *
     * @param url      http url
     * @param filename path+name
     * @return 失败返回null，成功返回file对象
     * @throws IllegalServiceException
     */
    @Override
    public File http2File(String url, String filename) throws IllegalServiceException {
        FileOutputStream out = null;
        BufferedInputStream in = null;
        HttpURLConnection connection = null;
        File file = null;

        try {
            URL uri = new URL(url);
            connection = (HttpURLConnection) uri.openConnection();
            connection.connect();

            in = new BufferedInputStream(connection.getInputStream());
            out = new FileOutputStream(filename);

            byte[] buf = new byte[1024];
            int size = 0;
            while ((size = in.read(buf)) != -1) {
                out.write(buf, 0, size);
            }
            out.flush();
            file = new File(filename);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalServiceException(e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return file;
    }

    @Override
    public String checkPath(String path) throws IllegalServiceException {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        return path;
    }

    @Override
    public BufferedImage http2File2(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage resizeImage(File file, int w, int h, int padding) throws Exception {
        return resizeImage(ImageIO.read(file), file, w, h, padding);
    }

    private BufferedImage resizeImage(BufferedImage image, File file, int w, int h, int padding) throws Exception {
        FileOutputStream out = new FileOutputStream(file);

        boolean hasNotAlpha = !image.getColorModel().hasAlpha();
        BufferedImage tag = new BufferedImage(w, h, hasNotAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
        Graphics2D dg = tag.createGraphics();
        dg.setColor(Color.WHITE);
        dg.fillRect(0, 0, w, h);
        dg.setColor(Color.BLACK);

        // Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        tag.getGraphics().drawImage(image.getScaledInstance(w - padding * 2, h - padding * 2, Image.SCALE_SMOOTH), padding, padding, null);
        ImageIO.write(tag, hasNotAlpha ? "jpg" : "png", out);

        out.close();
        return tag;
    }


}
