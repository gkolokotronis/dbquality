package com.dbquality;

/**
 * Main class of the program, It just creates the DBQualityHandler object and
 * calls the execute method
 *
 * @author George Kolokotronis
 * 
 */
public class DBQuality {

	public static void main(String[] args) {
		DBQualityExecutionHandler dbQuality = new DBQualityExecutionHandler();
		dbQuality.execute();

	}

}
