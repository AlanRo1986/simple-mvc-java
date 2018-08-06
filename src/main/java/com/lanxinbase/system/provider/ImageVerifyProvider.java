package com.lanxinbase.system.provider;

import com.lanxinbase.system.annotation.Provider;
import com.lanxinbase.system.exception.IllegalValidateException;
import com.lanxinbase.system.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * image verify class.
 * <p>
 * ImageVerifyUtils verify = new ImageVerifyUtils();
 * verify.make(response, request.getSession());
 * <p>
 * verify.check(request.getSession(),code); => true or throw new Exception.
 */
@Provider
public class ImageVerifyProvider {

    private static final Logger log = Logger.getLogger(ImageVerifyProvider.class.getName());

    private String KEY_SESSION_VERIFY = "key_session_image_verify";
    private String KEY_SESSION_DATETIME = "key_session_time_verify";

    private Long expireIn = 300000L;
    private String hash = "hash";

    private String raw = "qwertyupkijhgfdsazxcvbnmABCDEFGHJKLMNPQRSTUVWXYZ1234567890";
//    private String raw = "1234567890";

    public ImageVerifyProvider() {

    }

    public void make(HttpServletResponse response, HttpSession session) throws Exception {
        this.make(response, session, 120, 40);
    }

    public void make(HttpServletResponse response, HttpSession session, int width, int height) throws Exception {
        this.make(response, session, width, height, 4);
    }

    /**
     * make a image verify
     *
     * @param response output to browser.
     * @param session  http session,use it to save verify code.
     * @param width    default is 120px
     * @param height   default is 40px
     * @param num      the verify code.
     * @throws Exception
     */
    public void make(HttpServletResponse response, HttpSession session, int width, int height, int num) throws Exception {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int r = this.getRandom(200, 255);
        int g = this.getRandom(200, 255);
        int b = this.getRandom(200, 255);

        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(new Color(r, g, b));
        g2d.fillRect(0, 0, width, height);

        g2d.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < 5; i++) {
            r = this.getRandom(50, 200);
            g = this.getRandom(50, 200);
            b = this.getRandom(50, 200);
            g2d.setColor(new Color(r, g, b));

            g2d.drawLine(getRandom(0, width), getRandom(0, height), getRandom(0, width), getRandom(0, height));
        }

        int fontSize = (width - 26) / num;

        r = this.getRandom(0, 180);
        g = this.getRandom(0, 180);
        b = this.getRandom(0, 180);
        g2d.setColor(new Color(r, g, b));
        g2d.setFont(new Font(null, Font.PLAIN, fontSize));
        List<String> codes = this.getVerifyString(num);
        String code = "";

        int __x = (width - fontSize * num) / 2;
        for (int i = 0; i < codes.size(); i++) {
            code += codes.get(i);
            if (i == 0) {
                g2d.drawString(codes.get(i), __x, getRandom(20, height));
            } else {
                g2d.drawString(codes.get(i), fontSize * i + __x, getRandom(20, height));
            }
        }

        log.info(">>code:" + code);
        //绘制干扰点
        g2d.setStroke(new BasicStroke(2.6f));
        for (int i = 0; i < 10; i++) {
            r = this.getRandom(100, 255);
            g = this.getRandom(100, 255);
            b = this.getRandom(100, 255);
            g2d.setColor(new Color(r, g, b));

            int x = getRandom(0, width);
            int y = getRandom(0, height);
            g2d.drawLine(x, y, x + 2, y + 2);
        }

        session.setAttribute(KEY_SESSION_VERIFY, Md5Utils.md5(code.toLowerCase() + hash).toUpperCase());
        session.setAttribute(KEY_SESSION_DATETIME, System.currentTimeMillis() + expireIn);

        response.setContentType("image/jpeg");
        OutputStream ops = response.getOutputStream();
        ImageIO.write(image, "jpeg", ops);
        ops.close();
    }

    /**
     * check the code valid or invalid.
     *
     * @param session    Http session
     * @param verifyCode code string
     * @return true or throws.
     * @throws IllegalValidateException
     */
    public boolean check(HttpSession session, String verifyCode) throws IllegalValidateException {

        if (verifyCode == null) {
            throw new IllegalValidateException("请输入验证码");
        }

        Long makeTime = (Long) session.getAttribute(KEY_SESSION_DATETIME);
        if (makeTime == null) {
            throw new IllegalValidateException("验证码不存在");
        }

        if (makeTime.longValue() < System.currentTimeMillis()) {
            throw new IllegalValidateException("验证码已过期");
        }

        String source = (String) session.getAttribute(KEY_SESSION_VERIFY);
        if (source == null) {
            throw new IllegalValidateException("验证码不存在");
        }

        String now = Md5Utils.md5(verifyCode.toLowerCase() + hash).toUpperCase();
        if (now.equals(source)) {
            session.removeAttribute(KEY_SESSION_DATETIME);
            session.removeAttribute(KEY_SESSION_VERIFY);
            return true;
        }

        return false;
    }

    /**
     * get verify code.
     *
     * @param num the code length,default is 4.
     * @return
     */
    private List<String> getVerifyString(int num) {
        if (num <= 0) {
            num = 4;
        }
        List<String> res = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            res.add(String.valueOf(raw.charAt(r.nextInt(raw.length()))));
        }
        return res;
    }

    /**
     * get number by random.
     *
     * @param min min number
     * @param max max number
     * @return between min to max.
     */
    private int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

}
