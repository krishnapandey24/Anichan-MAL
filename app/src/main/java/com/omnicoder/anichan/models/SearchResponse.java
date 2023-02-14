package com.omnicoder.anichan.models;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {
    private int page,total_pages,total_results;

    @Json(name = "results")
    private List<Animes> results;

    public SearchResponse(int page, int total_pages, int total_results, ArrayList<Animes> results) {
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<Animes> getResults() {
        return results;
    }

    public void setResults(ArrayList<Animes> results) {
        this.results = results;
    }


}
