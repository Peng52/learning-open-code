package org.apache.rocketmq.test.peng;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.junit.Test;

/**
 * @author : pengcheng
 * @since : 2025-04-02
 */
public class _04_02_Test {

    /**
     *
     */
    @Test
    public void test() {
        String[] args = new String[]{"-h", "2321"};
        // 1. 定义命令行选项
        Options options = new Options();
        options.addOption("h", "help", true, "显示帮助信息");
        /*options.addOption("f", "file", true, "指定输入文件");
        options.addOption("v", "verbose", false, "详细输出模式");
*/
        // 2. 创建解析器
        CommandLineParser parser = new DefaultParser();

        // 3. 解析命令行
        CommandLine cmd = ServerUtil.parseCmdLine("MyApp", args, options, parser);

        // 4. 使用解析结果
        if (cmd.hasOption("f")) {
            System.out.println("输入文件: " + cmd.getOptionValue("f"));
        }

        if (cmd.hasOption("v")) {
            System.out.println("启用详细模式");
        }

        System.out.println("程序继续执行...");

    }
}
