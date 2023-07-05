package com.pxy.miniweather.entity;

public class CityImage {
    private String oriTitle;// 图片名
    private String hoverUrl;//图片链接
    private String thumbnailUrl;//图片缩略图链接
    private int width;//图片宽度
    private int height;//图片高度
    public void setOriTitle(String oriTitle) {
        this.oriTitle = oriTitle;
    }

    public String getOriTitle() {
        return oriTitle;
    }

    public void setHoverUrl(String hoverUrl) {
        this.hoverUrl = hoverUrl;
    }

    public String getHoverUrl() {
        return hoverUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "CityImage{" +
                "oriTitle='" + oriTitle + '\'' +
                ", hoverUrl='" + hoverUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
