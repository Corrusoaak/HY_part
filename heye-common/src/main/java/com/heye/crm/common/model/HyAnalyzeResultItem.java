package com.heye.crm.common.model;

import com.heye.crm.common.consts.Consts;
import java.text.SimpleDateFormat;

/**
 * @author : fanjinqian
 */
public class HyAnalyzeResultItem {
    private String date;
    private String labelName;
    private Integer labelValue;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Integer getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(Integer labelValue) {
        this.labelValue = labelValue;
    }

    public static HyAnalyzeResultItem toHyAnalyzeResultItem(HyAnalyze hyAnalyze, Integer aggLevel) {
        HyAnalyzeResultItem hyAnalyzeResultItem = new HyAnalyzeResultItem();
        switch (aggLevel) {
        // 如果是按照日的维度，将时间转换成日期
        case Consts.HY_ANALYZE_AGG_LEVEL_DAY: {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(hyAnalyze.getCreatedAt());
            hyAnalyzeResultItem.setDate(date);
            break;
        }
        // 如果是月，则取created_at的月份
        case Consts.HY_ANALYZE_AGG_LEVEL_MONTH: {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            String date = df.format(hyAnalyze.getCreatedAt());
            hyAnalyzeResultItem.setDate(date);
            break;
        }
        // 如果是年，则取created_at的年份
        case Consts.HY_ANALYZE_AGG_LEVEL_YEAR: {
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String date = df.format(hyAnalyze.getCreatedAt());
            hyAnalyzeResultItem.setDate(date);
            break;
        }
        default: {
            break;
        }
        }
        hyAnalyzeResultItem.setLabelName(hyAnalyze.getLabelName());
        hyAnalyzeResultItem.setLabelValue(hyAnalyze.getLabelValue());
        return hyAnalyzeResultItem;
    }
}
