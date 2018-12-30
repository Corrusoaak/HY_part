#!/bin/bash

################################### 权限 模块  ###################################
#  获取所有的权限列表
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "adminSessionId":"902948be361c40c8bf387ec3a8137e84", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/admin/getRightList"



################################### 角色 模块  ###################################
# 获取所有的角色列表
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "adminSessionId":"5cb5626efe8c4beb8c155fc6027b851e", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/admin/getRoleList"
# 获取角色权限列表
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "adminSessionId":"631127eef2b248b8b0f0a181f70ed190", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/role/getRoleRightList"
# 新建角色设置权限,0:局域，1:全局
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"roleName":"test3", "parentRoleId":1, "rightList":"1,2", "rangeType":1, "adminSessionId":"631127eef2b248b8b0f0a181f70ed190", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/role/addRoleAndRight"
# 更新角色介绍和名称
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"roleName":"test3", "roleId":10, "roleName":"test", "roleDesc":"test", "rangeType":0, "rightList":"2,1","adminSessionId":"5cb5626efe8c4beb8c155fc6027b851e", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/role/updateRole"
#删除角色中某一条权限
# curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"adminSessionId":"631127eef2b248b8b0f0a181f70ed190", "adminUserId":1, "roleId":5, "rightId":1}' -v "http://47.106.80.130:8088/api/v1/role/deleteRoleRight"

curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"adminSessionId":"5cb5626efe8c4beb8c155fc6027b851e", "adminUserId":1, "roleId":9}' -v "http://47.106.80.130:8088/api/v1/role/deleteRole"

################################### 管理员表  ###################################
# 通过密码登录
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"userName":"admin", "userPasswd":"123456"}' -v "http://47.106.80.130:8088/api/v1/admin/loginHyUserByPasswd"

# 获取账户管理用户列表
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":3, "adminSessionId":"5cb5626efe8c4beb8c155fc6027b851e", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/admin/getHyUserList"

# 获取我的账号详情
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{adminSessionId":"03a333ae6dfa4d0d83347332879b3671", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/admin/getUserDetailById"

# 搜索列表-真实名字
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":3, "adminSessionId":"1d44389b44a24df7b1f2e1d86ba78ae3", "adminUserId":1, "realName":"李书明"}' -v "http://47.106.80.130:8088/api/v1/admin/getHyUserList"

curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":3, "adminSessionId":"1d44389b44a24df7b1f2e1d86ba78ae3", "adminUserId":1, "locationName":"滨江"}' -v "http://47.106.80.130:8088/api/v1/admin/getHyUserList"

curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":3, "adminSessionId":"1d44389b44a24df7b1f2e1d86ba78ae3", "adminUserId":1, "roleName":"店长"}' -v "http://47.106.80.130:8088/api/v1/admin/getHyUserList"

# 修改密码，需要原来密码，新密码
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"originPasswd":"a", "userPasswd":"admin", "adminSessionId":"03a333ae6dfa4d0d83347332879b3671", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/admin/updateUserPasswd"

# 重置用户密码
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"adminSessionId":"03a333ae6dfa4d0d83347332879b3671", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/admin/resetAdminPasswd"

# 查询父权限列表，在创建管理员的时候使用
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "adminSessionId":"55c8e0e71cf641cc956ea30dc58faaab", "adminUserId":1, "roleId": 3, "userParentName":"东"}' -v "http://47.106.80.130:8088/api/v1/admin/getParentRoleAdmins"

# 创建管理员账号
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"adminSessionId":"55c8e0e71cf641cc956ea30dc58faaab", "adminUserId":1, "userName":"李书明", "roleId":4, "userParentName":"喜洋洋","userParentId":4,"userAccountName":"李书明", "userSex":0, "userTelephone":"18210287328", "userProvince":"浙江", "userCity":"杭州", "userLocation":"滨江","userDetailAddress":"xxx","adminUserIdentity":"1234567889900032", "userRangeType":0}' -v "http://47.106.80.130:8088/api/v1/admin/insertIntoHyUser"

# 更新账号
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"adminSessionId":"9517e62938e2499b8ca258698d155698", "adminUserId":1, userState“：1， "userName":"admin", "roleId":1, "userParentName":"喜洋洋","userParentId":4,"userAccountName":"李书明", "userSex":0, "userTelephone":"18210287328", "userProvince":"浙江", "userCity":"杭州", "userLocation":"滨江","userDetailAddress":"xxx","adminUserIdentity":"1234567889900032", "userRangeType":0}' -v "http://47.106.80.130:8088/api/v1/admin/updateHyUserById"

# 更新状态, 0:正常， 1：暂停， -1：销毁
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"adminSessionId":"9517e62938e2499b8ca258698d155698", "adminUserId":1, "userState":-1}' -v "http://47.106.80.130:8088/api/v1/admin/updateHyUserById"



