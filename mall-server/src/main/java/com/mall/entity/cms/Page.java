package com.mall.entity.cms;

public class Page {

    private int currentPage = 1;    //当前页数
    private int totalPages;        //总页数
    private int total;            //记录总行数
    private int pageSize = 10;    //每页记录行数
    private int nextPage;        //下一页
    private int prefPage;        //前一页
    
    public Page(){
    }
    
    public Page(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getTotalPages() {
        totalPages = total%pageSize == 0?total/pageSize:total/pageSize+1;
        return totalPages;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getNextPage() {
        if(currentPage<totalPages){
            nextPage = currentPage+1;
        }else{
            nextPage = currentPage;
        }
        return nextPage;
    }
    public int getPrefPage() {
        if(currentPage>1){
            prefPage = currentPage-1;
        }else{
            prefPage = currentPage;
        }
        return prefPage;
    }    
    
}