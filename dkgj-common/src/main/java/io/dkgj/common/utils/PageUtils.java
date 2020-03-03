/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.demo.com
 * <p>
 * 版权所有，侵权必究！
 */

package io.dkgj.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
public class PageUtils implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页数
     */
    private int currPage;

    private Integer clickNum = 0;

    private Integer regNum = 0;

    private Integer pvNum = 0;

    private Integer uvNum = 0;

    private Integer todayUvSum = 0;

    private Integer todayPvSum = 0;

    private Integer appOpenNum = 0;

    private Integer appDownNum = 0;

    private Long loanUvNum = 0L;

    private String typeBanner;

    private boolean isChannel = false;
    /**
     * 列表数据
     */
    private List<?> list;

    private Map<String,Object> params;

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 分页
     */
    public PageUtils(IPage<?> page) {
        this.list = page.getRecords();
        this.totalCount = (int) page.getTotal();
        this.pageSize = (int) page.getSize();
        this.currPage = (int) page.getCurrent();
        this.totalPage = (int) page.getPages();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public String getTypeBanner() {
        return typeBanner;
    }

    public void setTypeBanner(String typeBanner) {
        this.typeBanner = typeBanner;
    }

    public boolean isChannel() {
        return isChannel;
    }

    public void setChannel(boolean channel) {
        isChannel = channel;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Integer getRegNum() {
        return regNum;
    }

    public void setRegNum(Integer regNum) {
        this.regNum = regNum;
    }

    public Integer getPvNum() {
        return pvNum;
    }

    public void setPvNum(Integer pvNum) {
        this.pvNum = pvNum;
    }

    public Integer getUvNum() {
        return uvNum;
    }

    public void setUvNum(Integer uvNum) {
        this.uvNum = uvNum;
    }

    public Integer getAppOpenNum() {
        return appOpenNum;
    }

    public void setAppOpenNum(Integer appOpenNum) {
        this.appOpenNum = appOpenNum;
    }

    public Integer getAppDownNum() {
        return appDownNum;
    }

    public void setAppDownNum(Integer appDownNum) {
        this.appDownNum = appDownNum;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Long getLoanUvNum() {
        return loanUvNum;
    }

    public void setLoanUvNum(Long loanUvNum) {
        this.loanUvNum = loanUvNum;
    }

    public Integer getTodayUvSum() {
        return todayUvSum;
    }

    public void setTodayUvSum(Integer todayUvSum) {
        this.todayUvSum = todayUvSum;
    }

    public Integer getTodayPvSum() {
        return todayPvSum;
    }

    public void setTodayPvSum(Integer todayPvSum) {
        this.todayPvSum = todayPvSum;
    }
}
