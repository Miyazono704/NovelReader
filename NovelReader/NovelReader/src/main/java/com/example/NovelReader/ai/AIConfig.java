package com.example.NovelReader.ai;

/**
 * AI API配置类
 * 
 * 使用说明：
 * 1. 本项目支持OpenAI兼容的API接口
 * 2. 你可以使用以下服务商的API：
 *    - OpenAI (https://api.openai.com)
 *    - 智谱AI (https://open.bigmodel.cn)
 *    - 通义千问 (https://dashscope.aliyuncs.com)
 *    - DeepSeek (https://api.deepseek.com)
 *    - 月之暗面Kimi (https://api.moonshot.cn)
 *    - 或其他兼容OpenAI格式的API
 * 
 * 3. 配置步骤：
 *    a. 在对应平台注册账号并获取API Key
 *    b. 修改下面的配置参数
 */
public class AIConfig {
    
    // ==================== API配置 ====================
    
    /**
     * API接口地址
     * 
     * 常用地址示例：
     * - OpenAI: https://api.openai.com/v1/chat/completions
     * - 智谱AI: https://open.bigmodel.cn/api/paas/v4/chat/completions
     * - 通义千问: https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions
     * - DeepSeek: https://api.deepseek.com/v1/chat/completions
     * - Kimi: https://api.moonshot.cn/v1/chat/completions
     */
    public static final String API_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";
    
    /**
     * API密钥
     * 请替换为你自己的API Key
     */
    public static final String API_KEY = "sk-4254ba822f9640bea89d80945f3aebee";
    
    /**
     * 模型名称
     * 
     * 常用模型示例：
     * - OpenAI: gpt-3.5-turbo, gpt-4, gpt-4-turbo
     * - 智谱AI: glm-4, glm-4-flash, glm-3-turbo
     * - 通义千问: qwen-turbo, qwen-plus, qwen-max
     * - DeepSeek: deepseek-chat
     * - Kimi: moonshot-v1-8k, moonshot-v1-32k
     */
    public static final String MODEL = "qwen-turbo";
    
    // ==================== 请求参数 ====================
    
    /**
     * 温度参数（0.0-2.0）
     * 较低的值会使输出更加确定和集中
     * 较高的值会使输出更加随机和多样
     */
    public static final double TEMPERATURE = 0.7;
    
    /**
     * 最大token数
     */
    public static final int MAX_TOKENS = 2000;
    
    /**
     * 连接超时时间（毫秒）
     */
    public static final int CONNECT_TIMEOUT = 30000;
    
    /**
     * 读取超时时间（毫秒）
     */
    public static final int READ_TIMEOUT = 60000;
}
