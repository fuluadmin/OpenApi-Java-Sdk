package fulu.sup.open.api.core.utils;

import fulu.sup.open.api.core.MethodConst;
import fulu.sup.open.api.model.InputCardOrderDto;
import fulu.sup.open.api.model.InputDirectOrderDto;
import fulu.sup.open.api.model.InputMatchPhoneProductListDto;
import fulu.sup.open.api.model.InputOrderGetDto;
import fulu.sup.open.api.model.InputPhoneOrderDto;
import fulu.sup.open.api.model.InputProductDto;
import fulu.sup.open.api.model.InputProductTemplateDto;
import fulu.sup.open.api.model.InputTrafficOrderDto;
import fulu.sup.open.api.model.InputUserDto;
import fulu.sup.open.api.sdk.DefaultOpenApiClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: chenYing
 * @Date: 2019/8/19 0019 18:37
 */
public class SDKTest {
  private final static String APP_KEY = "i4esv1l+76l/7NQCL3QudG90Fq+YgVfFGJAWgT+7qO1Bm9o/adG/1iwO2qXsAXNB";
  private final static Logger LOGGER = LoggerFactory.getLogger(SDKTest.class);
  private final static String SYS_SECRET = "0a091b3aa4324435aab703142518a8f7";
  private final static String URL = "http://pre.openapi.fulu.com/api/getway";

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

  /**
   * 卡密下单
   *
   * @throws Exception
   */
  @Test
  public void cardOrderAddTest() throws Exception {
    LOGGER.info("\n卡密下单{}", repeat("=", 100));
    DefaultOpenApiClient client =
        new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_CARD_ORDER_ADD);
    InputCardOrderDto dto = new InputCardOrderDto();
    dto.setProductId(10000497);
    dto.setCustomerOrderNo(UUID.randomUUID().toString());
    dto.setBuyNum(3);

