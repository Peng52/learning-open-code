package org.apache.rocketmq.test.peng;

import org.apache.commons.cli.*;

public class CliExample {
    public static void main(String[] args) {
        String[] string = new String[]{"-h","232"};
        // 1. 定义选项
        Options options = new Options();
        options.addOption("h", "help", false, "显示帮助信息");
        options.addOption("f", "file", true, "指定输入文件");
        
        // 2. 创建解析器
        CommandLineParser parser = new DefaultParser();
        
        try {
            // 3. 解析命令行参数
            CommandLine cmd = parser.parse(options, args);
            
            // 4. 处理解析结果
            if (cmd.hasOption("h")) {
                printHelp(options);
                return;
            }
            
            if (cmd.hasOption("f")) {
                System.out.println("输入文件: " + cmd.getOptionValue("f"));
            }
            
        } catch (ParseException e) {
            System.err.println("参数解析失败: " + e.getMessage());
            printHelp(options);
            System.exit(1);
        }
    }
    
    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("myapp", options, true);
    }
}