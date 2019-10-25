package com.haoxt.agent.entity;

/**
 * Created by liuxx on 2019/10/22
 * 首页九宫格菜单实体类
 */

public class HomeMenu {

    String menuName;
    String menuAlias;
    String menuStatus;
    String order;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuAlias() {
        return menuAlias;
    }

    public void setMenuAlias(String menuAlias) {
        this.menuAlias = menuAlias;
    }

    public String getMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(String menuStatus) {
        this.menuStatus = menuStatus;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