# 更新手机号
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"adminSessionId":"9517e62938e2499b8ca258698d155698", "adminUserId":1, "newTelephoenNum":"xxx", "originTelephoenNum":"xx", "telMsgCode":"xxx"}' -v "http://47.106.80.130:8088/api/v1/admin/updateTelephoneNum"

################################### HyCustomer 客户表 ###################################
# 写入客户关系表
# 1. 需要先获取店铺列表，参考店铺模块
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"ctmName":"李书明", "storeId":1, "ctmAccountName":"test", "ctmPasswd":"admin", "ctmSex":0, "ctmEmail":"lism@heye.com", "ctmPicture":"浙江", "ctmPhoneNum":"18210287329", "ctmTelephone":"", "ctmBirthDay":"1991-04-01", "ctmProvince":"河南", "managerName":"admin", "ctmLocation":"浙江区", "ctmDetailAddress":"滨江区xxx", "ctmZipCode":"00001", "ctmIdentity":"12212122", "createdAt":"2018-04-06"}' -v "http://47.106.80.130:8088/api/v1/customer/insertIntoHyCustom"

# 根据id获取客户关系详情
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "ctmId":1, "adminSessionId":"902948be361c40c8bf387ec3a8137e84", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/customer/getHyCustomById"

# 获取客户关系列表
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "adminSessionId":"55c8e0e71cf641cc956ea30dc58faaab", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/customer/getHyCustomerList"

# 根据id删除客户关系
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "ctmId":1, "adminSessionId":"55c8e0e71cf641cc956ea30dc58faaab","adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/customer/deleteHyCustomById"

# 基于时间获取客户关系表，其中filterByDateType定义如下，0: 今天新增, 1: 本周新增, 2: 本月新增;
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "filterByDateType":0, "adminSessionId":"55c8e0e71cf641cc956ea30dc58faaab","adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/customer/getHyCustomByDateType"

# 查询api(支持根据日期和名称统一查询)
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "managerName":"lishuming", "filterByDateType":0, "adminSessionId":"55c8e0e71cf641cc956ea30dc58faaab","adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/customer/searchByManagerNameOrDateType"

# 根据id 批量删除客户关系
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"ctmIds":"5,8,10,1", "adminSessionId":"55c8e0e71cf641cc956ea30dc58faaab","adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/customer/batchDeleteHyCustomById"

# 更新客户关系表
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"adminSessionId":"55c8e0e71cf641cc956ea30dc58faaab","adminUserId":1, "ctmId":22, "ctmName":"李书明01", "ctmAccountName":"test01", "ctmPasswd":"admin01", "ctmSex":0, "ctmEmail":"lism@heye.com", "ctmPicture":"浙江", "ctmPhoneNum":"18210287329", "ctmTelephone":"", "ctmBirthDay":"1991-04-01", "ctmProvince":"河南", "managerName":"admin", "ctmLocation":"浙江区", "ctmDetailAddress":"滨江区xxx", "ctmZipCode":"00002", "ctmIdentity":"12212122"}' -v "http://47.106.80.130:8088/api/v1/customer/updateHyCustomById"



################################### 小程序 ###################################
# 小程序获取微信签名
#curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"url":"test_url"}' -v "http://47.106.80.130:8088/api/v1/customer/getWeiXinSign"
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"code":"test"}' -v "http://47.106.80.130:8088/api/v1/customer/getWeiXinCode"

# 微信登陆
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"code":"test"}' -v "http://47.106.80.130:8088/api/v1/weixin/wxLogin"
# 绑定手机号
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"code":"test", "ctmTelephone":"18867137888", "verifyCode":"123456"}' -v "http://47.106.80.130:8088/api/v1/weixin/bindTelephoneNum"
# 更新用户信息
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"ctmId":8, "ctmName":"李21", "ctmAccountName":"test01", "ctmPasswd":"admin01", "ctmSex":0, "ctmEmail":"lism@heye.com", "ctmPicture":"浙江", "ctmPhoneNum":"18867137888", "ctmTelephone":"", "ctmBirthDay":"1991-04-01", "ctmProvince":"河南", "managerName":"admin", "ctmLocation":"浙江区", "ctmDetailAddress":"滨江区xxx", "ctmZipCode":"00002", "ctmIdentity":"12212122"}' -v "http://47.106.80.130:8088/api/v1/weixin/updateCustomer"
# 请求发送验证码
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"ctmTelePhone":"18867137889"}' -v "http://47.106.80.130:8088/api/v1/weixin/sendPhoneIdentifyCode"

