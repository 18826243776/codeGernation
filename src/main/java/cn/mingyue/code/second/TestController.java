package cn.mingyue.code.second;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/5/2 15:44
 */
@RequestMapping
public class TestController {

    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "",required = true),
            @ApiImplicitParam(value = "",required = true)
    })
    public void add(){

    }
}
