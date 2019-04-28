package cn.mingyue.code.entity;

import cn.mingyue.code.Controller;
import cn.mingyue.code.Service;

import java.awt.*;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 9:57
 */
@Service(value = "test")
@Controller
public class TestEntity {
    private String testField = "test";

    private String test(Image image) {
        System.out.println(image);
        return null;
    }

}
