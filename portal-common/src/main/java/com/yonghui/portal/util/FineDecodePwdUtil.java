package com.yonghui.portal.util;

/**
 * 帆软解密密码工具类
 * Created by liuwei on 2017/6/14.
 */
public class FineDecodePwdUtil {

    private static final int[] PASSWORD_MASK_ARRAY = { 19, 78, 10, 15, 100, 213, 43, 23 };

    public static String passwordDecode(String paramString)
    {
        if ((paramString != null) && (paramString.startsWith("___")))
        {
            paramString = paramString.substring(3);
            StringBuilder localStringBuilder = new StringBuilder();
            int i = 0;
            for (int j = 0; j <= paramString.length() - 4; j += 4)
            {
                if (i == PASSWORD_MASK_ARRAY.length)
                    i = 0;
                String str = paramString.substring(j, j + 4);
                int k = Integer.parseInt(str, 16) ^ PASSWORD_MASK_ARRAY[i];
                localStringBuilder.append((char)k);
                i++;
            }
            paramString = localStringBuilder.toString();
        }
        return paramString;
    }
}
