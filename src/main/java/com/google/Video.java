package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private boolean flagged;
  private String flagReason;

  Video(String title, String videoId, List<String> tags, boolean flagged, String flagReason) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
    this.flagged = false;
    this.flagReason = null;
  }

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
    this.flagged = false;
    this.flagReason = null;
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  /** Returns if the video is flagged. */
  Boolean isFlagged() {
    return flagged;
  }

  /** Returns reason for flagging the video */
  String getFlagReason() {
    return flagReason;
  }

  /** Sets flagged to true */
  void setFlaggedTrue() {
    this.flagged = true;
  }

  /** Sets flagged to false */
  void setFlaggedFalse() {
    this.flagged = false;
  }

  /** Sets reason for flag */
  void setFlagReason(String reason) {
    this.flagReason = "(reason: " + reason + ")";
  }
}
