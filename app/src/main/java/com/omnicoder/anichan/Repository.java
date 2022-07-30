package com.omnicoder.anichan;


import com.omnicoder.anichan.Models.UpdateAnimeResponse;
import com.omnicoder.anichan.Network.MalApi;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    MalApi rxAPI;
    public static final String accessToken="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjBjMmViZGM4NGU4OGI3MTRjY2MwMTg4NmIyZWU2NDE4NGQ4ODk0ZWVmZGVjZjg2NWM2OTAwM2IxYjRiOTUzNjY2OWE5OGY2MzI5Mzg0YmRmIn0.eyJhdWQiOiI0OGI0ODE2NzI3ZGI2ODcxYzRmN2JkNjhjZThhODIzMyIsImp0aSI6IjBjMmViZGM4NGU4OGI3MTRjY2MwMTg4NmIyZWU2NDE4NGQ4ODk0ZWVmZGVjZjg2NWM2OTAwM2IxYjRiOTUzNjY2OWE5OGY2MzI5Mzg0YmRmIiwiaWF0IjoxNjU5MTcwOTU3LCJuYmYiOjE2NTkxNzA5NTcsImV4cCI6MTY2MTg0OTM1Nywic3ViIjoiMTQ5ODA5MDEiLCJzY29wZXMiOltdfQ.GG_NElrJyaSuAF1NN2-tR5jBZxCeSDFTGINe3dYlNMWMdeiFC3cyhcqTlQns-j0nfRDlFGIV8ATB3xz01AZMxZrUkwW8Y96vUHQC4-hL7RfT-sa1oe6bZ19o-O1ghVzvBN5ASczVesZ_SvPxEagOeQiNLXHZMjQTb7NhTzpWoevl9oLVWhzqjDeUAt1_PQ6RYxuFmgyTk7K6Z5k0op296e_gfGpcFqoiEJpZeB-iGg4D0M1DQIdjrU-vy9YW_aGS0NQTGo6gtiudDjv_fb1v8GnVi4YrAusMdD6Ao8o0dHxcn9K1DhuaNS-GPSc8IJfMCKSbpjWqssgPunsO-5EUQA";

    @Inject
    public Repository(MalApi rxAPI){
        this.rxAPI=rxAPI;
    }


    public Observable<UpdateAnimeResponse> updateAnime(Integer id, String status, boolean isRewatching, Integer score, Integer numOfWatchedEpisodes){
        return rxAPI.updateAnime(accessToken,
                id,
                status,
                isRewatching,
                score,
                numOfWatchedEpisodes,
                null,
                null,
                null,
                null,
                null
        );
    }


}
