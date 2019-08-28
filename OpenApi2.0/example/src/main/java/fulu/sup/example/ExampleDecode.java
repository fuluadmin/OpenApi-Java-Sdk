package fulu.sup.example;

import com.google.gson.reflect.TypeToken;
import fulu.sup.open.api.core.MethodConst;
import fulu.sup.open.api.core.utils.CardUtil;
import fulu.sup.open.api.core.utils.JSONUtil;
import fulu.sup.open.api.model.InputOrderGetDto;
import fulu.sup.open.api.sdk.DefaultOpenApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: chenYing
 * @Date: 2019/8/28 0028 10:19
 */
public class ExampleDecode {
  private final static String APP_KEY = "i4esv1l+76l/7NQCL3QudG90Fq+YgVfFGJAWgT+7qO1Bm9o/adG/1iwO2qXsAXNB";
  private final static Logger LOGGER = LoggerFactory.getLogger(ExampleDecode.class);
  private final static String SYS_SECRET = "0a091b3aa4324435aab703142518a8f7";
  private final static String URL = "http://pre.openapi.fulu.com/api/getway";

  public static void main(String[] args) throws Exception {
    LOGGER.info("\n订单查单{}", repeat("=", 100));
    DefaultOpenApiClient client = new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_ORDER_GET);
    InputOrderGetDto dto = new InputOrderGetDto();
    dto.setCustomerOrderNo("31b6b96b-a21e-4bc4-bc0c-6e77a2ffb698");
    client.setBizObject(dto);

    LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
    Future<String> future = client.excuteAsync();
    waitFor(future);
    Map<String, String> result = JSONUtil.fromJSON(future.get(), new TypeToken<Map>() {
    });
    Map<String, Object> resultMap = JSONUtil.fromJSON(result.get("result"), new TypeToken<Map<String, Object>>() {
    });

    List<Map<String, String>> cardList = (List<Map<String, String>>) resultMap.get("cards");
    StringBuilder decodeStr = new StringBuilder();
    for (Map<String, String> map : cardList) {
      decodeStr.append("card_number：").append(map.get("card_number")).append("；desc_card_number：")
          .append(CardUtil.cardDecode(map.get("card_number"), SYS_SECRET.getBytes("UTF-8")));
      decodeStr.append("  card_pwd：").append(map.get("card_pwd")).append("；card_pwd：")
          .append(CardUtil.cardDecode(map.get("card_pwd"), SYS_SECRET.getBytes("UTF-8")));
      decodeStr.append("\n");
    }

    System.out.println(decodeStr.toString());
    System.out.println(CardUtil.cardEncode("CD10002502019061217430016421", SYS_SECRET.getBytes("UTF-8")));
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
      TimeUnit.MILLISECONDS.sleep(500);
    }
    LOGGER.info("excuteAsync：\n{}", future.get());
  }

}
