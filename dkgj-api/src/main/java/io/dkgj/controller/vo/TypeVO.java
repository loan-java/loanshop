package io.dkgj.controller.vo;

public class TypeVO {

    private Integer id;
    private String name;
    private Integer position;

    public TypeVO() {
    }

    public TypeVO(Integer id, String name, Integer position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
