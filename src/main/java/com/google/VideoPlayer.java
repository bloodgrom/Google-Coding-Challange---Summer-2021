package com.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private boolean currentlyPlayingBool;
  private Video currentlyPlayingVideo;
  private String currentlyPlayingTitle;
  private String currentlyPlayingId;
  private boolean isPaused;
  private List<VideoPlaylist> playListLibrary;
  private ArrayList<Video> videoArrayGlobal;
  private HashMap<Integer, Boolean> flagHash;
  private HashMap<Integer, String> reasonHash;
  private HashMap<String, HashMap> videosIdToFlagAndReasonGlobal;


  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.currentlyPlayingBool = false;
    this.currentlyPlayingVideo = new Video("", "", new ArrayList<String>());
    this.currentlyPlayingTitle = "";
    this.currentlyPlayingId = "";
    this.isPaused = false;
    this.playListLibrary = new ArrayList<>();

    this.videoArrayGlobal = new ArrayList<>(videoLibrary.getVideos());
    this.flagHash = new HashMap<>();
    this.reasonHash = new HashMap<>();

    for(int sample = 0; sample < videoArrayGlobal.size(); sample++) {
      flagHash.put(sample, false);
      reasonHash.put(sample, null);
    }
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {

    HashMap<String, Integer> videosMap = new HashMap<>();

    String videoTitle = "";
    String videoId = "";
    List<String> videoTagsWithCommas = new ArrayList<>();
    String videoTags = "";
    ArrayList<Video> videoArray = new ArrayList<>(videoLibrary.getVideos());
    ArrayList<String> titleSort = new ArrayList<>();

    System.out.println("Here's a list of all available videos:");

    /* Sort videos by title and add them to a hashmap*/
    for(int i = 0; i < videoArray.size(); i++) {

      videoTitle = videoArray.get(i).getTitle();
      titleSort.add(videoTitle);
      videosMap.put(videoTitle, i);
    }

    Collections.sort(titleSort);

    /* Get video's id and tags(if any) by using the title as a key in a sorted order*/
    for(int z = 0; z < videoArray.size(); z++) {
      videoTitle = titleSort.get(z);
      videoId = videoArray.get(videosMap.get(titleSort.get(z))).getVideoId();
      videoTagsWithCommas = videoArray.get(videosMap.get(titleSort.get(z))).getTags();
      videoTags = "[";

      for(int y = 0; y < videoTagsWithCommas.size(); y++) {
        if(y != 0) {
          videoTags += " " + videoTagsWithCommas.get(y);
        }
        else {
          videoTags += videoTagsWithCommas.get(y);
        }
      }

      videoTags += "]";

      System.out.println(videoTitle + " (" + videoId + ") " + videoTags);
    }
  }

  public void playVideo(String videoId) {

    isPaused = false;

    ArrayList<Video> videoArray = new ArrayList<>(videoLibrary.getVideos());
    HashMap<String, String> videosIdToTitleMap = new HashMap<>();

    /* Mapping video's ids to titles*/
    for(int i = 0; i < videoArray.size(); i++) {
      videosIdToTitleMap.put(videoArray.get(i).getVideoId(), videoArray.get(i).getTitle());
    }

    if(videosIdToTitleMap.get(videoId) == null) {
      System.out.println("Cannot play video: Video does not exist");
    }
    else if(videoLibrary.getVideo(videoId).isFlagged() == true) {
      System.out.println("Cannot play video: Video is currently flagged " + videoLibrary.getVideo(videoId).getFlagReason());
    }
    else if(currentlyPlayingBool == false) {
      currentlyPlayingTitle = videosIdToTitleMap.get(videoId);
      currentlyPlayingId = videoId;
      System.out.println("Playing video: " + currentlyPlayingTitle);
      currentlyPlayingBool = true;
    }
    else if(currentlyPlayingBool == true) {
      System.out.println("Stopping video: " + currentlyPlayingTitle);
      currentlyPlayingTitle = videosIdToTitleMap.get(videoId);
      currentlyPlayingId = videoId;
      System.out.println("Playing video: " + currentlyPlayingTitle);
    }
    else {
      System.out.println("Something went wrong. Please try again later.");
    }
  }

  public void stopVideo() {

    isPaused = false;

    if(currentlyPlayingBool == false) {
      System.out.println("Cannot stop video: No video is currently playing");
    }
    else if(currentlyPlayingBool == true) {
      System.out.println("Stopping video: " + currentlyPlayingTitle);
      currentlyPlayingTitle = "";
      currentlyPlayingId = "";
      currentlyPlayingBool = false;
    }
    else {
      System.out.println("Something went wrong. Please try again later.");
    }
  }

  public void playRandomVideo() {

    isPaused = false;

    Random random = new Random();
    ArrayList<Video> videoArray = new ArrayList<>(videoLibrary.getVideos());
    int randomVideoIndex = random.nextInt(videoArray.size() - 1);
    
    if(5 > 6) {
      System.out.println("No videos available");
    }
    else if(currentlyPlayingBool == false) {
      currentlyPlayingTitle = videoArray.get(randomVideoIndex).getTitle();
      currentlyPlayingId = videoArray.get(randomVideoIndex).getVideoId();
      System.out.println("Playing video: " + currentlyPlayingTitle);
      currentlyPlayingBool = true;
    }
    else if(currentlyPlayingBool == true) {
      System.out.println("Stopping video: " + currentlyPlayingTitle);
      currentlyPlayingTitle = videoArray.get(randomVideoIndex).getTitle();
      currentlyPlayingId = videoArray.get(randomVideoIndex).getVideoId();
      System.out.println("Playing video: " + currentlyPlayingTitle);
    }
    else {
      System.out.println("Something went wrong. Please try again later.");
    }

  }

  public void pauseVideo() {

    if(currentlyPlayingBool == false) {
      System.out.println("Cannot pause video: No video is currently playing");
    }
    else if(currentlyPlayingBool == true && isPaused == false) {
      System.out.println("Pausing video: " + currentlyPlayingTitle);
      isPaused = true;
    }
    else if(currentlyPlayingBool == true && isPaused == true){
      System.out.println("Video already paused: " + currentlyPlayingTitle);
    }
    else {
      System.out.println("Something went wrong. Please try again later.");
    }
  }

  public void continueVideo() {

    if(currentlyPlayingBool == false) {
      System.out.println("Cannot continue video: No video is currently playing");
    }
    else if(currentlyPlayingBool == true && isPaused == false) {
      System.out.println("Cannot continue video: Video is not paused");
    }
    else if(currentlyPlayingBool == true && isPaused == true){
      System.out.println("Continuing video: " + currentlyPlayingTitle);
      isPaused = false;
    }
    else {
      System.out.println("Something went wrong. Please try again later.");
    }
  }

  public void showPlaying() {
    
    if(currentlyPlayingBool == false) {
      System.out.println("No video is currently playing");
    }
    else if(currentlyPlayingBool == true) {

      Video currentlyPlayingVideo = videoLibrary.getVideo(currentlyPlayingId);
      String videoTitle = "";
      String videoId = "";
      List<String> videoTagsWithCommas = new ArrayList<>();
      String videoTags = "";
      
      videoTitle = currentlyPlayingVideo.getTitle();
      videoId = currentlyPlayingVideo.getVideoId();
      videoTagsWithCommas = currentlyPlayingVideo.getTags();
      videoTags = "[";

      for(int y = 0; y < videoTagsWithCommas.size(); y++) {
        if(y != 0) {
          videoTags += " " + videoTagsWithCommas.get(y);
        }
        else {
          videoTags += videoTagsWithCommas.get(y);
        }
      }

      videoTags += "]";

      String currentlyPlayingString = "Currently playing: " + videoTitle + " (" + videoId + ") " + videoTags;

      if(isPaused == false){
        System.out.println(currentlyPlayingString);
      }
      else if(isPaused == true) {
        System.out.println(currentlyPlayingString + " - PAUSED");
      }
      else {
        System.out.println("Something went wrong. Please try again later.");
      }
    }
    else {
      System.out.println("Something went wrong. Please try again later.");
    }
  }

  public void createPlaylist(String playlistName) {

    boolean alreadyExists = false;
    
    for(int i = 0; i < playListLibrary.size(); i++ ) {
      if(playListLibrary.get(i).getName().equalsIgnoreCase(playlistName)) {
        alreadyExists = true;
        System.out.println("Cannot create playlist: A playlist with the same name already exists");
        break;
      }
    }

    if(alreadyExists == false) {
      playListLibrary.add(new VideoPlaylist(playlistName, new ArrayList<String>()));
      System.out.println("Successfully created new playlist: " + playlistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    
    ArrayList<Video> videoArray = new ArrayList<>(videoLibrary.getVideos());
    boolean doesPlaylistExists = false;
    boolean doesVideoExists = false;
    boolean isInPlaylist = false;
    int playListIndex = 0;
    int videoIndex = 0;
    
    if(playListLibrary.size() >= 1) {
      for(int i = 0; i < playListLibrary.size(); i++ ) {
        if(playListLibrary.get(i).getName().equalsIgnoreCase(playlistName)) {
          doesPlaylistExists = true;
          playListIndex = i;
          break;
        }
      }
      if(doesPlaylistExists == false) {
        System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      }
    }
    else {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    }

    if(doesPlaylistExists == true) {
      if(videoArray.size() >= 1) {
        for(int y = 0; y < videoArray.size(); y++) {
          if(videoArray.get(y).getVideoId().equals(videoId)) {
            doesVideoExists = true;
            videoIndex = y;
            break;
          }
        }
      }
      if(doesVideoExists == false){
        System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      }
    }

    if((doesPlaylistExists == true) && (doesVideoExists == true) && (videoLibrary.getVideo(videoId).isFlagged() == true)) {
      System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged " + videoLibrary.getVideo(videoId).getFlagReason());
    }

    if((doesPlaylistExists == true) && (doesVideoExists == true)) {
      for(int z = 0; z < playListLibrary.get(playListIndex).getIdList().size(); z++) {
        if(playListLibrary.get(playListIndex).getIdList().get(z).equals(videoId)) {
          System.out.println("Cannot add video to " + playlistName + ": Video already added");
          isInPlaylist = true;
          break;
        }
        else {
          isInPlaylist = false;
        }
      }
    }

    if((doesPlaylistExists == true) && (doesVideoExists == true) && (isInPlaylist == false)) {
      playListLibrary.get(playListIndex).addVideoToPlaylist(videoId);
      System.out.println("Added video to " + playlistName + ": " + videoArray.get(videoIndex).getTitle());
    }

    // if(playListLibrary.size() >= 1) {
    //   for(int k = 0; k < playListLibrary.size(); k++ ) {
    //     System.out.println("--------------------------------");
    //     System.out.println(playListLibrary.get(k).getName());
    //     System.out.println(playListLibrary.get(k).getIdList());
    //   }
    // }
  }

  public void showAllPlaylists() {

    String currentPlaylistName = "";
    ArrayList<String> nameSort = new ArrayList<>();
    HashMap<String, Integer> playlistMap = new HashMap<>();

    /* Sort videos by title and add them to a hashmap*/
    for(int i = 0; i < playListLibrary.size(); i++) {

      currentPlaylistName = playListLibrary.get(i).getName();
      nameSort.add(currentPlaylistName);
      playlistMap.put(currentPlaylistName, i);
    }

    Collections.sort(nameSort);

    if(nameSort.size() >= 1) {
      System.out.println("Showing all playlists:");
      for(int i = 0; i < nameSort.size(); i++ ) {
        System.out.println(nameSort.get(i));
      }
    }
    else {
      System.out.println("No playlists exist yet");
    }
  }

  public void showPlaylist(String playlistName) {

    boolean doesPlaylistExists = false;
    int playListIndex = 0;

    if(playListLibrary.size() >= 1) {
      for(int i = 0; i < playListLibrary.size(); i++ ) {
        if(playListLibrary.get(i).getName().equalsIgnoreCase(playlistName)) {
          doesPlaylistExists = true;
          playListIndex = i;
          break;
        }
      }
    }
    else {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exists");
    }

    if(doesPlaylistExists == true) {
      System.out.println("Showing playlist: " + playlistName);
      if(playListLibrary.get(playListIndex).getIdList().size() >= 1) {
        for(int y = 0; y < playListLibrary.get(playListIndex).getIdList().size(); y++ ) {
          Video currentVideo = videoLibrary.getVideo(playListLibrary.get(playListIndex).getIdList().get(y));

          String currentVideoTitle = currentVideo.getTitle();
          String currentVideoId = currentVideo.getVideoId();
          List<String> currentVideoTagsWithCommas = currentVideo.getTags();
          String currentVideoTags = "[";

          for(int k = 0; k < currentVideoTagsWithCommas.size(); k++) {
            if(k != 0) {
              currentVideoTags += " " + currentVideoTagsWithCommas.get(k);
            }
            else {
              currentVideoTags += currentVideoTagsWithCommas.get(k);
            }
          }

          currentVideoTags += "]";

          System.out.println(currentVideoTitle + " (" + currentVideoId + ") " + currentVideoTags);
          
        }
      }
      else {
        System.out.println("No videos here yet");
      }
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    
    ArrayList<Video> videoArray = new ArrayList<>(videoLibrary.getVideos());
    boolean doesPlaylistExists = false;
    boolean doesVideoExists = false;
    boolean isInPlaylist = false;
    int playListIndex = 0;
    int videoIndex = 0;

    if(playListLibrary.size() >= 1) {
      for(int i = 0; i < playListLibrary.size(); i++ ) {
        if(playListLibrary.get(i).getName().equalsIgnoreCase(playlistName)) {
          doesPlaylistExists = true;
          playListIndex = i;
          break;
        }
      }
      if(doesPlaylistExists == false) {
        System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
      }
    }
    else {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    }

    if(doesPlaylistExists == true) {
      if(videoArray.size() >= 1) {
        for(int y = 0; y < videoArray.size(); y++) {
          if(videoArray.get(y).getVideoId().equals(videoId)) {
            doesVideoExists = true;
            videoIndex = y;
            break;
          }
        }
      }
      if(doesVideoExists == false){
        System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      }
    }

    if((doesPlaylistExists == true) && (doesVideoExists == true)) {
      for(int z = 0; z < playListLibrary.get(playListIndex).getIdList().size(); z++) {
        if(playListLibrary.get(playListIndex).getIdList().get(z).equals(videoId)) {
          isInPlaylist = true;
          break;
        }
      }

      if(isInPlaylist == false) {
        System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
      }
    }

    if((doesPlaylistExists == true) && (doesVideoExists == true) && (isInPlaylist == true)) {
      playListLibrary.get(playListIndex).removeVideoFromPlaylist(videoId);
      System.out.println("Removed video from " + playlistName + ": " + videoArray.get(videoIndex).getTitle());
    }

  }

  public void clearPlaylist(String playlistName) {

    boolean doesPlaylistExists = false;
    int playListIndex = 0;

    if(playListLibrary.size() >= 1) {
      for(int i = 0; i < playListLibrary.size(); i++ ) {
        if(playListLibrary.get(i).getName().equalsIgnoreCase(playlistName)) {
          doesPlaylistExists = true;
          playListIndex = i;
          break;
        }
      }
    }
    else {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exists");
    }

    if(doesPlaylistExists == true) {
      playListLibrary.get(playListIndex).clearPlaylist();
      System.out.println("Successfully removed all videos from " + playlistName);
    }

  }

  public void deletePlaylist(String playlistName) {

    boolean doesPlaylistExists = false;
    int playListIndex = 0;

    if(playListLibrary.size() >= 1) {
      for(int i = 0; i < playListLibrary.size(); i++ ) {
        if(playListLibrary.get(i).getName().equalsIgnoreCase(playlistName)) {
          doesPlaylistExists = true;
          playListIndex = i;
          break;
        }
      }
    }
    else {
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exists");
    }

    if(doesPlaylistExists == true) {
      playListLibrary.remove(new VideoPlaylist(playlistName, new ArrayList<String>()));
      System.out.println("Deleted playlist: " + playlistName);
    }

  }

  public void searchVideos(String searchTerm) {
    
    HashMap<String, Integer> videosMap = new HashMap<>();

    String videoTitle = "";
    String videoId = "";
    List<String> videoTagsWithCommas = new ArrayList<>();
    String videoTags = "";
    ArrayList<Video> videoArray = new ArrayList<>(videoLibrary.getVideos());
    ArrayList<String> titleSort = new ArrayList<>();

    boolean isAMatch = false;

    var search_scanner = new Scanner(System.in);

    /* Sort videos by title and add them to a hashmap*/
    for(int i = 0; i < videoArray.size(); i++) {
      videoTitle = videoArray.get(i).getTitle();
      titleSort.add(videoTitle);
      videosMap.put(videoTitle, i);
    }

    Collections.sort(titleSort);

    HashMap<Integer, String> searchVideosMap = new HashMap<>();
    int counter = 0;

    for(int z = 0; z < videoArray.size(); z++) {
      videoTitle = titleSort.get(z);
      if(videoTitle.toLowerCase().contains(searchTerm.toLowerCase())) {
        isAMatch = true;
        counter++;
        videoId = videoArray.get(videosMap.get(titleSort.get(z))).getVideoId();
        searchVideosMap.put(counter, videoId);
        videoTagsWithCommas = videoArray.get(videosMap.get(titleSort.get(z))).getTags();
        videoTags = "[";

        for(int y = 0; y < videoTagsWithCommas.size(); y++) {
          if(y != 0) {
            videoTags += " " + videoTagsWithCommas.get(y);
          }
          else {
            videoTags += videoTagsWithCommas.get(y);
          }
        }
        videoTags += "]";
      }

      if(counter == 1 && isAMatch == true) {
        System.out.println("Here are the results for " + searchTerm + ":");
        System.out.println(counter + ") " + videoTitle + " (" + videoId + ") " + videoTags);
      }
      else if(counter > 1 && isAMatch == true) {
        System.out.println(counter + ") " + videoTitle + " (" + videoId + ") " + videoTags);
      }
      else if((z == videoArray.size() - 1) && (counter == 0)) {
        System.out.println("No search results for " + searchTerm);
      }
      else if((z == videoArray.size() - 1) && (counter > 0)) {
        System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
        System.out.println("If your answer is not a valid number, we will assume it's a no.");

        if(search_scanner.hasNextInt()) {
          int input = search_scanner.nextInt();
          if((input > 0 ) && (input <= counter)) {
            playVideo(searchVideosMap.get(input));
          }
          //search_scanner.close();
        }
      }

      isAMatch = false;
    }
  }

  public void searchVideosWithTag(String videoTag) {
    
    HashMap<String, Integer> videosMap = new HashMap<>();

    String videoTitle = "";
    String videoId = "";
    List<String> videoTagsWithCommas = new ArrayList<>();
    String videoTags = "";
    ArrayList<Video> videoArray = new ArrayList<>(videoLibrary.getVideos());
    ArrayList<String> titleSort = new ArrayList<>();

    boolean isAMatch = false;

    var search_scanner = new Scanner(System.in);

    /* Sort videos by title and add them to a hashmap*/
    for(int i = 0; i < videoArray.size(); i++) {
      videoTitle = videoArray.get(i).getTitle();
      titleSort.add(videoTitle);
      videosMap.put(videoTitle, i);
    }

    Collections.sort(titleSort);

    HashMap<Integer, String> searchVideosMap = new HashMap<>();
    int counter = 0;

    for(int z = 0; z < videoArray.size(); z++) {
      videoTagsWithCommas = videoArray.get(videosMap.get(titleSort.get(z))).getTags();
      for(int currentTag = 0; currentTag < videoTagsWithCommas.size(); currentTag++) {
        if(videoTagsWithCommas.get(currentTag).toLowerCase().contains(videoTag.toLowerCase()) && videoTag.toLowerCase().contains("#")) {
          isAMatch = true;
          counter++;
          videoTitle = titleSort.get(z);
          videoId = videoArray.get(videosMap.get(titleSort.get(z))).getVideoId();
          searchVideosMap.put(counter, videoId);
          videoTags = "[";

          for(int y = 0; y < videoTagsWithCommas.size(); y++) {
            if(y != 0) {
              videoTags += " " + videoTagsWithCommas.get(y);
            }
            else {
              videoTags += videoTagsWithCommas.get(y);
            }
          }
          videoTags += "]";
        }
      }

      if(counter == 1 && isAMatch == true) {
        System.out.println("Here are the results for " + videoTag + ":");
        System.out.println(counter + ") " + videoTitle + " (" + videoId + ") " + videoTags);
      }
      else if(counter > 1 && isAMatch == true) {
        System.out.println(counter + ") " + videoTitle + " (" + videoId + ") " + videoTags);
      }
      else if((z == videoArray.size() - 1) && (counter == 0)) {
        System.out.println("No search results for " + videoTag);
      }
      else if((z == videoArray.size() - 1) && (counter > 0)) {
        System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
        System.out.println("If your answer is not a valid number, we will assume it's a no.");

        if(search_scanner.hasNextInt()) {
          int input = search_scanner.nextInt();
          if((input > 0 ) && (input <= counter)) {
            playVideo(searchVideosMap.get(input));
          }
          //search_scanner.close();
        }
      }

      isAMatch = false;
    }

  }

  public void flagVideo(String videoId) {
    
    ArrayList<Video> videoArray = new ArrayList<>(videoLibrary.getVideos());
    HashMap<String, String> videosIdToTitleMap = new HashMap<>();

    /* Mapping video's ids to titles*/
    for(int i = 0; i < videoArray.size(); i++) {
      videosIdToTitleMap.put(videoArray.get(i).getVideoId(), videoArray.get(i).getTitle());
    }

    if(videosIdToTitleMap.get(videoId) == null) {
      System.out.println("Cannot play video: Video does not exist");
    }
    else if(videoLibrary.getVideo(videoId).isFlagged() == true) {
      System.out.println("Cannot flag video: Video is already flagged");
    }
    else {
      videoLibrary.getVideo(videoId).setFlaggedTrue();
      videoLibrary.getVideo(videoId).setFlagReason("Not supplied");
      System.out.println("Successfully flagged video: " + videosIdToTitleMap.get(videoId) + " " + videoLibrary.getVideo(videoId).getFlagReason());
    }
  }

  public void flagVideo(String videoId, String reason) {
    
    ArrayList<Video> videoArray = new ArrayList<>(videoLibrary.getVideos());
    HashMap<String, String> videosIdToTitleMap = new HashMap<>();

    /* Mapping video's ids to titles*/
    for(int i = 0; i < videoArray.size(); i++) {
      videosIdToTitleMap.put(videoArray.get(i).getVideoId(), videoArray.get(i).getTitle());
    }

    if(videosIdToTitleMap.get(videoId) == null) {
      System.out.println("Cannot play video: Video does not exist");
    }
    else if(videoLibrary.getVideo(videoId).isFlagged() == true) {
      System.out.println("Cannot flag video: Video is already flagged");
    }
    else {
      videoLibrary.getVideo(videoId).setFlaggedTrue();
      videoLibrary.getVideo(videoId).setFlagReason(reason);
      System.out.println("Successfully flagged video: " + videosIdToTitleMap.get(videoId) + " " + videoLibrary.getVideo(videoId).getFlagReason());
    }
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}