################################### 投诉建议  模块###################################
# 新建针对产品的投诉
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"ctmId":1, "adviseType":1, "adviseDesc":"投诉描述", "advisePictures":"照片，可选"}' -v "http://47.106.80.130:8088/api/v1/advise/addAdviseToPro"
# 新建针对产品的投诉
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"ctmId":3, "adviseType":1, "adviseDesc":"投诉描述", "advisePictures":"照片，可选"}' -v "http://47.106.80.130:8088/api/v1/advise/addAdviseToPro"
# 获取投诉列表，根据ctmId，客户Id
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "ctmId":3}' -v "http://47.106.80.130:8088/api/v1/advise/adviseList"

# 根据adviseId获取投诉详情
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "adviseId":1}' -v "http://47.106.80.130:8088/api/v1/advise/getAdviseByID"

# 回复更新投诉建议，更改投诉状态
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"adviseId": 1, "aviseReplies":"ok"}' -v "http://47.106.80.130:8088/api/v1/advise/replyAdvise"

#更新建议状态
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"adviseId": 1, "adviseState":2}' -v "http://47.106.80.130:8088/api/v1/advise/updateAdvise"


# 获取投诉列表，根据ctmId，客户Id
0: 全部状态
1: 未处理
2: 处理中
3: 已处理

0: 全部
1: 近一周
2: 近一月
3：近一年

0: 全部分类
1: 产品使用问题
2：销售使用问题

managerName
ctmName
location

curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "adviseState":0, "filterByDateType":1, "adviseType":0, "managerName":""}' -v "http://47.106.80.130:8088/api/v1/advise/searchAdviseList"

返回信息：
投诉ID
产品ID
投诉状态
投诉类型
问题描述
图片列表
问题回复
创建时间
修改时间
店长名字
客户名字
所属地区

# 修改状态
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{ "adviseId": 2, "adviseId":1}' -v "http://47.106.80.130:8088/api/v1/advise/updateAdvise"

################################### 店铺 模块  ###################################
# 获取店铺列表
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "adminSessionId":"902948be361c40c8bf387ec3a8137e84", "adminUserId":1}' -v "http://47.106.80.130:8088/api/v1/store/getHyStoreList"



################################### 地址 模块  ###################################
# 获取地址列表
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10}' -v "http://47.106.80.130:8088/api/v1/location/getLocationList"


curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10}' -v "http://47.106.80.130:8088/api/v1/location/getLocationList"
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "storeProvince":"浙江", "storeCity":"杭州", "storePosition":"下城区"}' -v "http://47.106.80.130:8088/api/v1/store/getHyStoreListByLoc"


curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "storeProvince":"浙江", "storeCity":"杭州", "storePosition":"下城区"}' -v "http://47.106.80.130:8088/api/v1/store/getHyStoreListByLoc"

curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10, "storeTelephone":"18210287328"}' -v "http://47.106.80.130:8088/api/v1/store/getHyStoreListByTelephone"

################################### 关怀 模块  ###################################

# 获取节日列表
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10}' -v "http://47.106.80.130:8088/api/v1/care/getCustomCareList"
注意：客户生日，该节日为不可编辑的，自动添加的；

#获取某一节日详情？
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10}' -v "http://47.106.80.130:8088/api/v1/care/getCustomCareList"
# 添加节日
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"festName":"五一节", "festDate":"2018-05-01", "sendTime":"10:00","sendDesc":"亲爱的和也用户，祝您五一节快乐", "ctmRangeType":0, "ctmSex":0, "ageRange":"40-50", "consumeRange":"1000-10000","switchSate":1}' -v "http://47.106.80.130:8088/api/v1/care/addCustomCare"
# 更新节日
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"careId":2, "festName":"五一节", "festDate":"2018-05-01", "sendTime":"10:00","sendDesc":"亲爱的和也用户，祝您五一节快乐", "ctmRangeType":0, "ctmSex":0, "ageRange":"40-50", "consumeRange":"1000-10000"}' -v "http://47.106.80.130:8088/api/v1/care/updateCustomCare"
# 删除节日
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"careId":1}' -v "http://47.106.80.130:8088/api/v1/care/deleteCustomCare"

################################### 分析 模块  ###################################
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"anaType":5, "aggLevel":1, "startDate":"2018-04", "endDate":"2018-06"}' -v "http://47.106.80.130:8088/api/v1/analyze/getCtmAnalyze"
#  >=startDate， <endDate，不包含endDate



##### 跟单模块

curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"orderId":"1232321", "expressId":"12321", "expressCompany":"test", "storeTelephone":"12312421", "storeManager":"adfl", "storeDetailAddress":"dafl", "deliverHouse":"af", "deliverId":"deliverId"}' -v "http://47.106.80.130:8088/api/v1/follow/addFollow"
curl -XPOST   -H 'Content-type:application/json;charset=utf-8' -d  '{"pageNo":1, "pageSize":10}' -v "http://47.106.80.130:8088/api/v1/follow/followList"


