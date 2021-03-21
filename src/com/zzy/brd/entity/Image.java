/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * 图片
 * @author lll 2014年12月19日
 */
//@Entity
//@Table(name = "images")
//@NamedQueries({
//    @NamedQuery(name = "images", query = "select i from Image i order by i.id")
//})
//@JsonIgnoreProperties({"id","thumbnailFilename","newFilename","contentType","dateCreated",
//	"lastUpdated"})
public class Image extends IdEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private String name;
    private String thumbnailFilename;
    /** 保存在服务器的图片的文件名 */
    private String newFilename;
    private String contentType;
    @Column(name = "size_")
    private Long size;
    private Long thumbnailSize;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @Transient
    private String url;
    @Transient
    private String newurl;
    @Transient
    private String thumbnailUrl;
    @Transient
    private String deleteUrl;
    @Transient
    private String deleteType;
    /** 错误描述 */
    @Transient
    private String error;
    
    public Image() {}
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the thumbnailFilename
     */
    public String getThumbnailFilename() {
        return thumbnailFilename;
    }

    /**
     * @param thumbnailFilename the thumbnailFilename to set
     */
    public void setThumbnailFilename(String thumbnailFilename) {
        this.thumbnailFilename = thumbnailFilename;
    }

    /**
     * @return the newFilename
     */
    public String getNewFilename() {
        return newFilename;
    }

    /**
     * @param newFilename the newFilename to set
     */
    public void setNewFilename(String newFilename) {
        this.newFilename = newFilename;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the size
     */
    public Long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * @return the thumbnailSize
     */
    public Long getThumbnailSize() {
        return thumbnailSize;
    }

    /**
     * @param thumbnailSize the thumbnailSize to set
     */
    public void setThumbnailSize(Long thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the lastUpdated
     */
    public Date getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the thumbnailUrl
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * @param thumbnailUrl the thumbnailUrl to set
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * @return the deleteUrl
     */
    public String getDeleteUrl() {
        return deleteUrl;
    }

    /**
     * @param deleteUrl the deleteUrl to set
     */
    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    /**
     * @return the deleteType
     */
    public String getDeleteType() {
        return deleteType;
    }

    /**
     * @param deleteType the deleteType to set
     */
    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }
    /**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	public String getNewurl() {
		return newurl;
	}

	public void setNewurl(String newurl) {
		this.newurl = newurl;
	}

	@Override
    public String toString() {
        return "Image{" + "name=" + name + ", thumbnailFilename=" + thumbnailFilename + ", newFilename=" + newFilename + ", contentType=" + contentType + ", url=" + url + ", thumbnailUrl=" + thumbnailUrl + ", deleteUrl=" + deleteUrl + ", deleteType=" + deleteType + '}';
    }

}
