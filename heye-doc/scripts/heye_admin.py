# -*- coding: UTF-8 -*-

import sys
import pinyin

reload(sys)
sys.setdefaultencoding('utf-8')

DATA_PATH = "/Users/lishuming/PycharmProjects/learnpython/datas/"
class HyAdminData:
    def __init__(self, code_s, name_s, loc_s, detail_loc_s, state_s, tele_n, phone_n, id_s):
        self.code = code_s
        self.name = name_s
        self.loc = loc_s
        self.detail_loc = detail_loc_s
        self.state = state_s
        self.phone = phone_n
        self.tel = tele_n
        self.id = id_s
        self.user_name = ""
        self.role_id = ""
        self.parent_name = ""
        self.rid = 0
        self.parent_rid = 0

class HyLocData:
    def __init__(self, prov, city, region):
        self.prov = prov
        self.city = city
        self.region = region

    @property
    def prov(self):
        return self.prov


class HyStore:
    def __init__(self, manager_id, phone, tel, name, loc, detail_loc):
        self.manager_id = manager_id
        self.phone = phone
        self.tel = tel
        self.name = name
        self.loc = loc
        self.detail_loc = detail_loc

if __name__ == "__main__":
    def get_d_loc_list(arr_loc_s):
        assert len(arr_loc_s) >= 4
        return '_'.join([arr_loc_s[0], arr_loc_s[1], arr_loc_s[2]])

    def format_location_sql(arr_loc_data):
        hy_loc_data = DATA_PATH + "heye_location_datas.sql"
        fp = open(hy_loc_data, 'w')

        for loc in arr_loc_data:
            sql = "insert into hy_location(loc_province, loc_city, loc_location, loc_state) values(\"" + loc.prov + "\",\"" + loc.city + "\",\"" + loc.region + "\"" + ", 1);\n"

            fp.write(sql)
        fp.close()

    def format_user_sql(arr_user_data):
        hy_data = DATA_PATH + "heye_user_datas.sql"
        fp = open(hy_data, 'w')

        for data in arr_user_data:
            if isinstance(data, HyAdminData):
                data.id = data.id.strip()
                if len(data.id) > 6:
                    user_passwd = data.id[len(data.id) - 6:len(data.id)]
                else:
                    user_passwd = ""
                sql = "insert into hy_user(user_id, user_parent_id, role_id, user_name, user_password, user_account_name, user_parent_name, user_phone_num, user_telephone, user_province, user_city, user_location, user_detail_address, user_state) values(" \
                  + str(data.rid) + "," + str(data.parent_rid) + "," + str(data.role_id) + ",\"" + "\",\"".join([data.user_name, user_passwd, data.name, data.parent_name, data.phone, data.tel, data.loc.prov, data.loc.city, data.loc.region, data.detail_loc]) + "\", 1);\n"

            fp.write(sql)
        fp.close()

    def format_store_sql(arr_data):
        hy_data = DATA_PATH + "heye_store_datas.sql"
        fp = open(hy_data, 'w')

        for data in arr_data:
            if isinstance(data, HyStore):
                sql = "insert into hy_store(store_manager_id, store_phone_num, store_telephone, store_name, store_province, store_city, store_position, store_address, store_state) values(" \
                  + str(data.manager_id) + ",\"" +  "\",\"".join([data.phone, data.tel, data.name, data.loc.prov, data.loc.city, data.loc.region, data.detail_loc]) + "\", 1);\n"
            fp.write(sql)
        fp.close()

    def get_acc_user_name(user_name):
        arr = user_name.split("_")
        if len(arr) > 0:
            return arr[0] + "_" + str(int(arr[1]) + 1)
        else:
            return arr + "_1"

    heye_admin_file = "/Users/lishuming/PycharmProjects/learnpython/datas/heye_admin.csv"

    fp = open(heye_admin_file, 'r')

    data_list = []
    user_name_list = {}

    data_parent_map = {}

    loc_list = []
    d_loc_list = {}
    line_no = 0
    rid = 8


    store_list = []
    for line in fp.readlines():
        if line_no == 0:
            line_no = line_no + 1
            continue
        line_no = line_no + 1
        arr = line.split(",")
        if len(arr) != 8:
            assert "len is falied:" + str(len(arr))

        loc_s = arr[2].split("_")
        distinct_loc = get_d_loc_list(loc_s)

        if (len(loc_s) < 4):
            assert "loc len error" + arr[2]
        loc_data = HyLocData(loc_s[0], loc_s[1], loc_s[2])

        if not d_loc_list.has_key(distinct_loc):
            d_loc_list[distinct_loc] = 1
            loc_list.append(loc_data)

        data = HyAdminData(arr[0], arr[1], loc_data, arr[3], arr[4], arr[5], arr[6], arr[7])

        if len(data.phone) > 16:
            data.phone = data.phone.split(" ")[1]
        if len(data.tel) > 16:
            data.tel = data.tel.split(" ")[1]

        if len(data.phone) > 16 or len(data.tel) > 16:
            print data.tel
        data.rid = rid
        if "（" in data.name:
            data.name = data.name.split("（")[0]
        user_name = pinyin.get(data.name, format="strip")
        while(user_name_list.has_key(user_name)):
            user_name = get_acc_user_name(user_name)
        data.user_name = user_name

        if len(data.code) == 11:
            data.role_id = "4"
        else:
            data.role_id = "5"

        p_code = data.code[0:11]
        if data_parent_map.has_key(p_code):
            data_parent = data_parent_map[p_code]
            data.parent_name = data_parent.user_name
            data.parent_rid = data_parent.rid

        data_parent_map[data.code] = data
        rid = rid + 1
        data_list.append(data)

        store = HyStore(rid, data.phone, data.tel, data.name + "店", loc_data, data.detail_loc)
        store_list.append(store)

    format_location_sql(loc_list)
    format_user_sql(data_list)
    format_store_sql(store_list)


