package com.lanxinbase.system.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lanxinbase.constant.Constant;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Hashtable;
import java.util.Random;

/**
 * QrCodeUtils qrCodeUtils = new QrCodeUtils();
 * String res = qrCodeUtils.encode("http://h5.test.com/#!/?r=1","d:/test.jpg","1.jpg");
 * String res = qrCodeUtils.encode("http://h5.test.com/#!/?r=1");
 */
public class QrCodeUtils {


    private static final String CHARSET = "utf-8";
    private static final String FORMAT = "jpg";
    private static final String fontType = "simhei";

    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    private static final int QRCODE_SIZE2 = 120;

    private static final String destPath = Constant.uploadDirectory + "/qrcode";

    /**
     * 二维码的粘贴坐标
     */
    private static final int x_qr = 362;
    private static final int y_qr = 467;


    /**
     * 生成图片
     *
     * @param content
     * @param cover
     * @return
     * @throws Exception
     */
    private BufferedImage createImage(String content, String cover) throws Exception {

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);

        int size = QRCODE_SIZE;
        if (!StringUtils.isEmptyTrim(cover)) {
            size = QRCODE_SIZE2;
        }
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (!StringUtils.isEmptyTrim(cover)) {
            return this.mergeImages(image, cover);
        }

        return image;
    }

    private BufferedImage mergeImages(BufferedImage source, String cover) throws Exception {
        File coverFile = new File(cover);
        if (!coverFile.exists()) {
            throw new Exception("cover file not found.");
        }

        Image src = ImageIO.read(new File(cover));
        int width = src.getWidth(null);
        int height = src.getHeight(null);


        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = image.createGraphics();
        g2.drawImage(src, 0, 0, width, height, null);
        g2.drawImage(source, x_qr, y_qr, source.getWidth(), source.getHeight(), null);

        Color c = new Color(47, 47, 47);
        g2.setColor(c);

        Font font = new Font(fontType, Font.PLAIN, 26);
        g2.setFont(font);

        /* 消除java.awt.Font字体的锯齿 */
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        String title = Constant.TITLE;
        AttributedString ats = new AttributedString(title);
        ats.addAttribute(TextAttribute.FONT, font, 0, title.length());
        AttributedCharacterIterator iter = ats.getIterator();

        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        g2.drawString(iter, 26, 582);

        g2.dispose();

        return image;

    }


//    /**
//     * 插入LOGO
//     *
//     * @param source   二维码图片
//     * @param logoPath LOGO图片地址
//     * @throws Exception
//     */
//    private BufferedImage insertQrCode(BufferedImage source, String logoPath, String title) throws Exception {
//
//        File file = new File(logoPath);
//        if (!file.exists()) {
//            throw new Exception("logo file not found.");
//        }
//
//        Image src = ImageIO.read(new File(logoPath));
//        int width = src.getWidth(null);
//        int height = src.getHeight(null);
//
//
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//        Graphics2D g2 = image.createGraphics();
//        g2.drawImage(src, 0, 0, width, height, null);
//        g2.drawImage(source, x_ReCode, y_ReCode, source.getWidth(), source.getHeight(), null);
//
//
//        Color c = new Color(47, 47, 47);
//        g2.setColor(c);
//
//
//        Font font = new Font(fontType, Font.PLAIN, fontSize);
//        g2.setFont(font);
//
//        /* 消除java.awt.Font字体的锯齿 */
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        AttributedString ats = new AttributedString(title);
//        ats.addAttribute(TextAttribute.FONT, font, 0, title.length());
//        AttributedCharacterIterator iter = ats.getIterator();
//
//        if (title.length() > fontLength) {
//
//            int count = (int) Math.ceil((double) title.length() / (double) fontLength);
//            for (int a = 0; a < count; a++) {
//
//                String tmp = title.substring(a * fontLength);
//                if (tmp.length() > fontLength) {
//                    tmp = tmp.substring(0, fontLength);
//                }
//                if (a == 1 && title.length() > fontLength * 2) {
//                    tmp = tmp.substring(0, tmp.length() - 4);
//                    tmp += "...";
//                }
//
//                /**
//                 * 消除锯齿
//                 */
//                ats = new AttributedString(tmp);
//                ats.addAttribute(TextAttribute.FONT, font, 0, tmp.length());
//                iter = ats.getIterator();
//
//                g2.drawString(iter, x_title, y_title);
//
//                //换行的高度
//                y_title += fontSize + 14;
//
//                if (a >= 1) {
//                    break;
//                }
//            }
//
//        } else {
//
//            FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
//            x_title = (image.getWidth() - fontMetrics.stringWidth(title)) / 2;
//            g2.drawString(iter, x_title, y_title + 20);
//        }
//
//        g2.dispose();
//
//
//        return image;
//
//    }

    /**
     * 生成二维码,文件名随机
     *
     * @param content 二维码的内容
     * @return
     * @throws Exception
     */
    public String encode(String content) throws Exception {

        BufferedImage image = createImage(content, null);

        mkdirs(destPath);

        String fileName = System.currentTimeMillis() + new Random().nextInt(99999999) + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));

        System.out.println(destPath + "/" + fileName);
        return fileName;
    }

    /**
     * 生成二维码，指定二维码文件名
     *
     * @param content
     * @param coverPath
     * @param fileName
     * @return
     * @throws Exception
     */
    public String encode(String content, String coverPath, String fileName) throws Exception {
        BufferedImage image = createImage(content, coverPath);
        mkdirs(destPath);
        fileName = fileName.substring(0, fileName.indexOf(".") > 0 ? fileName.indexOf(".") : fileName.length())
                + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．
     * (mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 存放目录
     */
    public void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成二维码
     *
     * @param content
     * @param coverPath
     * @param output
     * @throws Exception
     */
    public void encode(String content, String coverPath, OutputStream output) throws Exception {
        BufferedImage image = createImage(content, coverPath);
        ImageIO.write(image, FORMAT, output);
    }

    /**
     * 生成二维码
     *
     * @param content
     * @param output
     * @throws Exception
     */
    public void encode(String content, OutputStream output) throws Exception {
        encode(content, null, output);
    }

    /**
     * 解析二维码
     *
     * @param file 二维码图片
     * @return
     * @throws Exception
     */
    public String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 解析二维码
     *
     * @param path 二维码图片地址
     * @return
     * @throws Exception
     */
    public String decode(String path) throws Exception {
        return decode(new File(path));
    }

}  