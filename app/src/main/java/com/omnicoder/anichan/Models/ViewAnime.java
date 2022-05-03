package com.omnicoder.anichan.Models;

import android.os.Trace;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kotlin.jvm.Transient;

public class ViewAnime {
    private String poster_path,overview,release_date,original_language,title,backdrop_path,name,first_air_date,original_name,status,media_type,homepage,listStatus;
    private Integer id,number_of_seasons,number_of_episodes,runtime,watchTime;
    Boolean in_production;
    private Float popularity,vote_average;
    private List<Integer> genre_ids,episode_run_time;
    private List<Season> seasons;
    private List<ProductionCompany> production_companies;
    private Map<String,Object> alternative_titles,videos,next_episode_to_air,credits,external_ids;
    private Season season;
    int seasonNo,intentSeason;

    private boolean single,isTV;



    public ViewAnime(String poster_path, String overview, String release_date, String original_language, String title, String backdrop_path, String name, String first_air_date, String original_name, String status, String media_type, String homepage, Integer id, Integer number_of_seasons, Integer number_of_episodes, Integer runtime, List<Integer> episode_run_time, Boolean in_production, Float popularity, Float vote_average, List<Integer> genre_ids, List<Season> seasons, List<ProductionCompany> production_companies, Map<String, Object> alternative_titles, Map<String, Object> videos, Map<String, Object> next_episode_to_air, Map<String, Object> credits, Map<String, Object> external_ids) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.name = name;
        this.first_air_date = first_air_date;
        this.original_name = original_name;
        this.status =status;
        this.media_type = media_type;
        this.homepage = homepage;
        this.id = id;
        this.number_of_seasons = number_of_seasons;
        this.number_of_episodes = number_of_episodes;
        this.runtime = runtime;
        this.episode_run_time = episode_run_time;
        this.in_production = in_production;
        this.popularity = popularity;
        this.vote_average = vote_average;
        this.genre_ids = genre_ids;
        this.seasons = seasons;
        this.production_companies = production_companies;
        this.alternative_titles=alternative_titles;
        this.videos=videos;
        this.next_episode_to_air=next_episode_to_air;
        this.credits = credits;
        this.external_ids = external_ids;
    }


    public void setCredits(Map<String, Object> credits) {
        this.credits = credits;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setSeasonNo(int SeasonNo){
        this.seasonNo=SeasonNo;
    }

    public Season getSeason(){
        return seasons.get(seasonNo);
    }


    public void setSeason(Season season){
        this.season=season;
    }

    public String getSeasonPoster_path(int seasonNo) {
        return seasons.get(seasonNo).getPoster_path();
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        if(title==null){
            return name;
        }
        return title;
    }


    public String getBackdrop_path() {
        return backdrop_path;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_air_date() {
        if(media_type.equals("movie")){
            watchTime=runtime;
            return release_date;
        }
        if(first_air_date==null){
            return "?";
        }
        int runtime=episode_run_time.get(0)!=null ? episode_run_time.get(0):0;
        watchTime=runtime*number_of_episodes;
        return first_air_date;
    }

    public String getTotalRuntime(){
        if(watchTime>1440){
            return watchTime/24/60 + "d " + watchTime/60%24 + "h " + watchTime%60 + "m";
        }
        return watchTime/60%24 + "h " + watchTime%60+"m ";
    }

    public String getFirst_air_Year() {
        if(media_type.equals("movie")){
            watchTime=runtime;
            return release_date.substring(0,4);
        }

        if(first_air_date==null){
            return "";
        }
        return first_air_date.substring(0,4);
    }


    public String getStatus() {
        Trace.beginSection("GetStatus");
        String statuss=convertStatus(status);
        Trace.endSection();
        return statuss;
    }
    public boolean getSingle(){
        return single;
    }


    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_typeAndSingle(String media_type,boolean single,int seasonNos,boolean main) {
        this.media_type = media_type;
        if(media_type.equals("tv")){
            isTV=true;
            this.single= number_of_seasons == 1 || single;
            boolean haveSpecials=seasons.get(0).getName().equals("Specials");
            boolean s= seasonNos==number_of_seasons;
            int seasonNos2= s ? seasonNos-1 : seasonNos;
            boolean haveSpecial2=seasons.get(seasonNos2).getName().equals("Specials");
            this.intentSeason= haveSpecial2 && main ? 1 : seasonNos;
            this.seasonNo= (seasonNos2==0 && number_of_seasons==1) ? seasonNos2: (haveSpecial2 && main ? 1 : (haveSpecials || seasonNos==0  ? seasonNos : seasonNos-1));
        }
        else {
            isTV=false;
            this.single=false;
        }
    }



    public Integer getId() {
        return id;
    }

    public int getSeasonNo(){
        return seasonNo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVote_average() {
        if(vote_average==0.0){
            return "? /10";
        }
        return vote_average+"/10";
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public boolean getS(){
        return isTV && !(seasons.get(0).getSeason_number() == 1 && number_of_seasons == 1);
//        return !isMovie && (seasons.get(0).getSeason_number() == 1 && number_of_seasons == 1);
    }

    public String getNextEpisode(){
        return (String)next_episode_to_air.get("air_date");
    }



    public List<Map<String,Object>> getCast(){
        return (List<Map<String, Object>>) credits.get("cast");
    }

    public List<Crew> getCrew(){
        List<Map<String,Object>> crew=  (List<Map<String, Object>>) credits.get("crew");
        if(crew!=null) {
            List<Crew> removedCrewMembers = new ArrayList<>();
            List<Crew> crewList = new ArrayList<>();
            int size = crew.size();
            for (int i = 0; i < size; i++) {
                crewList.add(new Crew(crew.get(i)));
            }
            for (int i = 0; i < size; i++) {
                Crew crewMember = crewList.get(i);
                if (crewMember.getProfile_path() == null) {
                    removedCrewMembers.add(crewMember);
                }
                if (crewMember.getJob().equals("Comic Book")) {
                    crewMember.setJob("Creator");
                    crewList.add(0, crewMember);
                }
            }
            crewList.removeAll(removedCrewMembers);
            crewList.addAll(removedCrewMembers);
            return crewList;
        }
        return null;
    }

    public List<ProductionCompany> getAllProductionCompanies() {
        if(production_companies !=null) {
            List<ProductionCompany> removedProductionCompany = new ArrayList<>();
            int size =production_companies.size();
            for (int i = 0; i < size; i++) {
                ProductionCompany productionCompany = production_companies.get(i);
                if (productionCompany.getLogo_path() == null) {
                    removedProductionCompany.add(productionCompany);
                }
            }
            production_companies.removeAll(removedProductionCompany);
            production_companies.addAll(removedProductionCompany);
            return production_companies;
        }
        return null;
    }

    public String getFirstProductionCompany(){
        String name=production_companies.get(0).getName();
        if(name.length()>=16){
            String[] words= name.split(" ");
            return words[0]+" "+words[1];
        }
        return name;
    }


    public String getNumber_of_seasons() {
        return String.format(Locale.US,"%02d",number_of_seasons);
    }

    public int getNumber_of_seasons2() {
        return number_of_seasons;
    }

    public int getNoOfSeasons(){
        return number_of_seasons;
    }

    public String getNumber_of_episodes() {
        if(number_of_episodes!=null) {
            return number_of_episodes.toString();
        }
        return "--";
    }

    public int getNumber_of_episodesInt() {
        if(number_of_episodes!=null) {
            return number_of_episodes;
        }
        return 0;
    }


    public String getRuntime(){
        if(watchTime==0){
            return "? min";
        }
        return getTotalRuntime();
    }

    public String getSeasonRuntime(Season currentSeason){
        int runtime=episode_run_time.get(0) != null ? episode_run_time.get(0) : 0;
        watchTime=currentSeason.getEpisodeCount()*runtime;
        if(watchTime==0){
            return "? min";
        }
        return getTotalRuntime();
    }

    public String getEpisode_run_time() {
        try {
            return episode_run_time.get(0).toString();
        }catch (Exception e){
            return "--";
        }
    }



    public String convertStatus(String status){
        switch (status){
            case "Released":
                return "Released";
            case "Returning Series":
                return checkLastEpisode();
            case "Ended":
                return "Finished";
            case "In Production":
                return "Not Yet Aired";
            default:
                return"?";
        }
    }

    public String getListStatus(){
        return listStatus;
    }

    public String checkLastEpisode(){
        if(next_episode_to_air!=null) {
            String nextEpisodeAirDate=(String) next_episode_to_air.get("air_date");
            String airDate= single ?  seasons.get(intentSeason).getAir_date(): nextEpisodeAirDate;
            if(airDate==null || airDate.length()<10){
                if(single) {
                    if(season.getSeason_number()<number_of_seasons){
                        listStatus="Finished";
                    }else {
                        listStatus="Not Aired Yet";
                    }
                    return listStatus;
                }
                else return "Next Season In Production";
            }else{
                Calendar calendar=Calendar.getInstance();
                int currentMonth = calendar.get(Calendar.MONTH) + 1;
                int currentYear = calendar.get(Calendar.YEAR);
                int airingDateMonth =Integer.parseInt(airDate.substring(5, 7));
                int airingDateYear =Integer.parseInt(airDate.substring(0, 4));
                boolean b= (airingDateMonth == 12 && currentMonth == 1) || (airingDateMonth < currentMonth);
                boolean main=nextEpisodeAirDate!=null && intentSeason==number_of_seasons || number_of_seasons==1;
                if(airingDateMonth == currentMonth && currentYear==airingDateYear){
                    return "Airing";
                }else if(main){
                    return "Airing";
                }
                else if( b && airingDateYear<currentYear){
                    return "Finished";
                }
                else {
                    listStatus="Not Aired Yet";
                    return single ? "Not Aired Yet" : "Next Season In Production";
                }
            }
        }
        else {
            listStatus="Finished";
            return single ? "Finished" : "Next Season In Production";
        }
    }


    public String getTitles() {
        try{
            StringBuilder title= new StringBuilder();
            List<Map<String,String>> array= (List<Map<String, String>>) alternative_titles.get("results");
            for(int i=0;i<array.size();i++){
                Map<String,String> object= array.get(i);
                String type= object.get("iso_3166_1");
                if(type.equals("JP") || type.equals("US")){
                    title.append(object.get("title").toUpperCase());
                    title.append("\n");
                }
            }
            title.append(original_name);
            return title.toString();
        }
        catch (Exception e){
            return "none";
        }
    }

    public Map<String,Object> getVideos(){
        return videos;
    }

    public Map<String,Object> getLinks(){
        return external_ids;
    }

    public String getHomepage(){
        return homepage;
    }



}

