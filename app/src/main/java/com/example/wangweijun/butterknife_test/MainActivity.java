package com.example.wangweijun.butterknife_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String UTF_8 = "UTF-8";

    @BindView(R.id.tv)
    TextView tv;

    @BindView(R.id.et)
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 一个emoji表情对应着一个unicode编码
                Log.i("wang", "new line :   " + s.toString());// abc你好😁👿
                tv.setText(s.toString());

                try {
                    String encodeValue = URLEncoder.encode(s.toString(), UTF_8);
                    Log.i("wang", "encodeValue:"+encodeValue);

                    String deodeValue = URLDecoder.decode(encodeValue, UTF_8);

                    Log.i("wang", "deodeValue:"+deodeValue);




                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("wang", "############");
//                String rs = chineseToUnicode(s.toString());
//                Log.i("wang", "unicode: "+rs.trim());
//                Log.i("wang", "chinese: "+UnicodeTochinese(rs.trim()));


               String  stringToUnicode = stringToUnicode(s.toString());
                Log.i("wang", "stringToUnicode: "+stringToUnicode);
                String unicodeToString = unicodeToString(stringToUnicode);
                Log.i("wang", "unicodeToString: "+unicodeToString);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 你好aaa😄
        // content=%E4%BD%A0%E5%A5%BDaaa%F0%9F%98%84&packagename=com.tencent.qqpimsecure
//                String value = "你好aaa\uD83D\uDE04";



        setEmojiToTextView();

    }


    private void setEmojiToTextView(){
        int unicodeJoy = 0x1F602;
        String emojiString = getEmojiStringByUnicode(unicodeJoy);
        tv.setText(emojiString);
    }

    private String getEmojiStringByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }



    /**
     * 中文转unicode
     * @param  str
     * @return unicode
     */
    public String  chineseToUnicode(String str)
    {
        String result = "";
        for (int i = 0; i < str.length(); i++)
        {
            int chr1 = (char) str.charAt(i);
            result  += "\\u" + Integer.toHexString(chr1);
        }
        return result;
    }
    /**
     * unicode转中文
     * @return 中文
     */
    public static String UnicodeTochinese(String dataStr) {
        int index = 0;
        StringBuffer buffer = new StringBuffer();

        while(index<dataStr.length()) {
            if(!"\\u".equals(dataStr.substring(index,index+2))){
                buffer.append(dataStr.charAt(index));
                index++;
                continue;
            }
            String charStr = "";
            charStr = dataStr.substring(index+2,index+6);
            char letter = (char) Integer.parseInt(charStr, 16 );
            buffer.append(letter);
            index+=6;
        }
        return buffer.toString();
    }



    /**
     * 将utf-8的汉字转换成unicode格式汉字码
     * @param string
     * @return
     */
    public static String stringToUnicode(String string) {

        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            unicode.append("\\u" + Integer.toHexString(c));
        }
        String str = unicode.toString();

        return str.replaceAll("\\\\", "0x");
    }

    /**
     * 将unicode的汉字码转换成utf-8格式的汉字
     * @param unicode
     * @return
     */
    public static String unicodeToString(String unicode) {

        String str = unicode.replace("0x", "\\");

        StringBuffer string = new StringBuffer();
        String[] hex = str.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }
        return string.toString();
    }

}
