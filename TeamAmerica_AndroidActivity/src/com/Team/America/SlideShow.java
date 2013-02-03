package com.Team.America;

import android.R.string;

public class SlideShow {

	// ** Constructor **

	public SlideShow(int oSlideCount, string oName){
		m_iSlideCount = oSlideCount;
		m_oSlides = new int[m_iSlideCount];
		m_sSlideShowName = oName;
	}

	// ** Methods **

	public void saveSlideShow(int[] oSlides) {
		// save the slideshow to a file in storage
	}

	public string getSlideShowName(){
		return m_sSlideShowName;
	}

	public int getSlideCount(){
		return m_iSlideCount;
	}

	void startSlideShow(){

	}

	void endSlideShow(){

	}

	void advanceSlide(){
		
	}

	void goBackSlide(){

	}


	// ** Fields **

	int[] m_oSlides;
	int m_iSlideCount;
	string m_sSlideShowName;
	int m_iCurrentSlide;
}
