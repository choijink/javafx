package com.itgroup.carproject.utility;

public class Paging {
    private int totalCount = 0;
    private int totalPage = 0;

    private int pageNumber = 0 ;
    private int pageSize = 0 ;
    private int beginRow = 0 ;
    private int endRow = 0;

    private int pageCount = 10 ;
    private int beginPage = 0 ;
    private int endPage = 0 ;

    private String url = "" ;
    private String pagingHtml = "" ;
    private String pagingStatus = "" ;

    private String mode = "" ;
    private String keyword = "" ;

    private String paginationSize = " pagination-sm" ;

    public Paging(String _pageNumber, String _pageSize, int totalCount, String url, String mode, String keyword){
        if (_pageNumber==null || _pageNumber.equals("null") || _pageNumber.equals("")){
            _pageNumber = "1" ;
        }
        this.pageNumber = Integer.parseInt(_pageNumber);

        if (_pageSize ==null || _pageSize.equals("null") || _pageSize.equals("")){
            _pageSize = "10";
        }
        this.pageSize = Integer.parseInt(_pageSize);
        this.totalCount = totalCount ;
        this.url = url ;
        this.mode = mode == null ? "" : mode;
        this.keyword = keyword ;

        this.totalPage = (int)Math.ceil((double)totalCount / pageSize);

        this.beginRow = (pageNumber -1) * pageSize + 1 ;
        this.endRow = pageNumber * pageSize ;

        if (this.totalCount < this.endRow){
            this.endRow = this.totalCount ;
        }

        this.beginPage = (pageNumber-1) / pageSize * pageSize + 1 ;
        this.endPage = beginPage + pageCount -1 ;

        if (this.totalPage < this.endPage){
            this.endPage = this.totalPage ;
        }
        this.pagingStatus = "총 " + totalCount + "건[" + pageNumber + "/" + totalPage + "]" ;
    }

    public String getPagingStatus() {
        return pagingStatus;
    }

    public int getBeginRow() {
        return beginRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public String getMode() {
        return mode;
    }

    public void displayInformation() {
        System.out.println("총 레코드 건수 : " + totalCount + "\n");
        System.out.println("전체 페이지 수 : " + totalPage + "\n");
        System.out.println("보여줄 페이지 넘버 : " + pageNumber + "\n");
        System.out.println("한 페이지에 보여줄 건수 : " + pageSize + "\n");
        System.out.println("현재 페이지의 시작 행 : " + beginRow + "\n");
        System.out.println("현재 페이지의 끝 행 : " + endRow + "\n");
        System.out.println("보여줄 페이지 링크 수 : " + pageCount + "\n");
        System.out.println("페이징 처리 시작 페이지 번호 : " + beginPage + "\n");
        System.out.println("페이징 처리 끝 페이지 번호 : " + endPage + "\n");
        System.out.println("요청 URL : " + url + "\n");
        //System.out.println("하단의 숫자 페이지 링크 : " + pagingHtml + "\n");
        System.out.println("상단 우측의 현재 페이지 위치 표시 : " + pagingStatus + "\n");
        System.out.println("검색 모드 : " + mode + "\n");
        System.out.println("검색 키워드 : " + keyword + "\n");
    }

}