    client.setBizObject(dto);
    LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
    dto.setCustomerOrderNo(UUID.randomUUID().toString());
    waitFor(client.excuteAsync());
  }

  /**
   * 直充下单
   *
   * @throws Exception
   */
  @Test
  public void directOrderAddTest() throws Exception {
    LOGGER.info("\n直充下单{}", repeat("=", 100));
    DefaultOpenApiClient client =
        new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_DIRECT_ORDER_ADD);
    InputDirectOrderDto dto = new InputDirectOrderDto();
    dto.setProductId(10000498);
    dto.setBuyNum(1);
    client.setBizObject(dto);
    int count = 1;

    for (String chargeAccount : Arrays.asList("888888", "999999", "999990")) {
      dto.setChargeAccount(chargeAccount);
      dto.setCustomerOrderNo(UUID.randomUUID().toString());

      LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
      dto.setCustomerOrderNo(UUID.randomUUID().toString());
      if (count++ >= 3) {
        waitFor(client.excuteAsync());
      } else {
        LOGGER.info("excuteAsync：\n{}\n{}", client.excuteAsync().get(), repeat("-", 100));
      }

    }
  }

  /**
   * 手机号归属地
   *
   * @throws Exception
   */
  @Test
  public void matchPhoneProducGetTest() throws Exception {
    LOGGER.info("\n手机号归属地{}", repeat("=", 100));
    DefaultOpenApiClient client = new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_CHECK_PHONE);
    InputMatchPhoneProductListDto dto = new InputMatchPhoneProductListDto();
    dto.setFaceValue(50D);

    int count = 1;
    for (String phone : Arrays.asList("15972368779", "13971553804")) {
      dto.setPhone(phone);

      client.setBizObject(dto);

      LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
      if (count++ >= 2) {
        waitFor(client.excuteAsync());
      } else {
        LOGGER.info("excuteAsync：\n{}\n{}", client.excuteAsync().get(), repeat("-", 100));
      }

    }
  }

  /**
   * 订单查单
   *
   * @throws Exception
   */
  @Test
  public void orderInfoGetTest() throws Exception {
    LOGGER.info("\n订单查单{}", repeat("=", 100));
    DefaultOpenApiClient client = new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_ORDER_GET);
    InputOrderGetDto dto = new InputOrderGetDto();
    client.setBizObject(dto);
    int count = 1;

    for (String customerOrderNo : Arrays
        .asList("0d19f8e4-5af3-490d-a8d8-47fd457da7de", "31b6b96b-a21e-4bc4-bc0c-6e77a2ffb698")) {
      dto.setCustomerOrderNo(customerOrderNo);

      LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
      if (count++ >= 2) {
        waitFor(client.excuteAsync());
      } else {
        LOGGER.info("excuteAsync：\n{}\n{}", client.excuteAsync().get(), repeat("-", 100));
      }

    }
  }

  /**
   * 话费下单
   *
   * @throws Exception
   */
  @Test
  public void phoneOrderAddTest() throws Exception {
    LOGGER.info("\n话费下单{}", repeat("=", 100));
    DefaultOpenApiClient client =
        new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_PHONE_ORDER_ADD);
    InputPhoneOrderDto dto = new InputPhoneOrderDto();
    dto.setChargeValue(Double.valueOf(50));
    int count = 1;

    for (String chargePhone : Arrays.asList("15972368779", "13971553804")) {
      dto.setCustomerOrderNo(UUID.randomUUID().toString());
      dto.setChargePhone(chargePhone);
      client.setBizObject(dto);

      LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
      dto.setCustomerOrderNo(UUID.randomUUID().toString());
      if (count++ >= 2) {
        waitFor(client.excuteAsync());
      } else {
        LOGGER.info("excuteAsync：\n{}\n{}", client.excuteAsync().get(), repeat("-", 100));
      }

    }
  }

  /**
   * 获得商品信息
   *
   * @throws Exception
   */
  @Test
  public void productInfoGetTest() throws Exception {
    LOGGER.info("\n获得商品信息{}", repeat("=", 100));
    DefaultOpenApiClient client = new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_GOODS_GET);
    InputProductDto dto = new InputProductDto();
    int count = 1;

    for (String productId : Arrays
        .asList("10000498", "10000497", "10000496", "10000494", "10000492", "10000491", "10000490", "10000489",
            "10000488", "10000502")) {
      dto.setProductId(productId);
      client.setBizObject(dto);

      LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
      if (count++ >= 10) {
        waitFor(client.excuteAsync());
      } else {
        LOGGER.info("excuteAsync：\n{}\n{}", client.excuteAsync().get(), repeat("-", 100));
      }

    }
  }

  /**
   * 获得商品模板信息
   *
   * @throws Exception
   */
  @Test
  public void productTemplateGetTest() throws Exception {
    LOGGER.info("\n获得商品模板信息{}", repeat("=", 100));
    DefaultOpenApiClient client =
        new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_GOODS_TEMPLATE_GET);
    InputProductTemplateDto dto = new InputProductTemplateDto();
    dto.setTemplateId("e1dac0ea-dc86-4c9d-a778-c9e19203ecb8");
    client.setBizObject(dto);
    LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
    waitFor(client.excuteAsync());
  }

  /**
   * 流量下单
   *
   * @throws Exception
   */
  @Test
  public void trafficOrderAddTest() throws Exception {
    LOGGER.info("\n流量下单{}", repeat("=", 100));
    DefaultOpenApiClient client =
        new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_TRAFFIC_ORDER_ADD);
    InputTrafficOrderDto dto = new InputTrafficOrderDto();
    dto.setChargeValue(Double.valueOf(1024));
    dto.setPacketKind(4);

    int count = 1;
    for (String chargePhone : Arrays.asList("15972368779", "13971553804")) {
      client.setBizObject(dto);
      dto.setChargePhone(chargePhone);
      dto.setCustomerOrderNo(UUID.randomUUID().toString());

      LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
      dto.setCustomerOrderNo(UUID.randomUUID().toString());
      if (count++ >= 2) {
        waitFor(client.excuteAsync());
      } else {
        LOGGER.info("excuteAsync：\n{}\n{}", client.excuteAsync().get(), repeat("-", 100));
      }

    }
  }

  /**
   * 用户信息
   *
   * @throws Exception
   */
  @Test
  public void userInfoGetTest() throws Exception {
    LOGGER.info("\n用户信息{}", repeat("=", 100));
    DefaultOpenApiClient client =
        new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_USER_INFO_GET);
    InputUserDto dto = new InputUserDto();
    client.setBizObject(dto);

    LOGGER.info("excute：\n{}\n{}", client.excute(), repeat("-", 100));
    waitFor(client.excuteAsync());
  }

  /**
   * 用户信息：高并发场景
   *
   * @throws Exception
   */
  @Test
  public void userInfoGetTest2() throws Exception {
    LOGGER.info("\n用户信息：高并发场景{}", repeat("=", 100));
    Executor executor = Executors.newCachedThreadPool();

    final int count = 10;
    final CountDownLatch downLatch = new CountDownLatch(count);

    for (int i = 0; i < count; ++i) {
      executor.execute(new Runnable() {
        @Override
        public void run() {
          try {
            String name = Thread.currentThread().getName();
            LOGGER.info("\n线程名：{}{}", name, repeat("+", 100));

            DefaultOpenApiClient client =
                new DefaultOpenApiClient(URL, APP_KEY, SYS_SECRET, MethodConst.OPEN_API_USER_INFO_GET);
            InputUserDto dto = new InputUserDto();
            client.setBizObject(dto);

            LOGGER.info("\n线程名：{}\n{}\n{}", name, client.excute(), repeat("+", 100));
            waitFor(client.excuteAsync());
          } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
          } finally {
            downLatch.countDown();
          }

        }
      });
    }

    downLatch.await();
  }
}
