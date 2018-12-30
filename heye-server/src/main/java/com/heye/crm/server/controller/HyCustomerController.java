package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyCustomer;
import com.heye.crm.common.model.HyLocation;
import com.heye.crm.common.utils.CommonUtil;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultCode;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.configurer.WebConfig;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.consts.CRole;
import com.heye.crm.server.request.HyCustomerReq;
import com.heye.crm.server.response.HyAdminSessionResp;
import com.heye.crm.server.service.HyCustomerService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/customer")
public class HyCustomerController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyCustomerController.class);

    @Resource
    private HyCustomerService hyCustomerService;

    @Override
    public CRight getControllerCRight() {
        return CRight.CUSTOM_MANAGE;
    }

    /**
     * 获取hyCustom表的列表，入参为: pageNo和pageSize
     *
     * @param req
     * @return
     */
    @PostMapping("/getHyCustomerList")
    @ResponseBody
    public Result getHyCustomerList(@RequestBody HyCustomerReq req) {
        HyAdminSessionResp session = null;
        LOGGER.info("[getHyCustomerList] Req json: {}", new Gson().toJson(req));
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        return innerSearchByManagerNameOrDateType(req, session);
    }

    /**
     * 写入HyCustom表，请求参数为HyCustom表中个字段（除ctmId和updatedAt之外）
     *
     * @param req:
     * @return
     */
    @PostMapping("/insertIntoHyCustom")
    @ResponseBody
    public Result insertIntoHyCustom(@RequestBody HyCustomerReq req) {
        LOGGER.info("[insertIntoHyCustom] Req json: {}", new Gson().toJson(req));
        if (req.getCtmName() == null
                || req.getCtmTelephone() == null
                || req.getCtmProvince() == null || req.getCtmLocation() == null) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        try {
            Condition cond1 = new Condition(HyCustomer.class);
            cond1.createCriteria()
                    .andEqualTo("ctmTelephone", req.getCtmTelephone())
                    .andEqualTo("ctmState", Consts.HY_CUSTOM_STATE_OK);

            cond1.selectProperties("storeId", "ctmName", "ctmAccountName", "ctmPasswd", "ctmSex", "ctmEmail", "ctmPicture", "ctmPhoneNum",
                    "ctmTelephone", "ctmBirthDay", "ctmProvince", "ctmCity", "managerName", "ctmLocation", "ctmDetailAddress",
                    "ctmZipCode", "ctmIdentity", "createdAt");

            List<HyCustomer> results;
            try {
                results = hyCustomerService.findByCondition(cond1);
            } catch (Exception e) {
                LOGGER.warn("[insertIntoHyCustom] find failed:", e);
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }

            if (results.size() > 0) {
                return ResultGenerator.genFailResult(Consts.TELELPHONE_NUMBER_ALREADT_EXISTED);
            }

            HyCustomer hyCustomer = new HyCustomer();

            if (req.getStoreId() != 0L) {
                hyCustomer.setStoreId(req.getStoreId());
            }
            if (!StringUtils.isEmpty(req.getManagerName())) {
                hyCustomer.setManagerName(req.getManagerName());
            }
            if (!StringUtils.isEmpty(req.getCtmAccountName())) {
                hyCustomer.setCtmAccountName(req.getCtmAccountName());
            }

            hyCustomer.setCtmBirthDay(req.getCtmBirthDay());
            if (!StringUtils.isEmpty(req.getCtmDetailAddress())) {
                hyCustomer.setCtmDetailAddress(req.getCtmDetailAddress());
            }
            if (!StringUtils.isEmpty(req.getCtmEmail())) {
                hyCustomer.setCtmEmail(req.getCtmEmail());
            }

            if (!StringUtils.isEmpty(req.getCtmIdentity())) {
                hyCustomer.setCtmIdentity(req.getCtmIdentity());
            }
            if (!StringUtils.isEmpty(req.getCtmLocation())) {
                hyCustomer.setCtmLocation(req.getCtmLocation());
            }
            if (!StringUtils.isEmpty(req.getCtmName())) {
                hyCustomer.setCtmName(req.getCtmName());
            }
            if (!StringUtils.isEmpty(req.getCtmPasswd())) {
                hyCustomer.setCtmPasswd(req.getCtmPasswd());
            } else {
                hyCustomer.setCtmPasswd(Consts.DEFAULT_PASSWORD);
            }

            if (!StringUtils.isEmpty(req.getCtmPhoneNum())) {
                hyCustomer.setCtmPhoneNum(req.getCtmPhoneNum());
            }
            if (!StringUtils.isEmpty(req.getCtmPicture())) {
                hyCustomer.setCtmPicture(req.getCtmPicture());
            }
            if (!StringUtils.isEmpty(req.getCtmProvince())) {
                hyCustomer.setCtmProvince(req.getCtmProvince());
            }
            hyCustomer.setCtmSex(req.getCtmSex());
            if (!StringUtils.isEmpty(req.getCtmTelephone())) {
                hyCustomer.setCtmTelephone(req.getCtmTelephone());
            }
            if (!StringUtils.isEmpty(req.getCtmCity())) {
                hyCustomer.setCtmCity(req.getCtmCity());
            }
            if (!StringUtils.isEmpty(req.getCtmZipCode())) {
                hyCustomer.setCtmZipCode(req.getCtmZipCode());
            }
            hyCustomer.setLatitudeX(req.getLatitudeX());
            hyCustomer.setLatitudeY(req.getLatitudeY());

            hyCustomer.setCtmState(Consts.HY_CUSTOM_STATE_OK);
            hyCustomer.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            //hyCustomerService.save(hyCustomer);
            try {
                hyCustomerService.insertIntoCustomer(hyCustomer);
            } catch (Exception e) {
                LOGGER.warn("[insertIntoHyCustom] insert faile:", e);
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            LOGGER.info(e.toString());
            return ResultGenerator.genFailResult(Consts.INSERT_FAILED);
        }
    }

    /**
     * 客户账户密码登录
     *
     * @param req
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody HyCustomerReq req) {
        LOGGER.info("[login] Req json: {}", new Gson().toJson(req));

        Condition cond = new Condition(HyCustomer.class);
        cond.createCriteria()
                .andEqualTo("ctmTelephone", req.getCtmTelephone())
                .andEqualTo("ctmPasswd", req.getCtmPasswd());

        cond.selectProperties("storeId", "ctmName", "ctmAccountName", "ctmPasswd", "ctmSex", "ctmEmail", "ctmPicture", "ctmPhoneNum",
                "ctmTelephone", "ctmBirthDay", "ctmProvince", "ctmCity", "managerName", "ctmLocation", "ctmDetailAddress",
                "ctmZipCode", "ctmIdentity", "createdAt");
        List<HyCustomer> results;
        try {
            results = hyCustomerService.findByCondition(cond);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        if (results.size() == 0) {
            return ResultGenerator.genFailResult(Consts.NO_SUCH_ADMIN_USER);
        }
        // add session?
        return ResultGenerator.genSuccessResult(results.get(0));
    }

    /**
     * 根据ctmId删除yCustom表，参数为ctmId
     *
     * @param req : req.ctmId
     * @return
     */
    @PostMapping("/deleteHyCustomById")
    @ResponseBody
    public Result deleteHyCustomById(@RequestBody HyCustomerReq req) {
        LOGGER.info("[deleteHyCustomById] Req json: {}", new Gson().toJson(req));
        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        String filterProvince = null;
        String filterCity = null;
        String filterLocation = null;

        try {
            // we do not delete really, we update it's state to -1
            HyCustomer customer = hyCustomerService.findById(req.getCtmId());
            if (customer == null || customer.getCtmState() == Consts.HY_CUSTOM_STATE_DELETED) {
                return ResultGenerator.genFailResult(Consts.NO_SUCH_CUSTOM);
            }
            Result r = checkLocationRight(session, CRole.getRole(session.getRoleId()));
            if (r.getCode() != ResultCode.SUCCESS.code) {
                return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
            }
            HyLocation location = (HyLocation) r.getData();
            filterProvince = location.getLocProvince();
            filterCity = location.getLocCity();
            filterLocation = location.getLocLocation();

            switch (CRole.getRole(session.getRoleId())) {
            case SUPER_ADMIN:
                break;
            case PROVINCE_ADMIN:
                if (!filterProvince.equals(customer.getCtmProvince())) {
                    return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                }
                break;
            case CITY_ADMIN:
                if (!filterProvince.equals(customer.getCtmProvince())) {
                    return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                }
                if (!filterCity.equals(customer.getCtmCity())) {
                    return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                }
                break;
            case STORE_ADMIN:
                if (!filterProvince.equals(customer.getCtmProvince())) {
                    return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                }
                if (!filterCity.equals(customer.getCtmCity())) {
                    return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                }
                if (!filterLocation.equals(customer.getCtmLocation())) {
                    return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                }
                break;
            default:
                return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
            }

            customer.setCtmState(Consts.HY_CUSTOM_STATE_DELETED);
            try {
                hyCustomerService.update(customer);
            } catch (Exception e) {
                LOGGER.warn("");
            }
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.DELETE_FAILED);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据ctmIds删除hyCustom表，参数为ctmIds
     *
     * @param req : req.ctmIds, sessionId, userId
     * @return
     */
    @PostMapping("/batchDeleteHyCustomById")
    @ResponseBody
    public Result batchDeleteHyCustomById(@RequestBody HyCustomerReq req) {
        LOGGER.info("[batchDeleteHyCustomById] Req json: {}", new Gson().toJson(req));
        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        Result r = checkLocationRight(session, CRole.getRole(session.getRoleId()));
        if (r.getCode() != ResultCode.SUCCESS.code) {
            return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
        }
        String filterProvince = null;
        String filterCity = null;
        String filterLocation = null;
        HyLocation location = (HyLocation) r.getData();
        filterProvince = location.getLocProvince();
        filterCity = location.getLocCity();
        filterLocation = location.getLocLocation();

        if (req.getCtmIds() == null || req.getCtmIds().length() == 0) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        String[] arrs = req.getCtmIds().split(",");
        HyCustomer customer;
        try {
            for (int i = 0; i < arrs.length; i++) {
                customer = hyCustomerService.findById(Long.parseLong(arrs[i]));
                if (customer == null || customer.getCtmState() == Consts.HY_CUSTOM_STATE_DELETED) {
                    return ResultGenerator.genFailResult(Consts.NO_SUCH_CUSTOM);
                }

                /**
                switch (CRole.getRole(session.getRoleId())) {
                case SUPER_ADMIN:
                    break;
                case PROVINCE_ADMIN:
                    if (!filterProvince.equals(customer.getCtmProvince())) {
                        return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                    }
                    break;
                case CITY_ADMIN:
                    if (!filterProvince.equals(customer.getCtmProvince())) {
                        return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                    }
                    if (!filterCity.equals(customer.getCtmCity())) {
                        return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                    }
                    break;
                case STORE_ADMIN:
                    if (!filterProvince.equals(customer.getCtmProvince())) {
                        return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                    }
                    if (!filterCity.equals(customer.getCtmCity())) {
                        return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                    }
                    if (!filterLocation.equals(customer.getCtmLocation())) {
                        return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                    }
                    break;
                default:
                    return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
                }*/

                customer.setCtmState(Consts.HY_CUSTOM_STATE_DELETED);
                hyCustomerService.update(customer);
            }
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.DELETE_FAILED);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据ctmId查询hyCustom表，参数为ctmId
     *
     * @param req: req.ctmId
     * @return
     */
    @PostMapping("/getHyCustomById")
    @ResponseBody
    public Result getHyCustomById(@RequestBody HyCustomerReq req) {
        LOGGER.info("[getHyCustomById] Req json: {}", new Gson().toJson(req));
        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        try {
            HyCustomer result = hyCustomerService.findById(req.getCtmId());
            return ResultGenerator.genSuccessResult(result);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.SELECT_FAILED);
        }
    }

    /**
     * 根据filterByDateType筛选, 判断hyCustom中的createdAt来筛选数据库中数据, 注意分页;
     *
     * @param req: filterByDateType, 0: 今天新增, 1: 本周新增, 2: 本月新增;
     * @param req: pageNo, pageSize
     * @return
     */
    private Result innerSearchByManagerNameOrDateType(HyCustomerReq req,
                                                      HyAdminSessionResp session) {
        String filterProvince = null;
        String filterCity = null;
        String filterLocation = null;

        if (session != null) {
            int roleId = session.getRoleId();
            switch (CRole.getRole(roleId)) {
            case SUPER_ADMIN:
                break;
            case PROVINCE_ADMIN:
                filterProvince = session.getHyUser().getUserProvince();
                if (filterProvince == null || filterProvince.isEmpty()) {
                    return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
                }
                break;
            case CITY_ADMIN:
                filterProvince = session.getHyUser().getUserProvince();
                if (filterProvince == null || filterProvince.isEmpty()) {
                    return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
                }
                filterCity = session.getHyUser().getUserCity();
                if (filterCity == null || filterCity.isEmpty()) {
                    return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
                }
                break;
            case STORE_ADMIN:
                filterProvince = session.getHyUser().getUserProvince();
                if (filterProvince == null || filterProvince.isEmpty()) {
                    return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
                }
                filterCity = session.getHyUser().getUserCity();
                if (filterCity == null || filterCity.isEmpty()) {
                    return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
                }
                filterLocation = session.getHyUser().getUserLocation();
                if (filterLocation == null || filterLocation.isEmpty()) {
                    return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
                }
                break;
            case UNKNOWN:
                return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
            }
        }

        boolean searchByManagerName = false;
        boolean searchByDateType = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (req.getFilterByDateType() != -1) {
            searchByDateType = true;

            int filterByDateType = req.getFilterByDateType();

            Calendar calendar = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String temp = dateFormat.format(calendar.getTime());

            switch (filterByDateType) {
            case 1:
                String thisDayString = temp + " 00:00:00";
                timestamp = Timestamp.valueOf(thisDayString);
                break;
            case 2:
                int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                weekday = weekday == 0 ? 7 : weekday;
                System.out.println(weekday);
                long dateTime = System.currentTimeMillis() - (weekday - 1) * 86400000;
                Date date = new Date(dateTime);
                String thisWeekString = dateFormat.format(date) + " 00:00:00";
                timestamp = Timestamp.valueOf(thisWeekString);
                break;
            case 3:
                String thisMonthString = temp.substring(0, 7) + "-01 00:00:00";
                timestamp = Timestamp.valueOf(thisMonthString);
                break;
            }
        }
        if (req.getManagerName() != null && req.getManagerName().length() > 0) {
            searchByManagerName = true;
        }

        List<HyCustomer> list = null;
        Condition condition = new Condition(HyCustomer.class);
        Example.Criteria criteria = condition.createCriteria();

        // Do list all
        if (searchByDateType) {
            criteria.andGreaterThanOrEqualTo("createdAt", timestamp);
        }
        if (searchByManagerName) {
            criteria.andLike("managerName", CommonUtil.getEclipseLike(req.getManagerName()));
        }
        if (!StringUtils.isEmpty(req.getCtmName())) {
            criteria.andLike("ctmName", CommonUtil.getEclipseLike(req.getCtmName()));
        }
        criteria.andEqualTo("ctmState", Consts.HY_CUSTOM_STATE_OK);

        if (filterProvince != null && !filterProvince.isEmpty()) {
            criteria.andLike("ctmProvince", CommonUtil.getEclipseLike(filterProvince));
        }
        if (filterCity != null && !filterCity.isEmpty()) {
            criteria.andLike("ctmCity", CommonUtil.getEclipseLike(filterCity));
        }
        if (filterLocation != null && !filterLocation.isEmpty()) {
            criteria.andLike("ctmLocation", CommonUtil.getEclipseLike(filterLocation));
        }

        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");

        condition.selectProperties("storeId", "ctmName", "ctmAccountName", "ctmPasswd", "ctmSex", "ctmEmail", "ctmPicture", "ctmPhoneNum",
                "ctmTelephone", "ctmBirthDay", "ctmProvince", "ctmCity", "managerName", "ctmLocation", "ctmDetailAddress",
                "ctmZipCode", "ctmIdentity", "createdAt");

        list = hyCustomerService.findByCondition(condition);
        if (list == null) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }

        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根据hyCustom内容, 根据ctmId字段进行更新；
     * 如果没有该用户，返回错误；
     * 如果存在，更新所有字段；
     *
     * @param req
     * @return
     */
    @PostMapping("/updateHyCustomById")
    @ResponseBody
    public Result updateHyCustomById(@RequestBody HyCustomerReq req) {
        LOGGER.info("[updateHyCustomById] Req json: {}", new Gson().toJson(req));

        HyCustomer hyCustomer = hyCustomerService.findById(req.getCtmId());
        if (hyCustomer == null) {
            return ResultGenerator.genFailResult(Consts.NO_SUCH_OBJECT);
        }

        if (req.getStoreId() != 0L) {
            hyCustomer.setStoreId(req.getStoreId());
        }

        if (!StringUtils.isEmpty(req.getManagerName())) {
            hyCustomer.setManagerName(req.getManagerName());
        }

        if (!StringUtils.isEmpty(req.getCtmAccountName())) {
            hyCustomer.setCtmAccountName(req.getCtmAccountName());
        }

        if (req.getCtmBirthDay().equals(hyCustomer.getCtmBirthDay())) {
            hyCustomer.setCtmBirthDay(req.getCtmBirthDay());
        }

        if (!StringUtils.isEmpty(req.getCtmDetailAddress())) {
            hyCustomer.setCtmDetailAddress(req.getCtmDetailAddress());
        }

        if (!StringUtils.isEmpty(req.getCtmEmail())) {
            hyCustomer.setCtmEmail(req.getCtmEmail());
        }

        if (!StringUtils.isEmpty(req.getCtmIdentity())) {
            hyCustomer.setCtmIdentity(req.getCtmIdentity());
        }

        if (!StringUtils.isEmpty(req.getCtmLocation())) {
            hyCustomer.setCtmLocation(req.getCtmLocation());
        }

        if (!StringUtils.isEmpty(req.getCtmName())) {
            hyCustomer.setCtmName(req.getCtmName());
        }

        if (StringUtils.isEmpty(req.getCtmPasswd())) {
            hyCustomer.setCtmPasswd(req.getCtmPasswd());
        }

        if (!StringUtils.isEmpty(req.getCtmPhoneNum())) {
            hyCustomer.setCtmPhoneNum(req.getCtmPhoneNum());
        }

        if (!StringUtils.isEmpty(req.getCtmPicture())) {
            hyCustomer.setCtmPicture(req.getCtmPicture());
        }
        if (!StringUtils.isEmpty(req.getCtmProvince())) {
            hyCustomer.setCtmProvince(req.getCtmProvince());
        }
        if (req.getCtmSex() != 0) {
            hyCustomer.setCtmSex(req.getCtmSex());
        }
        if (!StringUtils.isEmpty(req.getCtmTelephone())) {
            hyCustomer.setCtmTelephone(req.getCtmTelephone());
        }
        if (!StringUtils.isEmpty(req.getCtmCity())) {
            hyCustomer.setCtmCity(req.getCtmCity());
        }
        if (!StringUtils.isEmpty(req.getCtmZipCode())) {
            hyCustomer.setCtmZipCode(req.getCtmZipCode());
        }
        if (!StringUtils.isEmpty(req.getManagerName())) {
            hyCustomer.setManagerName(req.getManagerName());
        }
        if (req.getCtmState() != 0) {
            hyCustomer.setCtmState(req.getCtmState());
        }

        try {
            hyCustomerService.update(hyCustomer);
        } catch (Exception e) {
            LOGGER.warn("[updateHyCustomById] update failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        return ResultGenerator.genSuccessResult();
    }
}
