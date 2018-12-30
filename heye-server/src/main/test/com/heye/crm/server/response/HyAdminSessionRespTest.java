package com.heye.crm.server.response;

import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.response.HyAdminSessionResp;
import org.junit.Test;

/**
 * @author : lishuming
 */
public class HyAdminSessionRespTest {
    @Test
    public void testHyAdminHasRight() {
        HyAdminSessionResp h1 = new HyAdminSessionResp();
        h1.setRightList("1,2,3,4,5");

        assert h1.hasRight(CRight.SUPER_RIGHT) == true;

        h1.setRightList("7");
        assert h1.hasRight(CRight.SUPER_RIGHT) == false;
    }
}
