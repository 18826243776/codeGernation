package cn.mingyue.code;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/5/3 19:35
 */
@ApiModel(value = "测试实体")
public class TestEntity {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("描述")
    private String des;
    @ApiModelProperty("实体")
    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
