package my.game.managers;

import javax.management.timer.Timer;


public class TextManager {
	
	public String text = "";
	public char[] textChar;
	private String message;
	private int index = 0;
	
	
	Timer timer = new Timer();
	
	public TextManager(){
		index = 0;
		
	}
	
	
	public void type(String arg){
		message = arg;
		
			typeText(index);
		
	}
	
	public void typeText(int i){
		textChar = message.toCharArray();
		if(i < textChar.length)
			text += textChar[i];
		System.out.println(text);
		index++;
			
	}

	public String getText() {
		return text;
	}
}
