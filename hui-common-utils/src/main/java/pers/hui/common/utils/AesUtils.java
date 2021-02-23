package pers.hui.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * <code>AesUtils</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/7 15:01.
 *
 * @author Gary.Hu
 */
public class AesUtils {
    /**
     * 加密的密码
     */
    public static final String sKey = "Hu_hui";
    /**
     * 使用AES算法加密
     */
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 默认的加密算法
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final String charsetName = "utf-8";

    /**
     * AES 加密操作
     *
     * @param content  加密前的内容
     * @param password 加密的密码
     * @return 返回Base64转码后的加密数据
     * @author : Hu weihui
     */
    public static String encrypt(final String content, String password) throws Exception {
        // 初始化密码器
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

        byte[] byteContent = content.getBytes();

        // 初始化为加密模式的密码器
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));

        // 加密处理
        byte[] result = cipher.doFinal(byteContent);

        //通过Base64转码返回
        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);

        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

        //执行操作
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));

        return new String(result, charsetName);

    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

        kg.init(128, new SecureRandom(password.getBytes()));

        SecretKey secretKey = kg.generateKey();

        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);

    }

}
