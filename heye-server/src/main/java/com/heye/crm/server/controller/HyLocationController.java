package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyLocation;
import com.heye.crm.common.model.location.HCity;
import com.heye.crm.common.model.location.HLocation;
import com.heye.crm.common.model.location.HProvince;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyLocationReq;
import com.heye.crm.server.service.HyLocationService;
import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/location")
public class HyLocationController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyLocationController.class);

    private static final Counter PROMETHEUS_COUNTER = Counter.build()
            .name("location")
            .labelNames("status")
            .help("location").register();

    @Resource
    HyLocationService hyLocationService;

    @Override
    public CRight getControllerCRight() {
        return CRight.UNKOWN;
    }

    @PostMapping("/getLocationList")
    @ResponseBody
    public Result getAllLocations(@RequestBody HyLocationReq req) {
        LOGGER.info("[getLocationList] Req json: {}", new Gson().toJson(req));

        /**
         * @author : lishuming
         */
        class HResult {
            List<HProvince> provinces;

            HResult() {
            }

            public List<HProvince> getProvinces() {
                return provinces;
            }

            public void setProvinces(List<HProvince> provinces) {
                this.provinces = provinces;
            }
        }

        Condition condition = new Condition(HyLocation.class);
        condition.createCriteria()
                .andEqualTo("locState", Consts.HY_LOCATIOIN_STATE_OK);

        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
        List<HyLocation> list = hyLocationService.findByCondition(condition);

        if (list.size() == 0) {
            return ResultGenerator.genFailResult(Consts.NOT_FOUND);
        }

        Map<String, Map<String, List<String>>> locMapping = new HashMap<>();
        for (HyLocation loc : list) {
            if (!locMapping.containsKey(loc.getLocProvince())) {
                List<String> locations = new ArrayList<>(Arrays.asList(loc.getLocLocation()));
                Map<String, List<String>> cityMapping = new HashMap<>();
                cityMapping.put(loc.getLocCity(), locations);
                locMapping.put(loc.getLocProvince(), cityMapping);
            } else {
                Map<String, List<String>> cityMapping = locMapping.get(loc.getLocProvince());
                if (!cityMapping.containsKey(loc.getLocCity())) {
                    cityMapping.put(loc.getLocCity(), new ArrayList<>(Arrays.asList(loc.getLocLocation())));
                } else {
                    List<String> locationLists = cityMapping.get(loc.getLocCity());
                    if (!locationLists.contains(loc.getLocLocation())) {
                        cityMapping.get(loc.getLocCity()).add(loc.getLocLocation());
                    }
                }
            }
        }

        Iterator<Map.Entry<String, Map<String, List<String>>>> iterator = locMapping.entrySet().iterator();

        HResult result = new HResult();
        List<HProvince> provinces = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, Map<String, List<String>>> entry = iterator.next();
            HProvince hProvince = new HProvince();
            hProvince.setValue(entry.getKey());

            List<HCity> cities = new ArrayList<>();
            Iterator<Map.Entry<String, List<String>>> iterator1 = entry.getValue().entrySet().iterator();
            while (iterator1.hasNext()) {
                HCity hCity = new HCity();
                Map.Entry<String, List<String>> entry1 = iterator1.next();
                hCity.setValue(entry1.getKey());

                List<HLocation> locations = new ArrayList<>();
                List<String> locs = entry1.getValue();
                for (String s : locs) {
                    HLocation hLocation = new HLocation();
                    hLocation.setValue(s);
                    locations.add(hLocation);
                }
                hCity.setLocations(locations);

                cities.add(hCity);
            }
            hProvince.setCities(cities);
            provinces.add(hProvince);
        }
        result.setProvinces(provinces);

        PROMETHEUS_COUNTER.labels("getLocationList_success").inc();

        return ResultGenerator.genSuccessResult(result);
    }

    // 获取省份列表

    // 获取城市列表, 根据省份

    // 获取地区列表， 根据省份+城市
}
