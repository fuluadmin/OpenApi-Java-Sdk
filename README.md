# OpenApi-Java-Sdk
OpenApi2.0 java版本的sdk  

第三方依赖：commons-codec-1.11.jar、gson-2.8.5.jar、bcprov-jdk15on.jar；  

卡密解密中对于强加密长度超过128的，需要替换jre/lib/security下两个jar包，“ocal_policy.jar ”和“US_export_policy.jar”下载及参考文档：https://blog.csdn.net/tomatocc/article/details/85096911
  
直充下单接口示例：
``` java
	DefaultOpenApiClient client =
                        new DefaultOpenApiClient(ApiUrl.getSelectedItem().toString(), AppKey.getText(), SysSecret.getText(), MethodConst.OPEN_API_DIRECT_ORDER_ADD);
    InputDirectOrderDto dto = new InputDirectOrderDto();
    dto.setCustomerOrderNo(CustomerOrderNo.getText());
    dto.setProductId(Integer.valueOf(ProductId.getSelectedItem().toString()));
    dto.setBuyNum(Integer.valueOf(NumValue.getText()));
    dto.setChargeAccount(ChargeAccountTemplateId.getText());

    client.setBizObject(dto);
    String result = client.excute();
```
  
话费下单接口示例：
``` java
	DefaultOpenApiClient client =
                        new DefaultOpenApiClient(ApiUrl.getSelectedItem().toString(), AppKey.getText(), SysSecret.getText(), MethodConst.OPEN_API_PHONE_ORDER_ADD);
    InputPhoneOrderDto dto = new InputPhoneOrderDto();
    dto.setCustomerOrderNo(CustomerOrderNo.getText());
    dto.setChargePhone(ChargeAccountTemplateId.getText());
    dto.setChargeValue(Double.valueOf(NumValue.getText()));

    client.setBizObject(dto);
    String result = client.excute();
```    
  
流量下单接口示例：
``` java
	DefaultOpenApiClient client =
                        new DefaultOpenApiClient(ApiUrl.getSelectedItem().toString(), AppKey.getText(), SysSecret.getText(), MethodConst.OPEN_API_TRAFFIC_ORDER_ADD);
    InputTrafficOrderDto dto = new InputTrafficOrderDto();
    dto.setCustomerOrderNo(CustomerOrderNo.getText());
    dto.setChargePhone(ChargeAccountTemplateId.getText());
    dto.setChargeValue(Double.valueOf(NumValue.getText()));
    dto.setPacketKind(4);

    client.setBizObject(dto);
    String result = client.excute();
```
  
卡密下单接口示例：
``` java
	DefaultOpenApiClient client =
                        new DefaultOpenApiClient(ApiUrl.getSelectedItem().toString(), AppKey.getText(), SysSecret.getText(), MethodConst.OPEN_API_CARD_ORDER_ADD);
    InputCardOrderDto dto = new InputCardOrderDto();
    dto.setCustomerOrderNo(CustomerOrderNo.getText());
    dto.setProductId(Integer.valueOf(ProductId.getSelectedItem().toString()));
    dto.setBuyNum(Integer.valueOf(NumValue.getText()));

    client.setBizObject(dto);
    String result = client.excute();
```

查单接口示例：
``` java
	DefaultOpenApiClient client =
                        new DefaultOpenApiClient(ApiUrl.getSelectedItem().toString(), AppKey.getText(), SysSecret.getText(), MethodConst.OPEN_API_ORDER_GET);
    InputOrderGetDto dto = new InputOrderGetDto();
    dto.setCustomerOrderNo(CustomerOrderNo.getText());

    client.setBizObject(dto);
    String result = client.excute();
```
  
用户信息接口示例：
``` java
	DefaultOpenApiClient client =
                        new DefaultOpenApiClient(ApiUrl.getSelectedItem().toString(), AppKey.getText(), SysSecret.getText(), MethodConst.OPEN_API_USER_INFO_GET);
    InputUserDto dto = new InputUserDto();

    client.setBizObject(dto);
    String result = client.excute();
```    
  
商品信息接口示例：
``` java
	DefaultOpenApiClient client =
                        new DefaultOpenApiClient(ApiUrl.getSelectedItem().toString(), AppKey.getText(), SysSecret.getText(), MethodConst.OPEN_API_GOODS_GET);
    InputProductDto dto = new InputProductDto();
    dto.setProductId(ProductId.getSelectedItem().toString());

    client.setBizObject(dto);
    String result = client.excute();
```
  
商品模板接口示例：
``` java
	DefaultOpenApiClient client =
                        new DefaultOpenApiClient(ApiUrl.getSelectedItem().toString(), AppKey.getText(), SysSecret.getText(), MethodConst.OPEN_API_GOODS_TEMPLATE_GET);
    InputProductTemplateDto dto = new InputProductTemplateDto();
    dto.setTemplateId(ChargeAccountTemplateId.getText());

    client.setBizObject(dto);
    String result = client.excute();       
```    
  
手机号归属地接口示例：
``` java
	DefaultOpenApiClient client =
                        new DefaultOpenApiClient(ApiUrl.getSelectedItem().toString(), AppKey.getText(), SysSecret.getText(), MethodConst.OPEN_API_CHECK_PHONE);
    InputMatchPhoneProductListDto dto = new InputMatchPhoneProductListDto();
    dto.setPhone(ChargeAccountTemplateId.getText());
    dto.setFaceValue(Double.valueOf(NumValue.getText()));

    client.setBizObject(dto);
    String result = client.excute();                                         
```    
  
卡密解密示例：
``` java
	String sysSecret = "0a091b3aa4324435aab703142518a8f7";
    String cardNumber = "12nCp6X/nALmrvr1erxK+D4L8n/kqz/RItKWUfvZrCU=";
    String cardPwd = "9HeOgdv+NpLihh2+5Gm0Mj4L8n/kqz/RItKWUfvZrCU=";
    String no = CardUtil.cardDecode(cardNumber, sysSecret.getBytes());
    String pwd = CardUtil.cardDecode(cardPwd, sysSecret.getBytes());
```    