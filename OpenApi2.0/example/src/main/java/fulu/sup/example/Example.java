package fulu.sup.example;

import com.google.gson.reflect.TypeToken;
import fulu.sup.open.api.core.MethodConst;
import fulu.sup.open.api.core.utils.JSONUtil;
import fulu.sup.open.api.model.CommonRequest;
import fulu.sup.open.api.model.InputOrderGetDto;
import fulu.sup.open.api.model.response.DefaultClientResponse;
import fulu.sup.open.api.sdk.DefaultOpenApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: chenYing
 * @Date: 2019/8/20 0020 14:59
 */
public class Example {

  private final static String APP_KEY = "i4esv1l+76l/7NQCL3QudG90Fq+YgVfFGJAWgT+7qO1Bm9o/adG/1iwO2qXsAXNB";
  private final static Logger LOGGER = LoggerFactory.getLogger(Example.class);
  private final static String SYS_SECRET = "0a091b3aa4324435aab703142518a8f7";
  private final static String URL = "http://pre.openapi.fulu.com/api/getway";

  /**
   * 订单查单：此处有三种方式
   *
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    ////已经存在实体类的
    LOGGER.info("\n订单查单{}", repeat("=", 100));
    DefaultOpenApiClient client =
        new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_USER_INFO_GET);
    InputOrderGetDto dto = new InputOrderGetDto();
    client.setBizObject(dto);
    dto.setCustomerOrderNo("0d19f8e4-5af3-490d-a8d8-47fd457da7de");

    LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
    waitFor(client.excuteAsync());

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////没有实体类(1)
    LOGGER.info("\n【自定义参数(1)】订单查单{}", repeat("=", 100));
    client = new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_USER_INFO_GET);
    CommonRequest request = new CommonRequest();
    request.setBizContent("customer_order_no", "0d19f8e4-5af3-490d-a8d8-47fd457da7de");
    //文档里面的查看：README.MD，包含文档及沙箱环境地址
    request.setMethod("fulu.order.info.get");
    client.setBizObject(request);
    DefaultClientResponse response = JSONUtil.fromJSON(client.excute(), new TypeToken<DefaultClientResponse>() {
    });
    System.out.println(response);

    LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
    waitFor(client.excuteAsync());

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////没有实体类(2)
    LOGGER.info("\n【自定义参数(2)】订单查单{}", repeat("=", 100));
    client = new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_USER_INFO_GET);
    Map<String, Object> bizContent = new HashMap<String, Object>();
    bizContent.put("customer_order_no", "0d19f8e4-5af3-490d-a8d8-47fd457da7de");
    client.setBizContent(JSONUtil.toJSON(bizContent));
    response = JSONUtil.fromJSON(client.excute(), new TypeToken<DefaultClientResponse>() {
    });
    System.out.println(response);

    LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
    waitFor(client.excuteAsync());

    System.exit(1);
  }

  private static String repeat(String ch, int num) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < num; i++) {
      str.append(ch);
    }
    return str.toString();
  }

  private static void waitFor(Future<String> future) throws Exception {
    while (!future.isDone()) {
      //      System.out.println(repeat("+", 100));
      TimeUnit.MILLISECONDS.sleep(500);
    }
    LOGGER.info("excuteAsync：\n{}", future.get());
  }

}
