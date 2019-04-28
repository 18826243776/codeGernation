package cn.mingyue.code;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 17:44
 */
public class TransferFactory {
    public Transfer<String> getTransfer() {
        return new TransferForString();
    }
}
