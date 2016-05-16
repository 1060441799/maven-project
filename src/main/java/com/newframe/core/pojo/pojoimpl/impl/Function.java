package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.SortableAndManageableEntity;
import com.newframe.core.pojo.basepojo.SortableEntityIfc;
import com.newframe.core.pojo.pojoimpl.FunctionIfc;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "core_function")
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","operations","roles","functions"})
public class Function extends SortableAndManageableEntity implements FunctionIfc, SortableEntityIfc {
    private Function parentFunction;// 父菜单
    private String functionName;// 菜单名称
    private Short functionLevel;// 菜单等级
    private String functionUrl;// 菜单地址
    private Short functionIframe;// 菜单地址打开方式
    private String functionOrder;// 菜单排序
    private String iconId;// 菜单图标
    private String iconDeskId;// 云桌面菜单图标
    private List<Function> functions = new ArrayList<Function>();

    private String functionAPPUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentfunctionid")
    public Function getParentFunction() {
        return this.parentFunction;
    }

    public void setParentFunction(Function parentFunction) {
        this.parentFunction = parentFunction;
    }

    @Column(name = "desk_iconid")
    public String getIconDeskId() {
        return iconDeskId;
    }

    public void setIconDeskId(String iconDeskId) {
        this.iconDeskId = iconDeskId;
    }

    @Column(name = "iconid")
    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    @Column(name = "functionname", nullable = false, length = 50)
    public String getFunctionName() {
        return this.functionName;
    }

    @Column(name = "functionAPPUrl", nullable = true, length = 50)
    public String getFunctionAPPUrl() {
        return functionAPPUrl;
    }

    public void setFunctionAPPUrl(String functionAPPUrl) {
        this.functionAPPUrl = functionAPPUrl;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Column(name = "functionlevel")
    public Short getFunctionLevel() {
        return this.functionLevel;
    }

    public void setFunctionLevel(Short functionLevel) {
        this.functionLevel = functionLevel;
    }

    @Column(name = "functionurl", length = 100)
    public String getFunctionUrl() {
        return this.functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    @Column(name = "functionorder")
    public String getFunctionOrder() {
        return functionOrder;
    }

    public void setFunctionOrder(String functionOrder) {
        this.functionOrder = functionOrder;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentFunction")
    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    @Column(name = "functioniframe")
    public Short getFunctionIframe() {
        return functionIframe;
    }

    public void setFunctionIframe(Short functionIframe) {
        this.functionIframe = functionIframe;
    }
}
