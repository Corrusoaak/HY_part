package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyRight;
import com.heye.crm.common.model.HyRole;
import com.heye.crm.common.model.HyRoleRight;
import com.heye.crm.common.model.HyUser;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.configurer.WebConfig;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.consts.CRole;
import com.heye.crm.server.request.HyRoleReq;
import com.heye.crm.server.response.HyAdminSessionResp;
import com.heye.crm.server.service.HyRightService;
import com.heye.crm.server.service.HyRoleRightService;
import com.heye.crm.server.service.HyRoleService;
import com.heye.crm.server.service.HyUserService;
import com.heye.crm.server.vo.HyRoleRightJoin;
import com.heye.crm.server.vo.HyRoleRightVoRoleVoRight;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/role")
public class HyRoleController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyRoleController.class);
    @Resource
    private HyUserService hyUserService;
    @Resource
    private HyRoleService hyRoleService;
    @Resource
    private HyRightService hyRightService;
    @Resource
    private HyRoleRightService hyRoleRightService;

    @Override
    public CRight getControllerCRight() {
        return CRight.SUPER_RIGHT;
    }

    /**
     * 添加角色，同时添加授权
     */
    @PostMapping("/addRoleAndRight")
    @ResponseBody
    public Result addRoleAndRight(@RequestBody HyRoleReq req) {
        LOGGER.info("[addRoleAndRight] req:" + new Gson().toJson(req));

        // 1. 解析请求参数，并校验;
        if (StringUtils.isEmpty(req.getRoleName()) || req.getParentRoleId() == null) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }
        if (session != null && !session.hasRight(getControllerCRight())) {
            return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
        }

        try {
            Condition cond = new Condition(HyRole.class);
            cond.createCriteria()
                    .andEqualTo("roleName", req.getRoleName())
                    .andEqualTo("roleState", Consts.HY_ROLE_STATE_OK);

            List<HyRole> hyRoles = hyRoleService.findByCondition(cond);
            if (hyRoles != null && hyRoles.size() > 0) {
                return ResultGenerator.genFailResult(Consts.ALREADY_EXISTED_IN_DB);
            }
        } catch (TooManyResultsException e) {
            return ResultGenerator.genFailResult(Consts.ALREADY_EXISTED_IN_DB);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        // 2. 将HyRoleReq请求，首先写入hy_role表；
        long roleId;
        try {
            HyRole hyRole = new HyRole();
            hyRole.setRoleParentId(req.getParentRoleId());
            hyRole.setRoleName(req.getRoleName());
            hyRole.setRoleDesc(req.getRoleDesc());
            hyRole.setRoleRangeType(req.getRangeType());
            hyRole.setRoleState(Consts.HY_ROLE_STATE_OK);
            hyRole.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            hyRoleService.save(hyRole);

            Condition condition = new Condition(HyRole.class);
            condition.createCriteria()
                    .andEqualTo("roleName", req.getRoleName());
            List<HyRole> list = hyRoleService.findByCondition(condition);
            if (list.size() <= 0) {
                return ResultGenerator.genFailResult(Consts.INSERT_FAILED);
            } else if (list.size() > 1) {
                return ResultGenerator.genFailResult(Consts.ALREADY_EXISTED_IN_DB);
            } else {
                roleId = list.get(0).getRoleId();
            }
        } catch (Exception e) {
            LOGGER.info("[addRoleAndRight] insert role failed:", e);
            return ResultGenerator.genFailResult(Consts.INSERT_FAILED);
        }

        // 3. 遍历写入HyRoleRight表（因为HyRole 一个Role->Right为一条记录）: 写入前校验在hy_role_right表中，
        // role_id+right_id存在与否;
        long successCount = 0;
        String[] rightList = req.getRightList().split(",");
        if (StringUtils.isEmpty(req.getRightList()) || rightList.length == 0) {
            LOGGER.warn("[addRoleAndRight] there no right list in this role: {}", req.getRoleName());
            //return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        } else {
            for (String rightIdString : rightList) {
                long rightId = Long.valueOf(rightIdString.trim());
                try {
                    Condition condition = new Condition(HyRight.class);

                    condition.createCriteria()
                            .andEqualTo("rightId", rightId);

                    List<HyRight> list = hyRightService.findByCondition(condition);
                    if (list.size() > 0) {
                        HyRoleRight hyRoleRight = req.toHyRoleRight(roleId, rightId);
                        try {
                            hyRoleRightService.save(hyRoleRight);
                            successCount++;
                        } catch (Exception e) {
                            LOGGER.info("[addRoleAndRight] save into hyRoleRight fail. "
                                    + new Gson().toJson(hyRoleRight));
                        }
                    } else {
                        LOGGER.info("[addRoleAndRight] rightId " + rightId + " not found.");
                    }
                } catch (Exception e) {
                    LOGGER.info("[addRoleAndRight] rightId " + rightId + " not found.");
                }
            }

            if (successCount == 0) {
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }
        }

        return ResultGenerator.genSuccessResultByMessage(Consts.SUSSESS);
    }

    /**
     * 获取角色下权限列表
     */
    @PostMapping("/getRoleRightList")
    @ResponseBody
    public Result getRoleRightList(@RequestBody HyRoleReq req) {
        LOGGER.info("req:" + new Gson().toJson(req));
        if (!checkPageReq(req)) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        // TODO: check sesion has enough right
        if (session != null) {
            switch (CRole.getRole(session.getRoleId())) {
            case SUPER_ADMIN:
                break;
            default:
                return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
            }
        }

        // 1. 分页获取RoleRight列表;
        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
        // 2. 注意必要的join操作;
        List<HyRoleRightVoRoleVoRight> list = hyRoleService.getRoleList();
        PageInfo pageInfo = new PageInfo<>(list);

        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 获取某一角色的权限列表
     *
     * @param req
     * @return
     */
    @PostMapping("/getRoleRightById")
    @ResponseBody
    public Result getRoleRightById(@RequestBody HyRoleReq req) {
        LOGGER.info("req:" + new Gson().toJson(req));
        if (!checkPageReq(req)) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        // TODO: check sesion has enough right
        if (session != null) {
            switch (CRole.getRole(session.getRoleId())) {
            case SUPER_ADMIN:
                break;
            default:
                return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
            }
        }

        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
        List<HyRoleRightVoRoleVoRight> list = hyRoleService.getRoleListById(req.getRrId());
        if (list.size() == 0) {
            //TODO:
            return ResultGenerator.genSuccessResult(Consts.NOT_EXISTED_IN_DB);
        }
        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 删除某一角色
     *
     * @param req
     * @return
     */
    @PostMapping("/deleteRole")
    @ResponseBody
    public Result deleteRole(@RequestBody HyRoleReq req) {
        LOGGER.info("req:" + new Gson().toJson(req));
        if (!checkPageReq(req)) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }

        if (session != null && !session.hasRight(this.getControllerCRight())) {
            return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
        }

        if (req.getRoleId() <= CRole.getMaxNoDeletedRoleId()) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        // 1. 该角色下有没有管理员
        Condition cond = new Condition(HyUser.class);
        cond.createCriteria().andEqualTo("roleId", req.getRoleId());
        List<HyUser> list = hyUserService.findByCondition(cond);
        if (list != null && list.size() > 0) {
            return ResultGenerator.genFailResult(Consts.HY_ROLE_HAS_ALREADY_USER_REGISTED);
        }

        // 2. 删除该角色下的权限映射， 确保没有以该role作为父权限的
        cond = new Condition(HyRole.class);
        cond.createCriteria().andEqualTo("roleParentId", req.getRoleId());
        List<HyRole> hyRoles = hyRoleService.findByCondition(cond);
        if (hyRoles != null && hyRoles.size() > 0) {
            LOGGER.warn("role has depend this parent:" + req.getRoleId());
            return ResultGenerator.genFailResult(Consts.HY_ROLE_IS_PARENT_OF_OTHERS);
        }

        cond = new Condition(HyRoleRight.class);
        cond.createCriteria().andEqualTo("roleId", req.getRoleId());
        List<HyRoleRight> hyRoleRights = hyRoleRightService.findByCondition(cond);
        if (hyRoleRights != null && hyRoleRights.size() > 0) {
            for (HyRoleRight roleRight : hyRoleRights) {
                hyRoleRightService.deleteById(roleRight.getRrId());
            }
        }

        // 3. 删除该角色
        hyRoleService.deleteById(req.getRoleId());

        return ResultGenerator.genSuccessResult(Consts.SUSSESS);
    }

    @PostMapping("/deleteRoleRight")
    @ResponseBody
    public Result deleteRoleRight(@RequestBody HyRoleReq req) {
        LOGGER.info("[deleteRoleRight] req:" + new Gson().toJson(req));

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }
        if (session != null) {
            switch (CRole.getRole(session.getRoleId())) {
            case SUPER_ADMIN:
                break;
            default:
                return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
            }
        }

        try {
            Condition cond = new Condition(HyRoleRight.class);
            cond.createCriteria()
                    .andEqualTo("roleId", req.getRoleId())
                    .andEqualTo("rightId", req.getRightId());
            List<HyRoleRight> lists = hyRoleRightService.findByCondition(cond);
            if (lists.size() == 0) {
                return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
            }

            hyRoleRightService.deleteById(lists.get(0).getRrId());
        } catch (Exception e) {
            LOGGER.warn("delete role-right failed: ", e);
            return ResultGenerator.genFailResult(Consts.ALREADY_EXISTED_IN_DB);
        }
        return ResultGenerator.genSuccessResult(Consts.SUSSESS);
    }

    @PostMapping("/updateRole")
    @ResponseBody
    public Result updateRole(@RequestBody HyRoleReq req) {
        LOGGER.info("req:" + new Gson().toJson(req));
        if (StringUtils.isEmpty(req.getRoleDesc()) && StringUtils.isEmpty(req.getRoleName())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }
        if (session != null && !session.hasRight(this.getControllerCRight())) {
            return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
        }

        try {
            // 1. 查看有无该角色
            List<HyRoleRightJoin> lists = hyRoleService.getRoleRightListById(req.getRoleId());
            if (lists.size() == 0) {
                LOGGER.warn("hy role right not found");
                return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
            }

            List<Long> toDelete = new ArrayList<>();
            List<Long> toAdd = new ArrayList<>();
            Map<Long, Long> rightIdToRrId = new HashMap<>();

            if (req.getRightList() != null && !req.getRightList().isEmpty()) {
                // rightR: 请求中的rightList
                // rightL: 已有的rightList
                // rightR中存在, rightL中不存在： 在roleRight中添加
                // rightL中存在, rightR中不存在: 在roleRight中删除
                String[] reqR = req.getRightList().split(",");
                List<Long> rightR = new ArrayList<>();
                for (String r : reqR) {
                    rightR.add(Long.parseLong(r));
                }

                List<Long> rightL = new ArrayList<>();
                for (HyRoleRightJoin join : lists) {
                    rightL.add(join.getRightId());

                    if (!rightR.contains(join.getRightId())) {
                        LOGGER.info("delete rightId: " + join.getRightId());
                        toDelete.add(join.getRightId());
                        rightIdToRrId.put(join.getRightId(), join.getRrId());
                    }
                }

                for (Long r : rightR) {
                    if (!rightL.contains(r)) {
                        toAdd.add(r);
                    }
                }
            }

            // 2. 更新角色设置
            long roleId = req.getRoleId();

            HyRole hyRole = hyRoleService.findById(roleId);
            LOGGER.debug("hy role id:" + lists.get(0).getRoleId());
            if (hyRole == null) {
                LOGGER.warn("hy role not found");
                return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
            }
            if (!StringUtils.isEmpty(req.getRoleDesc())) {
                hyRole.setRoleDesc(req.getRoleDesc());
            }
            if (!StringUtils.isEmpty(req.getRoleName())) {
                hyRole.setRoleName(req.getRoleName());
            }
            if (req.getRangeType() != hyRole.getRoleRangeType()) {
                hyRole.setRoleRangeType(req.getRangeType());
            }
            hyRole.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            hyRoleService.update(hyRole);

            //TODO: 3. 增删权限, 只只会增加权限，删除的话请走删除逻辑
            if (toAdd.size() != 0) {
                for (Long l : toAdd) {
                    HyRoleRight hyRoleRight = new HyRoleRight();
                    hyRoleRight.setRightId(l);
                    hyRoleRight.setRoleId(roleId);
                    hyRoleRight.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    hyRoleRightService.save(hyRoleRight);
                }
            }
            if (toDelete.size() != 0) {
                for (Long l : toDelete) {
                    LOGGER.info("delete rightId:" + l);
                    if (rightIdToRrId.containsKey(l)) {
                        hyRoleRightService.deleteById(rightIdToRrId.get(l));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.warn("update role failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        return ResultGenerator.genSuccessResult(Consts.SUSSESS);
    }
}
