package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {

  private final String name;
  private final List<String> idList;

  VideoPlaylist(String name, ArrayList<String> idList) {
    this.name = name;
    this.idList = new ArrayList<>(idList);
  }

  VideoPlaylist(String name) {
    this.name = name;
    this.idList = new ArrayList<>();
  }

  /** Returns the title of the playlist. */
  String getName() {
    return name;
  }

  /** Returns a list of the videos' id's in the playlist. */
  List<String> getIdList() {
    return idList;
  }

   /** Returns the title of the playlist. */
  void addVideoToPlaylist(String video_id) {
    this.idList.add(video_id);
  }

  void removeVideoFromPlaylist(String video_id) {
    this.idList.remove(video_id);
  }

  void clearPlaylist() {
    this.idList.clear();
  }

}
