package com.kenzan.fizzbuzzapp;

public abstract class Sorter {
	   abstract void initialize();
	   abstract void startPlay();
	   abstract void endPlay();

	   //template method
	   public final void play(){

	      //initialize the game
	      initialize();

	      //start game
	      startPlay();

	      //end game
	      endPlay();
	   }
}
