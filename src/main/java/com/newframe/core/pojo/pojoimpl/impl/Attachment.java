package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.SortableAndManageableEntity;
import com.newframe.core.pojo.basepojo.SortableEntityIfc;
import com.newframe.core.pojo.pojoimpl.AttachmentIfc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "core_attachment")
@Inheritance(strategy = InheritanceType.JOINED)
public class Attachment extends SortableAndManageableEntity implements AttachmentIfc, SortableEntityIfc {
    private String userId;// 创建人
    private String businessKey;// 业务类主键
    private String subclassname;// 子类名称全路径
    private String attachmenttitle;// 附件名称
    private byte[] attachmentcontent;// 附件内容
    private String realpath;// 附件物理路径
    private String note;
    private String swfpath;// swf格式路径
    private String extend;// 扩展名

    @Column(name = "extend", length = 32)
    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Column(name = "userid")
    @NotNull
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "businesskey", length = 32)
    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    @Column(name = "attachmenttitle", length = 100)
    public String getAttachmenttitle() {
        return this.attachmenttitle;
    }

    public void setAttachmenttitle(String attachmenttitle) {
        this.attachmenttitle = attachmenttitle;
    }

    @Column(name = "attachmentcontent", length = 3000)
    @Lob
    public byte[] getAttachmentcontent() {
        return this.attachmentcontent;
    }

    public void setAttachmentcontent(byte[] attachmentcontent) {
        this.attachmentcontent = attachmentcontent;
    }

    @Column(name = "realpath", length = 100)
    public String getRealpath() {
        return this.realpath;
    }

    public void setRealpath(String realpath) {
        this.realpath = realpath;
    }

    @Column(name = "note", length = 300)
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "swfpath", length = 300)
    public String getSwfpath() {
        return this.swfpath;
    }

    public void setSwfpath(String swfpath) {
        this.swfpath = swfpath;
    }

    @Column(name = "subclassname", length = 300)
    public String getSubclassname() {
        return subclassname;
    }

    public void setSubclassname(String subclassname) {
        this.subclassname = subclassname;
    }
}
