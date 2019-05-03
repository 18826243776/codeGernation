package cn.mingyue.code.second;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = " 测试实体 ")
public class TestSwController {
    @ApiImplicitParams(value = {@ApiImplicitParam(name = " id ", paramType = " query ", value = " id ", required = true)
            , @ApiImplicitParam(name = " name ", paramType = " query ", value = " 名字 ", required = true)
            , @ApiImplicitParam(name = " des ", paramType = " query ", value = " 描述 ", required = true)
    })

    public void add(String id, String name, String des) {
    }

    @ApiImplicitParams(value = {@ApiImplicitParam(name = " id ", paramType = " query ", value = " id ", required = true)
            , @ApiImplicitParam(name = " name ", paramType = " query ", value = " 名字 ", required = true)
            , @ApiImplicitParam(name = " des ", paramType = " query ", value = " 描述 ", required = true)
    })

    public void update(String id, String name, String des) {
    }
}
