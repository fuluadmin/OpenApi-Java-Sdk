package fulu.sup.open.api.core.utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author cheny
 */
public class HttpUtil {
  public final static int CONNECT_TIMEOUT = 10000;
  public final static int READ_TIMEOUT = 10000;

  /**
   * POST请求，json字符串形式数据
   *
   * @param url   请求地址
   * @param param 请求的json数据
   * @return response body
   * @throws Exception
   */
  public static String sendPostJson(String url, String param) {
    return sendPostWithHeads(url, param, "application/json", null);
  }

  private static String sendPostWithHeads(String url, String param, String contentType, Map<String, String> heads) {
    PrintWriter out = null;
    BufferedReader in = null;
    StringBuilder result = new StringBuilder();
    HttpURLConnection conn = null;

    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      conn = (HttpURLConnection) realUrl.openConnection();
      // 设置通用的请求属性
      conn.setRequestMethod("POST");// 提交模式
      conn.setConnectTimeout(CONNECT_TIMEOUT);// 连接超时 单位毫秒
      conn.setReadTimeout(READ_TIMEOUT);// 读取超时 单位毫秒
      if (contentType != null && !contentType.isEmpty()) {
        conn.setRequestProperty("Content-Type", contentType);
      }
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent",
          "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

      // 添加头信息
      if (heads != null && !heads.isEmpty()) {
        for (String key : heads.keySet()) {
          conn.setRequestProperty(key, heads.get(key));
        }
      }

      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(conn.getOutputStream());
      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      throw new RuntimeException("send  POST  request exception :" + e.getMessage(), e);
    } finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
        if (conn != null) {
          conn.disconnect();
        }
      } catch (Exception e2) {
        throw new RuntimeException("close Connection exception :" + e2.getMessage(), e2);
      }
    }
    return result.toString();
  }
}
