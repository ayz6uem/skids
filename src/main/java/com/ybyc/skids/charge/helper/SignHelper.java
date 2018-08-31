package com.ybyc.skids.charge.helper;

import com.ybyc.skids.charge.common.model.EncryptRequestData;
import com.ybyc.skids.charge.common.model.EncryptResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Slf4j
public class SignHelper {

    String secret;

    public SignHelper(String secret) {
        this.secret = secret;
    }

    /**
     * 获取请求参数的签名
     *
     * @param standardRequestData
     * @return
     */
    public void sign(EncryptRequestData standardRequestData) {

        String signData = standardRequestData.getOperatorID() + standardRequestData.getData() + standardRequestData.getTimeStamp() + standardRequestData.getSeq();
        String signResult = HmacMd5(signData).toUpperCase();

        standardRequestData.setSig(signResult);
    }

    /**
     * 校验参数的签名
     *
     * @param standardRequestData
     * @return
     */
    public boolean check(EncryptRequestData standardRequestData) {
        String signData = standardRequestData.getOperatorID() + standardRequestData.getData() + standardRequestData.getTimeStamp() + standardRequestData.getSeq();
        String signResult = HmacMd5(signData).toUpperCase();
        return Objects.equals(standardRequestData.getSig(),signResult);
    }

    /**
     * 获取响应参数签名
     *
     * @param standardResponseData 响应参数
     * @return
     */
    public void sign(EncryptResponseData standardResponseData) {
        //待签名数据
        String signString = String.valueOf(standardResponseData.getRet()) + standardResponseData.getMsg() + standardResponseData.getData();
        String sign = HmacMd5(signString).toUpperCase();

        standardResponseData.setSig(sign);
    }

    /**
     * 校验参数签名
     *
     * @param standardResponseData 响应参数
     * @return
     */
    public boolean check(EncryptResponseData standardResponseData) {
        //待签名数据
        String signString = String.valueOf(standardResponseData.getRet()) + standardResponseData.getMsg() + standardResponseData.getData();
        String sign = HmacMd5(signString).toUpperCase();

        return Objects.equals(standardResponseData.getSig(),sign);
    }

    private String HmacMd5(String data) {
        log.debug("待签名数据：{}", data);
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        try {
            SecretKeySpec key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);
            return new String(encodeHex(mac.doFinal(data.getBytes("UTF-8"))));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("HmacMd5 签名异常", e);
            throw new RuntimeException(e);
        }
    }


    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected static char[] encodeHex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }

}
