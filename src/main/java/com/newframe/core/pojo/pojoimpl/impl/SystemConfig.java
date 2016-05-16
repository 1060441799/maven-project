package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.ManageableEntity;
import com.newframe.core.pojo.basepojo.ManageableEntityIfc;
import com.newframe.core.pojo.pojoimpl.SystemConfigIfc;

import javax.persistence.*;

@Entity
@Table(name = "core_system_config")
public class SystemConfig extends ManageableEntity implements SystemConfigIfc,ManageableEntityIfc {

    private String userId;
    private String code;
    private String name;
    private String contents;
    private String note;

    @Column(name = "userid")
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "code", length = 100)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "content", length = 300)
    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Column(name = "note", length = 300)
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
