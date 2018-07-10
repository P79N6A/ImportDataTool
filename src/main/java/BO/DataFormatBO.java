package BO;

/**
 * Created by lilianga on 2018/6/8.
 */
public class DataFormatBO {
    long cityId;
    long districtId;
    long categoryId;
    String categoryCode;
    String categoryName;
    String brief;
    String introduction;
    String imageUrl;
    int modifiedRank;
    long poiId;
    int poiRank;
    int channelType;

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getModifiedRank() {
        return modifiedRank;
    }

    public void setModifiedRank(int modifiedRank) {
        this.modifiedRank = modifiedRank;
    }

    public long getPoiId() {
        return poiId;
    }

    public void setPoiId(long poiId) {
        this.poiId = poiId;
    }

    public int getPoiRank() {
        return poiRank;
    }

    public void setPoiRank(int poiRank) {
        this.poiRank = poiRank;
    }
}
