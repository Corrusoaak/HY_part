package com.heye.crm.server.analyze;

import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.consts.HyAnalyzeType;
import com.heye.crm.common.model.HyAnalyze;
import com.heye.crm.common.model.HyCustomer;
import com.heye.crm.common.utils.ResultGenerator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import static com.heye.crm.common.utils.CommonUtil.getAge;

/**
 * @author : ZhanHanbing
 */
public class HyAnalyzeUtil {

    public static List<HyAnalyze> getAnalyzeList(Logger logger, List<HyCustomer> customers, Timestamp lastTime, int aggLevel,
                                                 boolean isNewly) {
        Map<Integer, Integer> sexToNum = new HashMap<>();
        Map<String, Integer> regionToNum = new HashMap<>();
        Map<String, Integer> provinceToNum = new HashMap<>();
        Map<String, Integer> cityToNum = new HashMap<>();
        //Map<String, Integer> productToNum = new HashMap<>();
        Map<Integer, Integer> ageToNum = new HashMap<>();
        try {
            //性别

            for (HyCustomer hyCustomer : customers) {
                //性别
                if (sexToNum.containsKey(hyCustomer.getCtmSex())) {
                    sexToNum.put(hyCustomer.getCtmSex(), sexToNum.get(hyCustomer.getCtmSex()) + 1);
                } else {
                    sexToNum.put(hyCustomer.getCtmSex(), 1);
                }
                //地域
                if (regionToNum.containsKey(hyCustomer.getCtmLocation())) {
                    regionToNum.put(hyCustomer.getCtmLocation(), regionToNum.get(hyCustomer.getCtmLocation()) + 1);
                } else {
                    regionToNum.put(hyCustomer.getCtmLocation(), 1);
                }
                //省份
                if (provinceToNum.containsKey(hyCustomer.getCtmProvince())) {
                    provinceToNum.put(hyCustomer.getCtmProvince(), provinceToNum.get(hyCustomer.getCtmProvince()) + 1);
                } else {
                    provinceToNum.put(hyCustomer.getCtmProvince(), 1);
                }
                //城市
                if (cityToNum.containsKey(hyCustomer.getCtmCity())) {
                    cityToNum.put(hyCustomer.getCtmCity(), cityToNum.get(hyCustomer.getCtmCity()) + 1);
                } else {
                    cityToNum.put(hyCustomer.getCtmCity(), 1);
                }
                //产品
                //if (productToNum.containsKey(hyCustomer.getCtmLocation())) {
                //    productToNum.put(hyCustomer.getCtmLocation(), productToNum.get(hyCustomer.getCtmLocation()) + 1);
                //} else {
                //    productToNum.put(hyCustomer.getCtmLocation(), 1);
                //}
                //年龄
                if (ageToNum.containsKey(getAge(hyCustomer.getCtmBirthDay()))) {
                    ageToNum.put(getAge(hyCustomer.getCtmBirthDay()), ageToNum.get(getAge(hyCustomer.getCtmBirthDay())) + 1);
                } else {
                    ageToNum.put(getAge(hyCustomer.getCtmBirthDay()), 1);
                }
            }
        } catch (Exception e) {
            ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        logger.info(sexToNum.toString());
        logger.info(provinceToNum.toString());
        logger.info(ageToNum.toString());
        logger.info(regionToNum.toString());

        List<HyAnalyze> hyAnalyzeList = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : sexToNum.entrySet()) {
            HyAnalyze hyAnalyze = new HyAnalyze();
            hyAnalyze.setAggLevel(aggLevel);
            if (isNewly) {
                hyAnalyze.setAnaType(HyAnalyzeType.HY_ANALYZE_TYPE_SEX_NEWLY.getType());
            } else {
                hyAnalyze.setAnaType(HyAnalyzeType.HY_ANALYZE_TYPE_SEX_SUM.getType());
            }

            hyAnalyze.setCreatedAt(lastTime);
            hyAnalyze.setLabelName(entry.getKey().toString());
            hyAnalyze.setLabelValue(entry.getValue());
            hyAnalyze.setAnaName("");
            hyAnalyzeList.add(hyAnalyze);
        }

        for (Map.Entry<String, Integer> entry : regionToNum.entrySet()) {
            HyAnalyze hyAnalyze = new HyAnalyze();
            hyAnalyze.setAggLevel(aggLevel);
            if (isNewly) {
                hyAnalyze.setAnaType(HyAnalyzeType.HY_ANALYZE_TYPE_REGION_NEWLY.getType());
            } else {
                hyAnalyze.setAnaType(HyAnalyzeType.HY_ANALYZE_TYPE_REGION_SUM.getType());
            }
            hyAnalyze.setCreatedAt(lastTime);
            hyAnalyze.setLabelName(entry.getKey());
            hyAnalyze.setLabelValue(entry.getValue());
            hyAnalyze.setAnaName("");
            hyAnalyzeList.add(hyAnalyze);
        }

        for (Map.Entry<String, Integer> entry : provinceToNum.entrySet()) {
            HyAnalyze hyAnalyze = new HyAnalyze();
            hyAnalyze.setAggLevel(aggLevel);
            if (isNewly) {
                hyAnalyze.setAnaType(HyAnalyzeType.HY_ANALYZE_TYPE_PROVINCE_NEWLY.getType());
            } else {
                hyAnalyze.setAnaType(HyAnalyzeType.HY_ANALYZE_TYPE_PROVINCE_SUM.getType());
            }

            hyAnalyze.setCreatedAt(lastTime);
            hyAnalyze.setLabelName(entry.getKey());
            hyAnalyze.setLabelValue(entry.getValue());
            hyAnalyze.setAnaName("");
            hyAnalyzeList.add(hyAnalyze);
        }

        for (Map.Entry<String, Integer> entry : cityToNum.entrySet()) {
            HyAnalyze hyAnalyze = new HyAnalyze();
            hyAnalyze.setAggLevel(aggLevel);
            if (isNewly) {
                hyAnalyze.setAnaType(HyAnalyzeType.HY_ANALYZE_TYPE_CITY_NEWLY.getType());
            } else {
                hyAnalyze.setAnaType(HyAnalyzeType.HY_ANALYZE_TYPE_CITY_SUM.getType());
            }
            hyAnalyze.setCreatedAt(lastTime);
            hyAnalyze.setLabelName(entry.getKey());
            hyAnalyze.setLabelValue(entry.getValue());
            hyAnalyze.setAnaName("");
            hyAnalyzeList.add(hyAnalyze);
        }

        for (Map.Entry<Integer, Integer> entry : ageToNum.entrySet()) {
            HyAnalyze hyAnalyze = new HyAnalyze();
            hyAnalyze.setAggLevel(aggLevel);
            if (isNewly) {
                hyAnalyze.setAnaType(HyAnalyzeType.HY_ANALYZE_TYPE_AGE_NEWLY.getType());
            } else {
                hyAnalyze.setAnaType(HyAnalyzeType.HY_ANALYZE_TYPE_AGE_SUM.getType());
            }
            hyAnalyze.setCreatedAt(lastTime);
            hyAnalyze.setLabelName(entry.getKey().toString());
            hyAnalyze.setLabelValue(entry.getValue());
            hyAnalyze.setAnaName("");
            hyAnalyzeList.add(hyAnalyze);
        }
        return hyAnalyzeList;
    }
}
