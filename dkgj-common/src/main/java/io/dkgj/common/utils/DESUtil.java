package io.dkgj.common.utils;



import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 DES加密介绍
 DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现
 。
 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 */
public class DESUtil {

    //测试
    public static void main(String args[]) {
        //待加密内容
        //密码，长度要是8的倍数
        try {
            //String url = URLEncoder.encode("https://www.jiyawangluo.com/dkm-api/api/v1/getVcode?mobile=dg2FWoobJ+kGofsIXy3jkQ==&chCode=swp&chName=%E8%B4%B7%E6%AC%BE%E5%96%B5","utf-8");
            //System.out.println(url);

            String encStr = URLDecoder.decode("dg2FWoobJ%2BkGofsIXy3jkQ%3D%3D", "utf-8");
            System.out.println(encStr);
            String str = decode("dkmzz123","dkmzz123".getBytes(),encStr);
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String decode(String secretKey, byte[] iv, String encryptText) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
        return new String(decryptData);
    }
}
