package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyRight;
import com.heye.crm.common.model.HyRole;
import com.heye.crm.common.model.HyRoleHelper;
import com.heye.crm.common.model.HyUser;
import com.heye.crm.common.utils.CommonUtil;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.configurer.WebConfig;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.consts.CRole;
import com.heye.crm.server.dao.HyUserMapper;
import com.heye.crm.server.request.HyUserReq;
import com.heye.crm.server.response.HyAdminSessionResp;
import com.heye.crm.server.service.HyRightService;
import com.heye.crm.server.service.HyRoleService;
import com.heye.crm.server.service.HyUserService;
import com.heye.crm.server.vo.HyRoleRightJoin;
import com.heye.crm.server.vo.HyUserVoRight;
import com.heye.crm.server.vo.HyUserVoRole;
import org.apache.commons.lang3.StringUtils;
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
import java.sql.Timestamp;
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
@RequestMapping("/api/v1/admin")
public class HyAdminController extends Controller<HyUserReq> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyAdminController.class);

    @Resource
    private HyUserService hyUserService;
    @Resource
    private HyUserMapper hyUserMapper;
    @Resource
    private HyRightService hyRightService;
    @Resource
    private HyRoleService hyRoleService;

    @Override
    public CRight getControllerCRight() {
        return CRight.SUPER_RIGHT;
    }

    /**
     * 获取账户权限列表
     *
     * @param req
     * @return
     */
    @PostMapping("/getRoleList")
    @ResponseBody
    public Result getRoleList(@RequestBody HyUserReq req) {
        LOGGER.info("[getRoleList] req: " + new Gson().toJson(req));

        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }

        List<HyRoleRightJoin> result = hyRoleService.getRoleRightList();
        if (result == null || result.isEmpty()) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }

        //TODO:
        List<HyRoleHelper> hyRoles = new ArrayList<>();
        // roleId --> rightList的映射
        Map<Long, List<Long>> joinResult = new HashMap<>();
        // roleId --> role的映射
        Map<Long, HyRoleHelper> idToHyRole = new HashMap<>();

        for (HyRoleRightJoin join : result) {
            LOGGER.debug("[getRoleList] join roleID:" + join.getRoleId());

            if (joinResult.containsKey(join.getRoleId()) && join.getRightId() != 0) {
                joinResult.get(join.getRoleId()).add(join.getRightId());
            } else {
                HyRoleHelper hyRole = new HyRoleHelper();
                hyRole.setRoleId(join.getRoleId());
                hyRole.setRoleRangeType(join.getRoleRangeType());
                hyRole.setRoleDesc(join.getRoleDesc());
                hyRole.setRoleName(join.getRoleName());
                hyRole.setCreatedAt(join.getCreatedAt());
                hyRole.setRoleParentId(join.getRoleParentId());
                hyRole.setCreatedAt(join.getCreatedAt());
                hyRoles.add(hyRole);

                idToHyRole.put(join.getRoleId(), hyRole);

                if (join.getRightId() == 0) {
                    joinResult.put(join.getRoleId(), new ArrayList<>());
                } else {
                    joinResult.put(join.getRoleId(), new ArrayList<>(Arrays.asList(join.getRightId())));
                }
            }
        }

        Iterator<Map.Entry<Long, List<Long>>> entry = joinResult.entrySet().iterator();
        while (entry.hasNext()) {
            Map.Entry<Long, List<Long>> r = entry.next();

            Long roleId = r.getKey();
            LOGGER.debug("[getRoleList] entry roleID:" + roleId);
            if (idToHyRole.containsKey(roleId)) {
                String rightList = StringUtils.join(r.getValue(), ",");
                idToHyRole.get(roleId).setRightList(rightList);
            }

            // 5: 店长角色
            if (roleId > 5) {
                // 可以删除
                idToHyRole.get(roleId).setCanDelete(1);
            } else {
                // 不可以被删除
                idToHyRole.get(roleId).setCanDelete(2);
            }
        }

        return ResultGenerator.genSuccessResult(hyRoles);
    }

    /**
     * 获取账户权限列表
     *
     * @param req
     * @return
     */
    @PostMapping("/getRightList")
    @ResponseBody
    public Result getRightList(@RequestBody HyUserReq req) {
        LOGGER.info("[getRightList] req: " + new Gson().toJson(req));

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        if (session != null && !session.hasRight(getControllerCRight())) {
            return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
        }

        Condition cond = new Condition(HyRight.class);
        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
        cond.createCriteria()
                .andEqualTo("rightState", Consts.HY_RIGHT_STATE_OK);
        List<HyRight> rights = hyRightService.findByCondition(cond);

        List<HyRight> result = new ArrayList<>();
        for (HyRight right : rights) {
            if (right.getRightId() == 1L) {
                continue;
            }

            result.add(right);
        }

        PageInfo pageInfo = new PageInfo(result);

        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 获取账户权限列表
     *
     * @param req
     * @return
     */
    @PostMapping("/getHyUserList")
    @ResponseBody
    public Result getHyUserList(@RequestBody HyUserReq req) {
        LOGGER.info("[getHyUserList] req: " + new Gson().toJson(req));

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        List<HyUserVoRole> result;
        Map cond = Maps.newHashMap();

        if (!StringUtils.isEmpty(req.getRealName())) {
            cond.put("userAccountName", req.getRealName());
        } else if (!StringUtils.isEmpty(req.getLocationName())) {
            cond.put("userDetailAddress", req.getLocationName());
        } else if (!StringUtils.isEmpty(req.getRoleName())) {
            Condition cond1 = new Condition(HyRole.class);
            cond1.createCriteria()
                    .andLike("roleName", CommonUtil.getEclipseLike(req.getRoleName()));
            List<HyRole> roles = hyRoleService.findByCondition(cond1);
            if (roles.size() == 0) {
                return ResultGenerator.genSuccessResult(Consts.SUSSESS);
            }
            HyRole hyRole = roles.get(0);
            cond.put("roleId", hyRole.getRoleId());
        }
        LOGGER.debug("cond:" + cond.toString());

        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
        result = hyUserService.findAllWithRoleWithCond(cond);

        PageInfo pageInfo = new PageInfo(result);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * TODO: 管理账户登录, 返回Session信息
     *
     * @param req
     * @return
     */
    @PostMapping("/loginHyUserByPasswd")
    @ResponseBody
    public Result loginHyUserByPasswd(@RequestBody HyUserReq req) {
        LOGGER.info("[loginHyUserByPasswd] req: " + new Gson().toJson(req));

        if (StringUtils.isEmpty(req.getUserName()) || StringUtils.isEmpty(req.getUserPasswd())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        // 1. 需要join hy_role_right表，获取该账号对应role下的权限列表;
        // 2. 将获取到的用户和权限信息写入redis，并设置ttl;
        Condition condition = new Condition(HyUser.class);
        condition.createCriteria()
                .andEqualTo("userName", req.getUserName())
                .andEqualTo("userPasswd", req.getUserPasswd());
        List<HyUser> result = hyUserService.findByCondition(condition);
        if (result.size() == 0) {
            LOGGER.warn("login failed: {}", req.getUserName());
            return ResultGenerator.genFailResult(Consts.NOT_FOUND);
        }

        HyUser hyUser = result.get(0);

        if (hyUser.getUserState() != Consts.HY_ADMIN_ACCOUNT_STATE_OK) {
            return ResultGenerator.genFailResult(Consts.ACCOUNT_UNAVALIABLE);
        }

        int roleId = (int) hyUser.getRoleId();
        CRole cRole = CRole.getRole(roleId);

        StringBuilder rightList = new StringBuilder();
        if (cRole == CRole.UNKNOWN) {
            List<HyUserVoRight> list = hyUserService.findWithRightById(result.get(0).getUserId());
            if (list.size() == 0 && WebConfig.AUTHORIZE_SESSION_ENABLED) {
                return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
            }

            rightList.append(list.get(0).getRightId());
            for (int i = 1; i < list.size(); i++) {
                rightList.append("," + list.get(i).getRightId());
            }
        }

        String sessionId = CommonUtil.genSessionId();
        HyAdminSessionResp hySessionResp = new HyAdminSessionResp(result.get(0), sessionId,
                roleId, rightList.toString());
        try {
            setAdminSession(hySessionResp);
        } catch (Exception e) {
            LOGGER.warn("set session failed: ", e);
            if (WebConfig.AUTHORIZE_SESSION_ENABLED) {
                return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
            }
        }

        PageInfo pageInfo = new PageInfo(Arrays.asList(hySessionResp));
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * TODO: 获取用户详情
     */
    @PostMapping("/getParentRoleAdmins")
    @ResponseBody
    public Result getParentRoleAdmins(@RequestBody HyUserReq req) {
        LOGGER.info("[getParentRoleAdmins] req: " + new Gson().toJson(req));

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        // TODO: check sesion has enough right
        if (session != null && !session.hasRight(getControllerCRight())) {
            return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
        }

        CRole parentRole = CRole.getParentRole((int) req.getRoleId());

        Condition cond = new Condition(HyUser.class);
        Example.Criteria criteria = cond.createCriteria();
        criteria.andEqualTo("roleId", parentRole.getRoleId())
                .andEqualTo("userState", Consts.HY_ADMIN_ACCOUNT_STATE_OK);
        if (req.getUserParentName() != null && !req.getUserParentName().isEmpty()) {
            criteria.andLike("userName", CommonUtil.getEclipseLike(req.getUserParentName()));
        }

        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
        List<HyUser> list = hyUserService.findByCondition(cond);
        if (list.size() == 0) {
            return ResultGenerator.genFailResult(Consts.NO_SUCH_OBJECT);
        }

        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 写入账号
     *
     * @param req
     * @return
     */
    @PostMapping("/insertIntoHyUser")
    @ResponseBody
    public Result insertIntoHyUser(@RequestBody HyUserReq req) {
        LOGGER.info("[insertIntoHyUser] req: " + new Gson().toJson(req));

        if (req.getRoleId() == 0
                || !CommonUtil.checkParamStr(req.getUserTelephone())
                || !CommonUtil.checkParamStr(req.getUserIdentity())
                || !CommonUtil.checkParamStr(req.getUserName())
                || !CommonUtil.checkParamStr(req.getUserProvince())
                || !CommonUtil.checkParamStr(req.getUserCity())
                || !CommonUtil.checkParamStr(req.getUserLocation())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        // 校验身份证号码
        if (req.getUserIdentity().length() < 6) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        // 0. 校验父parentName和parentId是否存在，校验roleId是否存在，不存在报异常；
        // 1. 根据user_identity或者hy_user_name或者hy_phone_num查看该用户是否存在;
        // 2. 如果存在，返回已经存在错误；
        // 3. 否则写入数据库;

        // check permession
        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        if (session != null && !session.hasRight(this.getControllerCRight())) {
            return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
        }

        if (req.getUserParentId() == 0 || req.getUserParentName() == null) {
            // TODO: set parent: UNKOWN
        }

        HyUser hyUser = new HyUser();
        try {
            // 校验该账号是否存在
            Integer alreadyCount = hyUserMapper.getUserByIdOrNameOrPhone(req.getUserName(),
                    req.getUserTelephone(),
                    req.getUserIdentity());
            if (alreadyCount > 0) {
                return ResultGenerator.genFailResult(Consts.ALREADY_EXISTED_IN_DB);
            }

            // 写入该账号创建 时间
            hyUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            if (req.getUserAccountName() != null) {
                hyUser.setUserAccountName(req.getUserAccountName());
            }

            // 写入身份证
            hyUser.setUserIdentity(req.getUserIdentity());
            hyUser.setUserName(req.getUserName());
            // 电话
            hyUser.setUserTelephone(req.getUserTelephone());
            if (CommonUtil.checkParamStr(req.getUserPhoneNum())) {
                hyUser.setUserPhoneNum(req.getUserPhoneNum());
            }

            // 密码
            String id = req.getUserIdentity();
            String passwd = id.substring(id.length() - 6, id.length());
            hyUser.setUserPasswd(passwd);

            hyUser.setUserRangeType(Consts.HY_ADMIN_RANGE_TYPE_RANGE);
            hyUser.setUserSex(req.getUserSex());

            //hyUser.setUserBirthDay(this.userBirthDay);
            //hyUser.setUserZipCode(this.userZipCode);
            hyUser.setUserState(req.getUserState());
            if (CommonUtil.checkParamStr(req.getUserPicture())) {
                hyUser.setUserPicture(req.getUserPicture());
            }

            // 写入地址
            if (!StringUtils.isEmpty(req.getUserProvince())) {
                hyUser.setUserProvince(req.getUserProvince());
            }
            if (!StringUtils.isEmpty(req.getUserCity())) {
                hyUser.setUserCity(req.getUserCity());
            }
            if (!StringUtils.isEmpty(req.getUserLocation())) {
                hyUser.setUserLocation(req.getUserLocation());
            }
            if (req.getUserDetailAddress() != null) {
                hyUser.setUserDetailAddress(req.getUserDetailAddress());
            }

            hyUser.setRoleId(req.getRoleId());
            hyUser.setUserParentId(req.getUserParentId());
            hyUser.setUserParentName(req.getUserParentName());
            hyUserService.save(hyUser);
            return ResultGenerator.genSuccessResult(hyUser);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.INSERT_FAILED);
        }
    }

    /**
     * TODO: 修改密码， 请求包含;
     */
    @PostMapping("/updateUserPasswd")
    @ResponseBody
    public Result updateUserPasswd(@RequestBody HyUserReq req) {
        LOGGER.info("[updateUserPasswd] req: " + new Gson().toJson(req));

        if (StringUtils.isEmpty(req.getUserPasswd()) || StringUtils.isEmpty(req.getOriginPasswd())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        // TODO: 权限校验,每个类型的管理员都应该可以更改自己的密码
        HyUser hyUser = hyUserService.findById(req.getAdminUserId());
        if (hyUser == null) {
            return ResultGenerator.genFailResult(Consts.NO_SUCH_USER);
        }
        if (!req.getOriginPasswd().equals(hyUser.getUserPasswd())) {
            return ResultGenerator.genFailResult(Consts.ORIGIN_PASSWD_WRONG);
        }

        // 更新密码
        hyUser.setUserPasswd(req.getUserPasswd());

        try {
            hyUserService.update(hyUser);
        } catch (Exception e) {
            LOGGER.warn("[updateUserPasswd] update passwd failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 管理员重置密码
     */
    @Deprecated
    @PostMapping("/resetAdminPasswd")
    @ResponseBody
    public Result resetAdminpasswd(@RequestBody HyUserReq req) {
        LOGGER.info("[resetAdminPasswd] req: " + new Gson().toJson(req));

        // 1. 校验参数;
        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }
        // 只有管理员，可以重置密码
        if (session != null && !session.hasRight(this.getControllerCRight())) {
            return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
        }

        // TODO: 权限校验,每个类型的管理员都应该可以更改自己的密码
        HyUser hyUser = hyUserService.findById(req.getAdminUserId());
        if (hyUser == null) {
            return ResultGenerator.genFailResult(Consts.NO_SUCH_ADMIN_USER);
        }

        String id = hyUser.getUserIdentity();
        if (StringUtils.isEmpty(id)) {
            LOGGER.warn("[resetAdminPasswd] id empty");
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        String passwd = id.substring(id.length() - 6, id.length());
        hyUser.setUserPasswd(passwd);
        try {
            hyUserService.update(hyUser);
        } catch (Exception e) {
            LOGGER.warn("[resetAdminPasswd] update passwd failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 管理员重置密码
     */
    @Deprecated
    @PostMapping("/updateTelephoneNum")
    @ResponseBody
    public Result updateTelephoneNum(@RequestBody HyUserReq req) {
        LOGGER.info("[updateTelephoneNum] req: " + new Gson().toJson(req));

        // 1. 校验参数;
        if (StringUtils.isEmpty(req.getTelMsgCode()) || StringUtils.isEmpty(req.getOriginTelephoenNum())
                || StringUtils.isEmpty(req.getNewTelephoenNum())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        // 短信验证码
        String redisV = getSession(Consts.HY_VERIFY_CODE_PREFIX + req.getOriginTelephoenNum());
        if (StringUtils.isEmpty(redisV)) {
            LOGGER.warn("[updateTelephoneNum] update telephone num failed: empty");
            return ResultGenerator.genFailResult(Consts.VALIDATE_SMS_CODE_FAILED);
        }

        if (!redisV.equalsIgnoreCase(req.getTelMsgCode())) {
            return ResultGenerator.genFailResult(Consts.VALIDATE_SMS_CODE_FAILED);
        }

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }
        // 只有管理员，可以重置密码
        if (session != null && !session.hasRight(this.getControllerCRight())) {
            return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
        }

        // TODO: 权限校验,每个类型的管理员都应该可以更改自己的密码
        HyUser hyUser = hyUserService.findById(req.getAdminUserId());
        if (hyUser == null) {
            return ResultGenerator.genFailResult(Consts.NO_SUCH_ADMIN_USER);
        }

        hyUser.setUserTelephone(req.getNewTelephoenNum());
        try {
            hyUserService.update(hyUser);
        } catch (Exception e) {
            LOGGER.warn("[updateTelephoneNum] update telephone failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 获取自己管理员账号的详情
     */
    @PostMapping("/getUserDetailById")
    @ResponseBody
    public Result getUserDetailById(@RequestBody HyUserReq req) {
        LOGGER.info("[getUserDetailById] req: " + new Gson().toJson(req));

        // 1. 校验参数;
        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }
        //TODO: 各个管理员都应该可以获取自己账号的详情
        // 2. 根据userId，获取用户详情， 注意join on hy_role表获取 角色名称;
        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
        List<HyUserVoRole> list = hyUserService.findWithRoleById(req.getAdminUserId());
        if (list.size() == 0) {
            return ResultGenerator.genFailResult(Consts.NO_SUCH_OBJECT);
        }
        PageInfo pageInfo = new PageInfo(list);

        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 更新管理员账号的详情
     */
    @PostMapping("/updateHyUserById")
    @ResponseBody
    public Result updateHyUserById(@RequestBody HyUserReq req) {
        LOGGER.info("[updateHyUserById] req: " + new Gson().toJson(req));

        // 1. 校验参数;
        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        HyUser hyUser = null;
        try {
            Condition cond = new Condition(HyUser.class);
            cond.createCriteria()
                    .andEqualTo("userId", req.getUserId());

            List<HyUser> hyUserList = hyUserService.findByCondition(cond);
            if (hyUserList.size() == 0) {
                LOGGER.warn("[updateHyUserById] hy user empty");
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }

            hyUser = hyUserList.get(0);

            // 不能更改super admin信息
            if (CRole.getRole((int) hyUser.getRoleId()) == CRole.SUPER_ADMIN) {
                LOGGER.warn("[updateHyUserById] Cannot change super admin info");
                return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
            }

            if (!StringUtils.isEmpty(req.getUserAccountName())) {
                hyUser.setUserAccountName(req.getUserAccountName());
            }
            if (!StringUtils.isEmpty(req.getUserDetailAddress())) {
                hyUser.setUserDetailAddress(req.getUserDetailAddress());
            }
            if (!StringUtils.isEmpty(req.getUserIdentity())) {
                hyUser.setUserIdentity(req.getUserIdentity());
            }
            if (!StringUtils.isEmpty(req.getUserName())) {
                hyUser.setUserName(req.getUserName());
            }
            if (req.getUserRangeType() != hyUser.getUserRangeType()) {
                hyUser.setUserRangeType(Consts.HY_ADMIN_RANGE_TYPE_RANGE);
            }
            if (req.getUserSex() != hyUser.getUserSex()) {
                hyUser.setUserSex(req.getUserSex());
            }
            if (!StringUtils.isEmpty(req.getUserTelephone())) {
                hyUser.setUserTelephone(req.getUserTelephone());
            }
            if (!StringUtils.isEmpty(req.getUserPhoneNum())) {
                hyUser.setUserPhoneNum(req.getUserPhoneNum());
            }
            //TODO: 同前端协商该值
            if (req.getUserState() != hyUser.getUserState()) {
                hyUser.setUserState(req.getUserState());
            }
            if (!StringUtils.isEmpty(req.getUserProvince())) {
                hyUser.setUserProvince(req.getUserProvince());
            }
            if (!StringUtils.isEmpty(req.getUserCity())) {
                hyUser.setUserCity(req.getUserCity());
            }
            if (!StringUtils.isEmpty(req.getUserLocation())) {
                hyUser.setUserLocation(req.getUserLocation());
            }
            if (!StringUtils.isEmpty(req.getUserPicture())) {
                hyUser.setUserPicture(req.getUserPicture());
            }
            if (req.getRoleId() != hyUser.getRoleId()) {
                hyUser.setRoleId(req.getRoleId());
            }
            if (req.getUserParentId() != hyUser.getUserParentId()) {
                hyUser.setUserParentId(req.getUserParentId());
            }
            if (!StringUtils.isEmpty(req.getUserParentName())) {
                hyUser.setUserParentName(req.getUserParentName());
            }
            hyUserService.update(hyUser);
        } catch (Exception e) {
            LOGGER.warn("[updateHyUserById] get hy user failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        return ResultGenerator.genSuccessResult(hyUser);
    }

    @PostMapping("/deleteHyUserById")
    @ResponseBody
    public Result deleteHyUserById(@RequestBody HyUserReq req) {
        LOGGER.info("[deleteHyUserById] req: " + new Gson().toJson(req));

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        HyUser hyUser;
        try {
            Condition cond = new Condition(HyUser.class);
            cond.createCriteria()
                    .andEqualTo("userId", req.getUserId());

            List<HyUser> hyUserList = hyUserService.findByCondition(cond);
            if (hyUserList.size() == 0) {
                LOGGER.warn("[deleteHyUserById] hy user empty");
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }

            hyUser = hyUserList.get(0);
            hyUser.setUserState(Consts.HY_ADMIN_ACCOUNT_STATE_DESTROYED);
            hyUserService.update(hyUser);
        } catch (Exception e) {
            LOGGER.warn("[deleteHyUserById] delete hyuser failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        return ResultGenerator.genSuccessResult(Consts.SUSSESS);
    }
}
