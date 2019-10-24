package com.yt.plugin.quartz.utils;

import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QzStringUtils {


    public static boolean isNullOrBlank(String str) {
        if (str == null) {
            return true;
        } else if (str.equals("")) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    /**
     * 判断str字符串是否符合我们定义的标准格式，pattern表示格式(正则表达)
     *
     * @param str
     * @param pattern
     */
    public static boolean isDefinedPattern(String str, String pattern) {
        Assert.notNull(str);
        Assert.notNull(pattern);

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * Convenience method to return a Collection as a delimited (e.g. CSV)
     * String. E.g. useful for <code>toString()</code> implementations.
     *
     * @param coll  Collection to display
     * @param delim delimiter to use (probably a ",")
     */
    public static String collectionToDelimitedString(Collection coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * Convenience method to return a Collection as a CSV String. E.g. useful
     * for <code>toString()</code> implementations.
     *
     * @param coll Collection to display
     */
    public static String collectionToCommaDelimitedString(Collection coll) {
        return collectionToDelimitedString(coll, ",");
    }

    /**
     * Convenience method to return a Collection as a delimited (e.g. CSV)
     * String. E.g. useful for <code>toString()</code> implementations.
     *
     * @param coll   Collection to display
     * @param delim  delimiter to use (probably a ",")
     * @param prefix string to start each element with
     * @param suffix string to end each element with
     */
    public static String collectionToDelimitedString(Collection coll, String delim, String prefix, String suffix) {
        if (QzCollectionUtils.isEmpty(coll)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Iterator it = coll.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }


}
