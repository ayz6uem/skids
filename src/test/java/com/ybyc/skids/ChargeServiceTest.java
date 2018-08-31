package com.ybyc.skids;

import com.ybyc.skids.charge.common.AccessTemplate;
import com.ybyc.skids.charge.common.Operator;
import com.ybyc.skids.charge.common.model.AccessToken;
import com.ybyc.skids.charge.common.model.RequestData;
import com.ybyc.skids.charge.common.model.ResponseData;
import com.ybyc.skids.charge.param.QueryTokenParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChargeServiceTest {

    @Resource
    AccessTemplate accessTemplate;

    @Test
    public void test1(){
        String url = "http://127.0.0.1:9810/charge/exchange/query_token";
        RequestData requestData = new RequestData();
        requestData.setOperatorID(Operator.PLATFORM_OPERATOR_ID);
        QueryTokenParam queryTokenParam = new QueryTokenParam();
        queryTokenParam.setOperatorId(Operator.PLATFORM_OPERATOR_ID);
        queryTokenParam.setOperatorSecret(Operator.PLATFORM_OPERATOR_SECRET);
        requestData.setData(queryTokenParam);

        ResponseData<AccessToken> responseData = accessTemplate.post(url,requestData, AccessToken.class);
        System.out.println(responseData);
    }

}
