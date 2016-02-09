package com.dbquality;

/**
 * @author George Kolokotronis
 * 
 *         Main class of the program, It just creates the DBQualityHandler
 *         object and calls the execute method
 *
 */
public class DBQuality {

	public static void main(String[] args) {
		DBQualityExecutionHandler dbQuality = new DBQualityExecutionHandler();
		dbQuality.execute();

	}

}
