package com.lanxinbase.system.service;

public class TestServerImpl {

    private String p1;

    public TestServerImpl() {
        System.out.println("init TestServerImpl");
    }

    public void test(){
        System.out.println("TestServerImpl:"+p1);
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }
}
