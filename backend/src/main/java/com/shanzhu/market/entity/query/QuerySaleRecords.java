package com.shanzhu.market.entity.query;

public class QuerySaleRecords extends BaseQuery {
    private String cn;
    private String startSellTime;
    private String endSellTime;
    private String type;
    private String sellway;

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getStartSellTime() {
        return startSellTime;
    }

    public void setStartSellTime(String startSellTime) {
        this.startSellTime = startSellTime;
    }

    public String getEndSellTime() {
        return endSellTime;
    }

    public void setEndSellTime(String endSellTime) {
        this.endSellTime = endSellTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSellway() {
        return sellway;
    }

    public void setSellway(String sellway) {
        this.sellway = sellway;
    }
}
