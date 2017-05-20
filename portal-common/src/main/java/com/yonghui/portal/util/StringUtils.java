package com.yonghui.portal.util;

import java.util.Random;


public class StringUtils {

    /**
     * <p>
     * Random object used by random method. This has to be not local to the
     * random method so as to not return the same value in the same millisecond.
     * </p>
     */
    private static final Random RANDOM = new Random();

    private StringUtils() {
    }

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查对象是否为数字型字符串,包含负数开头的。
     */
    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        }
        char[] chars = obj.toString().toCharArray();
        int length = chars.length;
        if (length < 1)
            return false;

        int i = 0;
        if (length > 1 && chars[0] == '-')
            i = 1;

        for (; i < length; i++) {
            if (!Character.isDigit(chars[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 把通用字符编码的字符串转化为汉字编码。
     */
    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if (!isEmpty(unicode)) {
            for (int i = 0; i < unicode.length(); i++) {
                out.append(unicode.charAt(i));
            }
        }
        return out.toString();
    }

    public static String toUnderlineStyle(String name) {
        StringBuilder newName = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    newName.append("_");
                }
                newName.append(Character.toLowerCase(c));
            } else {
                newName.append(c);
            }
        }
        return newName.toString();
    }

    public static String convertString(byte[] data, int offset, int length) {
        if (data == null) {
            return null;
        } else {
            try {
                return new String(data, offset, length, Const.UTF8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static byte[] convertBytes(String data) {
        if (data == null) {
            return null;
        } else {
            try {
                return data.getBytes(Const.UTF8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(100);
        for (Object foo : array) {
            sb.append(String.valueOf(foo)).append(separator);
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        if (sb.length() > 0) {
            return sb.toString();
        }
        return null;
    }

    /**
     * <p>
     * Creates a random string based on a variety of options, using supplied
     * source of randomness.
     * </p>
     * <p>
     * <p>
     * If start and end are both {@code 0}, start and end are set to {@code ' '}
     * and {@code 'z'}, the ASCII printable characters, will be used, unless
     * letters and numbers are both {@code false}, in which case, start and end
     * are set to {@code 0} and {@code Integer.MAX_VALUE}.
     * <p>
     * <p>
     * If set is not {@code null}, characters between start and end are chosen.
     * </p>
     * <p>
     * <p>
     * This method accepts a user-supplied {@link Random} instance to use as a
     * source of randomness. By seeding a single {@link Random} instance with a
     * fixed seed and using it for each call, the same random sequence of
     * strings can be generated repeatedly and predictably.
     * </p>
     *
     * @param count   the length of random string to create
     * @param start   the position in set of chars to start at
     * @param end     the position in set of chars to end before
     * @param letters only allow letters?
     * @param numbers only allow numbers?
     * @param chars   the set of chars to choose randoms from. If {@code null}, then
     *                it will use the set of all chars.
     * @param random  a source of randomness.
     * @return the random string
     * @throws ArrayIndexOutOfBoundsException if there are not {@code (end - start) + 1} characters in the
     *                                        set array.
     * @throws IllegalArgumentException       if {@code count} &lt; 0.
     * @since 2.0
     */
    public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        if (start == 0 && end == 0) {
            end = 'z' + 1;
            start = ' ';
            if (!letters && !numbers) {
                start = 0;
                end = Integer.MAX_VALUE;
            }
        }

        char[] buffer = new char[count];
        int gap = end - start;

        while (count-- != 0) {
            char ch;
            if (chars == null) {
                ch = (char) (random.nextInt(gap) + start);
            } else {
                ch = chars[random.nextInt(gap) + start];
            }
            if (letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters && !numbers) {
                if (ch >= 56320 && ch <= 57343) {
                    if (count == 0) {
                        count++;
                    } else {
                        // low surrogate, insert high surrogate after putting it
                        // in
                        buffer[count] = ch;
                        count--;
                        buffer[count] = (char) (55296 + random.nextInt(128));
                    }
                } else if (ch >= 55296 && ch <= 56191) {
                    if (count == 0) {
                        count++;
                    } else {
                        // high surrogate, insert low surrogate before putting
                        // it in
                        buffer[count] = (char) (56320 + random.nextInt(128));
                        count--;
                        buffer[count] = ch;
                    }
                } else if (ch >= 56192 && ch <= 56319) {
                    // private high surrogate, no effing clue, so skip it
                    count++;
                } else {
                    buffer[count] = ch;
                }
            } else {
                count++;
            }
        }
        return new String(buffer);
    }

    /**
     * <p>
     * Creates a random string whose length is the number of characters
     * specified.
     * </p>
     * <p>
     * <p>
     * Characters will be chosen from the set of alpha-numeric characters as
     * indicated by the arguments.
     * </p>
     *
     * @param count   the length of random string to create
     * @param letters if {@code true}, generated string will include alphabetic
     *                characters
     * @param numbers if {@code true}, generated string will include numeric
     *                characters
     * @return the random string
     */
    public static String random(int count, boolean letters, boolean numbers) {
        return random(count, 0, 0, letters, numbers);
    }

    /**
     * <p>
     * Creates a random string whose length is the number of characters
     * specified.
     * </p>
     * <p>
     * <p>
     * Characters will be chosen from the set of alpha-numeric characters as
     * indicated by the arguments.
     * </p>
     *
     * @param count   the length of random string to create
     * @param start   the position in set of chars to start at
     * @param end     the position in set of chars to end before
     * @param letters if {@code true}, generated string will include alphabetic
     *                characters
     * @param numbers if {@code true}, generated string will include numeric
     *                characters
     * @return the random string
     */
    public static String random(int count, int start, int end, boolean letters, boolean numbers) {
        return random(count, start, end, letters, numbers, null, RANDOM);
    }

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     *
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 封装存储过程参数
     */
    public static String getParameter(String parameter, String proParameter) {
        // 存储过程未定义参数
        if (StringUtils.isEmpty(proParameter)) {
            return null;
        }
        // 组装参数
        String[] arr = parameter.split("@@");
        String[] arrPro = proParameter.split("@@");
        StringBuffer sb = new StringBuffer();
        boolean tag = false;
        for (String pro : arrPro) {
            tag = false;
            for (String p : arr) {
                if (pro.equals(p.split("=")[0])) {
                    tag = true;
                    if (p.split("=").length == 1) {
                        sb.append("null,");
                    } else if (StringUtils.isEmpty(p.split("=")[1])) {
                        sb.append("null,");
                    } else if (!StringUtils.isEmpty(p.split("=")[1])) {
                        sb.append("'" + p.split("=")[1] + "',");
                    }
                    break;
                }
            }
            if (!tag) {
                sb.append("null,");
            }
        }
        String str = sb.toString();
        return str.substring(0, str.length() - 1);
    }

    /**
     * 封装执行的sql语句
     *
     * @param sql          执行sql
     * @param parameter    请求参数
     * @param sqlParameter sql定义参数
     * @return
     */
    public static String getSqlByParameter(String sql, String parameter, String sqlParameter) {
        // 未定义参数
        if (StringUtils.isEmpty(sqlParameter)) {
            return sql;
        }
        // 组装参数
        String[] arr = parameter.split("@@");
        String[] arrSqlParameter = sqlParameter.split("@@");
        boolean tag = false;
        for (String exeSqlParameter : arrSqlParameter) {
            String exeSqlParameterTag = "#" + exeSqlParameter + "#";
            tag = false;
            for (String p : arr) {
                if (exeSqlParameter.equals(p.split("=")[0])) {
                    tag = true;
                    if (p.split("=").length == 1) {
                        tag = false;
                    } else if (StringUtils.isEmpty(p.split("=")[1])) {
                        tag = false;
                    } else if (!StringUtils.isEmpty(p.split("=")[1])) {
                        sql = sql.replace(exeSqlParameterTag, p.split("=")[1]);
                    }
                    break;
                }
            }
            if (!tag) {
                sql = sql.replace(exeSqlParameterTag, "");
            }
        }
        return sql;
    }

    /**
     * 测试封装 存储过程参数，sql语句参数
     *
     * @param args
     */
    /*public static void main(String args[]) {
        // 测试封装存储过程参数
        String parameter1 = "parametersdate=2017-05-01@@holdname=@@reportlabel=2@@edate=2017-05-10";
        String proParameter = "reportLabel@@sDate@@eDate@@holdName@@classId@@areaId@@areaMans@@shopId@@para";
        System.out.println(getParameter(parameter1, proParameter));

        // 测试封装存储过程参数
        String parameter14 = "test_param_e=5@@test_param_b=@@test_param_a=1";
        String proParameter4 = "test_param_a";
        System.out.println(getParameter(parameter14, proParameter4));

        // 测试封装执行sql语句
        String sql = "SELECT * from fresh.dim_category_fresh_tree where category like '%#category#%' and categoryID like '%#categoryID#%' and categoryPID like '%#categoryID#%' and id=#id#";
        String parameter2 = "category=干@@categoryID=1@@categoryPID=1@@id=2";
        String sqlParameter = "category@@categoryID@@categoryPID@@id";
        System.out.println(getSqlByParameter(sql, parameter2, sqlParameter));

        // 测试封装执行sql语句
        String sql3 = "SELECT * from fresh.dim_category_fresh_tree where category like '%#category#%'";
        String parameter3 = "category=干";
        String sqlParameter3 = "category";
        System.out.println(getSqlByParameter(sql3, parameter3, sqlParameter3));
    }*/

}