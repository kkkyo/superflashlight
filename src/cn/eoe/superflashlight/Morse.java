package cn.eoe.superflashlight;

import java.util.HashMap;
import java.util.Map;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Morse extends WarningLight{

	private final int DOT_TIME = 200;                //  ��ͣ����ʱ��
	private final int LINE_TIME = DOT_TIME *3;       //  ��ͣ����ʱ��
	private final int DOT_LINE_TIME = DOT_TIME;      //  �㵽�ߵ�ʱ����
	
	private final int CHAR_CHAR_TIME = DOT_TIME *3;  //�ַ����ַ�֮���ʱ����
	
	private final int WORD_WORD_TIME = DOT_TIME *7;  //���ʵ�����֮���ʱ����
	
	private String mMorseCode;
	
	private Map<Character, String> mMorseCodeMap = new HashMap<Character, String>();   //ӳ�䣬�������浽������
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mMorseCodeMap.put('a', ".-");
		mMorseCodeMap.put('b', "-...");
		mMorseCodeMap.put('c', "-.-.");
		mMorseCodeMap.put('d', "-..");
		mMorseCodeMap.put('e', ".");
		mMorseCodeMap.put('f', "..-.");
		mMorseCodeMap.put('g', "--.");
		mMorseCodeMap.put('h', "....");
		mMorseCodeMap.put('i', "..");
		mMorseCodeMap.put('j', ".---");
		mMorseCodeMap.put('k', "-.-");
		mMorseCodeMap.put('l', ".-..");
		mMorseCodeMap.put('m', "--");
		mMorseCodeMap.put('n', "-.");
		mMorseCodeMap.put('o', "---");
		mMorseCodeMap.put('p', ".--.");
		mMorseCodeMap.put('q', "--.-");
		mMorseCodeMap.put('r', ".-.");
		mMorseCodeMap.put('s', "...");
		mMorseCodeMap.put('t', "-");
		mMorseCodeMap.put('u', "..-");
		mMorseCodeMap.put('v', "...-");
		mMorseCodeMap.put('w', ".--");
		mMorseCodeMap.put('x', "-..-");
		mMorseCodeMap.put('y', "-.--");
		mMorseCodeMap.put('z', "--..");
		
		mMorseCodeMap.put('1', ".---");
		mMorseCodeMap.put('2', "..---");
		mMorseCodeMap.put('3', "...--");
		mMorseCodeMap.put('4', "....-");
		mMorseCodeMap.put('5', ".....");
		mMorseCodeMap.put('6', "-....");
		mMorseCodeMap.put('7', "--...");
		mMorseCodeMap.put('8', "---..");
		mMorseCodeMap.put('9', "----.");
		mMorseCodeMap.put('0', "-----");
	}
	
	public void onClick_SendMorseCode(View view)
	{
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA_FLASH)) {
			Toast.makeText(this, "��������ֻ�û����ͷ(T_T)", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(verifyMorseCode())
		{
			sendSentense(mMorseCode);
		}
	}
	
	private void sleep(long t)
	{
		try {
			Thread.sleep(t);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//���͵�
	private void sendDot()
	{
		openFlashlight();
		sleep(DOT_TIME);
		closeFlashlight();
	}
	
	//������
	private void sendLine()
	{
		openFlashlight();
		sleep(LINE_TIME);
		closeFlashlight();
	}
	
	//�����ַ�
	private void sendChar(char c)
	{
		String morseCode = mMorseCodeMap.get(c);
		if(morseCode != null)
		{
			char lastChar = ' ';
			for(int i = 0; i<morseCode.length(); i++)
			{
				char dotline = morseCode.charAt(i);
				if(dotline == '.')
				{
					sendDot();
				}
				else if(dotline == '-')
				{
					sendLine();
				}
				if(i>0 && i<mMorseCode.length() - 1)
				{
					if(lastChar == '.' && dotline == '-')
					{
						sleep(DOT_LINE_TIME);
					}
				}
				lastChar = dotline;
			}
		}
	}
	
	private void sendWord(String s)
	{
		for(int i = 0; i<s.length(); i++)
		{
			char c = s.charAt(i);
			sendChar(c);
			if(i < s.length() -1)
			{
				sleep(CHAR_CHAR_TIME);
			}
		}
	}
	
	private void sendSentense(String s)
	{
		String[] words = s.split(" +");
		for(int i=0; i<words.length; i++)
		{
			sendWord(words[i]);
			if(i < words.length - 1)
			{
				sleep(WORD_WORD_TIME);
			}
		}
		Toast.makeText(this, "Ī��˹�����Ѿ�������ϣ�", Toast.LENGTH_LONG).show();
	}
	
	private boolean verifyMorseCode()
	{
		mMorseCode = mEditTextMorseCode.getText().toString().toLowerCase();
		if("".equals(mMorseCode))
		{
			Toast.makeText(this, "������Ī��˹����", Toast.LENGTH_LONG).show();
			return false;
		}
		
		for(int i = 0; i<mMorseCode.length(); i++)
		{
			char c = mMorseCode.charAt(i);
			if(!(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9') && c != ' ')
			{
				Toast.makeText(this, "Ī��˹����ֻ�������ֻ���ĸ", Toast.LENGTH_LONG).show();
				return false;
			}
		}
		
		return true;
	}
}
