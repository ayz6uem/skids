package com.ybyc.skids.charge.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class AesCodec {

    private byte[] secret;
    private IvParameterSpec iv;

    public AesCodec(String secret, String iv) {
        this.secret = secret.getBytes();
        this.iv = new IvParameterSpec(iv.getBytes());
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String cbcEncrypt(String data){
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret, "AES");
            //使用CBC模式，需要一个向量iv, 固定16位，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
            //此处使用BASE64做转码。
            String encode = Base64.encodeBase64String(encrypted);
            return encode.replaceAll("\\s|\\r|\\n|\\t", "");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new IllegalArgumentException("加密失败");
        }
    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
    public String cbcDecrypt(String data) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            //先用base64解密
            byte[] encrypted = Base64.decodeBase64(data);
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, "UTF-8");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new IllegalArgumentException("解密失败");
        }
    }

}
