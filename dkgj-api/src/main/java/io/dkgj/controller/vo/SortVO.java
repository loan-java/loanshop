package io.dkgj.controller.vo;

public class SortVO {
    private String key;
    private String name;
    private Integer sortId;

    public SortVO() {
    }

    public SortVO(String key, String name, Integer sortId) {
        this.key = key;
        this.name = name;
        this.sortId = sortId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
}
