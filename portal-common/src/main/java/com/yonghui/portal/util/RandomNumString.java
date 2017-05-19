package com.yonghui.portal.util;

import java.util.Random;
import java.util.UUID;

/**
 * Created by 张海 on 2017/3/13.
 */
public class RandomNumString {
    private static char ch[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', '0', '1'};//最后又重复两个0和1，因为需要凑足数组长度为64

    private static Random random = new Random();

    //生成指定长度的随机字符串
    public static synchronized String createRandomString(int length) {
        if (length > 0) {
            int index = 0;
            char[] temp = new char[length];
            int num = random.nextInt();
            for (int i = 0; i < length % 5; i++) {
                temp[index++] = ch[num & 63];//取后面六位，记得对应的二进制是以补码形式存在的。
                num >>= 6;//63的二进制为:111111
                // 为什么要右移6位？因为数组里面一共有64个有效字符。为什么要除5取余？因为一个int型要用4个字节表示，也就是32位。
            }
            for (int i = 0; i < length / 5; i++) {
                num = random.nextInt();
                for (int j = 0; j < 5; j++) {
                    temp[index++] = ch[num & 63];
                    num >>= 6;
                }
            }
            return new String(temp, 0, length);
        } else if (length == 0) {
            return "";
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 生成 长度大于 32位的密文字符串
     *
     * @param length
     * @return
     */
    public static String createRandomStringAndUuid(int length) {
        length = length >= 32 ? length : 32;
        return createRandomString(length - 32) + createUuid();
    }

    /**
     * 根据用户id生成 token 长度大于40位
     *
     * @param length
     * @return
     */
    public static String createToken(String userId, int length) {
        length = length >= (32 + userId.length()) ? length : (32 + userId.length());
        return userId + createRandomString(length - 32 - userId.length()) + createUuid();
    }

    /**
     * 获取uuid
     *
     * @return
     */
    public static String createUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 测试 生成 60 位的密文
     *
     * @param args
     */
    public static void main(String[] args) {
        String randomString = createRandomStringAndUuid(60);
        System.out.println(randomString);
        String tokenStr = createToken("12345678", 60);
        System.out.println(tokenStr);

    }
}
