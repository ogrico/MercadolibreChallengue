package com.geovany.challenge.dto.stats;

public class ResponseStats {

    private String msg;
    private StatsCoupon[] StatsCoupon;

    public ResponseStats() {
    }

    public ResponseStats(StatsCoupon[] statsCoupons, String msg) {
        this.StatsCoupon = statsCoupons;
        this.msg = msg;
    }

    public StatsCoupon[] getStatsCoupon() {
        return StatsCoupon;
    }

    public void setStatsCoupon(StatsCoupon[] statsCoupons) {
        this.StatsCoupon = statsCoupons;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
