package com.omnicoder.anichan.Models.Responses;

import java.util.List;

public class RankingResponse {
     List<Data> data;
     Paging paging;

     public List<Data> getData() {
          return data;
     }

     public Paging getPaging() {
          return paging;
     }
}



