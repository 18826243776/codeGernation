package cn.mingyue.code.second;

import cn.mingyue.code.Controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(tags = " 栏目管理 ")
@RequestMapping(value = " /admin/column ")
public class TestApi {
    @Autowired
    TestService TestService;

    @ApiImplicitParams(value = {@ApiImplicitParam(value = " name ", required = true)
            , @ApiImplicitParam(paramType = " query ", value = " des ", required = true)
    })
    @RequestMapping(value = " /test ")
    void add() {
    }
}
