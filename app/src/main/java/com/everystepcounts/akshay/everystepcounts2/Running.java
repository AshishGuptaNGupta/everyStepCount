package com.everystepcounts.akshay.everystepcounts2;



public class Running {

 String distance;
 String date;
 String time;

 public Running(){
  //required
 }

 public Running(String distance, String date, String time) {
  this.distance = distance;
  this.date = date;
  this.time = time;
 }

 public String getDistance() {
  return distance;
 }

 public void setDistance(String distance) {
  this.distance = distance;
 }

 public String getDate() {
  return date;
 }

 public void setDate(String date) {
  this.date = date;
 }

 public String getTime() {
  return time;
 }

 public void setTime(String time) {
  this.time = time;
 }




}
