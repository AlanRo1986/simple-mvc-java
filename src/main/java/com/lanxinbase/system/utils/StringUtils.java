package com.lanxinbase.system.utils;

import java.util.*;

/**
 * 字符串工具类
 */
public class StringUtils {

    public StringUtils() {}

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public static boolean isEmptyTrim(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean hasLength(CharSequence str) {
        return !isEmpty(str);
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence)str);
    }

    /**
     * 判断指定字符对象是否包含特殊字符：空格、tab键、换行符。
     * @param str
     * @return
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence)str);
    }

    /**
     * 判断指定字符是否为空白字符，空白符包含：空格、tab键、换行符。
     * @param str 要检测的字符串
     * @return
     */
    public static boolean containsWhitespace(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for(int i = 0; i < strLen; ++i) {
                if (Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean containsWhitespace(String str) {
        return containsWhitespace((CharSequence)str);
    }

    /**
     * 删除空白符包含：空格、tab键、换行符
     * @param str 字符串
     * @return 返回处理过的字符串
     */
    public static String trimWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
                sb.deleteCharAt(0);
            }

            while(sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
                sb.deleteCharAt(sb.length() - 1);
            }

            return sb.toString();
        }
    }

    /**
     * 删除空白符包含：空格、tab键、换行符
     * 如：trimAllWhitespace("0 1 2   24 ")=>01224
     * @param str 字符串
     * @return 返回处理过的字符串
     */
    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        } else {
            int len = str.length();
            StringBuilder sb = new StringBuilder(str.length());

            for(int i = 0; i < len; ++i) {
                char c = str.charAt(i);
                if (!Character.isWhitespace(c)) {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    /**
     * 删除左边的空字符
     * @param str 要处理的字符串
     * @return 返回处理后的字符串
     */
    public static String trimLeftWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
                sb.deleteCharAt(0);
            }

            return sb.toString();
        }
    }

    /**
     * 删右边空白字符
     * @param str 要处理的字符串
     * @return 返回处理后的字符串
     */
    public static String trimRightWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
                sb.deleteCharAt(sb.length() - 1);
            }

            return sb.toString();
        }
    }

    /**
     * 删除左边指定的字符
     * @param str 原字符串
     * @param leadingCharacter 要删除的字符串
     * @return 返回处理后的字符串
     */
    public static String trimLeftCharacter(String str, char leadingCharacter) {
        if (!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
                sb.deleteCharAt(0);
            }

            return sb.toString();
        }
    }

    /**
     * 删除右边指定的字符
     * @param str 原字符串
     * @param trailingCharacter 要删除的字符串
     * @return 返回处理后的字符串
     */
    public static String trimRightCharacter(String str, char trailingCharacter) {
        if (!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
                sb.deleteCharAt(sb.length() - 1);
            }

            return sb.toString();
        }
    }

    /**
     * 确定此字符串实例的开头是否与指定的字符串匹配。
     * out(StringUtils.startsWithIgnoreCase("154848n.po",".1"));
     * out(StringUtils.startsWithIgnoreCase("N154848","n"));
     * @param str 要检测的字符串
     * @param prefix 指定的字符串匹配(不区分大小写)
     * @return true|false
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str != null && prefix != null) {
            if (str.startsWith(prefix)) {
                return true;
            } else if (str.length() < prefix.length()) {
                return false;
            } else {
                String lcStr = str.substring(0, prefix.length()).toLowerCase();
                String lcPrefix = prefix.toLowerCase();
                return lcStr.equals(lcPrefix);
            }
        } else {
            return false;
        }
    }

    /**
     * 确定此字符串实例的后缀是否与指定的字符串匹配。
     * out(StringUtils.endsWithIgnoreCase("N154848n.po",".po"));
     * out(StringUtils.endsWithIgnoreCase("N154848N","n"));
     * @param str 要检测的字符串
     * @param suffix 指定的字符串匹配(不区分大小写)
     * @return true|false
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if (str != null && suffix != null) {
            if (str.endsWith(suffix)) {
                return true;
            } else if (str.length() < suffix.length()) {
                return false;
            } else {
                String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
                String lcSuffix = suffix.toLowerCase();
                return lcStr.equals(lcSuffix);
            }
        } else {
            return false;
        }
    }

    /**
     * 按指定的字符串及位置进行匹配字符串
     *
     * out(StringUtils.substringMatch("N154848n.po",2,"5")); is true
     * out(StringUtils.substringMatch("N154848n.po",0,"5")); is false
     *
     * @param str 原来的字符串
     * @param index 要匹配字符串的位置
     * @param substring 要匹配的字符串
     * @return true|false
     */
    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        for(int j = 0; j < substring.length(); ++j) {
            int i = index + j;
            if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 统计指定的字符在原来字符串出现的次数
     * out(StringUtils.countOccurrencesOf("N15485555555548n.po","5"));
     * out(StringUtils.countOccurrencesOf("N154848n.po","po"));
     * @param str 原字符串
     * @param sub 要统计的字符串
     * @return >= 0
     */
    public static int countOccurrencesOf(String str, String sub) {
        if (hasLength(str) && hasLength(sub)) {
            int count = 0;

            int idx;
            for(int pos = 0; (idx = str.indexOf(sub, pos)) != -1; pos = idx + sub.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    /**
     * 字符串替换
     * @param inString 原字符串
     * @param oldPattern 要被替换的字符串
     * @param newPattern 要替换成的字符串
     * @return 返回处理后的字符串
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
            int index = inString.indexOf(oldPattern);
            if (index == -1) {
                return inString;
            } else {
                int capacity = inString.length();
                if (newPattern.length() > oldPattern.length()) {
                    capacity += 16;
                }

                StringBuilder sb = new StringBuilder(capacity);
                int pos = 0;

                for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                    sb.append(inString.substring(pos, index));
                    sb.append(newPattern);
                    pos = index + patLen;
                }

                sb.append(inString.substring(pos));
                return sb.toString();
            }
        } else {
            return inString;
        }
    }

    /**
     * 删除指定的字符串
     * @param inString 原字符串
     * @param pattern 需要删除的字符串
     * @return 返回处理后的字符串
     */
    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    /**
     * 删除指定的字符串
     * out(StringUtils.deleteAny("N15485555555548n.po","5"));
     * @param inString 原字符串
     * @param charsToDelete 需要删除的字符串
     * @return 返回处理后的字符串
     */
    public static String deleteAny(String inString, String charsToDelete) {
        if (hasLength(inString) && hasLength(charsToDelete)) {
            StringBuilder sb = new StringBuilder(inString.length());

            for(int i = 0; i < inString.length(); ++i) {
                char c = inString.charAt(i);
                if (charsToDelete.indexOf(c) == -1) {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return inString;
        }
    }

    /**
     * 给字符串加单引号
     * @param str 需要添加单引号的字符串
     * @return null|string
     */
    public static String quote(String str) {
        return str != null ? "'" + str + "'" : null;
    }

    public static String quote2(String str) {
        return !isEmpty(str) ? "<" + str + ">" : null;
    }

    /**
     * 如果是字符串对象则添加单引号
     * @param obj 对象
     * @return 处理后的对象
     */
    public static Object quoteIfString(Object obj) {
        return obj instanceof String ? quote((String)obj) : obj;
    }

    /**
     * 通过字符串“.”截取字符串后缀
     * out(StringUtils.unqualify("N154848n.text"));:text
     * @param qualifiedName 需要截取的字符串
     * @return 返回截取后的后缀名
     */
    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    /**
     * 通过指定的字符截取字符串后面的内容
     * out(StringUtils.unqualify("text.po","t".charAt(0)));=>.PO
     * @param qualifiedName 需要截取的字符串
     * @param separator 分隔符
     * @return 返回截取后的字符串
     */
    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    /**
     * 首字母大小
     * out(StringUtils.capitalize("text"));=>Text
     * @param str 需要处理的字符串
     * @return 返回处理后的字符串
     */
    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    /**
     * 首字母小写
     * @param str 需要处理的字符串
     * @return 返回处理后的字符串
     */
    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (!hasLength(str)) {
            return str;
        } else {
            char baseChar = str.charAt(0);
            char updatedChar;
            if (capitalize) {
                updatedChar = Character.toUpperCase(baseChar);
            } else {
                updatedChar = Character.toLowerCase(baseChar);
            }

            if (baseChar == updatedChar) {
                return str;
            } else {
                char[] chars = str.toCharArray();
                chars[0] = updatedChar;
                return new String(chars, 0, chars.length);
            }
        }
    }

    /**
     * 根据系统文件路径，获取文件名
     * out(StringUtils.getFilename("/www/cat/ok.text")); => ok.text
     * @param path 路径字符串
     * @return null|String
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        } else {
            int separatorIndex = path.lastIndexOf("/");
            return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
        }
    }

    /**
     * 根据系统文件路径，获取文件名后缀
     * out(StringUtils.getFilename("/www/cat/ok.text")); => text
     * @param path 路径字符串
     * @return null|String
     */
    public static String getFilenameExtension(String path) {
        if (path == null) {
            return null;
        } else {
            int extIndex = path.lastIndexOf(46);
            if (extIndex == -1) {
                return null;
            } else {
                int folderIndex = path.lastIndexOf("/");
                return folderIndex > extIndex ? null : path.substring(extIndex + 1);
            }
        }
    }

    /**
     * 根据系统文件路径，删除文件后缀
     * out(StringUtils.getFilename("/www/cat/ok.text")); => /www/cat/ok
     * @param path 路径字符串
     * @return null|String
     */
    public static String stripFilenameExtension(String path) {
        if (path == null) {
            return null;
        } else {
            int extIndex = path.lastIndexOf(46);
            if (extIndex == -1) {
                return path;
            } else {
                int folderIndex = path.lastIndexOf("/");
                return folderIndex > extIndex ? path : path.substring(0, extIndex);
            }
        }
    }

    /**
     * 应用相对路径
     * out(StringUtils.applyRelativePath("/www/cat/ok.text","../abs/a/b/d")); => /www/cat/../abs/a/b/d
     * @param path 原路径
     * @param relativePath 相对路径
     * @return string
     */
    public static String applyRelativePath(String path, String relativePath) {
        int separatorIndex = path.lastIndexOf("/");
        if (separatorIndex != -1) {
            String newPath = path.substring(0, separatorIndex);
            if (!relativePath.startsWith("/")) {
                newPath = newPath + "/";
            }

            return newPath + relativePath;
        } else {
            return relativePath;
        }
    }

    /**
     * 处理文件系统路径
     * @param path 需要处理的文件系统路径
     * @return string
     */
    public static String cleanPath(String path) {
        if (path == null) {
            return null;
        } else {
            String pathToUse = replace(path, "\\", "/");
            int prefixIndex = pathToUse.indexOf(":");
            String prefix = "";
            if (prefixIndex != -1) {
                prefix = pathToUse.substring(0, prefixIndex + 1);
                if (prefix.contains("/")) {
                    prefix = "";
                } else {
                    pathToUse = pathToUse.substring(prefixIndex + 1);
                }
            }

            if (pathToUse.startsWith("/")) {
                prefix = prefix + "/";
                pathToUse = pathToUse.substring(1);
            }

            String[] pathArray = split(pathToUse, "/");
            List<String> pathElements = new LinkedList();
            int tops = 0;

            int i;
            for(i = pathArray.length - 1; i >= 0; --i) {
                String element = pathArray[i];
                if (!".".equals(element)) {
                    if ("..".equals(element)) {
                        ++tops;
                    } else if (tops > 0) {
                        --tops;
                    } else {
                        pathElements.add(0, element);
                    }
                }
            }

            for(i = 0; i < tops; ++i) {
                pathElements.add(0, "..");
            }

            return prefix + collectionToDelimitedString(pathElements, "/");
        }
    }

    /**
     * 对比指定的两个文件系统路径
     * @param path1 文件路径1
     * @param path2 文件路径2
     * @return true|false
     */
    public static boolean pathEquals(String path1, String path2) {
        return cleanPath(path1).equals(cleanPath(path2));
    }

    /**
     * 解析本地化语言字符串
     * out(StringUtils.parseLocaleString("zh_CN"));
     * @param localeString 语言字符串
     * @return Locale
     */
    public static Locale parseLocaleString(String localeString) {
        String[] parts = split(localeString, "_ ");
        String language = parts.length > 0 ? parts[0] : "";
        String country = parts.length > 1 ? parts[1] : "";
        validateLocalePart(language);
        validateLocalePart(country);
        String variant = "";
        if (parts.length > 2) {
            int endIndexOfCountryCode = localeString.indexOf(country, language.length()) + country.length();
            variant = trimLeftWhitespace(localeString.substring(endIndexOfCountryCode));
            if (variant.startsWith("_")) {
                variant = trimLeftCharacter(variant, '_');
            }
        }

        return language.length() > 0 ? new Locale(language, country, variant) : null;
    }

    private static void validateLocalePart(String localePart) {
        for(int i = 0; i < localePart.length(); ++i) {
            char ch = localePart.charAt(i);
            if (ch != ' ' && ch != '_' && ch != '#' && !Character.isLetterOrDigit(ch)) {
                throw new IllegalArgumentException("Locale part \"" + localePart + "\" contains invalid characters");
            }
        }

    }

    /**
     * 本地化对象转换成对应的字符串
     * out(StringUtils.toLanguageTag(StringUtils.parseLocaleString("zh_CN"))); => zh-CN
     * @param locale 本地化对象
     * @return string
     */
    public static String toLanguageTag(Locale locale) {
        return locale.getLanguage() + (hasText(locale.getCountry()) ? "-" + locale.getCountry() : "");
    }

    /**
     * 解析国家时区
     * out((StringUtils.parseTimeZoneString("GMT+8"))); => sun.util.calendar.ZoneInfo[id="GMT+08:00",offset=28800000,dstSavings=0,useDaylight=false,transitions=0,lastRule=null]
     * @param timeZoneString 时区，必须以GMT开头
     * @return TimeZone
     */
    public static TimeZone parseTimeZoneString(String timeZoneString) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        if ("GMT".equals(timeZone.getID()) && !timeZoneString.startsWith("GMT")) {
            throw new IllegalArgumentException("Invalid time zone specification '" + timeZoneString + "'");
        } else {
            return timeZone;
        }
    }

    /**
     * 将指定的字符串添加到指定的字符串数组中
     * @param array 字符串数组
     * @param str 要添加到数组中的字符串
     * @return string[]
     */
    public static String[] addStringToArray(String[] array, String str) {
        if (ObjectUtils.isEmpty(array)) {
            return new String[]{str};
        } else {
            String[] newArr = new String[array.length + 1];
            System.arraycopy(array, 0, newArr, 0, array.length);
            newArr[array.length] = str;
            return newArr;
        }
    }

    /**
     * 合并字符串数组
     * @param array1 字符串数组1
     * @param array2 字符串数组2
     * @return string[]
     */
    public static String[] concatenateStringArrays(String[] array1, String[] array2) {
        if (ObjectUtils.isEmpty(array1)) {
            return array2;
        } else if (ObjectUtils.isEmpty(array2)) {
            return array1;
        } else {
            String[] newArr = new String[array1.length + array2.length];
            System.arraycopy(array1, 0, newArr, 0, array1.length);
            System.arraycopy(array2, 0, newArr, array1.length, array2.length);
            return newArr;
        }
    }

    /**
     * 合并字符串数组
     * @param array1 字符串数组1
     * @param array2 字符串数组2
     * @return string[]
     */
    public static String[] mergeStringArrays(String[] array1, String[] array2) {
        if (ObjectUtils.isEmpty(array1)) {
            return array2;
        } else if (ObjectUtils.isEmpty(array2)) {
            return array1;
        } else {
            List<String> result = new ArrayList();
            result.addAll(Arrays.asList(array1));
            String[] var3 = array2;
            int var4 = array2.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String str = var3[var5];
                if (!result.contains(str)) {
                    result.add(str);
                }
            }

            return toStringArray(result);
        }
    }

    /**
     * 字符串数组排序
     * @param array 需要排序的字符串数组
     * @return String[]
     */
    public static String[] sortStringArray(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return new String[0];
        } else {
            Arrays.sort(array);
            return array;
        }
    }

    /**
     * 字符串集合转成字符串数组
     * @param collection 字符串集合
     * @return String[]
     */
    public static String[] toStringArray(Collection<String> collection) {
        return collection == null ? null : collection.toArray(new String[collection.size()]);
    }

    /**
     * 字符串枚举转成字符串数组
     * @param enumeration 枚举对象
     * @return string[]
     */
    public static String[] toStringArray(Enumeration<String> enumeration) {
        if (enumeration == null) {
            return null;
        } else {
            List<String> list = Collections.list(enumeration);
            return (String[])list.toArray(new String[list.size()]);
        }
    }

    /**
     * 删除字符串数组中的空白字符串
     * @param array 需要删除空白字符串的数组
     * @return string[]
     */
    public static String[] trimArrayElements(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return new String[0];
        } else {
            String[] result = new String[array.length];

            for(int i = 0; i < array.length; ++i) {
                String element = array[i];
                result[i] = element != null ? element.trim() : null;
            }

            return result;
        }
    }

    /**
     * 删除字符串数组中重复的元素
     * @param array 需要处理的字符串数组
     * @return string[]
     */
    public static String[] removeDuplicateStrings(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return array;
        } else {
            Set<String> set = new LinkedHashSet();
            String[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String element = var2[var4];
                set.add(element);
            }

            return toStringArray(set);
        }
    }

    /**
     * 分割字符串
     * @param toSplit 需要分割的字符串
     * @param delimiter 分隔符
     * @return string[]
     */
    public static String[] split(String toSplit, String delimiter) {
        if (hasLength(toSplit) && hasLength(delimiter)) {
            int offset = toSplit.indexOf(delimiter);
            if (offset < 0) {
                return null;
            } else {
                return toSplit.split(delimiter);
            }
        } else {
            return null;
        }
    }


    /**
     * 字符串集合转成字符串
     * Collection<String> coll = new ArrayList<>();
     * coll.add("1");
     * coll.add("3");
     * coll.add("2");
     * out(StringUtils.collectionToDelimitedString(coll,"-","<",">")); => <1>-<3>-<2>
     * @param coll 字符串集合
     * @param delim 分隔符
     * @param prefix 前缀
     * @param suffix 后缀
     * @return string
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {
        if (CollectionUtils.isEmpty(coll)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            Iterator it = coll.iterator();

            while(it.hasNext()) {
                sb.append(prefix).append(it.next()).append(suffix);
                if (it.hasNext()) {
                    sb.append(delim);
                }
            }

            return sb.toString();
        }
    }

    /**
     * 集合转字符串
     * @param coll 字符串集合
     * @param delim 分隔符
     * @return string
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * 集合转字符串
     * @param coll 字符串集合，分隔符默认为“,”
     * @return string
     */
    public static String collectionToCommaDelimitedString(Collection<?> coll) {
        return collectionToDelimitedString(coll, ",");
    }

    /**
     * 数组转换成字符串
     * out(StringUtils.arrayToDelimitedString(new String[]{"0","1","10","12","12","1"},"-")); => 0-1-10-12-12-1
     * @param arr 数组
     * @param delim 分隔符
     * @return string
     */
    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (ObjectUtils.isEmpty(arr)) {
            return "";
        } else if (arr.length == 1) {
            return ObjectUtils.nullSafeToString(arr[0]);
        } else {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < arr.length; ++i) {
                if (i > 0) {
                    sb.append(delim);
                }

                sb.append(arr[i]);
            }

            return sb.toString();
        }
    }

    /**
     * 数组对象转字符串
     * @param arr 数组对象，分隔符默认为“,”
     * @return string
     */
    public static String arrayToCommaDelimitedString(Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    public static void out(Object obj) {
        System.out.println(obj.toString());
    }
}
