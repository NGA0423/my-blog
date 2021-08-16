package com.nga.util;

import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 公共函数
 */
@Component
public class CommonsUtil {
    /**
     * 格式化unix时间戳为日期
     * @param unixTime
     * @return
     */
    public static String fmdate(Integer unixTime){
        return fmdate(unixTime,"yyyy-MM-dd");
    }

    /**
     * 格式化unix时间戳为日期
     * @param unixTime
     * @param patten
     * @return
     */
    public static String fmdate(Integer unixTime,String patten){
        if (unixTime!=null&& StringUtils.isNotBlank(patten)){
            return DateKitUtil.formatDateByUnixTime(unixTime,patten);
        }
        return "";
    }

    /**
     * 英文格式的日期
     * @param unixTime
     * @return
     */
    public static String fmtdate_en(Integer unixTime){
        String fmdate = fmdate(unixTime, "d,MMM,yyyy");
        String[] split = fmdate.split(",");
        String rs="<span>"+split[0]+"</span>"+split[1]+" "+split[2];
        return rs;
    }

    /**
     * 英文格式的日期-月，日
     * @param unixTime
     * @return
     */
    public static String fmtdate_en_m(Integer unixTime){
        return fmdate(unixTime,"MMM d");
    }

    /**
     * 英文格式的日期-年
     * @param unxiTime
     * @return
     */
    public static String fmtdate_en_y(Integer unxiTime){
        return fmdate(unxiTime,"yyyy");
    }

    /**
     * 将中文yyyy年MM月 -> yyyy
     * @param date
     * @return
     */
    public static String parsedate_zh_y_m(String date){
        if (StringUtils.isNotBlank(date)){
            Date d = DateKitUtil.dateFormat(date, "yyyy年MM月");
            return DateKitUtil.dateFormat(d,"yyyy");
        }
        return null;
    }

    /**
     * 字符串转Date
     * @param date
     * @return
     */
    public static Date fmtdate_date(String date){
        if (StringUtils.isNotBlank(date)){
            return DateKitUtil.dateFormat(date,"yyyy年MM月");
        }
        return null;
    }

    /**
     * 根据unix时间戳获取Date
     * @param unixTime
     * @return
     */
    public static Date fmtdate_unixtime(Integer unixTime){
        if (unixTime!=null){
            return DateKitUtil.getDateByUnixTime(unixTime);
        }
        return null;
    }
}
