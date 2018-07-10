package BO;

import java.util.List;

/**
 * Created by hanlinli on 2017/9/29.
 */
public class DistrictCityMapDO {
    private long globalId;
    private int categoryId;
    private String Type;
    private String Name;
    private String EName;
    private List<DistrictCityParentDO> parentList;

    public long getGlobalId() {
        return globalId;
    }

    public void setGlobalId(long globalId) {
        this.globalId = globalId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public List<DistrictCityParentDO> getParentList() {
        return parentList;
    }

    public void setParentList(List<DistrictCityParentDO> parentList) {
        this.parentList = parentList;
    }
}
