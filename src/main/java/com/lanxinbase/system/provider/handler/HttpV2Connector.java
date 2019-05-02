package com.lanxinbase.system.provider.handler;

import com.lanxinbase.system.basic.BasicProvider;
import com.lanxinbase.system.provider.basic.HttpRequestData;
import com.lanxinbase.system.provider.basic.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Map;


/**
 * Created by alan.luo on 2019/01/04.
 * <p>
 * - GET -
 * HttpV2Provider http = new HttpV2Provider();
 * HttpRequestData data = new HttpRequestData();
 * data.setHeader("token","27090e726a67f19ff3f5d10a7250017f");
 * Response response = http.get("http://yundian.jihewisdom.com/yundian/api/index?lnt=119.96345&lat=31.02322",data);
 * <p>
 * <p>
 * - POST -
 * HttpV2Provider http = new HttpV2Provider();
 * HttpRequestData data = new HttpRequestData();
 * data.setHeader("token","27090e726a67f19ff3f5d10a7250017f");
 * data.setParam("lat","31.02322");
 * data.setParam("lnt","119.96345");
 * data.setParam("mobile","1383838438");
 * data.setParam("verifyCode","111111");
 * Response response = http.post("http://yundian.jihewisdom.com/yundian/api/login",data);
 * <p>
 * <p>
 * - PUT -
 * HttpV2Provider http = new HttpV2Provider();
 * HttpRequestData data = new HttpRequestData();
 * data.setHeader("token","27090e726a67f19ff3f5d10a7250017f");
 * Response response = http.put("http://yundian.jihewisdom.com/yundian/admin/battery/1",data);
 * <p>
 * - DELETE -
 * Response response = http.delete("http://yundian.jihewisdom.com/yundian/admin/battery/1",data);
 * <p>
 * <p>
 * - download -
 * HttpV2Provider http = new HttpV2Provider();
 * HttpRequestData data = new HttpRequestData();
 * data.setHeader("token","27090e726a67f19ff3f5d10a7250017f");
 * File file = http.download("http://yundian.jihewisdom.com/upload/images/20190104/15465882558721.jpg",data,"d:/1.jpg");
 * <p>
 * out(file.exists());
 * out(file.getAbsolutePath());
 * <p>
 * - raw -
 * HttpV2Provider http = new HttpV2Provider();
 * HttpRequestData data = new HttpRequestData();
 * InputStream in = http.raw("http://yundian.jihewisdom.com/upload/images/20190104/15465882558721.jpg",data);
 * try {
 * out(in.read());
 * out(in.read());
 * out(in.read());
 * } catch (IOException e) {
 * e.printStackTrace();
 * }finally {
 * http.close();
 * }
 */
public abstract class HttpV2Connector extends BasicProvider implements IHttpConnector {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    private static final int TIME_OUT = 60000;
    private static final String VERSION = "1.2";

    private HttpURLConnection conn = null;


    public HttpV2Connector() {
        super();
    }

    @Override
    public InputStream exec(String url, HttpRequestData data) {

        try {

            logger(">>>>>>> url:" + url + ":" + data.getMethod());

            String param = this.getParams(data.getParam());
            if (param != null && !param.trim().equals("") && METHOD_GET.equals(data.getMethod())) {
                if (url.indexOf("?") == -1) {
                    url += "?";
                }
                url = url + param;
            }


            URL uri = new URL(url);
            conn = (HttpURLConnection) uri.openConnection();
            conn.setUseCaches(false);
            if (!METHOD_GET.equals(data.getMethod())) {
                conn.setDoInput(true);
                conn.setDoOutput(true);
            }
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setRequestMethod(data.getMethod());
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("cache-control", "no-cache");
            conn.setRequestProperty("X-Powered-By", VERSION);

            this.setCookies(conn, data.getCookie());
            this.setHeaders(conn, data.getHeader());

            if (param != null && !METHOD_GET.equals(data.getMethod())) {
                logger(">>>>>>> post:" + param);
//                conn.setRequestProperty("Content-Type", "application/json");
                PrintWriter pw = new PrintWriter(conn.getOutputStream());
                pw.print(param);
                pw.flush();
            }

            //body
            if (data.getBody() != null && data.getBody().length() > 1) {
                logger(">>>>>>> body:" + data.getBody());
                byte[] outputInBytes = data.getBody().getBytes("UTF-8");
                OutputStream os = conn.getOutputStream();
                os.write(outputInBytes);
                os.close();
            }

            conn.connect();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("HTTP ERROR CODE: " + conn.getResponseCode() + " " + conn.getResponseMessage());
            }

            return conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (conn != null) {
//                conn.disconnect();
//                logger(">>>>>>> conn.disconnect");

//            }
        }

        return null;
    }

    /**
     * 需要调用close方法,关闭链接
     *
     * @param url
     * @param data
     * @return
     */
    @Override
    public InputStream raw(String url, HttpRequestData data) {
        return this.exec(url, data);
    }

    @Override
    public String http(String url, HttpRequestData data) {
        InputStream in = this.exec(url, data);
        InputStreamReader reader = null;
        StringBuffer res = new StringBuffer();

        try {
            reader = new InputStreamReader(in, "utf-8");
            BufferedReader buff = new BufferedReader(reader);

            String tmp;
            while ((tmp = buff.readLine()) != null) {
                res.append(tmp);
            }

            logger(">>>>>>> http response:" + res.toString());

            buff.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.close();
        }

        return res.toString();
    }

    @Override
    public File download(String url, HttpRequestData data, String filePath) {
        return this.download(url, data, new File(filePath));
    }

    @Override
    public File download(String url, HttpRequestData data, File file) {
        InputStream in = this.exec(url, data);

        this.checkDir(file.getParent());

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);

            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.close();
        }

        return file;
    }

    @Override
    public Response get(String url, HttpRequestData data) {
        data.setMethod(METHOD_GET);
        String res = this.http(url, data);

        Response response = new Response();
        response.setRaw(res);
        return response;
    }

    @Override
    public Response post(String url, HttpRequestData data) {
        data.setMethod(METHOD_POST);
        String res = this.http(url, data);

        Response response = new Response();
        response.setRaw(res);
        return response;
    }

    @Override
    public Response put(String url, HttpRequestData data) {
        data.setMethod(METHOD_PUT);
        String res = this.http(url, data);

        Response response = new Response();
        response.setRaw(res);
        return response;
    }

    @Override
    public Response delete(String url, HttpRequestData data) {
        data.setMethod(METHOD_DELETE);
        String res = this.http(url, data);

        Response response = new Response();
        response.setRaw(res);
        return response;
    }

    @Override
    public void close() {
        if (conn != null) {
            conn.disconnect();
            logger(">>>>>>> conn.disconnect");
        }
    }

    private void setCookies(HttpURLConnection conn, Map<String, String> cookies) {
        if (cookies != null && !cookies.isEmpty()) {
            for (String key : cookies.keySet()) {
                conn.addRequestProperty(key, cookies.get(key));
            }
        }
    }

    private void setHeaders(HttpURLConnection conn, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
        }
    }

    private String getParams(Map<String, Object> param) {
        StringBuffer sb = new StringBuffer();
        if (param != null && !param.isEmpty()) {
            for (String key : param.keySet()) {
                sb.append(key + "=" + param.get(key).toString() + "&");
            }
            return sb.toString().substring(0, sb.toString().length() - 1);
        }
        return null;
    }

    private void checkDir(String dir) {
        String[] dirs = dir.split("/");
        String path = "";
        for (String str : dirs) {
            path += str + "/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }


}
