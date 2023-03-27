package com.lesson.community.util;

import lombok.Data;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname SensitiveFliter
 * @Description 利用前缀树实现敏感词过滤器
 * @Date 2023/3/24 15:33
 * @Created by hwj
 */
@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    // 替换符
    private static final String REPLACEMENT = "***";
    //根节点
    private final TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init() {
        System.out.println("初始化后构造");
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt")) {
            assert is != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String keyword;
                while ((keyword = reader.readLine()) != null) {
                    this.addKeyword(keyword);
                }
            }
        } catch (IOException e) {
            logger.error("加载敏感词文件失败！" + e.getMessage());
        }
        System.out.println("构造完成");
    }

    // 将一个敏感词添加到前缀树中
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); ++i) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null) {
                // 子节点不存在,初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            // 指向子节点,进入下一层循环
            tempNode = subNode;

            // 循环结束,给终止结点加上结束标志
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * @author hwj
     * @Description 敏感词替换, 将传入字符串的敏感词替换后返回
     * @date 2023/3/27 13:47
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        // 前缀树上的指针 1
        TrieNode pointNode = rootNode;
        //指针 2 3
        int begin = 0, position = 0;
        // 结果
        StringBuilder res = new StringBuilder();
        while (position < text.length()) {
            char c = text.charAt(position);
            // 跳过符号
            if(isSymbol(c)){
                // 指针 1 处于根节点, 将此符号计入结果, 让指针2向下走一步
                if(pointNode == rootNode){
                    res.append(c);
                    ++begin;
                }
                // 无论符号在开头或是在中间, 指针3都向下走一步
                ++position;
                continue;
            }
            // 检查下级节点
            pointNode = pointNode.getSubNode(c);
            if(pointNode==null){
                // 以begin开头的字符串不是敏感词
                res.append(text.charAt(begin));
                // 进入下一个位置
                position = ++begin;
                // 指针1 重新指向根节点
                pointNode = rootNode;
            }else if(pointNode.isKeywordEnd){
                // 发现了敏感词,将begin~position字符串替换
                res.append(REPLACEMENT);
                // 进入下一个位置
                position = ++begin;
                // 指针1 重新指向根节点
                pointNode = rootNode;
            }else{
                // 检查下一个字符
                ++position;
            }
        }
        res.append(text.substring(begin));
        return res.toString();
    }

    //判断是否是符号
    private boolean isSymbol(Character c) {
        // 0x2E80--0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    // 前缀树
    @Data
    private static class TrieNode {
        // 关键词结束标识
        private boolean isKeywordEnd = false;

        // 子节点(key 是下级字符, value 是下级节点)
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        // 添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        // 获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }

}
