package com.lesson.community.entity;

import javafx.util.Pair;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname PageEntity
 * @Description 封装分页相关的信息
 * @Date 2023/3/21 16:37
 * @Created by hwj
 */
@Data
public class PageObject {

    // 当前的页码
    private int currentPage = 1;
    // 页面显示数据上限
    private int limit = 10;
    // 数据总数,用于计算总页数
    private int totalRows;
    //查询路径,用于复用分页链接
    private String pagePath;

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100)
            this.limit = limit;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage >= 1)
            this.currentPage = currentPage;
    }

    /**
     * @author hwj
     * @Description 获取当前页起始行
     * @date 2023/3/21 16:57
     */
    public int getOffset() {
        return (currentPage - 1) * limit;
    }

    /**
     * @author hwj
     * @Description 获取总页数
     * @date 2023/3/21 16:58
     */
    public int getPages() {
        return (totalRows % limit == 0 ? totalRows / limit : totalRows / limit + 1);
    }

    /**
     * @author hwj
     * @Description 获取页码范围
     * @date 2023/3/21 16:59
     */
    public Pair<Integer, Integer> getPageRange() {
        int begin = currentPage - 2, end = currentPage + 2;
        if (begin < 1) {
            begin = 1;
            if (getPages() >= 5)
                end += 4;
        }
        if (end > getPages()) {
            if (getPages() >= 5)
                begin = getPages() - 4;
            end = getPages();
        }
        return new Pair<>(begin, end);
    }
}
