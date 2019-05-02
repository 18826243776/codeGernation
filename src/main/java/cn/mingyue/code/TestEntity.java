package cn.mingyue.code;

import java.awt.*;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 9:57
 */
@Service(value = "TestEnum")
@Controller
public class TestEntity {
    private String testField = "TestEnum";

    private String test(Image image) {
        System.out.println(image);
        return null;
    }

}
